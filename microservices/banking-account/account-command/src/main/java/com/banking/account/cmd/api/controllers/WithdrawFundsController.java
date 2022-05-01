package com.banking.account.cmd.api.controllers;

import com.banking.account.cmd.api.command.DepositFundsCommand;
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
@RequestMapping("/api/v1/withdrawfunds")
@Slf4j
@RequiredArgsConstructor
public class WithdrawFundsController {

    private final CommandDispatcher commandDispatcher;

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> withDrawFunds(@PathVariable String id, @RequestBody WithdrawFundsCommand command){
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return ResponseEntity.ok(new BaseResponse("El retiro de dinero fue exitoso"));

        }catch (IllegalStateException | AggregateNotFoundException e){
            log.error(MessageFormat.format("El cliente envio request invalido {0}", e.toString()));
            return ResponseEntity.badRequest().body(new BaseResponse(e.toString()));

        }catch (Exception e){
            String error = MessageFormat.format("Errores mientras procesaba el request {0}", id);
            return ResponseEntity.internalServerError().body(new BaseResponse(error));

        }
    }


}
