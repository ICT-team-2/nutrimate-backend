package com.nutrimate.nutrimatebackend.model.pay;

import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("PaymentDto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {
  private Long userId;
  private Timestamp payDate;
  private int priceType;
  private int price;
  private String payMethod;
  private String paymentId;

}
