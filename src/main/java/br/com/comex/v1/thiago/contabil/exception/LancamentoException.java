package br.com.comex.v1.thiago.contabil.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LancamentoException extends RuntimeException {
    public LancamentoException() {
        super();
    }
    public LancamentoException(String message, Throwable cause) {
        super(message, cause);
    }
    public LancamentoException(String message) {
        super(message);
    }
    public LancamentoException(Throwable cause) {
        super(cause);
    }
}
