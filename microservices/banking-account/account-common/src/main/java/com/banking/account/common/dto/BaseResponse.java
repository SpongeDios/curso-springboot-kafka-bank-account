package com.banking.account.common.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse {
    private String message;
}
