package com.jeroka.paymentservice.service;

import com.jeroka.paymentservice.dto.PaymentRequestDTO;
import com.jeroka.paymentservice.dto.PaymentResponseDTO;
import com.jeroka.paymentservice.entity.Payment;
import com.jeroka.paymentservice.mapper.PaymentMapper;
import com.jeroka.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    
    @Transactional
    public Payment savePayment(Payment payment) {
        payment.setStatus("completed"); // Par défaut pour PayPal
        return paymentRepository.save(payment);
    }
} 