package com.nutrimate.nutrimatebackend.mapper.pay;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.pay.PaymentDto;

@Mapper
public interface PaymentMapper {
  public List<PaymentDto> findByPaymentUserId(Long userId);

  public int insertPayment(Map map);

  public int updatePayment(Map map);
}
