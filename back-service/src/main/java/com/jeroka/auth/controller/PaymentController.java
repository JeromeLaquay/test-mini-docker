package com.jeroka.auth.controller;

import com.jeroka.auth.dto.*;
import com.jeroka.auth.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:8080")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay-by-card")
    public ResponseEntity<String> payByCard(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        return paymentService.payByCard(paymentRequestDTO);
    }
}
