const RootChain = artifacts.require("RootChain")

const assertReverted = async (promise) => {
    try {
        await promise
    } catch (e) {
        const revertFound = e.message.search('revert') >= 0;
        assert(revertFound, `Expected "revert", got ${e} instead`)
        return
    }
    assert.fail('Expected revert not received')
}

contract("RootChain", accounts => {
    describe("#deposit()", () => {
        describe("is called with 0 values", () => {
            it("should fails", () => {
                return RootChain.deployed()
                    .then(instance => assertReverted(
                        instance.deposit({
                            from: accounts[0],
                            value: 0
                        })
                    ))
            })
        })
    })
})