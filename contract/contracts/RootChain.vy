Deposited: event({
    _depositer: address,
    _amount: uint256,
    _depositBlockNumber: uint256
})

currentDepositBlockNumber: public(uint256)

# @dev Constructor
@public
def __init():
    self.currentDepositBlockNumber = 1

@public
@payable
def deposit():
    assert msg.value > 0
    self.currentDepositBlockNumber += 1
    log.Deposited(msg.sender, as_unitless_number(msg.value), self.currentDepositBlockNumber)