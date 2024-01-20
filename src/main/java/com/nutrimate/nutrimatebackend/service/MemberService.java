package com.nutrimate.nutrimatebackend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nutrimate.nutrimatebackend.mapper.MemberMapper;
import com.nutrimate.nutrimatebackend.model.user.MemberDto;


@Service
public class MemberService {
  private MemberMapper memberMapper;
  private PasswordEncoder passwordEncoder;

  public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
    this.memberMapper = memberMapper;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public int insertMember(MemberDto memberDto) {
    String rawPassword = memberDto.getUserPwd();
    String encodePassword = passwordEncoder.encode(rawPassword);
    memberDto.setUserPwd(encodePassword);
    memberDto.setUserRole("ROLE_USER");
    return memberMapper.insertMember(memberDto);
  }

  public MemberDto findByUser(String userUid) {
    return memberMapper.findByUser(userUid);
  }
}
