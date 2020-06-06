package br.com.trevezani.tutorial.census.infrastructure.configuration;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
    	CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(ex.getMessage());
        errors.setCorrelationId(MDC.get("correlationId"));
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
    	if (ex instanceof NullPointerException) {
            errors.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            return new ResponseEntity<>(errors, headers, HttpStatus.BAD_REQUEST);
        } else {
            errors.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            return new ResponseEntity<>(errors, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

class CustomErrorResponse {
	private LocalDateTime timestamp;
	private String status;
	private String message;
	private String correlationId;

	public CustomErrorResponse() {
		
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
}