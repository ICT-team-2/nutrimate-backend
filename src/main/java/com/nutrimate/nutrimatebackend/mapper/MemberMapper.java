package com.nutrimate.nutrimatebackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.user.MemberDto;

@Mapper
public interface MemberMapper {

  int insertMember(MemberDto memberDto);

  MemberDto findByUser(String userUid);
}
