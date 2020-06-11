package member.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
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
import member.service.MemberEncryption;
import member.service.MemberService;

@RestController
@CrossOrigin
public class MemberController {
	@Resource(name = "mailSendService")
	private MailSendService mailSendService;
	@Autowired
	private MemberService memberService;
	
	
	@PostMapping("/emailoverlap")
	@ResponseBody
	public Map<String,Object> emailOverlapCheck(@RequestBody String email) {
		//이메일 뒤에 =가 붙는다. 왜지?
		email = email.substring(0,email.length()-1);
		String emailFormat = "";
		try {
			emailFormat = URLDecoder.decode(email, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		boolean check = memberService.emailOverlapCheck(emailFormat);
		map.put("check",check);
		return map;
	}
	//회원 가입 버튼 눌렀을 때
    @PostMapping("/signup")
    public void signup(@RequestBody MemberDTO dto) {
    	MemberEncryption encry = new MemberEncryption();
    	dto.setPassword(encry.encryption(dto.getPassword()));
    	System.out.println(dto.getPassword());
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
    
    //일반 로그인
    @PostMapping("/login")
    @ResponseBody
    public Map<String,Object> login(@RequestBody Map<String,Object> loginMap){
    	Map<String,Object> map = new HashMap<String, Object>();
    	
    	String password = (String)loginMap.get("password");
    	MemberEncryption encry = new MemberEncryption();
    	
    	password = encry.encryption(password);
    	loginMap.put("password",password);
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
    
    //소셜 로그인
    @PostMapping("/sociallogin")
    @ResponseBody
    public Map<String,Object> socialLogin(@RequestBody Map<String,Object> socialLoginMap){
    	System.out.println(socialLoginMap);
    	Map<String,Object> map = new HashMap<String, Object>();
    	
    	//카카오 아이디는 int형
    	//update시 테이블 타입 string과 int형이 맞지 않아 못찾는다함.
    	String email = socialLoginMap.get("email").toString();
    	socialLoginMap.put("email",email);
    	
    	//소셜 아이디가 있는지 검사
    	int cnt = memberService.socialCount(socialLoginMap);
    	System.out.println("cnt = "+cnt);
    	//없는 경우
    	if(cnt == 0) {
    		System.out.println("1번");
    		memberService.socialSignUp(socialLoginMap);
    	}
    	//있는 경우
    	else {
    		System.out.println("2번");
    		memberService.socialUpdate(socialLoginMap);
    	}
    	
    	MemberDTO dto = memberService.socialLogin(socialLoginMap);
    	map.put("dto", dto);
    	return map;
    }
    
}
