package com.test.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailQueueDTO {
    String fromAddress;
    String replyToAddress;
    String recipient;
    String subject;
    String textContent;
    String htmlContent;
}