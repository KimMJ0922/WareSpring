package member.service;

import java.util.Map;

import member.dto.MemberDTO;


public interface MemberService{
	//회원가입
	public void signup(MemberDTO dto);
	//이메일 인증
	public void updateEmailCheck(String email);
	//이메일 인증 여부 확인
	public String emailAuth(String email);
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
	public boolean nameOverlapCheck(Map<String, Object> map);
	//이메일 여부 확인
	public boolean emailFound(String email);
	//비밀번호 업데이트
	public void passwordUpdate(Map<String,String> map);
	//프로필 이미지 업로드
	public void profileImgUpdate(Map<String, String> map);	
	//선택한 프로필 이미지 업데이트
	public void updateProfileImg(Map<String, Object> map);
	//이름 업데이트
	public void updateName(Map<String, Object> map);
	//비밀번호 업데이트
	public void updatePassword(Map<String, Object> map);
	//프로필 파일명 가져오기
	public String getProfileName(Map<String, Object> map);
	//정보 지우기
	public void deleteMember(Map<String, Object> map);
	//회원 정보 가져오기
	public MemberDTO getMember(int no);
}
