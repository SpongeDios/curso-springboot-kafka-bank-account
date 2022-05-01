package com.banking.account.cmd.api.controllers;

import com.banking.account.cmd.api.command.DepositFundsCommand;
import com.banking.account.common.dto.BaseResponse;
import com.banking.cqrs.core.exceptions.AggregateNotFoundException;
import com.banking.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.MessageFormat;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/depositfunds")
@Slf4j
@RequiredArgsConstructor
public class DepositFundsController {

    private final CommandDispatcher commandDispatcher;

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable String id, @RequestBody DepositFundsCommand command){
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return ResponseEntity.ok(new BaseResponse("El deposito de dinero fue exitoso"));
        }catch (IllegalStateException | AggregateNotFoundException e){
            log.error(MessageFormat.format("El cliente envio request invalido {0}", e.toString()));
            return ResponseEntity.badRequest().body(new BaseResponse(e.toString()));
        }catch (Exception e){
            String error = MessageFormat.format("Errores mientras procesaba el request {0}", id);
            return ResponseEntity.internalServerError().body(new BaseResponse(error));
        }
    }
}
