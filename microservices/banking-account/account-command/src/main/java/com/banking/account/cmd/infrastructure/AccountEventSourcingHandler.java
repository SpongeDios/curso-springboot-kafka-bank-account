package com.banking.account.cmd.infrastructure;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.cqrs.core.domain.AggregateRoot;
import com.banking.cqrs.core.events.BaseEvent;
import com.banking.cqrs.core.handlers.EventSourcingHandler;
import com.banking.cqrs.core.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregateRoot) {
        eventStore.saveEvents(aggregateRoot.getId(), aggregateRoot.getUncommitedChanges(), aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommited();
    }

    @Override
    public AccountAggregate getById(String id) {
        AccountAggregate aggregate = new AccountAggregate();
        List<BaseEvent> events = eventStore.getEvent(id);

        if (!Objects.isNull(events) && !events.isEmpty()){
            aggregate.replayEvents(events);
            Integer latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder()).orElseThrow();
            aggregate.setVersion(latestVersion);
        }

        return aggregate;
    }
}
