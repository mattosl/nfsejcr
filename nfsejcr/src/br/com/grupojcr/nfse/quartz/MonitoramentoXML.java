package br.com.grupojcr.nfse.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MonitoramentoXML implements Job {
	
	private static Logger log = Logger.getLogger(MonitoramentoXML.class);
	private final static String KEY_ERRO = "ERRO:";

	public static void main(String[] args) throws Exception {
		
		new MonitoramentoXML().execute(null);
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			log.info("Executando Monitoramento XML...");
			
//			InitialContext initialContext = new InitialContext();
//			BloqueioDAO dao = (BloqueioDAO) initialContext.lookup("java:global/rhseed/BloqueioDAO");
//			dao.desbloquearServidoresNaoUtilizado();
//			dao.desbloquearSuprimentos();
//			
			log.info("Finalizado Monitoramento XML...");
			
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new JobExecutionException(e);
		}

		
	}

}
