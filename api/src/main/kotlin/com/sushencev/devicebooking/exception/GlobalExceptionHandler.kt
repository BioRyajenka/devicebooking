package com.sushencev.devicebooking.exception

import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest
import javax.validation.ValidationException


@RestControllerAdvice(annotations = [RestController::class])
class GlobalExceptionHandler {
    private val logger = LogManager.getLogger()

    @ExceptionHandler(ValidationException::class, IllegalArgumentException::class)
    fun handleValidationException(
        request: HttpServletRequest,
        ex: RuntimeException,
    ): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ex.message)
    }

    @ExceptionHandler(NotImplementedError::class)
    fun handleValidationException(
        request: HttpServletRequest,
        ex: NotImplementedError,
    ): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.NOT_IMPLEMENTED)
            .body(ex.message)
    }
}
