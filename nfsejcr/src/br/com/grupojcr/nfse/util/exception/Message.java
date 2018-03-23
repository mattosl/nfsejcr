package br.com.grupojcr.nfse.util.exception;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;



/**
 * Classe utilizada para leitura de mensagens do ResourceBundle.
 * As mensagens são adicionadas ao FacesContext através de uma entrada pré-definida no arquivo properties 'ApplicationMessages'.
 * 
 * @since 1.0 
 */
public class Message {

	private static ResourceBundle messageResources;
	private static String defaultMessage;
	private static final Logger log = Logger.getLogger(Message.class);
	
	/**
	 * Carrega o ResourceBundle da aplicação. 
	 * Também carrega a mensagem padrão com o código "message.default" que pode ser definida no resourceBundle.
	 * Caso ela não esteja definida no messageBundle, uma mensagem padronizada é utilizada: 'Ocorreu um erro inesperado. Contacte o administrador do sistema.'
	 * Esta mensagem padrão é utilizada quando o código de alguma mensagem não é encontrado no resourceBundle.
	 */
	static {
		try {
			
			if (FacesContext.getCurrentInstance() == null){
				messageResources = ResourceBundle.getBundle("ApplicationMessages"); //Não usa o contexto JSF (faces-config)
			}else {
				
				messageResources = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "appMessages"); //Usa o contexto JSF
			}
			
			//messageResources = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()); //MessageBundle
		} catch (Exception e) {
			log.error("Não foi possível acessar arquivo de properties com as mensagens do sistema.", e);
			messageResources = null;
		}
		
		//carrega msg padrao
		defaultMessage = getMessage("message.default");
		if(defaultMessage == null || "".equals(defaultMessage) ){
			defaultMessage = "Ocorreu um erro inesperado. Contacte o administrador do sistema.";
		}
    }
	 
	/**
	 * Adicionar ao FacesContext uma mensagem a partir do nome chave pré-definido no arquivo de mensagens. 
	 * @param msgKey nome chave da mensagem, ex: message.default
	 */
	public static void setMessage(String msgKey) {
		String mensagem = Message.getMessage(msgKey);		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, "");		
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

	/**
	 * Adicionar ao FacesContext uma mensagem a partir do nome chave pré-definido no arquivo de mensagens.
	 * @param msgKey nome chave da mensagem, ex: message.default
	 * @param severity 
	 */
	public static void setMessage(String msgKey, FacesMessage.Severity severity) {		
		String mensagem = Message.getMessage(msgKey);		
		FacesMessage message = new FacesMessage(severity, mensagem, "");		
		FacesContext.getCurrentInstance().addMessage(null, message);
    }

	/**
	 * Adicionar ao FacesContext uma mensagem a partir do nome chave pré-definido no arquivo de mensagens.
	 * @param msgKey nome chave da mensagem, ex: message.default
	 * @param args prâmetros que serão substituídos na mensagem
	 */
	public static void setMessage(String msgKey, String[] args) {
		String mensagem = Message.getMessage(msgKey, args);		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, "");		
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
	/**
	 * Adicionar ao FacesContext uma mensagem a partir do nome chave pré-definido no arquivo de mensagens.
	 * @param msgKey nome chave da mensagem, ex: message.default
	 * @param args prâmetros que serão substituídos na mensagem
	 * @param severity
	 */
	public static void setMessage(String msgKey, String[] args, FacesMessage.Severity severity) {		
		String mensagem = Message.getMessage(msgKey, args);		
		FacesMessage message = new FacesMessage(severity, mensagem, "");		
		FacesContext.getCurrentInstance().addMessage(null, message);

    }

	/**
	 * Adicionar ao FacesContext uma mensagem de exceção tratada pela aplicação.
	 * @param ae
	 */
	public static void setMessage(ApplicationException ae) {
		String mensagemDetalhada = null;
		if(ae.getRootCause() != null){
			mensagemDetalhada = ae.getRootCause().toString();
		}
		FacesMessage message = new FacesMessage(ae.getSeverity(), ae.getMessage(), mensagemDetalhada);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	
	/**
	 * Recupera a mensagem através da sua chave. Se não a encontrar,
	 * utiliza a mensagem padrão e a retorna.
	 * 
	 * @param key chave da mensagem. Ex: "mensagem.login"
	 * @return String mensagem definida no arquivo properties
	 */
	public static String getMessage(String key) {

		String msg = null;
		
		try {
			msg = messageResources.getString(key);
		}catch (MissingResourceException mre) {
			log.debug("Código da mensagem não encontrado", mre);
			msg =  defaultMessage;
		} catch (Exception e) {
			log.error("Não foi possível acessar arquivo de properties.", e);
			messageResources = null;
			msg =  defaultMessage;
		}
			
		// Retornando mensagem
		return (msg == null ? "" : msg );
	}
	
	/**
	 * Recupera a mensagem através da sua chave. Se não a encontrar,
	 * procura por uma mensagem chamada "message.default" e a retorna.
	 * Substitui as chaves com os parametros passados. não realiza nenhum 
	 * teste para verificar se o número de parâmetros coincide com o
	 * recebido.
	 * Para ocorrer a substituição, a string deve conter o seguinte padrão no
	 * arquivo de properties:
	 * mensagem.login=bem-vindo ao sistema {0}, {1}
	 * No lugar de {0} será colocado o item que está na posição 0 do array,
	 * no lugar de {1} o que está na 1, e assim por diante
	 * 
	 * @param key chave da mensagem. Ex: "mensagem.login"
	 * @param args prâmetros que serão substituídos na mensagem
	 * @return String mensagem
	 */
	public static String getMessage(String key, String[] args) {
		String msg = getMessage(key);
		if ("".equals(msg)){
			return "";
		}
		MessageFormat mf = new MessageFormat(msg);
		msg = mf.format(args);
		return (msg!=null?msg:"");
	}	
	
}
