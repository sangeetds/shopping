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
        logger.error(ex) { "Encountered error in the server: "}
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    /**
     * Handles all other exceptions and returns a standardized error response.
     *
     * @param ex The exception thrown.
     * @param request The web request during which the exception was thrown.
     * @return A ResponseEntity containing the error code and message.
     */
    @ExceptionHandler(Exception::class)
    fun handleException(ex: BaseDataNotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = "ERR002",
            errorMessage = ex.message ?: "Item not found in the server."
        )
        logger.error(ex) { "Item not found in the server: "}
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }
}