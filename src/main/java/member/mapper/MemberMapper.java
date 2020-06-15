package member.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import member.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	public MemberDTO login(String email);
	public void signup(MemberDTO dto);
	public void updateEmailCheck(String email);
	public MemberDTO login(Map<String,Object> map);
	public String emailAuth(String email);
	public int emailCount(Map<String,Object> map);
	public int socialCount(Map<String,Object> map);
	public void socialSignUp(Map<String,Object> map);
	public void socialUpdate(Map<String,Object> map);
	public MemberDTO socialLogin(Map<String,Object> map);
	public int emailOverlapCheck(String email);
	public int nameOverlapCheck(String name);
	public int emailFound(String email);
	public void passwordUpdate(Map<String,String> map);
	//출석 체크 확인
	public int attendanceCheck(String email);
	//번호 찾기
	public int attendanceMemberNo(String email);
	//출석 체크 넣기
	public void attendanceInsert(String email);
	//맴버 테이블 업데이트 
	public void updateMemberPoint(String email);
	
	//프로필 이미지 업로드
	public void profileImgUpdate(Map<String, String> map);
	
	public int memberNo(String email);
}
