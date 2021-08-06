package br.com.tt.PetShop.handler;

import br.com.tt.PetShop.exception.CreditoExcepetion;
import br.com.tt.PetShop.exception.ProdutoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ProdutoControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorInfo trataErro(ProdutoException e) {
        log.info("Erro no Produto", e);
        return new ErrorInfo("erro_criacao_produto", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorInfo trataErro(CreditoExcepetion e) {
        log.info("CPF com pendencias", e);
        return new ErrorInfo("cpf-pendencia", e.getMessage());
    }

}
