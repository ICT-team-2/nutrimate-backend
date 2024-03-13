package com.nutrimate.nutrimatebackend.model.record;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
@Alias("SportRecordDto")
public class SportRecordDto extends SportDto {
	RecordDto record;
	
	//운동기록 관련
	private int exerciseId; //운동기록 번호
	private Double sportCal; //운동 소모 칼로리
	private Double sportWeight; //운동 기록 시 몸무게
	private Integer sportSet; //운동 횟수
	private Integer sportTime; //운동 시간
	
}
