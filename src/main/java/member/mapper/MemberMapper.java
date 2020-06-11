package member.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import member.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	public MemberDTO login(String email);
	public void signup(MemberDTO dto);
	public void updateEmailCheck(String email);
	public MemberDTO login(Map<String,Object> map);
}
