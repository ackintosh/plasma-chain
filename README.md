# Plasma Chain

(((WIP)))

This project is implementing [Minimal Viable Plasma](https://ethresear.ch/t/minimal-viable-plasma/426) at the moment but will step forward to a evolutionary protocol when it has done.

Plasma MVP [Milestone](https://github.com/ackintosh/plasma-chain/milestone/1)

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

### Run plasma chain

```sh
# cd /path/to/project-root
$ ./gradlew bootRun
...
...
> Task :web:bootRun

      ___                       ___           ___           ___           ___                    ___                         ___
     /\  \                     /\  \         /\__\         /\  \         /\  \                  /\  \          ___          /\  \
    /::\  \                   /::\  \       /:/ _/_       |::\  \       /::\  \                |::\  \        /\  \        /::\  \
   /:/\:\__\                 /:/\:\  \     /:/ /\  \      |:|:\  \     /:/\:\  \               |:|:\  \       \:\  \      /:/\:\__\
  /:/ /:/  /  ___     ___   /:/ /::\  \   /:/ /::\  \   __|:|\:\  \   /:/ /::\  \            __|:|\:\  \       \:\  \    /:/ /:/  /
 /:/_/:/  /  /\  \   /\__\ /:/_/:/\:\__\ /:/_/:/\:\__\ /::::|_\:\__\ /:/_/:/\:\__\          /::::|_\:\__\  ___  \:\__\  /:/_/:/  /
 \:\/:/  /   \:\  \ /:/  / \:\/:/  \/__/ \:\/:/ /:/  / \:\~~\  \/__/ \:\/:/  \/__/          \:\~~\  \/__/ /\  \ |:|  |  \:\/:/  /
  \::/__/     \:\  /:/  /   \::/__/       \::/ /:/  /   \:\  \        \::/__/                \:\  \       \:\  \|:|  |   \::/__/
   \:\  \      \:\/:/  /     \:\  \        \/_/:/  /     \:\  \        \:\  \                 \:\  \       \:\__|:|__|    \:\  \
    \:\__\      \::/  /       \:\__\         /:/  /       \:\__\        \:\__\                 \:\__\       \::::/__/      \:\__\
     \/__/       \/__/         \/__/         \/__/         \/__/         \/__/                  \/__/        ~~~~           \/__/
...
...
```

### Deposit

```sh
$ cd contract
$ truffle console --network=development

truffle(development)> let instance = await RootChain.deployed()
truffle(development)> let accounts = await web3.eth.getAccounts()
truffle(development)> instance.deposit({from: accounts[0], value: web3.utils.toWei("0.001")})
```

## :memo:

#### Generate smart contract wrapper with web3j

```sh
$ web3j truffle generate contract/build/contracts/RootChain.json --package com.github.ackintosh.plasmachain.node.web3j -o node/src/main/gen/
```

#### Deploy RLPencoder

```sh
$ cd contract
$ truffle exec helper/deployRLPdecoder.test.js  --network development
```
