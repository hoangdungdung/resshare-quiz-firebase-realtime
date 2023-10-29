 
    import java.util.Date;
    import java.util.Properties;

    import javax.mail.Authenticator;
    import javax.mail.Message;
    import javax.mail.MessagingException;
    import javax.mail.PasswordAuthentication;
    import javax.mail.Session;
    import javax.mail.Transport;
    import javax.mail.internet.AddressException;
    import javax.mail.internet.InternetAddress;
    import javax.mail.internet.MimeMessage;

public class ffff {

	public static void main(String[] args) {
		new ffff().getSession();

	}
    String mailFrom = "sacmauhoagiay@gmail.com";
    String password = "392068@Ss";
	public Session getSession() {
	    //Gmail Host
	    String host = "smtp.gmail.com";
	    String username = "sacmauhoagiay@gmail.com";
	    //Enter your Gmail password
	    String password = "";

	    Properties prop = new Properties();
	    prop.put("mail.smtp.auth", true);
	    prop.put("mail.smtp.starttls.enable", "true");
	    prop.put("mail.smtp.host", host);
	    prop.put("mail.smtp.port", 587);
	    prop.put("mail.smtp.ssl.trust", host);

	    return Session.getInstance(prop, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(mailFrom, "392068@Ss");
	        }
	    });
	}
}
