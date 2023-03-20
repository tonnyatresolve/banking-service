package com.test.notification.service.sms;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.notification.dto.VaultCodeVerifyDto;
import com.test.notification.dto.VaultKeyDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsGenerationService {

    @Value("${test.vault.url}")
    private String vaultUrl;

    @Value("${test.vault.token}")
    private String vaultToken;

    // @Value("${test.sms.url}")
    // private String smsUrl;

    // @Value("${test.sms.account}")
    // private String smsAccount;

    // @Value("${test.sms.password}")
    // private String smsPassword;

    // @Autowired
    // WebClient webClient;

    public String generateVerifyCode (String mobile) {
        String otp = "";
        if(!checkExistingVaultKey(mobile)) {
            if(!createVaultKey(mobile)) log.error("unable to create vault key");
        } else {
             otp = generateVaultOtp(mobile);
        }
        
        return otp;
    }

    private Boolean checkExistingVaultKey (String mobile) {
        HttpStatus statusCode = WebClient.create(vaultUrl).get().uri("/keys/" + mobile)
        // HttpStatus statusCode = webClient.get().uri(vaultUrl + "/keys/" + mobile)
        .header("X-Vault-Token", vaultToken)
        .header("Content-Type", "application/json")
        .exchange()
        .map(response -> response.statusCode()).block();
            
        if (statusCode == HttpStatus.OK) return true; else return false;
    
    }


    private Boolean createVaultKey (String mobile) {
        VaultKeyDto vaultKeyDto = new VaultKeyDto();
        vaultKeyDto.setName(mobile);
        vaultKeyDto.setAccount_name(mobile);

        HttpStatus statusCode = WebClient.create(vaultUrl).post().uri("/keys/" + mobile)
        // HttpStatus statusCode = webClient.post().uri(vaultUrl+"/keys/" + mobile)
            .header("X-Vault-Token", vaultToken).contentType(MediaType.APPLICATION_JSON)
            .bodyValue(vaultKeyDto)
            .exchange()
            .map(response -> response.statusCode()).block();

        if (statusCode == HttpStatus.OK) return true; else return false;
    }

    private String generateVaultOtp (String mobile) {
        String otp = "";
        try {
            String responseJson = WebClient.create(vaultUrl).get().uri("/code/" + mobile)
            // String responseJson = webClient.get().uri(vaultUrl + "/code/" + mobile)
            .header("X-Vault-Token", vaultToken).header("Content-Type", "application/json")
            .retrieve()
            .bodyToMono(String.class)
            .block();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(responseJson);
            otp = jsonNode.get("data").get("code").asText();

            log.info("OTP generated: " + otp);

            return otp;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public Boolean verifyOtp (String mobile, VaultCodeVerifyDto request) throws JSONException, JsonMappingException, JsonProcessingException {
        
        String responseJson  = WebClient.create(vaultUrl).post().uri("/code/" + mobile)
        // String responseJson  = webClient.post().uri(vaultUrl + "/code/" + mobile)
            .header("X-Vault-Token", vaultToken).contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String.class)
            .block();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(responseJson);
        return Boolean.valueOf(jsonNode.get("data").get("valid").asText());

    }

    // private Boolean sendSMS (String otp, String mobile) {

    //         SmsRequestDTO request = new SmsRequestDTO();
    //         request.setMsisdn(mobile);
    //         request.setMsg("Verification Code: " + otp);
    //         request.setAcc(smsAccount);
    //         request.setPwd(smsPassword);
    //         request.setReq_sms_status("y");
    //         request.setSuffix("0");
    //         request.setXmlResp("y");
    
    //         log.info("request body: " + request.toString());
    //         String result = WebClient.create(smsUrl)
    //         .post().contentType(MediaType.TEXT_XML)
    //         .body(Mono.just(request), SmsRequestDTO.class)
    //         // .exchange()
    //         // .map(response -> response.toString()).block();

    //         // .retrieve()
    //         // .onStatus(HttpStatus::isError, response -> {
    //         //     logTraceResponse(response);
    //         //     return Mono.error(new IllegalStateException(
    //         //             String.format("Failed! %s")
    //         //     ));
    //         // });
    //         .exchange()
    //         .doOnSuccess(response -> {
    //             HttpStatus statusCode = response.statusCode();
    //             log.info("Status code of external system request {}", statusCode, response.bodyToMono(String.class));
    //         })
    //         .doOnError(onError -> {
    //             log.error("Error on connecting to external system {}", onError.getMessage());
    //         })
    //         .flatMap(response -> response.bodyToMono(String.class)).block();


    //         log.info("result: " + result);
    
    //         log.info("smartone: haha");
    //     return false;
    // }

    public static void logTraceResponse(ClientResponse response) {
        if (log.isTraceEnabled()) {
            log.trace("Response status: {}", response.statusCode());
            log.trace("Response headers: {}", response.headers().asHttpHeaders());
            response.bodyToMono(String.class)
                    .publishOn(Schedulers.elastic())
                    .subscribe(body -> log.trace("Response body: {}", body));
        }
    }
}