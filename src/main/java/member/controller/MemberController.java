package member.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import email.send.MailSendService;
import member.dto.MemberDTO;
import member.service.MemberEncryption;
import member.service.MemberService;
import point.dto.PointDto;
import spring.waregg.controller.LocalIPAddress;
import upload.util.SearchFile;
import upload.util.SpringFileWrite;

@RestController
@CrossOrigin
public class MemberController {
	LocalIPAddress lipa = new LocalIPAddress();
	final String ROOTPATH = "http://"+lipa.getLocalIpAddress()+":9000/";
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
    public void signup(@RequestBody Map<String, Object> userInfoMap) {
    	//JSON Object로 변환
		JSONObject jsonObject = new JSONObject(userInfoMap);
		//JSON 형태의 string 으로 변환
		String userInfoString = jsonObject.toJSONString();
		//string으로 변환한 것을 다시 JSONObject로 변환
		//제일 처음에 변환 한 것을 직접 JSONArray로 변환하면 
		//변환 오류가 생긴다. 이유는 모름
		JSONObject obj2 = (JSONObject)JSONValue.parse(userInfoString);
		JSONObject obj3 = (JSONObject)obj2.get("userInfo");
		
		MemberDTO dto = new MemberDTO();
    	MemberEncryption encry = new MemberEncryption();
    	//object에서 꺼낸 값을 dto에 저장
    	dto.setEmail(obj3.get("email").toString());
    	dto.setPassword(encry.encryption(obj3.get("password").toString()));
    	dto.setName(obj3.get("name").toString());
    	dto.setBirth(obj3.get("birth").toString());
    	//테이블 값 넣기
    	memberService.signup(dto);
    	//회원가입한 이메일에 메일 보내기
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
    	URI redirectUri = new URI("http://localhost:3000/");
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

    	//해당 이메일 계정이 없을 때
    	if(count == 0) {
    		map.put("login", "n");
    	}else {
    		//이메일 인증 여부 확인
    		String emailCheck = memberService.emailAuth(loginMap.get("email").toString());
    		
    		//이메일 인증을 안했을 때
    		if(!emailCheck.equals("y")) {
        		map.put("emailcheck", emailCheck);
        		map.put("login", "y");
        	}
    		//이메일 인증을 했을 때
    		else{
        		MemberDTO dto = memberService.login(loginMap);
        		//이미지 가져오기
        		String fileName = dto.getProfile();
        		fileName = fileName.substring(0,fileName.lastIndexOf("."));
        		if(fileName.equals(""+dto.getNo())) {
        			dto.setProfile(ROOTPATH+"profile/users/"+dto.getProfile());
        		}else {
        			dto.setProfile(ROOTPATH+"profile/default/"+dto.getProfile());
        		}

            	
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
    	Map<String,Object> map = new HashMap<String, Object>();
    	
    	//카카오 아이디는 int형
    	//update시 테이블 타입 string과 int형이 맞지 않아 못찾는다함.
    	String email = socialLoginMap.get("email").toString();
    	socialLoginMap.put("email",email);
    	
    	//소셜 아이디가 있는지 검사
    	int cnt = memberService.socialCount(socialLoginMap);

    	//없는 경우
    	if(cnt == 0) {
    		memberService.socialSignUp(socialLoginMap);
    	}
    	    	
    	MemberDTO dto = memberService.socialLogin(socialLoginMap);
    	if(dto.getProfile().indexOf("kakao") == -1 && dto.getProfile().indexOf("google") == -1) {
    		String fileName = dto.getProfile();
    		fileName = fileName.substring(0,fileName.lastIndexOf("."));
    		if(fileName.equals(""+dto.getNo())) {
    			dto.setProfile(ROOTPATH+"profile/users/"+dto.getProfile());
    		}else {
    			dto.setProfile(ROOTPATH+"profile/default/"+dto.getProfile());
    		}

    	}
    	map.put("dto", dto);
    	return map;
    }
    
    
    //이름 중복 체크
    @PostMapping("/nameoverlap")
    @ResponseBody
    public Map<String,Object> nameOverlapCheck(@RequestBody Map<String,Object> nameMap) {
    	Map<String,Object> map = new HashMap<String, Object>();
    	String name = (String)nameMap.get("name");
    	String provider = (String)nameMap.get("provider");
    	if(provider == null || provider == "") {
    		nameMap.put("provider", "default");
    		boolean check = memberService.nameOverlapCheck(nameMap);
        	map.put("check", check);
    	}else {
    		map.put("check", false);
    	}
    	return map;
    }
    
    //비밀번호 재설정
    @PostMapping("/forgotten")
    public Map<String,Object> tempPassword(@RequestBody Map<String,Object> emailMap) {
    	Map<String,Object> returnMap = new HashMap<String, Object>();
    	String email = emailMap.get("email").toString();
    	MemberEncryption encryption = new MemberEncryption();
    	Map<String, String> map = encryption.createTempPassword();
    	
    	//입력한 이메일이 있는지 검색
    	boolean check = memberService.emailFound(email);
    	//이메일이 없으면 없다고 보냄
    	if(!check) {
    		returnMap.put("check", false);
    		return returnMap;
    	}
    	
    	//임시 비밀번호 메일로 보내기
    	mailSendService.sendPassword(email,map.get("tempPassword").toString());
    	//db업데이트
    	map.put("email", email);
    	map.put("password", map.get("formatPassword").toString());
    	memberService.passwordUpdate(map);
    	returnMap.put("check", true);
    	return returnMap;
    }
    
    
    //프로필 이미지
    @RequestMapping(value = "/uploadProfileImg", 
			consumes = {"multipart/form-data"}, 
			method = RequestMethod.POST)
    public String uploadProfileImg(
    		@RequestParam MultipartFile uploadFile,
    		MultipartHttpServletRequest request,
    		@RequestParam String email,
    		@RequestParam String no
    ) {
    	//기존 프로필 파일 지우기
    	Map<String, Object> nomap = new HashMap<String, Object>();
    	nomap.put("no", no);
    	String profile = memberService.getProfileName(nomap);
    	System.out.println(profile);
    	if(profile.indexOf("google") == -1 && profile.indexOf("kakao") == -1) {
    		SearchFile sf = new SearchFile();
        	sf.userProfileDelete(request, profile);
    	}
    	
    	String path = request.getSession().getServletContext().getRealPath("/profile/users/");
    	    	
    	SpringFileWrite sfw = new SpringFileWrite();
    	
		String fileName = sfw.writeFile(uploadFile, path, no);
		
		try {
			fileName = URLDecoder.decode(fileName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("fileName", fileName);
		
		//프로필 이미지 업로드
		memberService.profileImgUpdate(map);
		
		
		return ROOTPATH+"profile/users/"+fileName;
    }
    
    @PostMapping("/defaultProfileImg")
    @ResponseBody
    public List<String> getDefaultProfileImg(
    		HttpServletRequest request,
    		@RequestBody Map<String,Object> map
    ){
    	String no = map.get("no").toString();
    	List<String> list = new ArrayList<String>();
    	SearchFile sf = new SearchFile();
    	//유저가 직접 올린 파일 가져오기
    	list = sf.userProfile(request, ROOTPATH, list, no);
    	//기본 이미지 가져오기
    	list = sf.defaultProfile(request, ROOTPATH, list);
    	return list;
    }
    
    //프로필 이미지 선택시
    @PostMapping("/updateProfileImg")
    public void updateProfileImg(@RequestBody Map<String, Object> map) {
    	String fileName = map.get("profile").toString();
    	try {
			fileName = URLDecoder.decode(fileName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	fileName = fileName.substring(fileName.lastIndexOf("/")+1,fileName.length());
    	map.put("profile",fileName);
    	memberService.updateProfileImg(map);
    }
    
    //소셜에서 가져온 이미지 선택시
    @PostMapping("/updatesocialprofileimg")
    public void updateSocialProfileImg(@RequestBody Map<String, Object> map) {
    	memberService.updateProfileImg(map);
    }
    //이름 변경
    @PostMapping("/nameupdate")
    public void updateName(@RequestBody Map<String, Object> map) {
    	memberService.updateName(map);
    }
    
    //비밀번호 변경
    @PostMapping("/passwordupdate")
    public void updatePassword(@RequestBody Map<String, Object> map) {
    	String password = map.get("password").toString();
    	MemberEncryption encry = new MemberEncryption();
    	map.put("password", encry.encryption(password));
    	memberService.updatePassword(map);
    }
    
    //회원 탈퇴
    @PostMapping("/deletemember")
    public void deleteMember(HttpServletRequest request,@RequestBody Map<String, Object> map) {
    	//기존에 사용한 프로필 파일명 가져오기
    	String profile = memberService.getProfileName(map);
    	//프로필 사진 지우기
    	SearchFile sf = new SearchFile();
    	sf.userProfileDelete(request, profile);
    	//테이블 레코드 지우기
    	memberService.deleteMember(map);
    }
    
    //포인트 내역 조회
    @GetMapping("/pointHistory")
    public List<PointDto> pointHistory(@RequestParam String member_no){
    	return memberService.pointHistory(member_no);
    }
}
