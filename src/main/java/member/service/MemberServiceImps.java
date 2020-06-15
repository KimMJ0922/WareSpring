package member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import member.dto.MemberDTO;
import member.mapper.MemberMapper;

@Service
public class MemberServiceImps implements MemberService{
	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public void signup(MemberDTO dto) {
		// TODO Auto-generated method stub
		memberMapper.signup(dto);
	}
	
	@Override
	public void updateEmailCheck(String email) {
		// TODO Auto-generated method stub
		memberMapper.updateEmailCheck(email);
	}
	
	@Override
	public String emailAuth(String email) {
		// TODO Auto-generated method stub
		return memberMapper.emailAuth(email);
	}
	
	@Override
	public MemberDTO login(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String email = map.get("email").toString();
		int cnt = memberMapper.attendanceCheck(email);
		if(cnt == 0) {
			memberMapper.attendanceInsert(email);
			memberMapper.updateMemberPoint(email);
		}
		return memberMapper.login(map);
	}
	
	@Override
	public int emailCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		System.out.println(map);
		return memberMapper.emailCount(map);
	}
	
	@Override
	public int socialCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return memberMapper.socialCount(map);
	}
	
	@Override
	public void socialSignUp(Map<String, Object> map) {
		// TODO Auto-generated method stub
		memberMapper.socialSignUp(map);
	}
	
	@Override
	public void socialUpdate(Map<String, Object> map) {
		// TODO Auto-generated method stub
		memberMapper.socialUpdate(map);
	}
	
	@Override
	public MemberDTO socialLogin(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String email = map.get("email").toString();
		int cnt = memberMapper.attendanceCheck(email);
		if(cnt == 0) {
			memberMapper.attendanceInsert(email);
			memberMapper.updateMemberPoint(email);
		}
		return memberMapper.socialLogin(map);
	}
	
	@Override
	public boolean emailOverlapCheck(String email) {
		// TODO Auto-generated method stub
		return memberMapper.emailOverlapCheck(email) == 1 ? true:false;
	}
	
	@Override
	public boolean nameOverlapCheck(String name) {
		// TODO Auto-generated method stub
		return memberMapper.nameOverlapCheck(name) == 1 ? true:false;
	}
	
	@Override
	public boolean emailFound(String email) {
		// TODO Auto-generated method stub
		return memberMapper.emailFound(email) == 1 ? true : false ;
	}
	
	@Override
	public void passwordUpdate(Map<String, String> map) {
		// TODO Auto-generated method stub
		memberMapper.passwordUpdate(map);
	}
}
