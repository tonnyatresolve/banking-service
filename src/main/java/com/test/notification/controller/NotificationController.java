package com.test.notification.controller;

import com.test.notification.config.KafkaProperties;
import com.test.notification.dto.ContactUsRequest;
import com.test.notification.dto.CrmRequest;
import com.test.notification.dto.EmailQueueDTO;
import com.test.notification.dto.LoanApplicationRequest;
import com.test.notification.dto.SmsQueueDTO;
import com.test.notification.dto.SubscriptionRequest;
import com.test.notification.dto.VaultCodeVerifyDto;
import com.test.notification.service.sms.SmsGenerationService;
import com.test.notification.service.template.TemplateService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@AllArgsConstructor
@Slf4j
public class NotificationController {
    private KafkaTemplate<String, EmailQueueDTO> kakfaProducer;
    private KafkaTemplate<String, SmsQueueDTO> kakfaSmsProducer;
    private KafkaProperties kafkaProperties;
    private final TemplateService templateService;
    private final SmsGenerationService smsService;

    // @CrossOrigin
    @PostMapping("/v1/send-subscription-email")
    public ResponseEntity<String> sendSubscriptionEmail(@RequestBody SubscriptionRequest subscriptionInfo) {
        try {
            // log.info("Sending subscription mailing request: " +
            // subscriptionInfo.toString());
            log.info("Sending subscription mailing request: ");
            EmailQueueDTO emailQueueDTO = templateService.generateEmail(subscriptionInfo);
            kakfaProducer.send(kafkaProperties.getTopics().getEmailQueue(), emailQueueDTO);
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }
    }

    // @CrossOrigin
    @PostMapping("/v1/send-loan-application-email")
    public ResponseEntity<String> sendLoanApplicationEmail(@RequestBody LoanApplicationRequest loanApplicationInfo) {
        try {
            // log.info("Sending loan application mailing request: " +
            // loanApplicationInfo.toString());
            log.info("Sending loan application mailing request: ");
            EmailQueueDTO emailQueueDTO = templateService.generateEmail(loanApplicationInfo);
            kakfaProducer.send(kafkaProperties.getTopics().getEmailQueue(), emailQueueDTO);
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }
    }

    // @CrossOrigin
    @PostMapping("/v1/send-enquiry-email")
    public ResponseEntity<String> sendEnquiryEmail(@RequestBody ContactUsRequest contactUsRequest) {
        try {
            // log.info("Sending contact us enquiry mailing request: " +
            // contactUsRequest.toString());
            log.info("Sending contact us enquiry mailing request: ");
            EmailQueueDTO emailQueueDTO = templateService.generateEmail(contactUsRequest);
            kakfaProducer.send(kafkaProperties.getTopics().getEmailQueue(), emailQueueDTO);
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }
    }

    @PostMapping("/v1/send-crm-email")
    public ResponseEntity<String> sendCrmEmail(@RequestBody CrmRequest crmRequest) {
        try {
            log.info("Sending crm mailing request: ");
            EmailQueueDTO emailQueueDTO = templateService.generateEmail(crmRequest);
            kakfaProducer.send(kafkaProperties.getTopics().getEmailQueue(), emailQueueDTO);
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }
    }

    @PostMapping("/v1/send-verification-sms")
    public ResponseEntity<Boolean> generateVerifyCode(@RequestBody String mobile) {
        try {
            log.info("Sending verification sms request: " + mobile);
            String otp = smsService.generateVerifyCode(mobile);
            if(otp.length() > 0) {
                SmsQueueDTO smsQueueDTO = new SmsQueueDTO();
                smsQueueDTO.setCode(otp);
                smsQueueDTO.setMobile(mobile);
                log.info("listener will receive: " + smsQueueDTO.getCode() + " mobile: " + smsQueueDTO.getMobile());
                kakfaSmsProducer.send(kafkaProperties.getTopics().getSmsQueue(), smsQueueDTO);
            }
            log.info("OTP: " + otp);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @PostMapping("/v1/verify-otp-code/{mobile}")
    public ResponseEntity<Boolean> verifyOtpCode(
        @PathVariable String mobile, @RequestBody VaultCodeVerifyDto request) {
        try {
            log.info("Sending verification code: " + request.getCode());    
            return ResponseEntity.status(HttpStatus.OK).body(smsService.verifyOtp(mobile, request));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }
}