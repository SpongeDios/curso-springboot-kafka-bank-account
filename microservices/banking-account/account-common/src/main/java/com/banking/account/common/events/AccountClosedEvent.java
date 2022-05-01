package com.banking.account.common.events;

import com.banking.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
}
