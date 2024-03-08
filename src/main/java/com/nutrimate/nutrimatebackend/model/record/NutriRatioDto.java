package com.nutrimate.nutrimatebackend.model.record;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Alias("NutriRatioDto")
public class NutriRatioDto {
	private int userId;
	private int carbo;
	private int protein;
	private int provi;
}
