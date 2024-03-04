package com.nutrimate.nutrimatebackend.service.pay;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.mapper.pay.PaymentMapper;
import com.nutrimate.nutrimatebackend.model.pay.PaymentDto;

@Service
public class PaymentService {
  private PaymentMapper paymentMapper;

  public PaymentService(PaymentMapper paymentMapper) {
    this.paymentMapper = paymentMapper;
  }

  public List<PaymentDto> getPaymentList(Long userId) {
    return paymentMapper.findByPaymentUserId(userId);
  }

  public int insertPayment(Map map) {
    return paymentMapper.insertPayment(map);
  }

  public int updatePayment(Map map) {
    return paymentMapper.updatePayment(map);
  }
}
