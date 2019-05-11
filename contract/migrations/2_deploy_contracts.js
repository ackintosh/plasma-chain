const RootChain = artifacts.require("RootChain");
const PriorityQueue = artifacts.require("PriorityQueue");

module.exports = (deployer, network, accounts) => {
  let priorityQueue
  deployer.deploy(PriorityQueue, {from: accounts[1]})
  .then((_priorityChain) => {
    priorityQueue = _priorityChain
    return deployer.deploy(
      RootChain,
      PriorityQueue.address,
      { from: accounts[1] }
    )
  })
  .then(() => priorityQueue.setRootChain(RootChain.address, {from: accounts[1]}))
};