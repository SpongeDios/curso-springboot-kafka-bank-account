package com.banking.account.query.domain.dao;

import com.banking.account.query.domain.BankAccount;
import com.banking.cqrs.core.domain.BaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<BankAccount, String> {

    BankAccount findByAccountHolder(String accountHolder);
    List<BaseEntity> findByBalanceGreaterThan(double balance);
    List<BaseEntity> findByBalanceLessThan(double balance);

}
