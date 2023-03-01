package com.eltxoko.txokoweb.exception

import org.springframework.http.HttpStatus

open class HttpException(msg: String, val status: HttpStatus) : RuntimeException(msg)

open class Exception400(msg: String) : HttpException(msg, HttpStatus.BAD_REQUEST)
open class Exception401(msg: String) : HttpException(msg, HttpStatus.UNAUTHORIZED)
open class Exception403(msg: String) : HttpException(msg, HttpStatus.FORBIDDEN)
open class Exception404(msg: String) : HttpException(msg, HttpStatus.NOT_FOUND)
open class Exception409(msg: String) : HttpException(msg, HttpStatus.CONFLICT)

open class Exception500(msg: String) : HttpException(msg, HttpStatus.INTERNAL_SERVER_ERROR)
