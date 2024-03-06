package com.nutrimate.nutrimatebackend.controller.member;

import com.nutrimate.nutrimatebackend.config.login.auth.PrincipalDetails;
import com.nutrimate.nutrimatebackend.model.member.MemberDto;
import com.nutrimate.nutrimatebackend.service.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@Log4j2
public class IndexController {
	
	
	private MemberService memberService; // 필드 주입
	private BCryptPasswordEncoder passwordEncoder;
	
	// 필드 주입의 단점
	// 테스트 코드 작성 시 정보를 가져올 수 없다
	
	// 생성자 주입
	public IndexController(MemberService memberService, BCryptPasswordEncoder passwordEncoder) {
		this.memberService = memberService;
		this.passwordEncoder = passwordEncoder;
	}
	
	// @GetMapping("/test/login")
	// public @ResponseBody String testLogin(Authentication authentication,
	// @AuthenticationPrincipal PrincipalDetails userDetails) {// DI(의존성 주입)
	// System.out.println("/test/login ========");
	// PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
	// System.out.println("authentication:" + principalDetails.getMemberDto());
	//
	// System.out.println("userDetails:" + userDetails.getUsername());
	// return "세션정보 확인하기";
	// }
	//
	// @GetMapping("/test/oauth/login")
	// public @ResponseBody String testOAuthLogin(Authentication authentication,
	// @AuthenticationPrincipal OAuth2User oauth) {// DI(의존성 주입)
	// System.out.println("/test/oauth/login ========");
	// OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
	// System.out.println("authentication:" + oauth2User.getAttributes());
	// System.out.println("oauth2User:" + oauth.getAttributes());
	// return "OAuth 세션정보 확인하기";
	// }
	
	// 스프링 시큐리티 자기만의 시큐리티 세션을 가지고있다
	// x라는 클래스를 만들어서 userDetails & OAuth2User 상속받아 사용
	
	@GetMapping({"", "/"})
	public String index() {
		return "index";
	}
	
	
	// OAuth 로그인을 해도 Principal
	@GetMapping("/api/v1/user")
	public Map user(Authentication authentication) {
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		Map<String, Object> json = new HashMap<>();
		json.put("user", "json");
		return json;
	}
	
	@GetMapping("/api/v1/admin")
	public String admin() {
		return "admin";
	}
	
	@GetMapping("/api/v1/manager")
	public String manager() {
		return "manager";
	}
	
	
	// @GetMapping("/loginForm")
	// public String loginForm() {
	// return "loginForm";
	// }
	//
	// @GetMapping("/joinForm")
	// public String joinForm() {
	// return "joinForm";
	// }
	
	
	@PostMapping("/member/join")
	public Map<String, Object> insertMember(@RequestBody MemberDto memberDto) {
		memberDto.setUserPwd(passwordEncoder.encode(memberDto.getUserPwd()));
		memberDto.setUserRole("ROLE_USER");
		memberService.insertMember(memberDto);
		Map<String, Object> json = new HashMap<>();
		json.put("memberDto", memberDto);
		System.out.println(memberDto);
		return json;
	}
	
	@PostMapping("/member/checkNick")
	public ResponseEntity<Map<String, Boolean>> checkNick(@RequestBody Map<String, String> params) {
		String nickName = params.get("userNick");
		
		boolean exists = memberService.checkNick(nickName);
		Map<String, Boolean> response = new HashMap();
		response.put("exists", exists);
		return ResponseEntity.ok().body(response);
	}
	
	
	@GetMapping("/member/mypage")
	public Map<String, Object> myPage(@RequestParam(value = "userId", required = false) Long userId) {
		System.out.println("userId :" + userId);
		MemberDto memberDto = memberService.findMemberInfoById(userId);
		Map<String, Object> json = new HashMap<>();
		json.put("memberDto", memberDto);
		return json;
	}
	
	
	@PutMapping("/member/mypage")
	public MemberDto updateMemberInfo(@RequestBody MemberDto memberDto) {
		log.info(memberDto);
		memberService.updateMemberInfo(memberDto);
		memberService.updateMemberDiet(memberDto);
		// memberDto.setUserPwd(passwordEncoder.encode(memberDto.getUserPwd()));
		return memberDto;
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	public String info() {
		return "개인정보";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/data")
	public String data() {
		return "데이터정보";
	}
	
	
}
