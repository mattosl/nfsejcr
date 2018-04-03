package br.com.grupojcr.nfse.quartz;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import br.com.grupojcr.nfse.util.exception.ApplicationException;

public class AgendarQuartzServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(AgendarQuartzServlet.class);
	private final static String KEY_ERRO = "ERRO:";
	private final static String KEY_MENSAGEM_PADRAO = "message.default";
	
	public void init() throws ServletException {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			agendarMonitoramentoXML(scheduler);

		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ServletException(e);
		}
	}
	
	protected JobKey agendarMonitoramentoXML(Scheduler scheduler) throws ApplicationException {
		try {
			// Cria o job a ser executado
			String jobName = "MONITORAMENTO_EMAIL";
			String group = "NFSE";
			JobKey jobKey = new JobKey(jobName, group);
			JobDetail jobDetail = JobBuilder.newJob(MonitoramentoXML.class).withIdentity(jobKey).build();

			//cria o horario de agendamento
			String cronExpression = "0 0 1/1 ? * *";
			String triggerName = "Trigger_" + jobName;
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, group).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();

			//remove o job se ele ja esta agendado
			if (scheduler.checkExists(jobKey)) {
				scheduler.deleteJob(jobKey);
			}
			
			//agenda o job para execucao
			scheduler.scheduleJob(jobDetail, trigger);
			
			return jobKey;
			
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, e);
		}
	}

}
