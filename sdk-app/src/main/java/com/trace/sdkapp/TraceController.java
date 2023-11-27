package com.trace.sdkapp;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.protos.common.Common;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.gateway.GatewayException;
/*import org.hyperledger.fabric.client.ChaincodeEvent;
import org.hyperledger.fabric.client.ChaincodeEventsRequest;
import org.hyperledger.fabric.client.CloseableIterator;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.CommitStatusException;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.EndorseException;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.GatewayException;
import org.hyperledger.fabric.client.Network;
import org.hyperledger.fabric.client.Status;
import org.hyperledger.fabric.client.SubmitException;*/
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeoutException;



@RestController // 注解
@AllArgsConstructor
@Slf4j
@RequestMapping("/trace")
public class TraceController {
    HyperLedgerFabricGatewayJavaConfig hyperLedgerFabricGatewayJavaConfig;
    @Autowired
    Gateway gateway;


    @GetMapping("/{key}")
    public Map<String, Object> queryrecordByKey(@PathVariable String key) throws Exception {
        Map<String, Object> result = Maps.newConcurrentMap();
        Contract contract=getContract();

        byte[] record = contract.evaluateTransaction("query", key);

        result.put("payload", StringUtils.newStringUtf8(record));
        result.put("status", "ok");



        return result;
    }

    @PutMapping("/compound")
    public Map<String, Object> create_record(@RequestBody Record_compound record) throws Exception {
//    public Map<String, Object> create_record(@RequestBody Compound compound) throws Exception {
//        Record_compound record = compound.getRecord();

        Map<String, Object> result = Maps.newConcurrentMap();
        Contract contract=getContract();
        System.out.println("======================================================================");
        System.out.println("recod :" + record);
        byte[] bytes = contract.submitTransaction("record_compund", record.getTraceCode(), record.getCompoundID(),
                record.getTimestamp(), record.getManufacturer(), record.getResponsiblePerson(),
                record.getReportHash());
        System.out.println("bytes"+bytes);
        result.put("payload", StringUtils.newStringUtf8(bytes));
        result.put("status", "ok");

        return result;
    }

    @PutMapping("/storage")
    public Map<String, Object> create_record(@RequestBody Record_storage record) throws Exception {

        Map<String, Object> result = Maps.newConcurrentMap();
        Contract contract=getContract();
        byte[] bytes = contract.submitTransaction("record_storage", record.getTrace_code(), record.getLocation(),
                record.getSource(), record.getCompoundID(), record.getTimestamp(),
                record.getResponsible_person(), record.getReport_hash());

        result.put("payload", StringUtils.newStringUtf8(bytes));
        result.put("status", "ok");
        return result;
    }

    @PutMapping("/transport")
    public Map<String, Object> create_record(@RequestBody Record_transport record) throws Exception {

        Map<String, Object> result = Maps.newConcurrentMap();
        Contract contract=getContract();
        byte[] bytes = contract.submitTransaction("record_transport", record.getTrace_code(), record.getSource(),
                record.getCompoundID(), record.getTimestamp(), record.getDestination(),
                record.getResponsible_person(), record.getReport_hash());

        result.put("payload", StringUtils.newStringUtf8(bytes));
        result.put("status", "ok");
        return result;
    }

    @PutMapping("/expend")
    public Map<String, Object> create_record(@RequestBody Record_expend record) throws Exception {

        Map<String, Object> result = Maps.newConcurrentMap();
        Contract contract=getContract();
        byte[] bytes = contract.submitTransaction("record_transport", record.getTrace_code(), record.getSource(),
                record.getCompoundID(), record.getTimestamp(), record.getResponsible_person(),
                record.getOperator(), record.getReport_hash());

        result.put("payload", StringUtils.newStringUtf8(bytes));
        result.put("status", "ok");
        return result;
    }


//    @RequestMapping("/tps-test")
//    public Map<String, Object> tpsTest() throws Exception {
//
//        Record_compound record = new Record_compound()
//                .setCompoundID("123")
//                .setManufacturer("123")
//                .setTrace_code("123")
//                .setTimestamp("123")
//                .setResponsible_person("123")
//                .setReport_hash("123");
////        return create_record(record);
//    }

    private Network getNetwork() {
        return gateway.getNetwork("mychannel");
    }
    public Contract getContract() throws GatewayException {



        // 获取合约
        return getNetwork().getContract("trace");
    }
}


