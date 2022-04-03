package com.trodix.episodate.core.exception;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.HttpStatusException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    // ===============================
    // = Handle specific exceptions
    // ===============================

    /**
     * For creating a custom payload exception response :
     */

    // @ExceptionHandler(BadRequestException.class)
    // public ResponseEntity<?> handleBadRequestException(BadRequestException exception, WebRequest
    // request) {
    // ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
    // request.getDescription(false));

    // return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    // }



    /**
     * For creating a standard payload for a custom exception :
     */

    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleResourceNotFoundException(final ResourceNotFoundException exception, final HttpServletResponse response)
            throws IOException {

        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(HttpStatusException.class)
    public void handleHttpStatusExceptionException(final HttpStatusException exception, final HttpServletResponse response)
            throws IOException {

        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public void handleBadRequestException(final BadRequestException exception, final HttpServletResponse response)
            throws IOException {

        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(final AccessDeniedException exception, final HttpServletResponse response)
            throws IOException {

        response.sendError(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public void handleBadCredentialsException(final BadCredentialsException exception, final HttpServletResponse response)
            throws IOException {

        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public void handleUnauthorizedException(final UnauthorizedException exception, final HttpServletResponse response)
            throws IOException {

        response.sendError(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
    }


    /**
     * Handle invalid path variable. ex: String to UUID conversion exception
     * 
     * @param exception
     * @param response
     * @throws IOException
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException exception, final HttpServletResponse response)
            throws IOException {

        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    /**
     * Handle validation exceptions
     * 
     * @param exception
     * @param response
     * @throws IOException
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        final List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        final ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorList);
        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
    }



    // ===============================
    // = Handle global exceptions
    // ===============================


    @ExceptionHandler(Exception.class)
    public void handleGlobalException(final Exception exception, final HttpServletResponse response)
            throws IOException {

        // response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An internal server error occured");
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }
}
