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
    private static final String BINARY = "0x600035601c52740100000000000000000000000000000000000000006020526f7fffffffffffffffffffffffffffffff6040527fffffffffffffffffffffffffffffffff8000000000000000000000000000000060605274012a05f1fffffffffffffffffffffffffdabf41c006080527ffffffffffffffffffffffffed5fa0e000000000000000000000000000000000060a0526020610dba6101403934156100a757600080fd5b6020610dba60c03960c05160205181106100c057600080fd5b50336002556101405160005560006001556001600555610da256600035601c52740100000000000000000000000000000000000000006020526f7fffffffffffffffffffffffffffffff6040527fffffffffffffffffffffffffffffffff8000000000000000000000000000000060605274012a05f1fffffffffffffffffffffffffdabf41c006080527ffffffffffffffffffffffffed5fa0e000000000000000000000000000000000060a05263d0e30db060005114156101a057600034116100ae57600080fd5b6005546001546005540110156100c357600080fd5b6001546005540161014052600033602082610180010152602081019050600060208261018001015260208101905034602082610180010152602081019050806101805261018090508051602082012090506101605260036101405160e05260c052604060c02060c052602060c02061016051815561014051600182015550600580546001825401101561015557600080fd5b600181540181555034610220523361024052610220516102605261014051610280527f72235260b946c311b2455bc3db7b029180adc32d36d49946ccb668abcd1a1a196060610240a1005b6346ab67cb600051141561024357604060046101403734156101c157600080fd5b60025433146101cf57600080fd5b60036101605160e05260c052604060c02060c052602060c0206101405181556101605160018201555060015461016051111561020d57610160516001555b600160055561014051610180527f38ced01204841ece6186d0d51d96712da8d7553e3ef7cef2394bc185506ec03d6020610180a1005b63ba88245060005114156107985761010060046101403760006103e861026857600080fd5b6103e86101405106141561027b57600080fd5b600033602082610260010152602081019050600060208261026001015260208101905061022051602082610260010152602081019050806102605261026090508051602082012090506102405260036101405160e05260c052604060c02060c052602060c0205461024051146102f057600080fd5b42610320526103205162093a806103205101101561030d57600080fd5b62093a8061032051016103005261014051610300516080600081121561033b578060000360020a8204610342565b8060020a82025b9050905017610340526000543b61035857600080fd5b60005430141561036757600080fd5b602061040060246390b5561d61038052610340516103a05261039c60006000545af161039257600080fd5b6000506104005161036052610360516103aa57600080fd5b6101405115156103bb5760006103e4565b633b9aca0061014051633b9aca00610140510204146103d957600080fd5b633b9aca0061014051025b6101605115156103f5576000610418565b612710610160516127106101605102041461040f57600080fd5b61271061016051025b610140511515610429576000610452565b633b9aca0061014051633b9aca006101405102041461044757600080fd5b633b9aca0061014051025b01101561045e57600080fd5b61016051151561046f576000610492565b612710610160516127106101605102041461048957600080fd5b61271061016051025b6101405115156104a35760006104cc565b633b9aca0061014051633b9aca00610140510204146104c157600080fd5b633b9aca0061014051025b01610180516101405115156104e257600061050b565b633b9aca0061014051633b9aca006101405102041461050057600080fd5b633b9aca0061014051025b61016051151561051c57600061053f565b612710610160516127106101605102041461053657600080fd5b61271061016051025b610140511515610550576000610579565b633b9aca0061014051633b9aca006101405102041461056e57600080fd5b633b9aca0061014051025b01101561058557600080fd5b6101605115156105965760006105b9565b61271061016051612710610160510204146105b057600080fd5b61271061016051025b6101405115156105ca5760006105f3565b633b9aca0061014051633b9aca00610140510204146105e857600080fd5b633b9aca0061014051025b0101101561060057600080fd5b6101805161014051151561061557600061063e565b633b9aca0061014051633b9aca006101405102041461063357600080fd5b633b9aca0061014051025b61016051151561064f576000610672565b612710610160516127106101605102041461066957600080fd5b61271061016051025b6101405115156106835760006106ac565b633b9aca0061014051633b9aca00610140510204146106a157600080fd5b633b9aca0061014051025b0110156106b857600080fd5b6101605115156106c95760006106ec565b61271061016051612710610160510204146106e357600080fd5b61271061016051025b6101405115156106fd576000610726565b633b9aca0061014051633b9aca006101405102041461071b57600080fd5b633b9aca0061014051025b01016104205260046104205160e05260c052604060c02060c052602060c02033815561022051600182015550336104405261014051610460526101605161048052610180516104a0527f41fb391842d4655dcf9e201494553582c224a4026944a79630a163459b2586da6080610440a1005b6000156108fe575b610140526000543b6107b157600080fd5b6000543014156107c057600080fd5b60206101e0600463d6362e976101805261019c6000545afa6107e157600080fd5b6000506101e051610160526101605160806000811215610809578060000360020a8204610810565b8060020a82025b905090507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff80600081121561084c578060000360020a8204610853565b8060020a82025b9050905061020052610160517fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff806000811215610897578060000360020a820461089e565b8060020a82025b90509050610220526040610260526102806102005181526102205181602001525061026051610240525b6000610240511115156108da576108f6565b60206102405103610280015160206102405103610240526108c8565b610140515650005b6358189e036000511415610b5b576101405161016051600658016107a0565b610180526101a05261016052610140526101808051610140526020810151610160525060006102005261022060006340000000818352015b426101605110151561096657610b4b565b6101c060046101405160e05260c052604060c0208060c052602060c02054825260018160c052602060c020015482602001525050600060006000600060016101e051026101c0516000f16109b957600080fd5b6000543b6109c657600080fd5b6000543014156109d557600080fd5b60206102a0600463b07576ac6102405261025c60006000545af16109f857600080fd5b6000506102a050600060046101405160e05260c052604060c02060c052602060c02055610200805160018251011015610a3057600080fd5b600181510181525060006000543b610a4757600080fd5b600054301415610a5657600080fd5b60206103206004631759f9206102c0526102dc6000545afa610a7757600080fd5b600050610320511115610b2d576101405161016051610180516101a0516101c0516101e05161020051610220516102405161026051610280516102a0516102c0516102e0516103005161032051600658016107a0565b610340526103605261032052610300526102e0526102c0526102a05261028052610260526102405261022052610200526101e0526101c0526101a05261018052610160526101405261034080516101405260208101516101605250610b3a565b6102005160005260206000f35b5b8151600101808352811415610955575b50506102005160005260206000f3005b63a732668e6000511415610b81573415610b7457600080fd5b60015460005260206000f3005b63dbd2ad726000511415610bc65760206004610140373415610ba257600080fd5b60036101405160e05260c052604060c02060c052602060c0205460005260206000f3005b63b557a7846000511415610c0e5760206004610140373415610be757600080fd5b600160036101405160e05260c052604060c02060c052602060c020015460005260206000f3005b63388d0d706000511415610c535760206004610140373415610c2f57600080fd5b60046101405160e05260c052604060c02060c052602060c0205460005260206000f3005b638ce805956000511415610c9b5760206004610140373415610c7457600080fd5b600160046101405160e05260c052604060c02060c052602060c020015460005260206000f3005b639f5d8e536000511415610cc1573415610cb457600080fd5b60055460005260206000f3005b60006000fd5b6100db610da2036100db6000396100db610da2036000f3";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_SUBMITBLOCK = "submitBlock";

    public static final String FUNC_STARTEXIT = "startExit";

    public static final String FUNC_PROCESSEXITS = "processExits";

    public static final String FUNC_CURRENTPLASMABLOCKNUMBER = "currentPlasmaBlockNumber";

    public static final String FUNC_PLASMABLOCKS__ROOT = "plasmaBlocks__root";

    public static final String FUNC_PLASMABLOCKS__BLOCKNUMBER = "plasmaBlocks__blockNumber";

    public static final String FUNC_EXITS__OWNER = "exits__owner";

    public static final String FUNC_EXITS__AMOUNT = "exits__amount";

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
        _addresses.put("1557660506177", "0x4D2D24899c0B115a1fce8637FCa610Fe02f1909e");
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

    public RemoteCall<TransactionReceipt> submitBlock(byte[] blockRoot, BigInteger plasmaBlockNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SUBMITBLOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(blockRoot), 
                new org.web3j.abi.datatypes.generated.Uint256(plasmaBlockNumber)), 
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
