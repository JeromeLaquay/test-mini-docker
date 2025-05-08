package com.jeroka.auth.service;

import com.jeroka.auth.exception.PaymentException;
import com.jeroka.auth.exception.ResourceNotFoundException;
import com.jeroka.auth.model.Payment;
import com.jeroka.auth.model.User;
import com.jeroka.auth.model.enums.PaymentStatus;
import com.jeroka.auth.repository.PaymentRepository;
import com.jeroka.auth.repository.UserRepository;
import com.jeroka.auth.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> payByCard(PaymentRequestDTO paymentRequestDTO) {
        return ResponseEntity.ok("Paiement effectué avec succès");
    }
} 