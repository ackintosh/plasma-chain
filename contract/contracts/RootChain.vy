###### Structs ######
struct PlasmaBlock:
    root: bytes32
    # "timestamp" is a reserved keyword (EVM opcode)
    blockTimestamp: uint256

struct PlasmaExit:
    owner: address
    amount: uint256
    # There's "isActive" property in Plasma MVP Specification on www.learnplasma.org
    # but it's not refer to how the property is used. 
    # isActive: bool
    isBlocked: bool

###### External Contracts ######
contract PriorityQueue:
    def minChild(_i: uint256) -> uint256: constant
    def insert(_k: uint256) -> bool: modifying
    def getMin() -> uint256: constant
    def delMin() -> uint256: modifying
    def getCurrentSize() -> uint256: constant
    def dd(): modifying

###### Events ######
# DepositCreated MUST be emitted whenever a deposit is created.
DepositCreated: event({
    owner: address,
    amount: uint256,
    blockNumber: uint256
})

# BlockSubmitted MUST be emitted whenever a block root is submitted.
BlockSubmitted: event({
    blockRoot: bytes32
})

# ExitStarted MUST be emitted whenever an exit is started.
ExitStarted: event({
    owner: address,
    blockNumber: uint256,
    txIndex: uint256,
    outputIndex: uint256
})

###### Storage veriables ######
# A priority queue of exits.
exitQueue: address
# Current Plasma chain block height. Should only ever be incremented so a block can’t be later rewritten.
currentPlasmaBlockNumber: public(uint256)
# Address of the operator. Although the operator does not necessarily need to remain constant, it’s likely easier if this is the case.
operator: address
# A mapping from block number to PlasmaBlock structs that represent each block. Should only be modified when the operator calls SubmitBlock.
plasmaBlocks: public(map(uint256, PlasmaBlock)) # "public" is just for debugging
# A mapping from exit IDs to PlasmaExit structs, to be modified when users start or challenge exits.
exits: public(map(uint256, PlasmaExit)) # "public" is just for debugging
nextDepositBlockNumber: public(uint256)

###### Storage constants ######
PLASMA_BLOCK_NUMBER_INTERVAL: constant(uint256) = 1000
INITIAL_DEPOSIT_BLOCK_NUMBER: constant(uint256) = 1
TOKEN_ID: constant(uint256) = 0
EXIT_PERIOD_SECONDS: constant(uint256) = 1 * 7 * 24 * 60 * 60 # 1 week
# Amount in ETH that must be provided as a bond when starting an exit.
EXIT_BOND: constant(uint256) = 1000

###### Methods ######

# @dev Constructor
@public
def __init__(_exitQueue: address):
    self.operator = msg.sender
    self.exitQueue = _exitQueue
    self.currentPlasmaBlockNumber = 0
    self.nextDepositBlockNumber = INITIAL_DEPOSIT_BLOCK_NUMBER

# Allows any user to deposit funds into the contract by attaching a value to the transaction.
@public
@payable
def deposit():
    assert msg.value > 0
    depositBlocknumber: uint256 = self.nextDepositBlockNumber + self.currentPlasmaBlockNumber
    root: bytes32 = sha3(
        concat(
            convert(msg.sender, bytes32),
            convert(TOKEN_ID, bytes32),
            convert(msg.value, bytes32)
        )
    )
    # MUST create a new PlasmaBlock consisting of a single transaction with an output of msg.value
    self.plasmaBlocks[depositBlocknumber] = PlasmaBlock({
        root: root,
        blockTimestamp: depositBlocknumber
    })
    self.nextDepositBlockNumber += 1
    # MUST emit DepositCreated.
    log.DepositCreated(msg.sender, as_unitless_number(msg.value), depositBlocknumber)

# Allows operator to submit the latest block root.
# @param _blockRoot - Root hash of the Merkle tree of transactions in the block.
@public
def submitBlock(_blockRoot: bytes32):
    # MUST check that msg.sender is operator.
    assert msg.sender == self.operator

    # MUST increment currentPlasmaBlockNumber by one.
    self.currentPlasmaBlockNumber += 1

    # MUST insert a new PlasmaBlock with _blockRoot and block.timestamp.
    self.plasmaBlocks[self.currentPlasmaBlockNumber] = PlasmaBlock({
        root: _blockRoot,
        blockTimestamp: as_unitless_number(block.timestamp)
    })

    self.nextDepositBlockNumber = INITIAL_DEPOSIT_BLOCK_NUMBER
    # MUST emit BlockSubmitted.
    log.BlockSubmitted(_blockRoot)

# Allows any user to attempt to withdraw funds from the contract by pointing to a transaction output.
# @param _txoBlockNumber - Block number in which the transaction output was created.
# @param _txoTxIndex - Index of the transaction inside the block.
# @param _txoOutputIndex - Index of the output inside the transaction (either 0 or 1).
# @param _encodedTx - RLP encoded transaction that created the output.
# @param _txInclusionProof - Merkle proof that _encodedTx was included at _txoBlockNumber and _txoTxIndex.
# @param _txSignatures - Initial signatures by the owners of each input to the transaction.
# @param _txConfirmationSignatures - Confirmation signatures by the owners of each input to the transaction.
@public
@payable
def startExit(
    _txoBlockNumber: uint256,
    _txoTxIndex: uint256,
    _txoOutputIndex: uint256,
    _encodedTx: bytes32, # TODO
    _txInclusionProof: bytes32, # TODO
    _txSignatures: bytes32, # TODO
    _txConfirmationSignatures: bytes32, # TODO
    amount: uint256
):
    # Check a bond is provided
    assert msg.value == EXIT_BOND

    # Check the block number is a deposit
    assert _txoBlockNumber % PLASMA_BLOCK_NUMBER_INTERVAL != 0

    # Validate the given owner and amount
    requestRoot: bytes32 = sha3(
        concat(
            convert(msg.sender, bytes32),
            convert(TOKEN_ID, bytes32),
            convert(amount, bytes32)
        )
    )
    assert requestRoot == self.plasmaBlocks[_txoBlockNumber].root

    # TODO: Check msg.sender is the same as the exitor
    # see https://github.com/ackintosh/plasma-chain/pull/35
    # - decode the _encodedTx parameter with RLP decoder
    # - check the decoded exitor address is equal to msg.sender

    # TODO: Validate merkle proof
    # TODO: MUST use _txInclusionProof to check that _encodedTx was included in the Plasma chain at _txoBlockNumber and _txoTxIndex.

    # TODO: Validate signatures
    # TODO: MUST check that msg.sender owns the output of _encodedTx given by _txoOutputIndex.

    exitableAt: uint256 = as_unitless_number(block.timestamp) + EXIT_PERIOD_SECONDS
    priority: uint256 = bitwise_or(shift(exitableAt, 128), _txoBlockNumber)
    enqueued: bool = PriorityQueue(self.exitQueue).insert(priority)
    assert enqueued

    utxoPos: uint256 = (_txoBlockNumber * 1000000000) + (_txoTxIndex * 10000) + _txoOutputIndex
    self.exits[utxoPos] = PlasmaExit({
        owner: msg.sender,
        amount: amount,
        isBlocked: False
    })
    # MUST emit ExitStarted.
    log.ExitStarted(msg.sender, _txoBlockNumber, _txoTxIndex, _txoOutputIndex)

@private
def getNextExit() -> (uint256, uint256):
    priority: uint256 = PriorityQueue(self.exitQueue).getMin()
    utxoPos: uint256 = shift(shift(priority, 128), -128)
    exitableAt: uint256 = shift(priority, -128)
    return utxoPos, exitableAt

# Pays out any exits that have passed their challenge period.
# @return - Number of exits processed by this call.
@public
@payable
def processExits() -> uint256:
    utxoPos: uint256
    exitableAt: uint256
    processingExit: PlasmaExit
    processed: uint256 = 0
    # MUST process exits in priority order, based on minimum of exitQueue.
    for i in range(1073741824):
        (utxoPos, exitableAt) = self.getNextExit()
        if not exitableAt < as_unitless_number(block.timestamp):
            break
        
        processingExit = self.exits[utxoPos]
        # MUST NOT pay any withdrawals where isBlocked is true.
        if processingExit.isBlocked:
            continue
        send(processingExit.owner, as_wei_value(processingExit.amount + EXIT_BOND, "wei"))

        # Delete the exit from exit queue
        PriorityQueue(self.exitQueue).delMin()
        # Delete owner of the utxo
        self.exits[utxoPos].owner = ZERO_ADDRESS
        processed += 1
        if PriorityQueue(self.exitQueue).getCurrentSize() <= 0:
            return processed
    return processed

# Allows any user to prove that a given exit is invalid.
# @param _exitingTxoBlockNumber - Block in which the exiting output was created.
# @param _exitingTxoTxIndex - Index of the transaction (within the block) that created the exiting output.
# @param _exitingTxoOutputIndex - Index of the exiting output within the transaction that created it (either 0 or 1).
# @param _encodedSpendingTx - RLP encoded transaction that spends the exiting output.
# @param _spendingTxConfirmationSignature - Confirmation signature by the owner of the exiting output over _encodedSpendingTx.
# @return  - true if the challenge was successful, false otherwise.
@public
def challengeExit(
    _exitingTxoBlockNumber: uint256,
    _exitingTxoTxIndex: uint256,
    _exitingTxoOutputIndex: uint256,
    _encodedSpendingTx: bytes32,
    _spendingTxConfirmationSignature: bytes32
) -> bool:
    # TODO: MUST check that _encodedSpendingTx spends the specified output.
    # TODO: MUST check that _spendingTxConfirmationSignature is correctly signed by the owner of the PlasmaExit.

    # MUST block the PlasmaExit by setting isBlocked to true if the above conditions pass.
    utxoPos: uint256 = (_exitingTxoBlockNumber * 1000000000) + (_exitingTxoTxIndex * 10000) + _exitingTxoOutputIndex
    self.exits[utxoPos].isBlocked = True

    send(msg.sender, as_wei_value(EXIT_BOND, "wei"))
    return True