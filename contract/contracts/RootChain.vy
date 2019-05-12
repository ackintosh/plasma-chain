# Structs
struct PlasmaBlock:
    root: bytes32
    blockNumber: uint256

struct Exit:
    owner: address
    amount: uint256

# External Contracts
contract PriorityQueue:
    def minChild(_i: uint256) -> uint256: constant
    def insert(_k: uint256) -> bool: modifying
    def getMin() -> uint256: constant
    def delMin() -> uint256: modifying
    def getCurrentSize() -> uint256: constant
    def dd(): modifying

# Events
DepositCreated: event({
    owner: address,
    amount: uint256,
    blockNumber: uint256
})

BlockSubmitted: event({
    blockRoot: bytes32
})

ExitStarted: event({
    owner: address,
    blockNumber: uint256,
    txIndex: uint256,
    outputIndex: uint256
})

# Storage veriables
operator: address
exitQueue: address
plasmaBlocks: public(map(uint256, PlasmaBlock)) # "public" is just for debugging
currentPlasmaBlockNumber: public(uint256)
nextDepositBlockNumber: public(uint256)
exits: public(map(uint256, Exit)) # "public" is just for debugging

PLASMA_BLOCK_NUMBER_INTERVAL: constant(uint256) = 1000
INITIAL_DEPOSIT_BLOCK_NUMBER: constant(uint256) = 1
TOKEN_ID: constant(uint256) = 0
EXIT_PERIOD_SECONDS: constant(uint256) = 1 * 7 * 24 * 60 * 60 # 1 week

# @dev Constructor
@public
def __init__(_exitQueue: address):
    self.operator = msg.sender
    self.exitQueue = _exitQueue
    self.currentPlasmaBlockNumber = 0
    self.nextDepositBlockNumber = INITIAL_DEPOSIT_BLOCK_NUMBER

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
    self.plasmaBlocks[depositBlocknumber] = PlasmaBlock({
        root: root,
        blockNumber: depositBlocknumber
    })
    self.nextDepositBlockNumber += 1
    log.DepositCreated(msg.sender, as_unitless_number(msg.value), depositBlocknumber)

# @dev submit plasma block
@public
def submit(blockRoot: bytes32, plasmaBlockNumber: uint256):
    assert msg.sender == self.operator
    self.plasmaBlocks[plasmaBlockNumber] = PlasmaBlock({
        root: blockRoot,
        blockNumber: plasmaBlockNumber
    })
    if plasmaBlockNumber > self.currentPlasmaBlockNumber:
        self.currentPlasmaBlockNumber = plasmaBlockNumber
    self.nextDepositBlockNumber = INITIAL_DEPOSIT_BLOCK_NUMBER
    log.BlockSubmitted(blockRoot)

# @dev exit
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

    # TODO: Validate signatures

    exitableAt: uint256 = as_unitless_number(block.timestamp) + EXIT_PERIOD_SECONDS
    priority: uint256 = bitwise_or(shift(exitableAt, 128), _txoBlockNumber)
    enqueued: bool = PriorityQueue(self.exitQueue).insert(priority)
    assert enqueued

    utxoPos: uint256 = (_txoBlockNumber * 1000000000) + (_txoTxIndex * 10000) + _txoOutputIndex
    self.exits[utxoPos] = Exit({
        owner: msg.sender,
        amount: amount
    })
    log.ExitStarted(msg.sender, _txoBlockNumber, _txoTxIndex, _txoOutputIndex)

@private
def getNextExit() -> (uint256, uint256):
    priority: uint256 = PriorityQueue(self.exitQueue).getMin()
    utxoPos: uint256 = shift(shift(priority, 128), -128)
    exitableAt: uint256 = shift(priority, -128)
    return utxoPos, exitableAt

@public
@payable
def processExits() -> uint256:
    utxoPos: uint256
    exitableAt: uint256
    (utxoPos, exitableAt) = self.getNextExit()
    processingExit: Exit
    processed: uint256 = 0
    for i in range(1073741824):
        if not exitableAt < as_unitless_number(block.timestamp):
            break
        
        processingExit = self.exits[utxoPos]
        send(processingExit.owner, as_wei_value(processingExit.amount, "wei"))

        # Delete the exit from exit queue
        PriorityQueue(self.exitQueue).delMin()
        # Delete owner of the utxo
        self.exits[utxoPos].owner = ZERO_ADDRESS
        processed += 1
        if PriorityQueue(self.exitQueue).getCurrentSize() > 0:
            (utxoPos, exitableAt) = self.getNextExit()
        else:
            return processed
    return processed