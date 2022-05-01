package com.banking.account.cmd.infrastructure;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.account.cmd.domain.EventStoreRepository;
import com.banking.cqrs.core.events.BaseEvent;
import com.banking.cqrs.core.events.EventModel;
import com.banking.cqrs.core.exceptions.AggregateNotFoundException;
import com.banking.cqrs.core.exceptions.ConcurrencyException;
import com.banking.cqrs.core.infrastructure.EventStore;
import com.banking.cqrs.core.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);

        if (expectedVersion != -1 && eventStream.get(eventStream.size()-1).getVersion() != expectedVersion)
            throw new ConcurrencyException();

        AtomicInteger version = new AtomicInteger(expectedVersion);

        events.forEach(event -> {
            version.getAndIncrement();
            event.setVersion(version.get());
            EventModel eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version.get())
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            EventModel persistedEvent = eventStoreRepository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()){
                eventProducer.produce(event.getClass().getSimpleName() ,event);
            }
        });

    }

    @Override
    public List<BaseEvent> getEvent(String aggregateId) {
        List<EventModel> events = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (Objects.isNull(events) || events.isEmpty())
            throw new AggregateNotFoundException("La cuenta del banco es incorrecta");

        return events.stream()
                .map(EventModel::getEventData)
                .collect(Collectors.toList());
    }
}
