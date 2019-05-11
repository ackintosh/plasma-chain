struct PlasmaBlock:
    root: bytes32
    blockNumber: uint256

struct Exit:
    owner: address
    amount: uint256
    exitableAt: uint256

DepositCreated: event({
    owner: address,
    amount: uint256,
    blockNumber: uint256
})

BlockSubmitted: event({
    blockRoot: bytes32
})

operator: address
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
def __init__():
    self.operator = msg.sender
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

# @dev exit deposit
@public
@payable
def exit(
    depositBlockNumber: uint256,
    amount: uint256
):
    # Check the block number is a deposit
    assert depositBlockNumber % PLASMA_BLOCK_NUMBER_INTERVAL != 0

    # Validate the given owner and amount
    requestRoot: bytes32 = sha3(
        concat(
            convert(msg.sender, bytes32),
            convert(TOKEN_ID, bytes32),
            convert(amount, bytes32)
        )
    )
    assert requestRoot == self.plasmaBlocks[depositBlockNumber].root

    exit: Exit = Exit({
        owner: msg.sender,
        amount: amount,
        exitableAt: as_unitless_number(block.timestamp) + EXIT_PERIOD_SECONDS
    })
    self.exits[depositBlockNumber] = exit