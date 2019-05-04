Deposited: event({
    _depositer: address,
    _amount: uint256
})

@public
@payable
def deposit():
    assert msg.value > 0
    log.Deposited(msg.sender, as_unitless_number(msg.value))