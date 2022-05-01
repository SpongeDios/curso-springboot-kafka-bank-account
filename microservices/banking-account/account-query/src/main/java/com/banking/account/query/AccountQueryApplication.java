package com.banking.account.query;


import com.banking.account.query.api.queries.*;
import com.banking.cqrs.core.infrastructure.QueryDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class AccountQueryApplication {

    private final QueryDispatcher queyDispatcher;
    private final QueryHandler queryHandler;

    public static void main(String[] args) {
        SpringApplication.run(AccountQueryApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers(){
        queyDispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler::handle);
        queyDispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler::handle);
        queyDispatcher.registerHandler(FindAccountByHolderQuery.class, queryHandler::handle);
        queyDispatcher.registerHandler(FindAccountWithBalanceQuery.class, queryHandler::handle);
    }


}
