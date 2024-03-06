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
@Alias("SportDto")
public class SportDto {
	private int userId; // 유저아이디
	
	private int sportId; // 운동아이디
	private String sportName; // 운동이름
	private Integer sportMet; // Met
}
