package member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import member.mapper.MemberMapper;


@Service
public class MemberServiceImps implements MemberService{
	@Autowired
	private MemberMapper member;
	
    
}
