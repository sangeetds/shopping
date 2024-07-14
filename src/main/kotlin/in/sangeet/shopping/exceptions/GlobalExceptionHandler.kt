package `in`.sangeet.shopping.exceptions

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

/**
 * Global exception handler for catching and handling all exceptions.
 */
@ControllerAdvice
class GlobalExceptionHandler {

    val logger = KotlinLogging.logger {}

    /**
     * Handles RuntimeExceptions and returns a standardized error response.
     *
     * @param ex The exception thrown.
     * @param request The web request during which the exception was thrown.
     * @return A ResponseEntity containing the error code and message.
     */
    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = "ERR001",
            errorMessage = ex.message ?: "An unexpected error occurred"
        )
        logger.error(ex) { "Encountered error in the server: " }
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    /**
     * Handles BaseDataNotFoundException exceptions and returns an error response.
     *
     * @param ex The exception thrown.
     * @param request The web request during which the exception was thrown.
     * @return A ResponseEntity containing the error code and message.
     */
    @ExceptionHandler(BaseDataNotFoundException::class)
    fun handleException(ex: BaseDataNotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = "ERR002",
            errorMessage = ex.message ?: "Item not found in the server."
        )
        logger.error(ex) { "Item not found in the server: ${ex.message}" }
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }


    /**
     * Handles all UserAlreadyExistsException exceptions and returns a 400 error response.
     *
     * @param ex The exception thrown.
     * @param request The web request during which the exception was thrown.
     * @return A ResponseEntity containing the error code and message.
     */
    @ExceptionHandler(value = [UserAlreadyExistsException::class, UserDetailsNotCorrectException::class, ProductDetailsNotCorrectException::class, ProductAlreadyExistsException::class])
    fun handleException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = "ERR003",
            errorMessage = ex.message ?: "User already exists with the given email or username"
        )
        logger.error(ex) { "Data already exists " }
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

}