package com.nutrimate.nutrimatebackend.mapper.member;

import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.member.MemberDto;

@Mapper
public interface MemberMapper {

  int insertMember(MemberDto memberDto);

  int insertMemberWithOAuth(MemberDto memberDto);

  int insertOAuth(MemberDto memberDto);

  MemberDto findCommonMemberByUid(String userUid);

  MemberDto findOAuthMemberByProviderId(String providerId);

  MemberDto findMemberInfoById(Long userId);

  int updateMemberInfo(MemberDto memberDto);

  int updateMemberDiet(MemberDto memberDto);

  boolean checkPhoneNumber(String userPhone);

  boolean checkNick(String nickName);

  boolean checkEmail(String email);



}
