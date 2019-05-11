package com.github.ackintosh.plasmachain.node.web3j;

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
    private static final String BINARY = "0x600035601c52740100000000000000000000000000000000000000006020526f7fffffffffffffffffffffffffffffff6040527fffffffffffffffffffffffffffffffff8000000000000000000000000000000060605274012a05f1fffffffffffffffffffffffffdabf41c006080527ffffffffffffffffffffffffed5fa0e000000000000000000000000000000000060a052602061068a6101403934156100a757600080fd5b602061068a60c03960c05160205181106100c057600080fd5b5033600055610140516001556000600355600160045561067256600035601c52740100000000000000000000000000000000000000006020526f7fffffffffffffffffffffffffffffff6040527fffffffffffffffffffffffffffffffff8000000000000000000000000000000060605274012a05f1fffffffffffffffffffffffffdabf41c006080527ffffffffffffffffffffffffed5fa0e000000000000000000000000000000000060a05263d0e30db060005114156101a057600034116100ae57600080fd5b6004546003546004540110156100c357600080fd5b6003546004540161014052600033602082610180010152602081019050600060208261018001015260208101905034602082610180010152602081019050806101805261018090508051602082012090506101605260026101405160e05260c052604060c02060c052602060c02061016051815561014051600182015550600480546001825401101561015557600080fd5b600181540181555034610220523361024052610220516102605261014051610280527f72235260b946c311b2455bc3db7b029180adc32d36d49946ccb668abcd1a1a196060610240a1005b63762ca848600051141561024357604060046101403734156101c157600080fd5b60005433146101cf57600080fd5b60026101605160e05260c052604060c02060c052602060c0206101405181556101605160018201555060035461016051111561020d57610160516003555b600160045561014051610180527f38ced01204841ece6186d0d51d96712da8d7553e3ef7cef2394bc185506ec03d6020610180a1005b632af9cc41600051141561040557604060046101403760006103e861026757600080fd5b6103e86101405106141561027a57600080fd5b6000336020826101a001015260208101905060006020826101a0010152602081019050610160516020826101a0010152602081019050806101a0526101a090508051602082012090506101805260026101405160e05260c052604060c02060c052602060c0205461018051146102ef57600080fd5b42610260526102605162093a806102605101101561030c57600080fd5b62093a8061026051016102405261014051610240516080600081121561033a578060000360020a8204610341565b8060020a82025b9050905017610280526001543b61035757600080fd5b60015430141561036657600080fd5b602061034060246390b5561d6102c052610280516102e0526102dc60006001545af161039157600080fd5b600050610340516102a0526102a0516103a957600080fd5b60056101405160e05260c052604060c02060c052602060c02033815561016051600182015550336103605261014051610380527fc007c9ebf9bafe164d68bdf8d59fbdd4b4a8b9c589ef8c18ce358ab664605eb76040610360a1005b630770ed76600051141561042b57341561041e57600080fd5b60015460005260206000f3005b63dbd2ad726000511415610470576020600461014037341561044c57600080fd5b60026101405160e05260c052604060c02060c052602060c0205460005260206000f3005b63b557a78460005114156104b8576020600461014037341561049157600080fd5b600160026101405160e05260c052604060c02060c052602060c020015460005260206000f3005b63a732668e60005114156104de5734156104d157600080fd5b60035460005260206000f3005b639f5d8e5360005114156105045734156104f757600080fd5b60045460005260206000f3005b63388d0d706000511415610549576020600461014037341561052557600080fd5b60056101405160e05260c052604060c02060c052602060c0205460005260206000f3005b638ce805956000511415610591576020600461014037341561056a57600080fd5b600160056101405160e05260c052604060c02060c052602060c020015460005260206000f3005b60006000fd5b6100db610672036100db6000396100db610672036000f3";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_SUBMIT = "submit";

    public static final String FUNC_EXIT = "exit";

    public static final String FUNC_PRIORITYQUEUE = "priorityQueue";

    public static final String FUNC_PLASMABLOCKS__ROOT = "plasmaBlocks__root";

    public static final String FUNC_PLASMABLOCKS__BLOCKNUMBER = "plasmaBlocks__blockNumber";

    public static final String FUNC_CURRENTPLASMABLOCKNUMBER = "currentPlasmaBlockNumber";

    public static final String FUNC_NEXTDEPOSITBLOCKNUMBER = "nextDepositBlockNumber";

    public static final String FUNC_EXITS__OWNER = "exits__owner";

    public static final String FUNC_EXITS__AMOUNT = "exits__amount";

    public static final Event DEPOSITCREATED_EVENT = new Event("DepositCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BLOCKSUBMITTED_EVENT = new Event("BlockSubmitted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event EXITSTARTED_EVENT = new Event("ExitStarted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1557505324562", "0x4436B3c22da5f8313Bc98bf726F16bBf9e17290D");
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

    public RemoteCall<TransactionReceipt> submit(byte[] blockRoot, BigInteger plasmaBlockNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SUBMIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(blockRoot), 
                new org.web3j.abi.datatypes.generated.Uint256(plasmaBlockNumber)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> exit(BigInteger depositBlockNumber, BigInteger amount, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_EXIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(depositBlockNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<String> priorityQueue() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PRIORITYQUEUE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteCall<BigInteger> currentPlasmaBlockNumber() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CURRENTPLASMABLOCKNUMBER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> nextDepositBlockNumber() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NEXTDEPOSITBLOCKNUMBER, 
                Arrays.<Type>asList(), 
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

    public static RemoteCall<RootChain> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _priorityQueue) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_priorityQueue)));
        return deployRemoteCall(RootChain.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<RootChain> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _priorityQueue) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_priorityQueue)));
        return deployRemoteCall(RootChain.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<RootChain> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _priorityQueue) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_priorityQueue)));
        return deployRemoteCall(RootChain.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<RootChain> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _priorityQueue) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_priorityQueue)));
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
    }
}
