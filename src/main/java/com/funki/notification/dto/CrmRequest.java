package com.test.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmRequest {
  private String assignee;
  private String assigner;
  private String content;
  private String recordDate;
  private String followUp;
  private String applyNo;
  private String recipient;
}
