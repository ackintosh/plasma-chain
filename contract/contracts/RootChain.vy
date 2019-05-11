struct PlasmaBlock:
    root: bytes32
    blockNumber: uint256

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
PLASMA_BLOCK_NUMBER_INTERVAL: constant(uint256) = 1000
INITIAL_DEPOSIT_BLOCK_NUMBER: constant(uint256) = 1
TOKEN_ID: constant(uint256) = 0

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