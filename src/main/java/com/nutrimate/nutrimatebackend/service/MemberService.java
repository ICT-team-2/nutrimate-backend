package com.nutrimate.nutrimatebackend.service;

import com.nutrimate.nutrimatebackend.mapper.MemberMapper;
import com.nutrimate.nutrimatebackend.model.user.MemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
}
