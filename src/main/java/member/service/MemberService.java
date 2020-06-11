package member.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import member.dto.MemberDTO;


public interface MemberService{
	public void signup(MemberDTO dto);
	public void updateEmailCheck(String email);
	public MemberDTO login(Map<String,Object> map);
}
