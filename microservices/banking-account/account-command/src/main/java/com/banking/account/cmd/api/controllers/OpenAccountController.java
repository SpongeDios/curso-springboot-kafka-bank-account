package com.banking.account.cmd.api.controllers;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.cmd.api.dto.OpenAccountResponse;
import com.banking.account.common.dto.BaseResponse;
import com.banking.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.text.MessageFormat;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
@Slf4j
@RequiredArgsConstructor
public class OpenAccountController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command){
        String id = UUID.randomUUID().toString();
        command.setId(id);
        try{
            commandDispatcher.send(command);
            return ResponseEntity
                    .created(URI.create("/api/v1/openBankAccount"))
                    .body(new OpenAccountResponse("La cuenta del banco se ha creado exitosamente", id));

        }catch(IllegalStateException e){
            log.error("No se pudo generar la cuenta de banco {0}", e);
            return ResponseEntity.badRequest().body(new OpenAccountResponse(e.toString()));
        }catch (Exception e){
            String errorMessage = MessageFormat.format("Error mientras procesaba el request {0}", id);
            log.error(errorMessage, e);
            return ResponseEntity.internalServerError().body(new OpenAccountResponse(errorMessage, id));
        }
    }

}
