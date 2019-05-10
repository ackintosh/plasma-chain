struct PlasmaBlock:
    root: bytes32
    blockNumber: uint256

Deposited: event({
    _depositer: address,
    _amount: uint256,
    _depositBlockNumber: uint256
})

BlockSubmitted: event({
    _root: bytes32
})

plasmaBlocks: map(uint256, PlasmaBlock)
currentPlasmaBlockNumber: public(uint256)
nextDepositBlockNumber: public(uint256)
PLASMA_BLOCK_NUMBER_INTERVAL: constant(uint256) = 1000
INITIAL_DEPOSIT_BLOCK_NUMBER: constant(uint256) = 1

# @dev Constructor
@public
def __init():
    self.currentPlasmaBlockNumber = 0
    self.nextDepositBlockNumber = INITIAL_DEPOSIT_BLOCK_NUMBER

@public
@payable
def deposit():
    assert msg.value > 0
    depositBlocknumber: uint256 = self.nextDepositBlockNumber + self.currentPlasmaBlockNumber
    self.nextDepositBlockNumber += 1
    log.Deposited(msg.sender, as_unitless_number(msg.value), depositBlocknumber)

# @dev submit plasma block
@public
def submit(_root: bytes32, plasmaBlockNumber: uint256):
    # TODO: ensure msg.sender == operator
    self.plasmaBlocks[plasmaBlockNumber] = PlasmaBlock({
        root: _root,
        blockNumber: plasmaBlockNumber
    })
    if plasmaBlockNumber > self.currentPlasmaBlockNumber:
        self.currentPlasmaBlockNumber = plasmaBlockNumber
    self.nextDepositBlockNumber = INITIAL_DEPOSIT_BLOCK_NUMBER
    log.BlockSubmitted(_root)