package br.com.grupojcr.nfse.business;

import java.io.StringReader;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import br.com.grupojcr.nfse.dao.ColigadaDAO;
import br.com.grupojcr.nfse.dao.NotaFiscalServicoDAO;
import br.com.grupojcr.nfse.dto.FiltroConsultaNFSE;
import br.com.grupojcr.nfse.entity.Coligada;
import br.com.grupojcr.nfse.entity.NotaFiscalServico;
import br.com.grupojcr.nfse.entity.Usuario;
import br.com.grupojcr.nfse.entity.xml.ListaNfseXML;
import br.com.grupojcr.nfse.entity.xml.NfseXML;
import br.com.grupojcr.nfse.entity.xml.tcCompNfseXML;
import br.com.grupojcr.nfse.enumerator.EstiloXML;
import br.com.grupojcr.nfse.enumerator.MunicipioIBGE;
import br.com.grupojcr.nfse.enumerator.Status;
import br.com.grupojcr.nfse.util.TreatString;
import br.com.grupojcr.nfse.util.Util;
import br.com.grupojcr.nfse.util.exception.ApplicationException;

@Stateless
public class NFSEBusiness {
	
	private static Logger LOG = Logger.getLogger(NFSEBusiness.class);
	private final static String KEY_MENSAGEM_PADRAO = "message.default.erro";
	
	@EJB
	private NotaFiscalServicoDAO daoNotaFiscalServico;
	
	@EJB
	private ColigadaDAO daoColigada;
	
	/**
	 * Método responsavel por incluir nfse do xml no banco de dados
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @param xml : String
	 * @param usuario : Usuario
	 * @throws ApplicationException
	 */
	public void incluirXML(String xml, Usuario usuario) throws ApplicationException {
		try {
			if(TreatString.isNotBlank(xml)) {
				try {
					JAXBContext context = JAXBContext.newInstance(NfseXML.class);
					Unmarshaller unmarshaller = context.createUnmarshaller();
					NfseXML nfse = (NfseXML) unmarshaller.unmarshal(new StringReader(xml));
					
					if(Util.isNotNull(nfse)) {
						
						Long codigoMunicipio = nfse.getInformacaoNota().getServico().getCodigoMunicipio();
						
						if(codigoMunicipio.equals(0)) {
							if(TreatString.isNotBlank(nfse.getInformacaoNota().getPrestadorServico().getEndereco().getCodigoMunicipio())) {
								codigoMunicipio = Long.parseLong(nfse.getInformacaoNota().getPrestadorServico().getEndereco().getCodigoMunicipio());
							}
						}
						
						NotaFiscalServico nfs = new NotaFiscalServico();
						nfs.setMunicipio(MunicipioIBGE.obterPorCodigo(nfse.getInformacaoNota().getServico().getCodigoMunicipio()).getMunicipio());
						nfs.setCnpjPrestador(nfse.getInformacaoNota().getPrestadorServico().getIdentificacao().getCnpj());
						nfs.setPrestador(nfse.getInformacaoNota().getPrestadorServico().getNomeFantasia());
						nfs.setCnpjTomador(nfse.getInformacaoNota().getTomadorServico().getIdentificacao().getCpfCnpj().getCnpj());
						nfs.setTomador(nfse.getInformacaoNota().getTomadorServico().getRazaoSocial());
						nfs.setNumeroNota(nfse.getInformacaoNota().getNumero());
						nfs.setDtEmissao(nfse.getInformacaoNota().getDataEmissao());
						nfs.setStatus(Status.PENDENTE);
						nfs.setValor(nfse.getInformacaoNota().getServico().getValores().getLiquidoNfse());
						nfs.setXml(xml);
						nfs.setDataInclusao(Calendar.getInstance().getTime());
						nfs.setUsuarioInclusao(usuario);
						nfs.setEstiloXML(EstiloXML.CURITIBA_UNICA);
						Coligada coligada = daoColigada.obterColigadaPorCNPJ(nfs.getCnpjTomador());
						if(Util.isNotNull(coligada)) {
							nfs.setColigada(coligada);
							daoNotaFiscalServico.incluir(nfs);
						}
						
					}
				} catch (JAXBException e) {
					try {
						JAXBContext context = JAXBContext.newInstance(ListaNfseXML.class);
						Unmarshaller unmarshaller = context.createUnmarshaller();
						ListaNfseXML nfse = (ListaNfseXML) unmarshaller.unmarshal(new StringReader(xml));
						
						for(tcCompNfseXML nf : nfse.getListaNfse()) {
							
							Long codigoMunicipio = nf.getNfse().getInformacaoNota().getServico().getCodigoMunicipio();
							
							if(codigoMunicipio.equals(0L)) {
								if(TreatString.isNotBlank(nf.getNfse().getInformacaoNota().getPrestadorServico().getEndereco().getCodigoMunicipio())) {
									codigoMunicipio = Long.parseLong(nf.getNfse().getInformacaoNota().getPrestadorServico().getEndereco().getCodigoMunicipio());
								}
							}
							
							NotaFiscalServico nfs = new NotaFiscalServico();
							nfs.setMunicipio(MunicipioIBGE.obterPorCodigo(codigoMunicipio).getMunicipio());
							nfs.setCnpjPrestador(nf.getNfse().getInformacaoNota().getPrestadorServico().getIdentificacao().getCnpj());
							nfs.setPrestador(nf.getNfse().getInformacaoNota().getPrestadorServico().getNomeFantasia());
							nfs.setCnpjTomador(nf.getNfse().getInformacaoNota().getTomadorServico().getIdentificacao().getCpfCnpj().getCnpj());
							nfs.setTomador(nf.getNfse().getInformacaoNota().getTomadorServico().getRazaoSocial());
							nfs.setNumeroNota(nf.getNfse().getInformacaoNota().getNumero());
							nfs.setDtEmissao(nf.getNfse().getInformacaoNota().getDataEmissao());
							nfs.setStatus(Status.PENDENTE);
							nfs.setValor(nf.getNfse().getInformacaoNota().getServico().getValores().getLiquidoNfse());
							nfs.setXml(xml);
							nfs.setDataInclusao(Calendar.getInstance().getTime());
							nfs.setUsuarioInclusao(usuario);
							nfs.setEstiloXML(EstiloXML.CURITIBA_VARIAS);
							Coligada coligada = daoColigada.obterColigadaPorCNPJ(nfs.getCnpjTomador());
							if(Util.isNotNull(coligada)) {
								nfs.setColigada(coligada);
								daoNotaFiscalServico.incluir(nfs);
							}
							
							
						}
					} catch (JAXBException ex) {
						LOG.info("Nota não mapeada.");
						throw ex;
					}
				}
			}
		} catch (ApplicationException e) {
			LOG.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "incluirXML" }, e);
		}
	}
	
	/**
	 * Método responsavel por validar se o xml é mapeado no sistema
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @param xml : String
	 * @return Boolean
	 * @throws ApplicationException
	 */
	public Boolean validarXML(String xml) throws ApplicationException {
		try {
			if(TreatString.isNotBlank(xml)) {
				try {
					JAXBContext context = JAXBContext.newInstance(NfseXML.class);
					Unmarshaller unmarshaller = context.createUnmarshaller();
					NfseXML nfse = (NfseXML) unmarshaller.unmarshal(new StringReader(xml));
					
					if(Util.isNotNull(nfse)) {
						return Boolean.TRUE;
					}
				} catch (JAXBException e) {
					try {
						JAXBContext context = JAXBContext.newInstance(ListaNfseXML.class);
						Unmarshaller unmarshaller = context.createUnmarshaller();
						ListaNfseXML nfse = (ListaNfseXML) unmarshaller.unmarshal(new StringReader(xml));
						
						if(Util.isNotNull(nfse)) {
							return Boolean.TRUE;
						}
					} catch (JAXBException ex) {
						return Boolean.FALSE;
					}
				}
			}
			return Boolean.FALSE;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "validarXML" }, e);
		}
	}
	
	/**
	 * Método responsavel por listar coligadas ativas
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @return List<Coligada>
	 * @throws ApplicationException
	 */
	public List<Coligada> listarColigadasAtivas() throws ApplicationException {
		try {
			return daoColigada.listarColigadasAtivas();
		} catch (ApplicationException e) {
			LOG.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "listarColigadasAtivas" }, e);
		}
	}
	
	/**
	 * Método responsavel por obter quantidade de nfse no BD
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @param filtro : FiltroConsultaNFSE
	 * @return Integer
	 * @throws ApplicationException
	 */
	public Integer obterQtdNotasServico(FiltroConsultaNFSE filtro) throws ApplicationException {
		try {
			return daoNotaFiscalServico.obterQtdNota(filtro);
		} catch (ApplicationException e) {
			LOG.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "obterQtdNotasServico" }, e);
		}
	}
	
	/**
	 * Método responsavel por listar notas de serviço paginadas
	 * @author Leonan Mattos <leonan.mattos@grupojcr.com.br>
	 * @since 05/04/2018
	 * @param first : int
	 * @param pageSize : int
	 * @param filtro : FiltroConsultaNFSE
	 * @return List<NotaFiscalServico>
	 * @throws ApplicationException
	 */
	public List<NotaFiscalServico> listarNotaServicoPaginada(int first, int pageSize, FiltroConsultaNFSE filtro) throws ApplicationException {
		try {
			return daoNotaFiscalServico.listarNotaServicoPaginada(first, pageSize, filtro);
		} catch (ApplicationException e) {
			LOG.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "listarNotaServicoPaginada" }, e);
		}
	}
}
