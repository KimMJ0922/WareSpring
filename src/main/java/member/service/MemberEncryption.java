package member.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MemberEncryption {
	public String encryption(String password) {
		SecurityUtil securityUtil = new SecurityUtil();
		String formatPassword = securityUtil.encryptSHA256(password);
		return formatPassword;
	}
}
