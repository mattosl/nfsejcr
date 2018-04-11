package br.com.grupojcr.nfse.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.grupojcr.nfse.business.LoginBusiness;
import br.com.grupojcr.nfse.business.MonitoramentoBusiness;
import br.com.grupojcr.nfse.business.NFSEBusiness;
import br.com.grupojcr.nfse.dto.AnexoDTO;
import br.com.grupojcr.nfse.dto.FiltroConsultaNFSE;
import br.com.grupojcr.nfse.dto.NotaFiscalServicoDTO;
import br.com.grupojcr.nfse.entity.Coligada;
import br.com.grupojcr.nfse.entity.NotaFiscalServico;
import br.com.grupojcr.nfse.entity.Usuario;
import br.com.grupojcr.nfse.entity.datamodel.NotaFiscalServicoDataModel;
import br.com.grupojcr.nfse.entity.xml.NfseXML;
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
	
	private List<Coligada> listaColigada;
	private List<MunicipioIBGE> listaMunicipio;
	private List<NotaFiscalServico> notasSelecionadas;
	private List<AnexoDTO> listaAnexo;
	
	private FiltroConsultaNFSE filtro;
	private NotaFiscalServico notaFiscal;
	private NotaFiscalServicoDTO notaFiscalDTO;
	
	@EJB
	private NFSEBusiness nfseBusiness;

	@EJB
	private MonitoramentoBusiness monitoramentoBusiness;
	
	@Inject
	private NotaFiscalServicoDataModel dataModel;
	
	/**
	 * Método responsavel por iniciar processo
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @throws ApplicationException
	 */
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
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "iniciarProcesso" }, e);
		}
	}
	
	/**
	 * Método responsavel por realizar a pesquisa de nfs-e
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @throws ApplicationException
	 */
	public void pesquisar() throws ApplicationException {
		try {
			
			if(getFiltro().getDtInicial().after(getFiltro().getDtFinal())) {
				setExibirResultado(Boolean.FALSE);
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
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "pesquisar" }, e);
		}
	}
	
	/**
	 * Método responsavel por inicializar as datas do filtro de pesquisa
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @throws ApplicationException
	 */
	public void carregarDatas() throws ApplicationException {
		try {
			Calendar calendarioInicial = Calendar.getInstance();
			calendarioInicial.set(Calendar.DAY_OF_MONTH, calendarioInicial.getActualMinimum(Calendar.DAY_OF_MONTH));
			Calendar calendarioFinal = Calendar.getInstance();
			calendarioFinal.set(Calendar.DAY_OF_MONTH, calendarioFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
			
			getFiltro().setDtInicial(calendarioInicial.getTime());
			getFiltro().setDtFinal(calendarioFinal.getTime());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "carregarDatas" }, e);
		}
	}
	
	/**
	 * Método responsavel atualizar as nfs-e recebidas no e-mail
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @throws ApplicationException
	 */
	public void atualizar() throws ApplicationException {
		try {
			monitoramentoBusiness.lerXML();
			Message.setMessage("consultarNFSE.atualizar");
		} catch (ApplicationException e) {
			LOG.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "atualizar" }, e);
		}
	}
	
	/**
	 * Método responsavel por carregar os dados da nfse na modal de detalhes
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @throws ApplicationException
	 */
	public void detalhar() throws ApplicationException {
		try {
			if(Util.isNotNull(getNotaFiscal())) {
				if(getNotaFiscal().getEstiloXML().equals(EstiloXML.CURITIBA)) {
					try {
						Reader reader = new StringReader(getNotaFiscal().getXml());
						XMLInputFactory xif = XMLInputFactory.newFactory();
				        XMLStreamReader xsr = xif.createXMLStreamReader(reader);
				        List<NfseXML> notas = new ArrayList<NfseXML>();
				        while(xsr.hasNext()) {
				        	int next = xsr.next();
				        	
				        	switch(next) {
				        		case XMLStreamReader.START_ELEMENT:
				        			if(xsr.getLocalName().equalsIgnoreCase("Nfse")) {
						        		JAXBContext context = JAXBContext.newInstance(NfseXML.class);
							        	Unmarshaller unmarshaller = context.createUnmarshaller();
							        	unmarshaller.setSchema(null);
							        	JAXBElement<NfseXML> nfse = unmarshaller.unmarshal(xsr, NfseXML.class);
							        	
							        	if(Util.isNotNull(nfse.getValue())) {
							        		NfseXML xml = nfse.getValue();
							        		notas.add(xml);
							        	}
						        	}
				        			break;
				        		case XMLStreamReader.END_ELEMENT:
				        			break;
				        	}
				        	
				        }
				        for(NfseXML xml : notas) {
							if(xml.getInformacaoNota().getNumero().equals(getNotaFiscal().getNumeroNota())) {
								NotaFiscalServicoDTO dto = montarNota(xml);
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
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "detalhar" }, e);
		}
	}

	/**
	 * Método responsavel por efetuar o download do XML
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @param nfs : NotaFiscalServico
	 * @return StreamedContent
	 * @throws ApplicationException
	 */
	public StreamedContent download(NotaFiscalServico nfs) throws ApplicationException {
		try {
			InputStream arquivo = new ByteArrayInputStream(nfs.getXml().getBytes("UTF-8"));
			String nomeArquivo = TreatDate.format("dd-MM-yyyy", nfs.getDtEmissao()) + "-" + nfs.getNumeroNota() + ".xml";
			return new DefaultStreamedContent(arquivo, "text/xml", nomeArquivo);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "download" }, e);
		}
	}
	
	/**
	 * Método responsavel montar a nfse para xml na modal de detalhes
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @param xml : NfseXML
	 * @return NotaFiscalServicoDTO
	 * @throws ApplicationException
	 */
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
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "montarNota" }, e);
		}
	}
	
	/**
	 * Método responsavel por realizar o upload dos XML
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @param event : FileUploadEvent
	 * @throws ApplicationException
	 */
	public void doUploadXML(FileUploadEvent event) throws ApplicationException {
		try {
			UploadedFile uploadedFile = event.getFile();
			String fileNameUploaded = uploadedFile.getFileName();
			AnexoDTO dto = new AnexoDTO();
			InputStream is = uploadedFile.getInputstream();
			String xml = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String str;
			while ((str = in.readLine()) != null) {
				xml += str;
			}
			is.close();
			dto.setConteudo(xml);
			dto.setNomeArquivo(fileNameUploaded);
			getListaAnexo().add(dto);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "doUploadXML" }, e);
		}
	}
	
	/**
	 * Método responsavel por salvar as nfse incluídas manualmente pelo upload
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @return String
	 * @throws ApplicationException
	 */
	public String salvarAnexos() throws ApplicationException {
		try {
			Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.getExternalContext().getFlash().setKeepMessages(true);
			if(getListaAnexo().size() > 0) {
				for(AnexoDTO dto : getListaAnexo()) {
					Boolean valida = nfseBusiness.validarXML(dto.getConteudo());
					if(!valida) {
						FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_FATAL, dto.getNomeArquivo(), "NFS-e não identificada pelo sistema.");
						fc.addMessage(null, fm);
					}
				}
				if(fc.getMessageList().size() > 0) {
					return null;
				}
				for(AnexoDTO dto : getListaAnexo()) {
					nfseBusiness.incluirXML(dto.getConteudo(), usuario);
				}
			}
			
			Message.setMessage("consultarNFSE.incluir.sucesso");
			
		} catch (ApplicationException e) {
			LOG.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "salvarAnexos" }, e);
		}
		return voltar();
	}
	
	/**
	 * Método responsavel por exportar as NFSE para o RM
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 09/04/2018
	 * @return String
	 * @throws ApplicationException
	 */
	public void exportarRM() throws ApplicationException {
		try {
			
			if(getNotasSelecionadas().size() == 0) {
				throw new ApplicationException("consultarNFSE.exportar.nenhum.selecionado", FacesMessage.SEVERITY_WARN);
			}
			
			Message.setMessage("consultarNFSE.exportar.sucesso");
			
		} catch (ApplicationException e) {
			LOG.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "exportarRM" }, e);
		}
	}
	
	/**
	 * Método responsavel por remover anexo do upload
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @param dto : AnexoDTO
	 * @throws ApplicationException
	 */
	public void excluirAnexo(AnexoDTO dto) throws ApplicationException {
		try {
			getListaAnexo().remove(dto);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "excluirAnexo" }, e);
		}
	}
	
	/**
	 * Método responsavel por voltar para tela de pesquisa
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @return String
	 */
	public String voltar() {
		return "/pages/nfse/listar_nfse.xhtml?faces-redirect=true";
	}
	
	/**
	 * Método responsavel por carregar tela de inclusão de nfse manualmente
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @return String
	 */
	public String iniciarIncluir() {
		return "/pages/nfse/incluir_nfse.xhtml?faces-redirect=true";
	}
	
	/**
	 * Método responsavel por inicializar atributos da tela de inclusão de nfse
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 */
	public void iniciarAtributos() {
		setListaAnexo(new ArrayList<AnexoDTO>());
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

	public List<AnexoDTO> getListaAnexo() {
		return listaAnexo;
	}

	public void setListaAnexo(List<AnexoDTO> listaAnexo) {
		this.listaAnexo = listaAnexo;
	}

}
