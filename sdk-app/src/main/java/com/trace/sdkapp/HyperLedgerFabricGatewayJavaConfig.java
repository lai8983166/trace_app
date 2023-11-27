package com.trace.sdkapp;

import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.protos.common.Common;
//import org.hyperledger.fabric.client.Contract;
//import org.hyperledger.fabric.client.Gateway;
//import org.hyperledger.fabric.client.Network;
//import org.hyperledger.fabric.client.identity.Identities;
//import org.hyperledger.fabric.client.identity.Signers;
//import org.hyperledger.fabric.client.identity.X509Identity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.function.Consumer;

@Configuration
@Slf4j
@AllArgsConstructor
//@NoArgsConstructor(force=true)
public class HyperLedgerFabricGatewayJavaConfig {



    @Bean
    public Gateway gateway() throws Exception, CertificateException, InvalidKeyException {


        BufferedReader certificateReader = Files.newBufferedReader(Paths.get("/root/go/src/github.com/hyperledger/sdk-app/src/main/resources/crypto-config/peerOrganizations/org1.example.com/users/User1@org1.example.com/msp/signcerts/User1@org1.example.com-cert.pem"), StandardCharsets.UTF_8);

        X509Certificate certificate = Identities.readX509Certificate(certificateReader);

        BufferedReader privateKeyReader = Files.newBufferedReader(Paths.get("/root/go/src/github.com/hyperledger/sdk-app/src/main/resources/crypto-config/peerOrganizations/org1.example.com/users/User1@org1.example.com/msp/keystore/priv_sk"), StandardCharsets.UTF_8);

        PrivateKey privateKey = Identities.readPrivateKey(privateKeyReader);

        Wallet wallet = Wallets.newInMemoryWallet();
        wallet.put("user1" , Identities.newX509Identity("Org1MSP", certificate , privateKey));


        Gateway.Builder builder = Gateway.createBuilder()
                .identity(wallet , "user1")
                .networkConfig(Paths.get("/root/go/src/github.com/hyperledger/sdk-app/src/main/resources/connection.json"));

        Gateway gateway = builder.connect();


        log.info("=========================================== connected fabric gateway {} " , gateway);

        return gateway;

    }


}

