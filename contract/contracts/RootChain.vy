Deposited: event({
    _depositer: address,
    _amount: uint256,
    _depositBlockNumber: uint256
})

BlockSubmitted: event({
    _root: bytes32
})

currentPlasmaBlockNumber: public(uint256)
currentDepositBlockNumber: public(uint256)
PLASMA_BLOCK_NUMBER_INTERVAL: constant(uint256) = 1000

# @dev Constructor
@public
def __init():
    self.currentPlasmaBlockNumber = 0
    self.currentDepositBlockNumber = 1

@public
@payable
def deposit():
    assert msg.value > 0
    self.currentDepositBlockNumber += 1
    log.Deposited(msg.sender, as_unitless_number(msg.value), self.currentDepositBlockNumber)

# @dev submit plasma block
@public
def submit(_root: bytes32):
    # TODO: ensure msg.sender == operator
    # TODO: store plasma chain merkle root
    log.BlockSubmitted(_root)