package br.com.grupojcr.nfse.business;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.activation.DataHandler;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;
import javax.naming.InitialContext;

@Stateless
public class MonitoramentoBusiness {
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void obterEmail() throws Exception {
		try {
			InitialContext ic = new InitialContext();
			Session session = ((Session) ic.lookup("java:jboss/mail/MailService"));
			
			Store store = session.getStore("imap");
			store.connect();
			
			Folder folder = store.getFolder("inbox");
			 
			if (!folder.exists()) {
				System.out.println("No INBOX...");
				System.exit(0);
			}
			folder.open(Folder.READ_WRITE);
			Message[] msg = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

			for (int i = 0; i < msg.length; i++) {
				hasAttachments(msg[i]);
				lerEmail(msg[i]);
				msg[i].setFlag(Flags.Flag.SEEN, true);
				System.out.println();
			}
			folder.close(true);
			store.close();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void lerEmail(Message message) {  
        try {  
            // Get the header information  
            String from = ((InternetAddress) message.getFrom()[0]).getPersonal();  
            if (from == null) {  
                from = ((InternetAddress) message.getFrom()[0]).getAddress();  
            }  
            System.out.println("FROM: " + from);  
            String subject = message.getSubject();  
            System.out.println("SUBJECT: " + subject);  
            Date dataa = message.getReceivedDate();  
            System.out.println("Numero da MSG: " + message.getMessageNumber());  
            System.out.println("Recebido em: " + dataa);  
            // -- Get the message part (i.e. the message itself) --  
            Part messagePart = message;  
            Object content = messagePart.getContent();  
            // -- or its first body part if it is a multipart message --  
            if (content instanceof Multipart) {  
                messagePart = ((Multipart) content).getBodyPart(0);
                Multipart multipart = (Multipart) message.getContent();
                System.out.println("[ Multipart Message ]");
                for (int j = 0; j < multipart.getCount(); j++) {
					BodyPart bodyPart = multipart.getBodyPart(j);

					String disposition = bodyPart.getDisposition();

					if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) { // BodyPart.ATTACHMENT
																								// doesn't
																								// work
																								// for
																								// gmail
						System.out.println("Mail have some attachment");

						DataHandler handler = bodyPart.getDataHandler();
						System.out.println("file name : " + handler.getName());
						InputStream inputStream = handler.getInputStream();
						String extensao = handler.getName().substring(handler.getName().lastIndexOf("."), handler.getName().length());
						
						int data = inputStream.read();
						while (data != -1) {
							 System.out.println((char) data);

							data = inputStream.read();

						}
						inputStream.close();
					} else {
						System.out.println("Body: " + bodyPart.getContent());
						content = bodyPart.getContent().toString();
					}
                }
            }  
            System.out.println("-----------------------------");  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
    }
	
	public boolean hasAttachments(Message msg) throws MessagingException, IOException {
		if (msg.isMimeType("multipart/mixed")) {
		    Multipart mp = (Multipart)msg.getContent();
		    if (mp.getCount() > 1)
			return true;
		}
		return false;
    }

}
