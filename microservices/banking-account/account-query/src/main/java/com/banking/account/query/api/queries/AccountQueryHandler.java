package com.banking.account.query.api.queries;

import com.banking.account.query.api.dto.EqualityType;
import com.banking.account.query.domain.BankAccount;
import com.banking.account.query.domain.dao.AccountRepository;
import com.banking.cqrs.core.domain.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountQueryHandler implements QueryHandler{

    private final AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccounts.forEach(bankAccountList::add);
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        Optional<BankAccount> bankAccount = accountRepository.findById(query.getId());
        if (bankAccount.isEmpty())
            return Collections.emptyList();

        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        BankAccount bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());
        if (Objects.isNull(bankAccount))
            return Collections.emptyList();

        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount);
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        return query
                .getEqualityType() == EqualityType.GREATHER_THAN ? accountRepository
                .findByBalanceGreaterThan(query.getBalance()) : accountRepository
                .findByBalanceLessThan(query.getBalance());
    }
}
