package com.banking.account.query.api.controllers;

import com.banking.account.common.dto.BaseResponse;
import com.banking.account.query.api.dto.AccountLookupResponse;
import com.banking.account.query.api.dto.EqualityType;
import com.banking.account.query.api.queries.FindAccountByHolderQuery;
import com.banking.account.query.api.queries.FindAccountByIdQuery;
import com.banking.account.query.api.queries.FindAccountWithBalanceQuery;
import com.banking.account.query.api.queries.FindAllAccountsQuery;
import com.banking.account.query.domain.BankAccount;
import com.banking.cqrs.core.infrastructure.QueryDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/bankAccountLookup")
@RequiredArgsConstructor
@Slf4j
public class AccountLookupController {

    private final QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<AccountLookupResponse> getAllAccounts(){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());
            if (Objects.isNull(accounts) || accounts.isEmpty())
                return ResponseEntity.noContent().build();

            AccountLookupResponse response = new AccountLookupResponse();
            response.setAccounts(accounts);
            response.setMessage("Se realizo la consulta con exito");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            String errorMessage = "Ocurrio un error ejecutando la consulta";
            log.error(errorMessage, e);
            return ResponseEntity.internalServerError().body(new AccountLookupResponse(errorMessage));
        }
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable String id){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
            if (Objects.isNull(accounts) || accounts.isEmpty())
                return ResponseEntity.noContent().build();

            AccountLookupResponse response = new AccountLookupResponse();
            response.setAccounts(accounts);
            response.setMessage("Se realizo la consulta con exito");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            String errorMessage = "Ocurrio un error ejecutando la consulta";
            log.error(errorMessage, e);
            return ResponseEntity.internalServerError().body(new AccountLookupResponse(errorMessage));
        }
    }

    @GetMapping("/byholder/{account}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable String account){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(account));
            if (Objects.isNull(accounts) || accounts.isEmpty())
                return ResponseEntity.noContent().build();

            AccountLookupResponse response = new AccountLookupResponse();
            response.setAccounts(accounts);
            response.setMessage("Se realizo la consulta con exito");
            return ResponseEntity.ok(response);

        }catch (Exception e){
            String errorMessage = "Ocurrio un error ejecutando la consulta";
            log.error(errorMessage, e);
            return ResponseEntity.internalServerError().body(new AccountLookupResponse(errorMessage));
        }
    }

    @GetMapping("/bybalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountByBalance(@PathVariable EqualityType equalityType, @PathVariable double balance){

        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(balance, equalityType));
            if (Objects.isNull(accounts) || accounts.isEmpty())
                return ResponseEntity.noContent().build();

            AccountLookupResponse response = new AccountLookupResponse();
            response.setAccounts(accounts);
            response.setMessage("Se realizo la consulta con exito");
            return ResponseEntity.ok(response);

        }catch (Exception e){
            String errorMessage = "Ocurrio un error ejecutando la consulta";
            log.error(errorMessage, e);
            return ResponseEntity.internalServerError().body(new AccountLookupResponse(errorMessage));
        }

    }


}
