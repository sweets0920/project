package util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	
	// 매개변수로 발신자, 수신자, 제목, 내용
	public static void sendMail(String from, String to, String subject, String content) {
		
		// 1. 메일서버 정보 설정 (Property 객체로 처리)
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", "smtp.naver.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.protocols", "TLSv1.2"); // No appropriate protocol 에러
		
		// 2. 인증을 위해 Session 객체 생성
		Session session = Session.getDefaultInstance(prop, new Authenticator() {
			@Override
			 protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("bluishbreeze", "wldusdl0817!");
			 }
		});
		session.setDebug(true);
		
		// 3. MimeMessage 객체 생성 (발신자, 수신자, 제목, 내용 설정)
			try {
			MimeMessage mm = new MimeMessage(session);
			mm.setFrom(new InternetAddress(from)); // 발신자
			mm.setRecipient(Message.RecipientType.TO, new InternetAddress(to)); // 수신자
			mm.setSubject(subject); // 제목
			// mm.setText(content); // 내용 (단순 텍스트로)
			mm.setContent(content, "text/html; charset=utf-8"); // 내용 (html로)
			
			// 4. 메일발송
			Transport.send(mm);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static void main(String[] args) {
		sendMail("bluishbreeze@naver.com", "bluishbreeze@naver.com", "test", "<b>한글</b>testtest");
	}
	
}
