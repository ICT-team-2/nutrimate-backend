package com.nutrimate.nutrimatebackend.service.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nutrimate.nutrimatebackend.mapper.member.MemberMapper;
import com.nutrimate.nutrimatebackend.model.member.MemberDto;
import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class MemberService {
  private MemberMapper memberMapper;
  private PasswordEncoder passwordEncoder;

  public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
    this.memberMapper = memberMapper;
    this.passwordEncoder = passwordEncoder;
  }

  public int insertMember(MemberDto memberDto) {
    return memberMapper.insertMember(memberDto);
  }

  public MemberDto findCommonMemberByUid(String userUid) {
    MemberDto memberDto = memberMapper.findCommonMemberByUid(userUid);
    log.info("memberDto: " + memberDto);
    return memberDto;
  }

  @Transactional
  public MemberDto insertOAuthMember(MemberDto memberDto) {
    memberMapper.insertMemberWithOAuth(memberDto);
    memberMapper.insertOAuth(memberDto);
    return memberDto;
  }

  public MemberDto findMemberInfoById(Long userId) {
    MemberDto memberDto = memberMapper.findMemberInfoById(userId);
    log.info("findMemberInfoById: " + memberDto);
    return memberDto;
  }


  public MemberDto updateMemberInfo(MemberDto memberDto) {
    log.info("updateMemberInfo: " + memberDto);
    memberMapper.updateMemberInfo(memberDto);
    return memberDto;
  }

  public MemberDto updateMemberDiet(MemberDto memberDto) {
    memberMapper.updateMemberDiet(memberDto);
    return memberDto;
  }

  public boolean checkNick(String nickName) {
    boolean exists = memberMapper.checkNick(nickName);
    return exists;
  }
}