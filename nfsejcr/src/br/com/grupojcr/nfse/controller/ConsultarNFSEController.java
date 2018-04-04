package br.com.grupojcr.nfse.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.grupojcr.nfse.business.LoginBusiness;
import br.com.grupojcr.nfse.business.MonitoramentoBusiness;
import br.com.grupojcr.nfse.business.NFSEBusiness;
import br.com.grupojcr.nfse.dto.FiltroConsultaNFSE;
import br.com.grupojcr.nfse.dto.NotaFiscalServicoDTO;
import br.com.grupojcr.nfse.entity.Coligada;
import br.com.grupojcr.nfse.entity.NotaFiscalServico;
import br.com.grupojcr.nfse.entity.datamodel.NotaFiscalServicoDataModel;
import br.com.grupojcr.nfse.entity.xml.ListaNfseXML;
import br.com.grupojcr.nfse.entity.xml.NfseXML;
import br.com.grupojcr.nfse.entity.xml.tcCompNfseXML;
import br.com.grupojcr.nfse.enumerator.EstiloXML;
import br.com.grupojcr.nfse.enumerator.MunicipioIBGE;
import br.com.grupojcr.nfse.util.TreatDate;
import br.com.grupojcr.nfse.util.TreatNumber;
import br.com.grupojcr.nfse.util.Util;
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
	
	private NotaFiscalServico notaFiscal;
	private NotaFiscalServicoDTO notaFiscalDTO;
	
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
		} catch (ApplicationException e) {
			LOG.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "atualizar" }, e);
		}
	}
	
	public void detalhar() throws ApplicationException {
		try {
			if(Util.isNotNull(getNotaFiscal())) {
				if(getNotaFiscal().getEstiloXML().equals(EstiloXML.CURITIBA_UNICA)) {
					try {
						JAXBContext context = JAXBContext.newInstance(NfseXML.class);
						Unmarshaller unmarshaller = context.createUnmarshaller();
						NfseXML nfse = (NfseXML) unmarshaller.unmarshal(new StringReader(getNotaFiscal().getXml()));
						
						NotaFiscalServicoDTO dto = montarNota(nfse);
						dto.setMunicipio(getNotaFiscal().getMunicipio());
						setNotaFiscalDTO(dto);
					} catch (JAXBException e) {
						throw new ApplicationException("message.empty", new String[] {"XML com erro."}, FacesMessage.SEVERITY_ERROR);
					}
				} else if(getNotaFiscal().getEstiloXML().equals(EstiloXML.CURITIBA_VARIAS)) {
					try {
						JAXBContext context = JAXBContext.newInstance(ListaNfseXML.class);
						Unmarshaller unmarshaller = context.createUnmarshaller();
						ListaNfseXML lista = (ListaNfseXML) unmarshaller.unmarshal(new StringReader(getNotaFiscal().getXml()));
						
						for(tcCompNfseXML xml : lista.getListaNfse()) {
							if(xml.getNfse().getInformacaoNota().getNumero().equals(getNotaFiscal().getNumeroNota())) {
								NotaFiscalServicoDTO dto = montarNota(xml.getNfse());
								dto.setMunicipio(getNotaFiscal().getMunicipio());
								setNotaFiscalDTO(dto);
								break;
							}
						}
					} catch (JAXBException e) {
						throw new ApplicationException("message.empty", new String[] {"XML com erro."}, FacesMessage.SEVERITY_ERROR);
					}
				}
			}
		} catch (ApplicationException e) {
			LOG.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "detalhar" }, e);
		}
	}

	public StreamedContent download(NotaFiscalServico nfs) throws ApplicationException {
		try {
			InputStream arquivo = new ByteArrayInputStream(nfs.getXml().getBytes("UTF-8"));
			String nomeArquivo = TreatDate.format("dd-MM-yyyy", nfs.getDtEmissao()) + "-" + nfs.getNumeroNota() + ".xml";
			return new DefaultStreamedContent(arquivo, "text/xml", nomeArquivo);
		} catch (Exception e) {
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "download" }, e);
		}
	}
	
	private NotaFiscalServicoDTO montarNota(NfseXML xml) throws ApplicationException {
		try {
			NotaFiscalServicoDTO dto = new NotaFiscalServicoDTO();
			dto.setNumeroNota(xml.getInformacaoNota().getNumero().toString());
			dto.setDtEmissao(TreatDate.format("dd/MM/yyyy", xml.getInformacaoNota().getDataEmissao()));
			dto.setCodigoVerificacao(xml.getInformacaoNota().getCodigoVerificacao());
			dto.setRazaoSocialPrestador(xml.getInformacaoNota().getPrestadorServico().getNomeFantasia());
			dto.setCnpjPrestador(Util.formatarCNPJ(xml.getInformacaoNota().getPrestadorServico().getIdentificacao().getCnpj()));
			dto.setInscricaoMunicipalPrestador(xml.getInformacaoNota().getPrestadorServico().getIdentificacao().getInscricaoMunicipal());
			dto.setEnderecoPrestador(xml.getInformacaoNota().getPrestadorServico().getEndereco().getEndereco() + ", " + xml.getInformacaoNota().getPrestadorServico().getEndereco().getNumero() + " - " + xml.getInformacaoNota().getPrestadorServico().getEndereco().getBairro() + " - " + xml.getInformacaoNota().getPrestadorServico().getEndereco().getCep());
			dto.setRazaoSocialTomador(xml.getInformacaoNota().getTomadorServico().getRazaoSocial());
			dto.setCnpjTomador(Util.formatarCNPJ(xml.getInformacaoNota().getTomadorServico().getIdentificacao().getCpfCnpj().getCnpj()));
			dto.setInscricaoMunicipalTomador(xml.getInformacaoNota().getTomadorServico().getIdentificacao().getInscricaoMunicipal());
			dto.setEnderecoTomador(xml.getInformacaoNota().getTomadorServico().getEndereco().getEndereco() + ", " + xml.getInformacaoNota().getTomadorServico().getEndereco().getNumero() + " - " + xml.getInformacaoNota().getTomadorServico().getEndereco().getBairro() + " - " + xml.getInformacaoNota().getTomadorServico().getEndereco().getCep());
			dto.setDiscriminacao(xml.getInformacaoNota().getServico().getDiscriminacao());
			dto.setValorDeducoes(TreatNumber.formatMoney(xml.getInformacaoNota().getServico().getValores().getDeducoes()));
			dto.setBaseCalculo(TreatNumber.formatMoney(xml.getInformacaoNota().getServico().getValores().getBaseCalculo()));
			dto.setAliquota(TreatNumber.formatMoney(xml.getInformacaoNota().getServico().getValores().getAliquota()));
			dto.setValorISS(TreatNumber.formatMoney(xml.getInformacaoNota().getServico().getValores().getIss()));
			dto.setCredAbatimentoIPTU(TreatNumber.formatMoney(xml.getInformacaoNota().getServico().getValores().getOutrasRetencoes()));
			dto.setValorTotal(TreatNumber.formatMoneyCurrency(xml.getInformacaoNota().getServico().getValores().getLiquidoNfse()));
			
			return dto;
		} catch (Exception e) {
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "montarNota" }, e);
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

	public NotaFiscalServico getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscalServico notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public NotaFiscalServicoDTO getNotaFiscalDTO() {
		return notaFiscalDTO;
	}

	public void setNotaFiscalDTO(NotaFiscalServicoDTO notaFiscalDTO) {
		this.notaFiscalDTO = notaFiscalDTO;
	}

}
