package com.test.notification.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@ConfigurationProperties("kafka")
public class KafkaProperties {
    private boolean enabled;

    private Topics topics;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Topics {
        private String emailQueue;
        private String smsQueue;
    }
}