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
	//소셜 로그인 해당 아이디가 있는지 확인
	public int socialCount(Map<String,Object> map);
	//소셜 해당 계정 등록
	public void socialSignUp(Map<String,Object> map);
	//소셜 계정의 정보 업데이트
	public void socialUpdate(Map<String,Object> map);
	//소셜 계정 정보 
	public MemberDTO socialLogin(Map<String,Object> map);
	//이메일 중복 체크
	public boolean emailOverlapCheck(String email);
	//이름 중복 체크
	public boolean nameOverlapCheck(String name);
}
