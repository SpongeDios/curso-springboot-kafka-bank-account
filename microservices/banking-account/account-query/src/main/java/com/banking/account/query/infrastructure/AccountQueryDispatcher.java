package com.banking.account.query.infrastructure;

import com.banking.cqrs.core.domain.BaseEntity;
import com.banking.cqrs.core.infrastructure.QueryDispatcher;
import com.banking.cqrs.core.queries.BaseQuery;
import com.banking.cqrs.core.queries.QueryHandlerMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        List<QueryHandlerMethod> handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);

    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if (Objects.isNull(handlers) || handlers.isEmpty())
            throw new RuntimeException("Ningun query handler fue registrado para este objeto query");

        if (handlers.size() > 1)
            throw new RuntimeException("No puede enviar un Query que tenga dos o mas handlers");

        return handlers.get(0).handle(query);
    }
}
