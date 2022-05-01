package com.banking.account.common.events;

import com.banking.account.common.dto.AccountType;
import com.banking.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountOpenedEvent extends BaseEvent {

    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
    private Date createdAt;

}
