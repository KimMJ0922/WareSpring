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
}
