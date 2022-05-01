package com.banking.account.query.domain;

import com.banking.account.common.dto.AccountType;
import com.banking.cqrs.core.domain.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BankAccount extends BaseEntity {

    @Id
    private String id;
    private String accountHolder;
    private Date creationDate;
    private AccountType accountType;
    private double balance;


}
