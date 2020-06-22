package member.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class MemberEncryption {
	public String encryption(String password) {
		SecurityUtil securityUtil = new SecurityUtil();
		String formatPassword = securityUtil.encryptSHA256(password);
		return formatPassword;
	}
	
	//임시 비밀번호 생성
	public Map<String, String> createTempPassword() {
		int cnt = 0;
		String tempPassword = "";
		while(true) {
			if(cnt == 15) {
				break;
			}
			double ranNum = Math.random()*123;
			int ran = (int)ranNum;
			if(ran>=33 && ran<=122) {
				tempPassword += (char)ranNum;
				cnt++;
			}
		}
		
		SecurityUtil securityUtil = new SecurityUtil();
		String formatPassword = securityUtil.encryptSHA256(tempPassword);
		Map<String, String> map = new HashMap<String, String>();
		map.put("tempPassword", tempPassword);
		map.put("formatPassword", formatPassword);
		
		return map;
	}
}
