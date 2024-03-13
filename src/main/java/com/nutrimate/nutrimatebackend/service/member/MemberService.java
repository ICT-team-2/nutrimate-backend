package com.nutrimate.nutrimatebackend.service.member;

import com.nutrimate.nutrimatebackend.mapper.member.MemberMapper;
import com.nutrimate.nutrimatebackend.model.member.MemberDto;
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
	
	public MemberDto findMemberInfoById(Long userId) {
		MemberDto memberDto = memberMapper.findMemberInfoById(userId);
		log.info("findMemberInfoById: " + memberDto);
		return memberDto;
	}
	
	double getUserCalToUserInfo(MemberDto memberDto) {
		//Mifflin-St Jeor 공식
		double bmr = switch (memberDto.getUserGender()) {
			case "M" -> (10 * memberDto.getUserWeight()) + (6.25 * memberDto.getUserHeight()) - 5;
			case "F" -> (10 * memberDto.getUserWeight()) + (6.25 * memberDto.getUserHeight()) - 161;
			default -> 0.0;
		};
		
		return switch (memberDto.getUserSportHard()) {
			case "LOW" -> bmr * 1.2;
			case "MEDIUM" -> bmr * 1.375;
			case "HIGH" -> bmr * 1.9;
			default -> 0.0;
		};
	}
	
	public MemberDto updateMemberInfo(MemberDto memberDto) {
		log.info("updateMemberInfo1: " + memberDto);
		if (memberDto.getUserCal() == 0.0)
			memberDto.setUserCal(getUserCalToUserInfo(memberDto));
		log.info("updateMemberInfo2: " + memberDto);
		memberMapper.updateMemberInfo(memberDto);
		return memberDto;
	}
	
	public boolean checkInsertNutriRatio(MemberDto memberDto) {
		return memberMapper.findCheckInsertNutriRatio(memberDto) == 1;
	}
	
	public int insertMemberDiet(MemberDto memberDto) {
		return memberMapper.insertMemberDiet(memberDto);
	}
	
	public MemberDto updateMemberDiet(MemberDto memberDto) {
		if (!checkInsertNutriRatio(memberDto))
			insertMemberDiet(memberDto);
		else
			memberMapper.updateMemberDiet(memberDto);
		return memberDto;
	}
	
	public boolean checkNick(String nickName) {
		boolean exists = memberMapper.checkNick(nickName);
		return exists;
	}
	
	public int deleteMember(int userId) {
		return memberMapper.deleteMember(userId);
	}
}
