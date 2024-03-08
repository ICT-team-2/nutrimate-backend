package com.nutrimate.nutrimatebackend.service.pay;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.mapper.pay.PaymentMapper;
import com.nutrimate.nutrimatebackend.model.pay.PaymentDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PaymentService {
  private PaymentMapper paymentMapper;

  public PaymentService(PaymentMapper paymentMapper) {
    this.paymentMapper = paymentMapper;
  }

  public List<PaymentDto> getPaymentList(Long userId) {
    log.info("paymentMapper: " + paymentMapper);

    return paymentMapper.findByPaymentUserId(userId);
  }

  public int insertPayment(Map map) {
    return paymentMapper.insertPayment(map);
  }

}
