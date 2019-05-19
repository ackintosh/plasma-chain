package com.github.ackintosh.plasmamvp.node.web3j;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.2.0.
 */
public class RootChain extends Contract {
    private static final String BINARY = "0x600035601c52740100000000000000000000000000000000000000006020526f7fffffffffffffffffffffffffffffff6040527fffffffffffffffffffffffffffffffff8000000000000000000000000000000060605274012a05f1fffffffffffffffffffffffffdabf41c006080527ffffffffffffffffffffffffed5fa0e000000000000000000000000000000000060a05260206111bd6101403934156100a757600080fd5b60206111bd60c03960c05160205181106100c057600080fd5b503360025561014051600055600060015560016005556111a556600035601c52740100000000000000000000000000000000000000006020526f7fffffffffffffffffffffffffffffff6040527fffffffffffffffffffffffffffffffff8000000000000000000000000000000060605274012a05f1fffffffffffffffffffffffffdabf41c006080527ffffffffffffffffffffffffed5fa0e000000000000000000000000000000000060a05263d0e30db060005114156101a057600034116100ae57600080fd5b6005546001546005540110156100c357600080fd5b6001546005540161014052600033602082610180010152602081019050600060208261018001015260208101905034602082610180010152602081019050806101805261018090508051602082012090506101605260036101405160e05260c052604060c02060c052602060c02061016051815561014051600182015550600580546001825401101561015557600080fd5b600181540181555034610220523361024052610220516102605261014051610280527f72235260b946c311b2455bc3db7b029180adc32d36d49946ccb668abcd1a1a196060610240a1005b63baa47694600051141561024857602060046101403734156101c157600080fd5b60025433146101cf57600080fd5b60018054600182540110156101e357600080fd5b6001815401815550600360015460e05260c052604060c02060c052602060c020610140518155600154600182015550600160055561014051610160527f38ced01204841ece6186d0d51d96712da8d7553e3ef7cef2394bc185506ec03d6020610160a1005b63ba88245060005114156107b2576101006004610140376103e8341461026d57600080fd5b60006103e861027b57600080fd5b6103e86101405106141561028e57600080fd5b600033602082610260010152602081019050600060208261026001015260208101905061022051602082610260010152602081019050806102605261026090508051602082012090506102405260036101405160e05260c052604060c02060c052602060c02054610240511461030357600080fd5b42610320526103205162093a806103205101101561032057600080fd5b62093a8061032051016103005261014051610300516080600081121561034e578060000360020a8204610355565b8060020a82025b9050905017610340526000543b61036b57600080fd5b60005430141561037a57600080fd5b602061040060246390b5561d61038052610340516103a05261039c60006000545af16103a557600080fd5b6000506104005161036052610360516103bd57600080fd5b6101405115156103ce5760006103f7565b633b9aca0061014051633b9aca00610140510204146103ec57600080fd5b633b9aca0061014051025b61016051151561040857600061042b565b612710610160516127106101605102041461042257600080fd5b61271061016051025b61014051151561043c576000610465565b633b9aca0061014051633b9aca006101405102041461045a57600080fd5b633b9aca0061014051025b01101561047157600080fd5b6101605115156104825760006104a5565b612710610160516127106101605102041461049c57600080fd5b61271061016051025b6101405115156104b65760006104df565b633b9aca0061014051633b9aca00610140510204146104d457600080fd5b633b9aca0061014051025b01610180516101405115156104f557600061051e565b633b9aca0061014051633b9aca006101405102041461051357600080fd5b633b9aca0061014051025b61016051151561052f576000610552565b612710610160516127106101605102041461054957600080fd5b61271061016051025b61014051151561056357600061058c565b633b9aca0061014051633b9aca006101405102041461058157600080fd5b633b9aca0061014051025b01101561059857600080fd5b6101605115156105a95760006105cc565b61271061016051612710610160510204146105c357600080fd5b61271061016051025b6101405115156105dd576000610606565b633b9aca0061014051633b9aca00610140510204146105fb57600080fd5b633b9aca0061014051025b0101101561061357600080fd5b61018051610140511515610628576000610651565b633b9aca0061014051633b9aca006101405102041461064657600080fd5b633b9aca0061014051025b610160511515610662576000610685565b612710610160516127106101605102041461067c57600080fd5b61271061016051025b6101405115156106965760006106bf565b633b9aca0061014051633b9aca00610140510204146106b457600080fd5b633b9aca0061014051025b0110156106cb57600080fd5b6101605115156106dc5760006106ff565b61271061016051612710610160510204146106f657600080fd5b61271061016051025b610140511515610710576000610739565b633b9aca0061014051633b9aca006101405102041461072e57600080fd5b633b9aca0061014051025b01016104205260046104205160e05260c052604060c02060c052602060c0203381556102205160018201556000600282015550336104405261014051610460526101605161048052610180516104a0527f41fb391842d4655dcf9e201494553582c224a4026944a79630a163459b2586da6080610440a1005b600015610918575b610140526000543b6107cb57600080fd5b6000543014156107da57600080fd5b60206101e0600463d6362e976101805261019c6000545afa6107fb57600080fd5b6000506101e051610160526101605160806000811215610823578060000360020a820461082a565b8060020a82025b905090507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff806000811215610866578060000360020a820461086d565b8060020a82025b9050905061020052610160517fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8060008112156108b1578060000360020a82046108b8565b8060020a82025b90509050610220526040610260526102806102005181526102205181602001525061026051610240525b6000610240511115156108f457610910565b60206102405103610280015160206102405103610240526108e2565b610140515650005b6358189e036000511415610b305760006101e05261020060006340000000818352015b6101405161016051610180516101a0516101c0516101e05161020051600658016107ba565b6102205261024052610200526101e0526101c0526101a0526101805261016052610140526102208051610140526020810151610160525042610160511015156109a857610b20565b61018060046101405160e05260c052604060c0208060c052602060c02054825260018160c052602060c0200154826020015260028160c052602060c0200154826040015250506101c051156109fc57610b10565b600060006000600060016101a0516103e86101a051011015610a1d57600080fd5b6103e86101a0510102610180516000f1610a3657600080fd5b6000543b610a4357600080fd5b600054301415610a5257600080fd5b60206102c0600463b07576ac6102605261027c60006000545af1610a7557600080fd5b6000506102c050600060046101405160e05260c052604060c02060c052602060c020556101e0805160018251011015610aad57600080fd5b600181510181525060006000543b610ac457600080fd5b600054301415610ad357600080fd5b60206103406004631759f9206102e0526102fc6000545afa610af457600080fd5b60005061034051111515610b0f576101e05160005260206000f35b5b815160010180835281141561093b575b50506101e05160005260206000f3005b632191ac436000511415610f165760a06004610140373415610b5157600080fd5b610140511515610b62576000610b8b565b633b9aca0061014051633b9aca0061014051020414610b8057600080fd5b633b9aca0061014051025b610160511515610b9c576000610bbf565b6127106101605161271061016051020414610bb657600080fd5b61271061016051025b610140511515610bd0576000610bf9565b633b9aca0061014051633b9aca0061014051020414610bee57600080fd5b633b9aca0061014051025b011015610c0557600080fd5b610160511515610c16576000610c39565b6127106101605161271061016051020414610c3057600080fd5b61271061016051025b610140511515610c4a576000610c73565b633b9aca0061014051633b9aca0061014051020414610c6857600080fd5b633b9aca0061014051025b0161018051610140511515610c89576000610cb2565b633b9aca0061014051633b9aca0061014051020414610ca757600080fd5b633b9aca0061014051025b610160511515610cc3576000610ce6565b6127106101605161271061016051020414610cdd57600080fd5b61271061016051025b610140511515610cf7576000610d20565b633b9aca0061014051633b9aca0061014051020414610d1557600080fd5b633b9aca0061014051025b011015610d2c57600080fd5b610160511515610d3d576000610d60565b6127106101605161271061016051020414610d5757600080fd5b61271061016051025b610140511515610d71576000610d9a565b633b9aca0061014051633b9aca0061014051020414610d8f57600080fd5b633b9aca0061014051025b01011015610da757600080fd5b61018051610140511515610dbc576000610de5565b633b9aca0061014051633b9aca0061014051020414610dda57600080fd5b633b9aca0061014051025b610160511515610df6576000610e19565b6127106101605161271061016051020414610e1057600080fd5b61271061016051025b610140511515610e2a576000610e53565b633b9aca0061014051633b9aca0061014051020414610e4857600080fd5b633b9aca0061014051025b011015610e5f57600080fd5b610160511515610e70576000610e93565b6127106101605161271061016051020414610e8a57600080fd5b61271061016051025b610140511515610ea4576000610ecd565b633b9aca0061014051633b9aca0061014051020414610ec257600080fd5b633b9aca0061014051025b01016101e0526001600260046101e05160e05260c052604060c02060c052602060c020015560006000600060006103e8336000f1610f0a57600080fd5b600160005260206000f3005b63a732668e6000511415610f3c573415610f2f57600080fd5b60015460005260206000f3005b63dbd2ad726000511415610f815760206004610140373415610f5d57600080fd5b60036101405160e05260c052604060c02060c052602060c0205460005260206000f3005b63b557a7846000511415610fc95760206004610140373415610fa257600080fd5b600160036101405160e05260c052604060c02060c052602060c020015460005260206000f3005b63388d0d70600051141561100e5760206004610140373415610fea57600080fd5b60046101405160e05260c052604060c02060c052602060c0205460005260206000f3005b638ce805956000511415611056576020600461014037341561102f57600080fd5b600160046101405160e05260c052604060c02060c052602060c020015460005260206000f3005b63a82c9375600051141561109e576020600461014037341561107757600080fd5b600260046101405160e05260c052604060c02060c052602060c020015460005260206000f3005b639f5d8e5360005114156110c45734156110b757600080fd5b60055460005260206000f3005b60006000fd5b6100db6111a5036100db6000396100db6111a5036000f3";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_SUBMITBLOCK = "submitBlock";

    public static final String FUNC_STARTEXIT = "startExit";

    public static final String FUNC_PROCESSEXITS = "processExits";

    public static final String FUNC_CHALLENGEEXIT = "challengeExit";

    public static final String FUNC_CURRENTPLASMABLOCKNUMBER = "currentPlasmaBlockNumber";

    public static final String FUNC_PLASMABLOCKS__ROOT = "plasmaBlocks__root";

    public static final String FUNC_PLASMABLOCKS__BLOCKNUMBER = "plasmaBlocks__blockNumber";

    public static final String FUNC_EXITS__OWNER = "exits__owner";

    public static final String FUNC_EXITS__AMOUNT = "exits__amount";

    public static final String FUNC_EXITS__ISBLOCKED = "exits__isBlocked";

    public static final String FUNC_NEXTDEPOSITBLOCKNUMBER = "nextDepositBlockNumber";

    public static final Event DEPOSITCREATED_EVENT = new Event("DepositCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BLOCKSUBMITTED_EVENT = new Event("BlockSubmitted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event EXITSTARTED_EVENT = new Event("ExitStarted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1557660506177", "0xF657ddece40De78D746eEc48254F5C6ae42B1998");
    }

    @Deprecated
    protected RootChain(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected RootChain(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected RootChain(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected RootChain(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<DepositCreatedEventResponse> getDepositCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DEPOSITCREATED_EVENT, transactionReceipt);
        ArrayList<DepositCreatedEventResponse> responses = new ArrayList<DepositCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositCreatedEventResponse typedResponse = new DepositCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.blockNumber = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DepositCreatedEventResponse> depositCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DepositCreatedEventResponse>() {
            @Override
            public DepositCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DEPOSITCREATED_EVENT, log);
                DepositCreatedEventResponse typedResponse = new DepositCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.blockNumber = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DepositCreatedEventResponse> depositCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSITCREATED_EVENT));
        return depositCreatedEventFlowable(filter);
    }

    public List<BlockSubmittedEventResponse> getBlockSubmittedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BLOCKSUBMITTED_EVENT, transactionReceipt);
        ArrayList<BlockSubmittedEventResponse> responses = new ArrayList<BlockSubmittedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BlockSubmittedEventResponse typedResponse = new BlockSubmittedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.blockRoot = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BlockSubmittedEventResponse> blockSubmittedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BlockSubmittedEventResponse>() {
            @Override
            public BlockSubmittedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BLOCKSUBMITTED_EVENT, log);
                BlockSubmittedEventResponse typedResponse = new BlockSubmittedEventResponse();
                typedResponse.log = log;
                typedResponse.blockRoot = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BlockSubmittedEventResponse> blockSubmittedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BLOCKSUBMITTED_EVENT));
        return blockSubmittedEventFlowable(filter);
    }

    public List<ExitStartedEventResponse> getExitStartedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(EXITSTARTED_EVENT, transactionReceipt);
        ArrayList<ExitStartedEventResponse> responses = new ArrayList<ExitStartedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ExitStartedEventResponse typedResponse = new ExitStartedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.blockNumber = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.txIndex = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.outputIndex = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ExitStartedEventResponse> exitStartedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ExitStartedEventResponse>() {
            @Override
            public ExitStartedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(EXITSTARTED_EVENT, log);
                ExitStartedEventResponse typedResponse = new ExitStartedEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.blockNumber = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.txIndex = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.outputIndex = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ExitStartedEventResponse> exitStartedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EXITSTARTED_EVENT));
        return exitStartedEventFlowable(filter);
    }

    public RemoteCall<TransactionReceipt> deposit(BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> submitBlock(byte[] _blockRoot) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SUBMITBLOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_blockRoot)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> startExit(BigInteger _txoBlockNumber, BigInteger _txoTxIndex, BigInteger _txoOutputIndex, byte[] _encodedTx, byte[] _txInclusionProof, byte[] _txSignatures, byte[] _txConfirmationSignatures, BigInteger amount, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STARTEXIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_txoBlockNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_txoTxIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(_txoOutputIndex), 
                new org.web3j.abi.datatypes.generated.Bytes32(_encodedTx), 
                new org.web3j.abi.datatypes.generated.Bytes32(_txInclusionProof), 
                new org.web3j.abi.datatypes.generated.Bytes32(_txSignatures), 
                new org.web3j.abi.datatypes.generated.Bytes32(_txConfirmationSignatures), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> processExits(BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PROCESSEXITS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> challengeExit(BigInteger _exitingTxoBlockNumber, BigInteger _exitingTxoTxIndex, BigInteger _exitingTxoOutputIndex, byte[] _encodedSpendingTx, byte[] _spendingTxConfirmationSignature) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHALLENGEEXIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_exitingTxoBlockNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_exitingTxoTxIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(_exitingTxoOutputIndex), 
                new org.web3j.abi.datatypes.generated.Bytes32(_encodedSpendingTx), 
                new org.web3j.abi.datatypes.generated.Bytes32(_spendingTxConfirmationSignature)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> currentPlasmaBlockNumber() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CURRENTPLASMABLOCKNUMBER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<byte[]> plasmaBlocks__root(BigInteger arg0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PLASMABLOCKS__ROOT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(arg0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<BigInteger> plasmaBlocks__blockNumber(BigInteger arg0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PLASMABLOCKS__BLOCKNUMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(arg0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> exits__owner(BigInteger arg0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EXITS__OWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(arg0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> exits__amount(BigInteger arg0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EXITS__AMOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(arg0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> exits__isBlocked(BigInteger arg0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EXITS__ISBLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(arg0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> nextDepositBlockNumber() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NEXTDEPOSITBLOCKNUMBER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static RootChain load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new RootChain(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static RootChain load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RootChain(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RootChain load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new RootChain(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static RootChain load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new RootChain(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<RootChain> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _exitQueue) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_exitQueue)));
        return deployRemoteCall(RootChain.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<RootChain> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _exitQueue) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_exitQueue)));
        return deployRemoteCall(RootChain.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<RootChain> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _exitQueue) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_exitQueue)));
        return deployRemoteCall(RootChain.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<RootChain> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _exitQueue) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_exitQueue)));
        return deployRemoteCall(RootChain.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class DepositCreatedEventResponse {
        public Log log;

        public String owner;

        public BigInteger amount;

        public BigInteger blockNumber;
    }

    public static class BlockSubmittedEventResponse {
        public Log log;

        public byte[] blockRoot;
    }

    public static class ExitStartedEventResponse {
        public Log log;

        public String owner;

        public BigInteger blockNumber;

        public BigInteger txIndex;

        public BigInteger outputIndex;
    }
}
