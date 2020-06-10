package member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import member.dto.MemberDTO;
import member.service.MemberService;

@Controller
public class MemberController {
	private MemberService ms;
	
	 // 메인 페이지
    @GetMapping("/")
    public String index() {
    	System.out.println("왔냐");
    	
        return "/index";
    }

    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String dispSignup() {
        return "signup";
    }

}
