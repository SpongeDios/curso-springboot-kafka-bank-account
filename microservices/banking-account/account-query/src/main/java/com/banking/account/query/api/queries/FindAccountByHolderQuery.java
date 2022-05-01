package com.banking.account.query.api.queries;

import com.banking.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FindAccountByHolderQuery extends BaseQuery {
    private String accountHolder;
}
