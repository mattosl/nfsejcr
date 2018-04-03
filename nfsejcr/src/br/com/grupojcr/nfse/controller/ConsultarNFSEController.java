package br.com.grupojcr.nfse.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.grupojcr.nfse.business.LoginBusiness;
import br.com.grupojcr.nfse.business.MonitoramentoBusiness;
import br.com.grupojcr.nfse.business.NFSEBusiness;
import br.com.grupojcr.nfse.dto.FiltroConsultaNFSE;
import br.com.grupojcr.nfse.entity.Coligada;
import br.com.grupojcr.nfse.entity.NotaFiscalServico;
import br.com.grupojcr.nfse.entity.datamodel.NotaFiscalServicoDataModel;
import br.com.grupojcr.nfse.enumerator.MunicipioIBGE;
import br.com.grupojcr.nfse.util.TreatDate;
import br.com.grupojcr.nfse.util.exception.ApplicationException;
import br.com.grupojcr.nfse.util.exception.ControllerExceptionHandler;
import br.com.grupojcr.nfse.util.exception.Message;

@Named
@ViewScoped
@ControllerExceptionHandler
public class ConsultarNFSEController implements Serializable {

	protected static Logger LOG = Logger.getLogger(LoginBusiness.class);
	private final static String KEY_MENSAGEM_PADRAO = "message.default.erro";
	
	private static final long serialVersionUID = 764194435849716691L;
	
	private Boolean exibirResultado;
	
	private List<String> teste = new ArrayList<String>(Arrays.asList("teste", "teste", "teste", "teste"));
	private List<String> testeSelecionados = new ArrayList<String>();
	
	private List<Coligada> listaColigada;
	private List<MunicipioIBGE> listaMunicipio;
	private List<NotaFiscalServico> notasSelecionadas;
	
	private FiltroConsultaNFSE filtro;
	
	@EJB
	private NFSEBusiness nfseBusiness;

	@EJB
	private MonitoramentoBusiness monitoramentoBusiness;
	
	@Inject
	private NotaFiscalServicoDataModel dataModel;
	
	public void iniciarProcesso() throws ApplicationException {
		try {
			setFiltro(new FiltroConsultaNFSE());
			setExibirResultado(Boolean.FALSE);
			setListaColigada(nfseBusiness.listarColigadasAtivas());
			setListaMunicipio(new ArrayList<MunicipioIBGE>(Arrays.asList(MunicipioIBGE.values())));
			carregarDatas();
			getFiltro().setSituacao(0);
		} catch (ApplicationException e) {
			LOG.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "iniciarProcesso" }, e);
		}
	}
	
	public void pesquisar() throws ApplicationException {
		try {
			
			if(getFiltro().getDtInicial().after(getFiltro().getDtFinal())) {
				throw new ApplicationException("consultarNFSE.periodo.invalido", new String[] {TreatDate.format("dd/MM/yyyy", getFiltro().getDtInicial()) , TreatDate.format("dd/MM/yyyy", getFiltro().getDtFinal())}, FacesMessage.SEVERITY_ERROR);
			}
			if(nfseBusiness.obterQtdNotasServico(getFiltro()) == 0) {
				setExibirResultado(Boolean.FALSE);
				throw new ApplicationException("message.datatable.noRecords", FacesMessage.SEVERITY_WARN);
			}
			
			dataModel.setFiltro(getFiltro());
			setExibirResultado(Boolean.TRUE);
		} catch (ApplicationException e) {
			LOG.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "pesquisar" }, e);
		}
	}
	
	public void carregarDatas() throws ApplicationException {
		try {
			Calendar calendarioInicial = Calendar.getInstance();
			calendarioInicial.set(Calendar.DAY_OF_MONTH, calendarioInicial.getActualMinimum(Calendar.DAY_OF_MONTH));
			Calendar calendarioFinal = Calendar.getInstance();
			calendarioFinal.set(Calendar.DAY_OF_MONTH, calendarioFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
			
			getFiltro().setDtInicial(calendarioInicial.getTime());
			getFiltro().setDtFinal(calendarioFinal.getTime());
		} catch (Exception e) {
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "carregarDatas" }, e);
		}
	}
	
	public void atualizar() throws ApplicationException {
		try {
			monitoramentoBusiness.lerXML();
			Message.setMessage("consultarNFSE.atualizar");
		} catch (Exception e) {
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "atualizar" }, e);
		}
	}
	
	public String iniciarIncluir() {
		return "/pages/nfse/incluir_nfse.xhtml";
	}

	public List<String> getTeste() {
		return teste;
	}

	public void setTeste(List<String> teste) {
		this.teste = teste;
	}

	public List<String> getTesteSelecionados() {
		return testeSelecionados;
	}

	public void setTesteSelecionados(List<String> testeSelecionados) {
		this.testeSelecionados = testeSelecionados;
	}

	public List<Coligada> getListaColigada() {
		return listaColigada;
	}

	public void setListaColigada(List<Coligada> listaColigada) {
		this.listaColigada = listaColigada;
	}

	public Boolean getExibirResultado() {
		return exibirResultado;
	}

	public void setExibirResultado(Boolean exibirResultado) {
		this.exibirResultado = exibirResultado;
	}

	public FiltroConsultaNFSE getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroConsultaNFSE filtro) {
		this.filtro = filtro;
	}

	public List<MunicipioIBGE> getListaMunicipio() {
		return listaMunicipio;
	}

	public void setListaMunicipio(List<MunicipioIBGE> listaMunicipio) {
		this.listaMunicipio = listaMunicipio;
	}

	public NotaFiscalServicoDataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(NotaFiscalServicoDataModel dataModel) {
		this.dataModel = dataModel;
	}

	public List<NotaFiscalServico> getNotasSelecionadas() {
		return notasSelecionadas;
	}

	public void setNotasSelecionadas(List<NotaFiscalServico> notasSelecionadas) {
		this.notasSelecionadas = notasSelecionadas;
	}

}
