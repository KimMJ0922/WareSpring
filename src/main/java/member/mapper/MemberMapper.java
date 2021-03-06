package member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import member.dto.MemberDTO;
import point.dto.PointDto;

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
	public int nameOverlapCheck(Map<String, Object> map);
	public int emailFound(String email);
	public void passwordUpdate(Map<String,String> map);
	//출석 체크 확인
	public int attendanceCheck(String email);
	//번호 찾기
	public int attendanceMemberNo(String email);
	//출석 체크 넣기
	public void attendanceInsert(Map<String, Object> map);
	//맴버 테이블 업데이트 
	public void updateMemberPoint(Map<String, Object> map);
	
	//프로필 이미지 업로드
	public void profileImgUpdate(Map<String, String> map);
	
	public void updateProfileImg(Map<String, Object> map);
	//이름 업데이트
	public void updateName(Map<String, Object> map);
	
	//비밀번호 업데이트
	public void updatePassword(Map<String, Object> map);
	
	public String getProfileName(Map<String, Object> map);
	
	public void deleteMember(Map<String, Object> map);
	
	public MemberDTO getMember(int no);
	
	//포인트 내역 조회
	public List<PointDto> pointHistory(String member_no);
}
