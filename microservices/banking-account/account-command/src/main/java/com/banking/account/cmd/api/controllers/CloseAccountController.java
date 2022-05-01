package com.banking.account.cmd.api.controllers;

import com.banking.account.cmd.api.command.CloseAccountCommand;
import com.banking.account.cmd.api.command.WithdrawFundsCommand;
import com.banking.account.common.dto.BaseResponse;
import com.banking.cqrs.core.exceptions.AggregateNotFoundException;
import com.banking.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequestMapping("/api/v1/closeBankAccount")
@Slf4j
@RequiredArgsConstructor
public class CloseAccountController {

    private final CommandDispatcher commandDispatcher;

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> closeBankAccount(@PathVariable String id){
        try {
            CloseAccountCommand command = new CloseAccountCommand(id);
            commandDispatcher.send(command);
            return ResponseEntity.ok(new BaseResponse("Se cerro la cuenta bancaria exitosamente"));

        }catch (IllegalStateException | AggregateNotFoundException e){
            log.error(MessageFormat.format("El cliente envio request invalido {0}", e.toString()));
            return ResponseEntity.badRequest().body(new BaseResponse(e.toString()));

        }catch (Exception e){
            String error = MessageFormat.format("Errores mientras procesaba el request {0}", id);
            return ResponseEntity.internalServerError().body(new BaseResponse(error));

        }
    }
}
