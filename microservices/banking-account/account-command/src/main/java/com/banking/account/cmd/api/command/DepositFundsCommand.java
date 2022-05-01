package com.banking.account.cmd.api.command;

import com.banking.cqrs.core.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositFundsCommand extends BaseCommand {

    private double amount;

}
