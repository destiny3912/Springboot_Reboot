package com.rookie.springbootreboot.config.api;

import com.rookie.springbootreboot.config.api.code.BaseErrorCode;
import com.rookie.springbootreboot.config.api.code.reason.ErrorReason;
import com.rookie.springbootreboot.config.api.code.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 예외 핸들러
 * 상속받고 있는 ResponseEntityExceptionHandler 의 코드를 까보는 것을 추천
 * */
@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

    /**
     * JPA 연관관계 위반 예외 핸들링
     * @throwable RuntimeException
     * */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> validation(ConstraintViolationException exception, WebRequest request) {
        String errorMessage = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ConstraintViolationException 처리중 에러 발생"));

        return handleExceptionInternalConstraint(exception, ErrorStatus.valueOf(errorMessage), HttpHeaders.EMPTY, request);
    }

    /**
     * 살제 에러 핸들링 및 에러 JSON 리턴
     * */
    private ResponseEntity<Object> handleExceptionInternalConstraint(Exception exception, ErrorStatus errorCommonStatus, HttpHeaders headers, WebRequest request) {
        BaseResponse<Object> body = BaseResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), null);
        log.info(body.toString());

        return ResponseEntity.status(errorCommonStatus.getHttpStatus()).body(body);
    }

    /**
     * Request Parameter 존재하지 않는 에러 (Query String)
     * @exceptionHandlerFor MissingServletRequestParameterException
     * */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BaseResponse<Object> res = BaseResponse.onFailure(ErrorStatus.BAD_REQUEST.getCode(), ex.getMessage(), null);

        return ResponseEntity.status(400).body(res);
    }

    /**
     * Path Variable 존재하지 않는 에러
     * @exceptionHandlerFor MissingPathVariableException
     * */
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BaseResponse<Object> res = BaseResponse.onFailure(ErrorStatus.BAD_REQUEST.getCode(), ex.getMessage(), null);

        return ResponseEntity.status(400).body(res);
    }

    /**
     * Valid 어노테이션에서 걸리는 에러들
     * */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();
        log.error(errors.toString());

        // 한번에 에러가 여러가지 발생 할 수 있으므로 애러 매핑
        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                    errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
                });

        return handleExceptionInternalArgs(exception,HttpHeaders.EMPTY,ErrorStatus.valueOf("BAD_REQUEST"), request, errors);
    }

    /**
     * 살제 에러 핸들링 및 에러 JSON 리턴
     * */
    private ResponseEntity<Object> handleExceptionInternalArgs(Exception exception, HttpHeaders headers, ErrorStatus errorCommonStatus, WebRequest request, Map<String, String> errorArgs) {
        BaseResponse<Object> body = BaseResponse.onFailure(errorCommonStatus.getCode(),errorCommonStatus.getMessage(), errorArgs);
        log.info(body.toString());

        return ResponseEntity.status(400).body(body);
    }

    /**
     * Handling 안된 예외들
     * */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exception(Exception exception, WebRequest request) {
        exception.printStackTrace();

        return handleExceptionInternalFalse(exception, ErrorStatus.INTERNAL_SERVER_ERROR, HttpHeaders.EMPTY, ErrorStatus.INTERNAL_SERVER_ERROR.getHttpStatus(),request, exception.getMessage());
    }

    /**
     * 실제 에러 핸들링 및 JSON 리턴
     * */
    private ResponseEntity<Object> handleExceptionInternalFalse(Exception exception, ErrorStatus errorCommonStatus, HttpHeaders headers, HttpStatusCode status, WebRequest request, String errorPoint) {

        BaseResponse<Object> body = BaseResponse.onFailure(errorCommonStatus.getCode(),errorCommonStatus.getMessage(), errorPoint);

        log.info(body.toString());

        return ResponseEntity.status(status).body(body);
    }

    /**
     * @param baseException
     * BaseException 을 처리하는 핸들러
     * */
    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<Object> onThrowException(BaseException baseException, HttpServletRequest request) {

        ErrorReason errorReasonHttpStatus = baseException.getErrorReasonHttpStatus();
        return handleExceptionInternal(baseException, errorReasonHttpStatus, null, request);
    }

    /**
     * 실제 애러 핸들링 및 JSON 리턴
     * */
    private ResponseEntity<Object> handleExceptionInternal(Exception exception, ErrorReason reason, HttpHeaders headers, HttpServletRequest request) {

        BaseResponse<Object> body = BaseResponse.onFailure(reason.getCode(),reason.getMessage(),null);

        return ResponseEntity.status(reason.getHttpStatus()).body(body);
    }
}
