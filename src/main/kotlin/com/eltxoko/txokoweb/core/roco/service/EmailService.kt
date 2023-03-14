package com.eltxoko.txokoweb.core.roco.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class EmailService(
    private val emailSender: JavaMailSender,
) {

    private val charPool: List<Char> = ('A'..'Z') + ('0'..'9')

    @Async("mailExecutor")
    fun sendVerificationCode(email: String, code: String) {
        val message = SimpleMailMessage()
        message.setTo(email)
        message.subject = "초코로코 인증코드입니다."
        message.text = "인증코드: $code"
        emailSender.send(message)
    }

    fun createVerificationCode(): String {
        var code = ""
        val random = Random(System.nanoTime())
        for (i in 0 until 6) {
            code += charPool[random.nextInt(0, charPool.size)]
        }
        return code
    }
}
