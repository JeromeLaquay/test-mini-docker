package com.jeroka.paymentservice.mapper;

import com.jeroka.paymentservice.dto.PaymentRequestDTO;
import com.jeroka.paymentservice.dto.PaymentResponseDTO;
import com.jeroka.paymentservice.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentRequestDTO dto) {
        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setCurrency(dto.getCurrency());
        payment.setPaymentMethodId(dto.getPaymentMethodId());
        payment.setUserId(dto.getUserId());
        payment.setOrderId(dto.getOrderId());
        payment.setPaymentIntentId(dto.getPaymentIntentId());
        return payment;
    }

    public PaymentResponseDTO toDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setCurrency(payment.getCurrency());
        dto.setStatus(payment.getStatus());
        dto.setPaymentMethodId(payment.getPaymentMethodId());
        dto.setUserId(payment.getUserId());
        dto.setOrderId(payment.getOrderId());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setUpdatedAt(payment.getUpdatedAt());
        dto.setPaymentIntentId(payment.getPaymentIntentId());
        dto.setErrorMessage(payment.getErrorMessage());
        return dto;
    }
} 