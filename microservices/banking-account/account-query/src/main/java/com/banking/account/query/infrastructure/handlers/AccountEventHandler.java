package com.banking.account.query.infrastructure.handlers;

import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawEvent;
import com.banking.account.query.domain.BankAccount;
import com.banking.account.query.domain.dao.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountEventHandler implements EventHandler{

    private final AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        BankAccount bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreatedAt())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();

        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        Optional<BankAccount> bankAccount = accountRepository.findById(event.getId());
        if (bankAccount.isEmpty()){
            return;
        }
        double currentBalance = bankAccount.get().getBalance();
        double latestBalance = currentBalance + event.getAmount();

        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(FundsWithdrawEvent event) {
        Optional<BankAccount> bankAccount = accountRepository.findById(event.getId());
        if (bankAccount.isEmpty())
            return;

        double currentBalance = bankAccount.get().getBalance();
        double latestBalance = currentBalance - event.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }

}
