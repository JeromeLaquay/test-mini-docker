package com.jeroka.subscription.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire global des exceptions pour l'API.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Structure de réponse d'erreur.
     */
    private static class ApiError {
        private final String message;
        private final String status;
        private final LocalDateTime timestamp;
        private final Object details;

        public ApiError(String message, String status, Object details) {
            this.message = message;
            this.status = status;
            this.timestamp = LocalDateTime.now();
            this.details = details;
        }

        public String getMessage() {
            return message;
        }

        public String getStatus() {
            return status;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public Object getDetails() {
            return details;
        }
    }

    /**
     * Gère les exceptions de validation des arguments de méthode.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiError apiError = new ApiError(
                "Erreur de validation des données",
                HttpStatus.BAD_REQUEST.toString(),
                errors);

        logger.warn("Erreur de validation: {}", errors);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gère les exceptions de violation de contraintes.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String path = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(path, message);
        });

        ApiError apiError = new ApiError(
                "Violation de contraintes",
                HttpStatus.BAD_REQUEST.toString(),
                errors);

        logger.warn("Violation de contraintes: {}", errors);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gère les exceptions EntityNotFound.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.toString(),
                null);

        logger.warn("Entité non trouvée: {}", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    /**
     * Gère les exceptions d'accès refusé.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
        ApiError apiError = new ApiError(
                "Accès refusé: Vous n'avez pas les autorisations nécessaires pour effectuer cette action",
                HttpStatus.FORBIDDEN.toString(),
                ex.getMessage());

        logger.warn("Accès refusé: {}", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    /**
     * Gère toutes les autres exceptions non traitées.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllOtherExceptions(Exception ex) {
        ApiError apiError = new ApiError(
                "Une erreur inattendue s'est produite",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                ex.getMessage());

        logger.error("Erreur inattendue: ", ex);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 