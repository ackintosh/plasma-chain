# Plasma MVP

(((WIP)))

This project is implementing [Minimal Viable Plasma](https://ethresear.ch/t/minimal-viable-plasma/426) according to [Plasma MVP Specification](https://www.learnplasma.org/en/resources/#plasma-mvp-specification), with lots of respect to [OmiseGO's research implementation](https://github.com/omisego/plasma-mvp) and [LayerX's Vyper implementation](https://github.com/LayerXcom/plasma-mvp-vyper).

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
$ ganache-cli --mnemonic 'candy maple cake sugar pudding cream honey rich smooth crumble sweet treat' --networkId 1557660506177
$ cd contract
$ truffle migrate --network local
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

### Deposit - Exit

```sh
$ cd contract
$ truffle console --network=local

truffle(local)> let accounts = await web3.eth.getAccounts()
truffle(local)> let rootChain = await RootChain.deployed()
truffle(local)> rootChain.deposit({from: accounts[0], value: web3.utils.toWei("0.001")})
truffle(local)> rootChain.startExit(1, 0, 0, "0x00", "0x00", "0x00", "0x00", web3.utils.toWei("0.001"), {from: accounts[0]})
truffle(local)> rootChain.processExits()
```

## :memo:

#### Generate smart contract wrapper with web3j

```sh
$ web3j truffle generate contract/build/contracts/RootChain.json --package com.github.ackintosh.plasmachain.node.web3j -o node/src/main/gen/
```

#### Deploy RLPencoder

```sh
$ cd contract
$ truffle exec helper/deployRLPdecoder.test.js  --network local
```
