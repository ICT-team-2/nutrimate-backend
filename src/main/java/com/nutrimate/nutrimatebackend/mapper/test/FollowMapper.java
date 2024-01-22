package com.nutrimate.nutrimatebackend.mapper.test;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.nutrimate.nutrimatebackend.model.member.MemberDto;

@Mapper
public interface FollowMapper {

  @Select(value = "SELECT sysdate FROM dual")
  String getTimeByAnnotation();

  @Select(value = "SELECT * FROM member")
  List<MemberDto> getAllUser();

}
