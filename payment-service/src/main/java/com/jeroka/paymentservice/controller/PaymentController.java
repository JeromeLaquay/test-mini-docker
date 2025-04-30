package com.jeroka.paymentservice.controller;

import com.jeroka.paymentservice.dto.PaymentRequestDTO;
import com.jeroka.paymentservice.dto.PaymentResponseDTO;
import com.jeroka.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    @Value("${PAYPAL_CLIENT_ID}")
    private String paypalClientId;

    @Value("${PAYPAL_CLIENT_SECRET}")
    private String paypalClientSecret;

    @Value("${PAYPAL_MODE}")
    private String paypalMode;

    @Value("${ROUTE_FRONTEND}")
    private String routeFrontend;

    private final APIContext apiContext;

    @Autowired
    private final PaymentService paymentService;

    public PaymentController() {
        // Remplace par tes vraies credentials PayPal
        this.apiContext = new APIContext(
            paypalClientId,
            paypalClientSecret,
            paypalMode // "sandbox" ou "live"
        );
    }

    @PostMapping("/paypal")
    public Map<String, String> payWithPaypal(@RequestParam double amount) throws PayPalRESTException {
        Amount payAmount = new Amount();
        payAmount.setCurrency("EUR");
        payAmount.setTotal(String.format("%.2f", amount));

        Transaction transaction = new Transaction();
        transaction.setDescription("Paiement via PayPal");
        transaction.setAmount(payAmount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(routeFrontend+"/api/payments/cancel");
        redirectUrls.setReturnUrl(routeFrontend+"/api/payments/success");
        payment.setRedirectUrls(redirectUrls);

        Payment createdPayment = payment.create(apiContext);

        // Retourne l'URL d'approbation PayPal
        String approvalUrl = createdPayment.getLinks().stream()
            .filter(link -> "approval_url".equals(link.getRel()))
            .findFirst()
            .map(Links::getHref)
            .orElse("");

        Map<String, String> response = new HashMap<>();
        response.put("approval_url", approvalUrl);
        return response;
    }

    public ResponseEntity<PaymentResponseDTO> processPayPalPayment(
            @Valid @RequestBody PaymentRequestDTO paymentRequest) {
        PaymentResponseDTO response = paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/success")
    public String paymentSuccess(@RequestParam String paymentId, @RequestParam String PayerID) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(PayerID);
        Payment executedPayment = payment.execute(apiContext, paymentExecution);
        return "Paiement réussi : " + executedPayment.getId();
    }

    @GetMapping("/cancel")
    public String paymentCancel() {
        return "Paiement annulé";
    }
} 