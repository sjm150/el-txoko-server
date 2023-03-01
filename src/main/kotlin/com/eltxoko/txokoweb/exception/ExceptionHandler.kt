package com.eltxoko.txokoweb.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    fun handle(e: Exception): ResponseEntity<Any> = ResponseEntity(
        e.message,
        HttpStatus.INTERNAL_SERVER_ERROR,
    )

    @ExceptionHandler(HttpException::class)
    fun handle(e: HttpException): ResponseEntity<Any> = ResponseEntity(
        e.message,
        e.status,
    )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(e: MethodArgumentNotValidException): ResponseEntity<Any> = ResponseEntity(
        e.bindingResult.allErrors[0].defaultMessage,
        HttpStatus.BAD_REQUEST,
    )

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handle(e: HttpRequestMethodNotSupportedException): ResponseEntity<Any> = ResponseEntity(
        e.message,
        HttpStatus.BAD_REQUEST,
    )

    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun handle(e: HttpMessageNotReadableException): ResponseEntity<Any> = ResponseEntity(
        e.message,
        HttpStatus.BAD_REQUEST,
    )

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handle(e: MethodArgumentTypeMismatchException): ResponseEntity<Any> = ResponseEntity(
        e.message,
        HttpStatus.BAD_REQUEST,
    )
}
