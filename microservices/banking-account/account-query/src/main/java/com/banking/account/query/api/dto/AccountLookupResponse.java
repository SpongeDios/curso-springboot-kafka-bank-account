package com.banking.account.query.api.dto;

import com.banking.account.common.dto.BaseResponse;
import com.banking.account.query.domain.BankAccount;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountLookupResponse extends BaseResponse {
    private List<BankAccount> accounts;

    public AccountLookupResponse(String message) {
        super(message);
    }

}
