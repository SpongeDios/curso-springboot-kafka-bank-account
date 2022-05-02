package com.banking.account.cmd.infrastructure;

import com.banking.cqrs.core.commands.BaseCommand;
import com.banking.cqrs.core.commands.CommandHandlerMethod;
import com.banking.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        List<CommandHandlerMethod> handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }


    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if (Objects.isNull(handlers) || handlers.isEmpty())
            throw new RuntimeException("El command handler no fue registrado");

        if (handlers.size() > 1)
            throw new RuntimeException("No puede enviar un command que tiene mas de un handler");

        handlers.get(0).handle(command);
    }
}
