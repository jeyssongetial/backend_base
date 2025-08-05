package com.lego.backend.infrastruture.error;

import com.lego.backend.domain.ex.ApplicationException;
import com.lego.backend.domain.ex.TechnicalException;
import com.lego.backend.infrastruture.error.dto.ErrorDetail;
import com.lego.backend.infrastruture.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.stream.Collectors;

@Hidden
@ControllerAdvice()
public class ApiExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException exception) {
        ErrorDetail errorDetail = toErrorDetail(TechnicalException.Type.ERROR_EXCEPCION_RESPUESTA_ESTADO.build());
        errorDetail = errorDetail.toBuilder()
                .detail(exception.getMessage())
                .build();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(Collections.singletonList(errorDetail))
                .build();

        return new ResponseEntity<>(errorResponse, exception.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorDetail errorDetail = toErrorDetail(TechnicalException.Type.ERROR_EXCEPCION_GENERICA.build());
        errorDetail = errorDetail.toBuilder()
                .detail(ex.getMessage())
                .build();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(Collections.singletonList(errorDetail))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<ErrorResponse> handleBusinessExceptions(ApplicationException exception) {
        ErrorDetail errorDetail = toErrorDetail(exception);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(Collections.singletonList(errorDetail))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        var errores = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    return ErrorDetail.builder()
                            .id("VALIDATION_ERROR")
                            .type("VALIDATION")
                            .message("Error en el campo: " + fieldError.getField())
                            .detail(fieldError.getDefaultMessage())
                            .build();
                })
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(errores)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    private ErrorDetail toErrorDetail(ApplicationException exception) {
        return ErrorDetail.builder()
                .id(exception.getId())
                .type(exception.getErrorType().name())
                .message(exception.getMessage())
                .detail(exception.getDetail())
                .build();
    }

}
