package br.com.grupojcr.nfse.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.activation.DataHandler;
import javax.annotation.Resource;
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
public class EnviaEmail {
	
	@Resource(name = "java:jboss/mail/MailService")
	private Session session;
	
	public EnviaEmail() {
	}

//	@Asynchronous
//	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
//	public void enviaEmailOrdemCompra(String assunto, String[] destinatariosPara, MovimentoDTO dto,
//			MonitorAprovacaoDTO primeiraAprovacao, MonitorAprovacaoDTO segundaAprovacao) throws Exception {
//		try {
//			InitialContext ic = new InitialContext();
//			session = ((Session) ic.lookup("java:jboss/mail/MailService"));
//
//			InternetAddress[] destinatario = new InternetAddress[destinatariosPara.length];
//
//			InternetAddress remetente = null;
//			remetente = new InternetAddress("mattosl@grupojcr.com.br");
//
//			for (int i = 0; i < destinatariosPara.length; i++) {
//				destinatario[i] = new InternetAddress(destinatariosPara[i]);
//			}
//
//			Message message = new MimeMessage(session);
//			message.setFrom(remetente);
//			message.setRecipients(Message.RecipientType.TO, destinatario);
//			message.setSubject(MimeUtility.encodeText(assunto, "UTF-8", null));
//
//			BufferedReader fis = new BufferedReader(
//					new InputStreamReader(getClass().getResourceAsStream("/email.ordemcompra.html")));
//			String bodyEmail = IOUtils.toString(fis);
//			bodyEmail = bodyEmail.replace("${nome}", "João Carlos Ribeiro");
//			bodyEmail = bodyEmail.replace("${empresa}", dto.getNomeEmpresa().toUpperCase());
//			bodyEmail = bodyEmail.replace("${dtEmissao}", dto.getDataEmissao());
//			bodyEmail = bodyEmail.replace("${identificadorRM}", dto.getIdMov().toString());
//			bodyEmail = bodyEmail.replace("${fornecedor}",
//					dto.getIdFornecedor() + " - " + dto.getFornecedor().toUpperCase());
//			bodyEmail = bodyEmail.replace("${centroCusto}",
//					dto.getIdCentroCusto() + " - " + dto.getCentroCusto().toUpperCase());
//			bodyEmail = bodyEmail.replace("${valorTotal}", dto.getMoeda() + " " + dto.getValorTotal());
//			bodyEmail = bodyEmail.replace("${nomePrimeiroAprovador}", primeiraAprovacao.getNomeUsuario().toUpperCase());
//			bodyEmail = bodyEmail.replace("${nomeSegundoAprovador}", segundaAprovacao.getNomeUsuario().toUpperCase());
//			bodyEmail = bodyEmail.replace("${dtPrimeiraAprovacao}",
//					TreatDate.format("dd/MM/yyyy", primeiraAprovacao.getDtAprovacao()));
//			bodyEmail = bodyEmail.replace("${dtSegundaAprovacao}",
//					TreatDate.format("dd/MM/yyyy", segundaAprovacao.getDtAprovacao()));
//			bodyEmail = bodyEmail.replace("${primeiraObservacao}", primeiraAprovacao.getObservacao());
//			bodyEmail = bodyEmail.replace("${segundaObservacao}", segundaAprovacao.getObservacao());
//
//			message.setContent(BrasilUtils.converterCaracteresEspeciaisHTML(bodyEmail), "text/html");
//
//			Transport.send(message);
//		} catch (MessagingException e) {
//			throw new RuntimeException(e);
//		}
//	}
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void obterEmail() throws Exception {
		try {
			InitialContext ic = new InitialContext();
			session = ((Session) ic.lookup("java:jboss/mail/MailService"));
			
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
				printMessage(msg[i]);
				msg[i].setFlag(Flags.Flag.SEEN, true);
				System.out.println();
			}
			folder.close(true);
			store.close();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void printMessage(Message message) {  
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
            // -- Get the content type --  
//            String contentType = messagePart.getContentType();  
            // -- If the content is plain text, we can print it --  
//            System.out.println("CONTENT:" + contentType);  
            
//            if (contentType.startsWith("text/plain") || contentType.startsWith("text/html")) {  
//                InputStream is = messagePart.getInputStream();  
//                BufferedReader reader = new BufferedReader(new InputStreamReader(is));  
//                String thisLine = reader.readLine();  
//                while (thisLine != null) {  
//                    System.out.println(thisLine);  
//                    thisLine = reader.readLine();  
//                }  
//            }  
            System.out.println("-----------------------------");  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
    }
	
	boolean hasAttachments(Message msg) throws MessagingException, IOException {
		if (msg.isMimeType("multipart/mixed")) {
		    Multipart mp = (Multipart)msg.getContent();
		    if (mp.getCount() > 1)
			return true;
		}
		return false;
    }
}
