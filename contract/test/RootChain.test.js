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

        describe("with appropriate values", () => {
            it("should create a new plasma block", () => {
                return RootChain.deployed()
                    .then(async instance => {
                        await instance.deposit({
                            from: accounts[0],
                            value: web3.utils.toWei("0.001")
                        })

                        const root = await instance.plasmaBlocks__root(1)
                        assert.notEqual(root, web3.utils.bytesToHex(new Array(32)))

                        const nextDepositBlockNumber = (await instance.nextDepositBlockNumber()).toNumber()
                        assert.equal(nextDepositBlockNumber, 2 )
                    })
            })

            it("should emit a DepositCreated event", () => {
                return RootChain.deployed()
                    .then(async instance => {
                        const result = await instance.deposit({
                            from: accounts[0],
                            value: web3.utils.toWei("0.001")
                        })

                        assert.equal(result.logs[0].event, "DepositCreated")
                    })
            })
        })
    })
})