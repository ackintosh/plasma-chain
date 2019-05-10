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
    private static final String BINARY = "0x6101ff56600035601c52740100000000000000000000000000000000000000006020526f7fffffffffffffffffffffffffffffff6040527fffffffffffffffffffffffffffffffff8000000000000000000000000000000060605274012a05f1fffffffffffffffffffffffffdabf41c006080527ffffffffffffffffffffffffed5fa0e000000000000000000000000000000000060a052631f045c7660005114156100b85734156100ac57600080fd5b60006000556001600155005b63d0e30db0600051141561015257600034116100d357600080fd5b6001546000546001540110156100e857600080fd5b6000546001540161014052600180546001825401101561010757600080fd5b600181540181555034610160523361018052610160516101a052610140516101c0527f73a19dd210f1a7f902193214c0ee91dd35ee5b4d920cba8d519eca65a7b488ca6060610180a1005b63762ca84860005114156101a9576040600461014037341561017357600080fd5b600160015561014051610180527f38ced01204841ece6186d0d51d96712da8d7553e3ef7cef2394bc185506ec03d6020610180a1005b63a732668e60005114156101cf5734156101c257600080fd5b60005460005260206000f3005b639f5d8e5360005114156101f55734156101e857600080fd5b60015460005260206000f3005b60006000fd5b6100046101ff036100046000396100046101ff036000f3";

    public static final String FUNC___INIT = "__init";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_SUBMIT = "submit";

    public static final String FUNC_CURRENTPLASMABLOCKNUMBER = "currentPlasmaBlockNumber";

    public static final String FUNC_NEXTDEPOSITBLOCKNUMBER = "nextDepositBlockNumber";

    public static final Event DEPOSITED_EVENT = new Event("Deposited", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BLOCKSUBMITTED_EVENT = new Event("BlockSubmitted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1557357431302", "0x2C2B9C9a4a25e24B174f26114e8926a9f2128FE4");
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

    public List<DepositedEventResponse> getDepositedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DEPOSITED_EVENT, transactionReceipt);
        ArrayList<DepositedEventResponse> responses = new ArrayList<DepositedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositedEventResponse typedResponse = new DepositedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._depositer = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._depositBlockNumber = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DepositedEventResponse> depositedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DepositedEventResponse>() {
            @Override
            public DepositedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DEPOSITED_EVENT, log);
                DepositedEventResponse typedResponse = new DepositedEventResponse();
                typedResponse.log = log;
                typedResponse._depositer = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._depositBlockNumber = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DepositedEventResponse> depositedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSITED_EVENT));
        return depositedEventFlowable(filter);
    }

    public List<BlockSubmittedEventResponse> getBlockSubmittedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BLOCKSUBMITTED_EVENT, transactionReceipt);
        ArrayList<BlockSubmittedEventResponse> responses = new ArrayList<BlockSubmittedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BlockSubmittedEventResponse typedResponse = new BlockSubmittedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._root = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse._root = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BlockSubmittedEventResponse> blockSubmittedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BLOCKSUBMITTED_EVENT));
        return blockSubmittedEventFlowable(filter);
    }

    public RemoteCall<TransactionReceipt> __init() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC___INIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> deposit(BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> submit(byte[] _root, BigInteger plasmaBlockNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SUBMIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_root), 
                new org.web3j.abi.datatypes.generated.Uint256(plasmaBlockNumber)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    @Deprecated
    public static RemoteCall<RootChain> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RootChain.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<RootChain> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RootChain.class, web3j, transactionManager, contractGasProvider, BINARY, "");
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

    public static class DepositedEventResponse {
        public Log log;

        public String _depositer;

        public BigInteger _amount;

        public BigInteger _depositBlockNumber;
    }

    public static class BlockSubmittedEventResponse {
        public Log log;

        public byte[] _root;
    }
}
