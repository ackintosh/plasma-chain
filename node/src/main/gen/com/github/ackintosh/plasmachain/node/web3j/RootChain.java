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
    private static final String BINARY = "0x600035601c52740100000000000000000000000000000000000000006020526f7fffffffffffffffffffffffffffffff6040527fffffffffffffffffffffffffffffffff8000000000000000000000000000000060605274012a05f1fffffffffffffffffffffffffdabf41c006080527ffffffffffffffffffffffffed5fa0e000000000000000000000000000000000060a052341561009e57600080fd5b33600055600060025560016003556103d356600035601c52740100000000000000000000000000000000000000006020526f7fffffffffffffffffffffffffffffff6040527fffffffffffffffffffffffffffffffff8000000000000000000000000000000060605274012a05f1fffffffffffffffffffffffffdabf41c006080527ffffffffffffffffffffffffed5fa0e000000000000000000000000000000000060a05263d0e30db060005114156101a057600034116100ae57600080fd5b6003546002546003540110156100c357600080fd5b6002546003540161014052600033602082610180010152602081019050600060208261018001015260208101905034602082610180010152602081019050806101805261018090508051602082012090506101605260016101405160e05260c052604060c02060c052602060c02061016051815561014051600182015550600380546001825401101561015557600080fd5b600181540181555034610220523361024052610220516102605261014051610280527f72235260b946c311b2455bc3db7b029180adc32d36d49946ccb668abcd1a1a196060610240a1005b63762ca848600051141561024357604060046101403734156101c157600080fd5b60005433146101cf57600080fd5b60016101605160e05260c052604060c02060c052602060c0206101405181556101605160018201555060025461016051111561020d57610160516002555b600160035561014051610180527f38ced01204841ece6186d0d51d96712da8d7553e3ef7cef2394bc185506ec03d6020610180a1005b63dbd2ad726000511415610288576020600461014037341561026457600080fd5b60016101405160e05260c052604060c02060c052602060c0205460005260206000f3005b63b557a78460005114156102d057602060046101403734156102a957600080fd5b600160016101405160e05260c052604060c02060c052602060c020015460005260206000f3005b63a732668e60005114156102f65734156102e957600080fd5b60025460005260206000f3005b639f5d8e53600051141561031c57341561030f57600080fd5b60035460005260206000f3005b60006000fd5b6100b16103d3036100b16000396100b16103d3036000f3";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_SUBMIT = "submit";

    public static final String FUNC_PLASMABLOCKS__ROOT = "plasmaBlocks__root";

    public static final String FUNC_PLASMABLOCKS__BLOCKNUMBER = "plasmaBlocks__blockNumber";

    public static final String FUNC_CURRENTPLASMABLOCKNUMBER = "currentPlasmaBlockNumber";

    public static final String FUNC_NEXTDEPOSITBLOCKNUMBER = "nextDepositBlockNumber";

    public static final Event DEPOSITCREATED_EVENT = new Event("DepositCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BLOCKSUBMITTED_EVENT = new Event("BlockSubmitted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1557505324562", "0xd54b47F8e6A1b97F3A84f63c867286272b273b7C");
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

    public static RemoteCall<RootChain> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RootChain.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<RootChain> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RootChain.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<RootChain> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RootChain.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<RootChain> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RootChain.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
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
}
