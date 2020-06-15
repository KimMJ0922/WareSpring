package email.send;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSendService {
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Async
	public void sendMail(String email,String name) {
		String link = "<a href='http://localhost:9000/updatemailcheck?email="+email+"' target='_blenk'>메일 인증</a>";
		MimeMessage mail = javaMailSender.createMimeMessage();
// 		simpleMessage.setFrom("보낸사람@naver.com"); // NAVER, DAUM, NATE일 경우 넣어줘야 함
		try {
			mail.setSubject("Ware.gg 이메일 인증 메일입니다.");
			mail.setText(
				new StringBuffer().append("<h1>[이메일 인증]</h1>")
				.append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
				.append(link)
				.toString(),"utf-8","html"
			);
			//받을 메일
			mail.addRecipient(RecipientType.TO, new InternetAddress(email));
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		javaMailSender.send(mail);
	}
	
	//임시 비밀번호 
	@Async
	public void sendPassword(String email,String tempPassword) {
		String link = "<a href='http://localhost:9000/updatemailcheck?email="+email+"' target='_blenk'>메일 인증</a>";
		MimeMessage mail = javaMailSender.createMimeMessage();
// 		simpleMessage.setFrom("보낸사람@naver.com"); // NAVER, DAUM, NATE일 경우 넣어줘야 함
		try {
			mail.setSubject("Ware.gg 임비 비밀번호 발급 메일입니다.");
			mail.setText(
				new StringBuffer().append("<h1>임시 비밀번호</h1>")
				.append("<p>발급 임시 비밀번호 : "+tempPassword.toString()+"입니다. </p>")
				.toString(),"utf-8","html"
			);
			//받을 메일
			mail.addRecipient(RecipientType.TO, new InternetAddress(email));
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		javaMailSender.send(mail);
	}
	
}
