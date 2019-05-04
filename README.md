# UTXO

(((WIP)))

### Deploy root chain contract

```sh
# Setup virtual Python environment
$ virtualenv -p python3.7 --no-site-packages ~/vyper-venv-python37 
$ source ~/vyper-venv-python37/bin/activate.fish

# Install Vyper
$ pip install vyper==0.1.0b9
```

```sh
# Deploy root chain contract
$ ganache-cli --mnemonic 'candy maple cake sugar pudding cream honey rich smooth crumble sweet treat'
$ cd contract
$ truffle migrate --network development
```

### Deposit

```sh
$ truffle console --network=development

truffle(development)> let instance = await RootChain.deployed()
truffle(development)> let accounts = await web3.eth.getAccounts()
truffle(development)> instance.deposit({from: accounts[0], value: web3.utils.toWei("0.001")})
```