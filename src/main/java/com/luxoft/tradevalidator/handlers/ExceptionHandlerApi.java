package com.luxoft.tradevalidator.handlers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.luxoft.tradevalidator.exception.TradeValidationException;
import com.luxoft.tradevalidator.vo.ApiErrorVO;
import com.luxoft.tradevalidator.vo.DetailedErrorVO;
import com.luxoft.tradevalidator.vo.ObjectDetailedErrorVO;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlerApi extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TradeValidationException.class)
    protected ResponseEntity<Object> handleTradeValidationException(TradeValidationException ex) {
    	logger.error(ex);
    	ApiErrorVO apiError = new ApiErrorVO(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");
        
        List<ObjectDetailedErrorVO> allErrors = new ArrayList<>();
        ex.getTradeValidationList().forEach(error->{
        	List<DetailedErrorVO> fieldErrors = new LinkedList<>();
        	error.getFieldErrors().forEach((field, messages)-> {
        		fieldErrors.add(new DetailedErrorVO(field, messages)); 
        	});
        	allErrors.add(new ObjectDetailedErrorVO(error.getTradeData(), fieldErrors));
        });
        
        apiError.setSubTreeErrors(allErrors);
        return buildResponseEntity(apiError);
    }
    
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
    	logger.error(ex);
        return buildResponseEntity(new ApiErrorVO(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error."));
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
    	logger.error(ex);
        return buildResponseEntity(new ApiErrorVO(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
    	
        ApiErrorVO apiError = new ApiErrorVO(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
    	
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        
        return buildResponseEntity(new ApiErrorVO(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
    }
    
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ApiErrorVO(HttpStatus.BAD_REQUEST, error, ex));
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiErrorVO(HttpStatus.BAD_REQUEST, error, ex));
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ApiErrorVO apiError = new ApiErrorVO(HttpStatus.BAD_REQUEST);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        
        return buildResponseEntity(apiError);
    }
	
    private ResponseEntity<Object> buildResponseEntity(ApiErrorVO apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
	
}