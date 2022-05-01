package com.banking.account.cmd;

import com.banking.account.cmd.api.command.*;
import com.banking.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class AccountCmdApplication {

    private final CommandDispatcher commandDispatcher;
    private final CommandHandler commandHandler;

    public static void main(String[] args) {
        SpringApplication.run(AccountCmdApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers(){
        commandDispatcher.registerHandler(OpenAccountCommand.class, command -> commandHandler.handle((OpenAccountCommand) command));
        commandDispatcher.registerHandler(DepositFundsCommand.class, command -> commandHandler.handle((DepositFundsCommand) command));
        commandDispatcher.registerHandler(WithdrawFundsCommand.class, command -> commandHandler.handle((WithdrawFundsCommand) command));
        commandDispatcher.registerHandler(CloseAccountCommand.class, command -> commandHandler.handle((CloseAccountCommand) command));

    }

}
