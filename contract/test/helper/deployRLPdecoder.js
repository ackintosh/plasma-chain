const utils = require('ethereumjs-util')
const assert = require('assert')

// ref: https://github.com/ethereum/vyper/blob/master/tests/parser/functions/rlp/conftest.py#L15-L20
// ref: https://github.com/cryptoeconomicslab/plasma-predicates/blob/master/test/helpers/deployRLPdecoder.js
async function deployRLPdecoder(address) {
  try {
    // ref: https://github.com/ethereum/vyper/blob/master/docs/built-in-functions.rst#rlplist
    const deployTx =
      '0xf90237808506fc23ac00830330888080b902246102128061000e60003961022056600060007f010000000000000000000000000000000000000000000000000000000000000060003504600060c082121515585760f882121561004d5760bf820336141558576001905061006e565b600181013560f783036020035260005160f6830301361415585760f6820390505b5b368112156101c2577f010000000000000000000000000000000000000000000000000000000000000081350483602086026040015260018501945060808112156100d55760018461044001526001828561046001376001820191506021840193506101bc565b60b881121561014357608081038461044001526080810360018301856104600137608181141561012e5760807f010000000000000000000000000000000000000000000000000000000000000060018401350412151558575b607f81038201915060608103840193506101bb565b60c08112156101b857600182013560b782036020035260005160388112157f010000000000000000000000000000000000000000000000000000000000000060018501350402155857808561044001528060b6838501038661046001378060b6830301830192506020810185019450506101ba565bfe5b5b5b5061006f565b601f841315155857602060208502016020810391505b6000821215156101fc578082604001510182826104400301526020820391506101d8565b808401610420528381018161044003f350505050505b6000f31b2d4f'
    const RLP_DECODER_ADDRESS = '0xCb969cAAad21A78a24083164ffa81604317Ab603'
    const to = '0xd2c560282c9C02465C2dAcdEF3E859E730848761'

    await web3.eth.sendTransaction({
      from: address,
      to: to,
      value: 10 ** 17
    })

    const receipt = await web3.eth.sendSignedTransaction(deployTx)
    const rlpDecoderAddr = utils.toChecksumAddress(receipt.contractAddress)

    // Ensure the address that RLPdecoder has been deployed equals to the expected one.
    assert(rlpDecoderAddr == RLP_DECODER_ADDRESS)

    return rlpDecoderAddr
  } catch (e) {
    if (e.message.indexOf('Returned error') < 0) {
      console.error(e)
    }
    return e
  }
}

module.exports = {
  deployRLPdecoder
}