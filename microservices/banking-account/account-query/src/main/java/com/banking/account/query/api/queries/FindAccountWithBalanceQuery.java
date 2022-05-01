package com.banking.account.query.api.queries;

import com.banking.account.query.api.dto.EqualityType;
import com.banking.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {
    private double balance;
    private EqualityType equalityType;
}
