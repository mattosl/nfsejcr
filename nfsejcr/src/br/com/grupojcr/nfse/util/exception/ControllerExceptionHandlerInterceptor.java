package br.com.grupojcr.nfse.util.exception;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

@ControllerExceptionHandler
@Interceptor
public class ControllerExceptionHandlerInterceptor implements Serializable {

	private static final long serialVersionUID = -5167052716266988396L;

	@AroundInvoke
    public Object handleException(InvocationContext invocationContext) throws Exception {
        String targetClassName = invocationContext.getTarget().getClass().getName();
        String methodName = invocationContext.getMethod().getName();
        Logger logger = Logger.getLogger((String)targetClassName);
        logger.debug("Interceptor ControllerExceptionHandler sobre a Classe: " + targetClassName + " - Metodo: " + methodName);
        try {
            return invocationContext.proceed();
        }
        catch (ApplicationException appEx) {
            if (appEx.getRootCause() != null) {
                logger.error("Erro tratado pela aplicacao: ", (Throwable)appEx);
            } else {
                logger.debug("Erro tratado pela aplicacao: ", (Throwable)appEx);
            }
            this.addMessage(appEx.getSeverity(), appEx.getMessage(), "");
        }
        catch (Exception ex) {
            logger.error("Erro NAO tratado pela aplicacao. Operacao [" + methodName + "] em [" + targetClassName + "] - " + ex.getMessage(), (Throwable)ex);
            this.addMessage(FacesMessage.SEVERITY_ERROR, Message.getMessage((String)"mensagem.padrao"), ex.getMessage());
        }
        logger.debug("Fim Interceptor ControllerExceptionHandler.");
        return null;
    }
	
	private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(severity, summary, detail);
        context.addMessage(null, msg);
    }
}
