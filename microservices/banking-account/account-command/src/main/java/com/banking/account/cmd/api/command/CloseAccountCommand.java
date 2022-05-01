package com.banking.account.cmd.api.command;

import com.banking.cqrs.core.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CloseAccountCommand extends BaseCommand {

    public CloseAccountCommand(String id) {
        super(id);
    }

}
