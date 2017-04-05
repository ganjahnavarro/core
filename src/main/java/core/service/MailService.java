package core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MailService {
	
	@Autowired MailSender mailSender;
	@Autowired TaskExecutor taskExecutor;
	
	public void sendMail(String email, String subject, String body) throws Exception {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					sendEmail(email, subject, body);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void sendEmail(String email, String subject, String body) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom("1applicationbot@gmail.com");
			mailMessage.setTo(email);
			mailMessage.setSubject(subject);
			mailMessage.setText(body);
			mailSender.send(mailMessage);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
