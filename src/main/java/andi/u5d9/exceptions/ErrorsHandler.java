package andi.u5d9.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import andi.u5d9.payloads.ErrorsPayload;

import java.time.LocalDateTime;

@RestControllerAdvice
// Se vogliamo avere una classe che gestisca le eccezioni a livello globale per l'applicazione dobbiamo usare @RestControllerAdvice
// Così facendo creiamo un "controller" speciale il cui compito non è quello di ricevere le request come gli altri controller, bensì
// di ricevere le eccezioni.
// Ogniqualvolta faremo un throw di una particolare eccezione, essa arriverà qua e quindi potremo stabilire come rispondere in tale situazione
// Questo ci evita di dover fare una gestione delle eccezioni endpoint per endpoint

// Per gestire le specifiche eccezioni, aggiungeremo un metodo per ogni eccezione annotato dall'annotazione @ExceptionHandler che ci permetterà
// di specificare quale eccezione debba gestire un dato metodo
public class ErrorsHandler {

    @ExceptionHandler(BadRequestException.class) // Tra le parentesi specifico il tipo di eccezione gestita da questo metodo
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorsPayload handleBadRequest(BadRequestException ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorsPayload handleNotFound(NotFoundException ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ErrorsPayload handleGenericServerError(Exception ex) {
        ex.printStackTrace(); // E'importante avere lo stack trace in console per permetterci di fixare il bug
        return new ErrorsPayload("C'è stato un errore, lo risolveremo presto", LocalDateTime.now());

    }

}
