package com.test.notification.dto;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter 
@NoArgsConstructor 
@ToString
@XmlRootElement()
public class SmsRequestDTO {

    @Value("${test.smsAccount}")
    private String acc;

    @Value("${test.smsPassword}")
    private String pwd;

    private String msisdn;

    @Value("${test.smsSuffix}")
    private String suffix;

    private String msg;

    @Value("${test.smsReqSmsStatus}")
    private String req_sms_status;
     
    @Value("${test.smsXmlResp}")
    private String xmlResp;

}
