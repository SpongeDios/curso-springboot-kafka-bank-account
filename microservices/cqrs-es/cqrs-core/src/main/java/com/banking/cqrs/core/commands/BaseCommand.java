package com.banking.cqrs.core.commands;

import com.banking.cqrs.core.messages.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class BaseCommand extends Message {
    public BaseCommand(String id) {
        super(id);
    }
}
