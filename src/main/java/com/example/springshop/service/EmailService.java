package com.example.springshop.service;

import com.example.springshop.dto.BucketDTO;
import com.example.springshop.dto.BucketDetailsDTO;
import com.example.springshop.dto.EmailContext;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final BucketService bucketService;

    public EmailService(JavaMailSender javaMailSender, BucketService bucketService) {
        this.javaMailSender = javaMailSender;
        this.bucketService = bucketService;
    }

    public void sendEmail() throws MessagingException {
        BucketDTO bucketDTO = bucketService.getBucketByUser("admin");

        String emailContent = "В вашей корзине " + bucketDTO.getBucketDetails().get(0).getAmount() + " товаров на сумму " + bucketDTO.getSum() + ".";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());

        helper.setTo("matveijzuev@gmail.com");
        helper.setSubject("Корзина покупок");
        helper.setFrom("zuevm1083@gmail.com");
        helper.setText(emailContent, true);

        javaMailSender.send(message);
    }
}
