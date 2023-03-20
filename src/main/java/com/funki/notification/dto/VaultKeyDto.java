package com.test.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VaultKeyDto {
    
    private String name;
    private String account_name;
    
    @Builder.Default
    private Boolean generate = true;

    @Builder.Default
    private String issuer = "Test";

    @Builder.Default
    private String period = "60";

    @Builder.Default
    private String algorithm = "SHA256";

    @Builder.Default
    private String digits = "6";

    @Builder.Default
    private Integer key_size = 20;

}