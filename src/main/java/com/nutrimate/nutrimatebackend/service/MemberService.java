package com.nutrimate.nutrimatebackend.service;

import com.nutrimate.nutrimatebackend.mapper.MemberMapper;
import com.nutrimate.nutrimatebackend.model.user.MemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Log4j2
public class MemberService {
	private MemberMapper memberMapper;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
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
		memberMapper.insertMember(memberDto);
		log.info("memberDto: " + memberDto);
		return memberMapper.insertCommonMember(memberDto);
	}
	
	public MemberDto findCommonMemberByUid(String userUid) {
		MemberDto dto = memberMapper.findCommonMemberByUid(userUid);
		log.info("dto: " + dto);
		return dto;
	}
}
