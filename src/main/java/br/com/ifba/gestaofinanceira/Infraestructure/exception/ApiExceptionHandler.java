package br.com.ifba.gestaofinanceira.Infraestructure.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ApiExceptionHandler {

    @Value( value = "${server.erro.inclue-exception:false}")
    private boolean printStackTrace;

    // Metodo responsável por tratar exceções do tipo BusinessException
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(final BusinessException businessException, final WebRequest request) {

        // Obtém a mensagem da exceção
        final String menssageErro = businessException.getMessage();

        // Metodo responsável por montar o objeto de resposta de erro da API
        return construirMensagemDeErro(businessException, menssageErro, HttpStatus.BAD_REQUEST, request);
    }

    /**
     *  Metodo responsável por tratar exceções do tipo BusinessException
     * Trata exceções de validação lançadas quando os dados recebidos na requisição
     * @param methodArgumentNotValidException exceção contendo os erros de validação encontrados
     * @return ResponseEntity contendo os detalhes dos erros e o status HTTP 400 (Bad Request)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationException> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException) {

        // Obtém a lista de erros de validação dos campos da requisição
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();

        // Concatena os nomes dos campos que apresentaram erro
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));

        // Concatena as mensagens de erro associadas aos campos inválidos
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        // Cria o objeto contendo os detalhes da exceção de validação
        ValidationException validationDetails = new ValidationException(
                "Validation Failed",
                HttpStatus.BAD_REQUEST.value(),
                "One or more fields are invalid.",
                fields,
                fieldMessages
        );

        return new ResponseEntity<>(validationDetails, HttpStatus.BAD_REQUEST);
    }

    // Metodo responsável por montar o objeto de resposta de erro da API
    public ResponseEntity<Object> construirMensagemDeErro(Exception exception, String message, HttpStatus httpStatus, WebRequest request) {

        ApiError apiError = new ApiError();
        apiError.setTimesTamp(LocalDateTime.now());
        apiError.setStatus(httpStatus.value());
        apiError.setError(httpStatus.getReasonPhrase());
        apiError.setMessage(message);

        apiError.setPath(request.getDescription(false).replace("uri=", ""));

        if (printStackTrace) {
            // Forma nativa do Java para extrair a Stacktrace sem precisar da biblioteca ExceptionUtils
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            exception.printStackTrace(printWriter);

            apiError.setStackTrace(stringWriter.toString());
        }
        return new ResponseEntity<>(apiError, httpStatus);
    }

}
