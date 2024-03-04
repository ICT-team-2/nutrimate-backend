package com.nutrimate.nutrimatebackend.controller.pay;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.pay.PaymentDto;
import com.nutrimate.nutrimatebackend.service.pay.PaymentService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class PaymentController {
  private PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping("/processPayment")
  public List<PaymentDto> processPayment(@RequestBody Long userId) {
    List<PaymentDto> paymentList = paymentService.getPaymentList(userId);
    for (PaymentDto paymentDto : paymentList) {
      log.info("paymentDto: " + paymentDto.getUserId());
      log.info("paymentDto: " + paymentDto.getPaymentId());
      log.info("paymentDto: " + paymentDto.getPrice());
      log.info("paymentDto: " + paymentDto.getPayMethod());
      log.info("paymentDto: " + paymentDto.getPayDate());
      log.info("paymentDto: " + paymentDto.getPriceType());
    }

    return null;
  }

  @PostMapping("/Payment/Write.do")
  public int insertList(@RequestBody Map map) {
    int updatePayment = paymentService.updatePayment(map);
    int insertPayment = paymentService.insertPayment(map);
    return insertPayment;
  }
}

