package member.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import email.send.MailSendService;
import member.dto.MemberDTO;
import member.service.MemberService;

@RestController
@CrossOrigin
public class MemberController {
	@Resource(name = "mailSendService")
	private MailSendService mailSendService;
	@Autowired
	private MemberService memberService;
	
	//회원 가입 버튼 눌렀을 때
    @PostMapping("/signup")
    public void signup(@RequestBody MemberDTO dto) {
    	System.out.println("회원가입 : "+dto);
    	memberService.signup(dto);
    	mailSendService.sendMail(dto.getEmail(),dto.getName());
    	
    	
    }
    
    //메일 인증 눌렀을 때
    //해당 아이디의 emailcheck를 업데이트하고 로그인 페이지로 넘어감
    @GetMapping("/updatemailcheck")
    public ResponseEntity<Object> updateEmailCheck(@RequestParam String email) 
    		throws URISyntaxException
    {
    	//해당 이메일 업데이트
    	memberService.updateEmailCheck(email);
    	//업데이트 후 로그인 페이지로 이동
    	URI redirectUri = new URI("http://localhost:3000/signin");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }
    
    
    @PostMapping("/login")
    @ResponseBody
    public Map<String,Object> login(@RequestBody Map<String,Object> loginMap){
    	System.out.println(loginMap);
    	Map<String,Object> map = new HashMap<String, Object>();
    	int count = memberService.emailCount(loginMap);
    	System.out.println(count);
    	//해당 이메일 계정이 없을 때
    	if(count == 0) {
    		map.put("login", "n");
    		
    	}else {
    		MemberDTO dto = memberService.login(loginMap);
    		//이메일 인증을 안했을 때
    		if(!dto.getEmailcheck().equals("y")) {
        		map.put("emailcheck", dto.getEmailcheck());
        		map.put("login", "y");
        	}else {
        		map.put("dto", dto);
        		map.put("login", "y");
        	}
    	}
    	return map;
    }
    
}
