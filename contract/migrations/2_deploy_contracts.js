const RootChain = artifacts.require("RootChain");
const PriorityQueue = artifacts.require("PriorityQueue");

module.exports = (deployer, network, accounts) => {
  deployer.deploy(PriorityQueue)
  .then(() => deployer.deploy(
    RootChain,
    PriorityQueue.address,
    {from: accounts[1]}
  ))
};