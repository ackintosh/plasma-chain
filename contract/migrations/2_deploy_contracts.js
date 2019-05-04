const RootChain = artifacts.require("RootChain");

module.exports = (deployer, network, accounts) => {
  deployer.deploy(RootChain),
  {from: accounts[1]}
};