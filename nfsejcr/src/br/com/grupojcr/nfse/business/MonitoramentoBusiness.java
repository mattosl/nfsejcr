package br.com.grupojcr.nfse.business;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Date;

import javax.activation.DataHandler;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import br.com.grupojcr.nfse.entity.xml.NfseXML;
import br.com.grupojcr.nfse.util.exception.ApplicationException;

@Stateless
public class MonitoramentoBusiness {
	
	private static Logger log = Logger.getLogger(MonitoramentoBusiness.class);
	
	public void lerXML() throws ApplicationException {
		try {
			lerEmail();
		} catch (ApplicationException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ApplicationException("mensagem.padrao.ERRO_PTE", new String[] { "obterUsuarioPorLoginSenha" }, e);
		}
		
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void lerEmail() throws Exception {
		try {
			// Obter session configurado no servidor
			InitialContext ic = new InitialContext();
			Session session = ((Session) ic.lookup("java:jboss/mail/MailService"));
			
			// Obter Pasta INBOX
			Store store = session.getStore("imap");
			store.connect();
			
			Folder folder = store.getFolder("inbox");
			 
			if (!folder.exists()) {
				System.exit(0);
			}
			folder.open(Folder.READ_WRITE);
			Message[] msg = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

			for (int i = 0; i < msg.length; i++) {
				if(hasAttachments(msg[i])) {
					lerAnexos(msg[i]);
				}
			}
			folder.close(true);
			store.close();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void lerAnexos(Message message) {  
        try { 
            // Get the header information  
            String from = ((InternetAddress) message.getFrom()[0]).getPersonal();  
            if (from == null) {  
                from = ((InternetAddress) message.getFrom()[0]).getAddress();  
            }  
            log.info("FROM: " + from);  
            String subject = message.getSubject();  
            log.info("SUBJECT: " + subject);  
            Date data = message.getReceivedDate();  
            log.info("Numero da MSG: " + message.getMessageNumber());  
            log.info("Recebido em: " + data);  
            // -- Get the message part (i.e. the message itself) --  
            Part messagePart = message;  
            Object content = messagePart.getContent();  
            // -- or its first body part if it is a multipart message --  
            if (content instanceof Multipart) {  
                messagePart = ((Multipart) content).getBodyPart(0);
                Multipart multipart = (Multipart) message.getContent();
                for (int j = 0; j < multipart.getCount(); j++) {
					BodyPart bodyPart = multipart.getBodyPart(j);

					String disposition = bodyPart.getDisposition();

					if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) {
						log.info("ENCONTRADO ANEXO");

						DataHandler handler = bodyPart.getDataHandler();
						InputStream inputStream = handler.getInputStream();
						log.info("file name : " + handler.getName());
						String extensao = handler.getName().substring(handler.getName().lastIndexOf("."), handler.getName().length());
						if(extensao.equalsIgnoreCase(".XML")) {
							String xml = "";
							int arquivo = inputStream.read();
							while (arquivo != -1) {
								xml += ((char) arquivo);
								
								arquivo = inputStream.read();
								
							}
							inputStream.close();
							
							JAXBContext context = JAXBContext.newInstance(NfseXML.class);
							Unmarshaller unmarshaller = context.createUnmarshaller();
							NfseXML nfse = (NfseXML) unmarshaller.unmarshal(new StringReader(xml));
							System.out.println(nfse);
							
							message.setFlag(Flags.Flag.SEEN, true);
						}
						
						
					} else {
						log.info("Body: " + bodyPart.getContent());
						content = bodyPart.getContent().toString();
					}
                }
            }  
        } catch (Exception ex) {  
        	log.error(ex.getStackTrace());  
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
