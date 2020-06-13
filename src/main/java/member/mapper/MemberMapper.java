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
	public int emailCount(Map<String,Object> map);
	public int socialCount(Map<String,Object> map);
	public void socialSignUp(Map<String,Object> map);
	public void socialUpdate(Map<String,Object> map);
	public MemberDTO socialLogin(Map<String,Object> map);
	public int emailOverlapCheck(String email);
	public int nameOverlapCheck(String name);
	public int emailFound(String email);
	public void passwordUpdate(Map<String,String> map);
}
