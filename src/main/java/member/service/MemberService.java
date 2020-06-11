package member.service;

import java.util.Map;

import member.dto.MemberDTO;


public interface MemberService{
	//회원가입
	public void signup(MemberDTO dto);
	//이메일 인증
	public void updateEmailCheck(String email);
	//로그인 
	public MemberDTO login(Map<String,Object> map);
	//로그인 하기 전 체크
	public int emailCount(Map<String,Object> map);
}
