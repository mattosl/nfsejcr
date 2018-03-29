package br.com.grupojcr.nfse.business;

import java.io.StringReader;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import br.com.grupojcr.nfse.dao.ColigadaDAO;
import br.com.grupojcr.nfse.dao.NotaFiscalServicoDAO;
import br.com.grupojcr.nfse.entity.Coligada;
import br.com.grupojcr.nfse.entity.NotaFiscalServico;
import br.com.grupojcr.nfse.entity.Usuario;
import br.com.grupojcr.nfse.entity.xml.NfseXML;
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
	
	public void incluirXML(String xml, Usuario usuario) throws ApplicationException {
		try {
			if(TreatString.isNotBlank(xml)) {
				JAXBContext context = JAXBContext.newInstance(NfseXML.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				NfseXML nfse = (NfseXML) unmarshaller.unmarshal(new StringReader(xml));

				if(Util.isNotNull(nfse)) {
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
					Coligada coligada = daoColigada.obter(7L); //TODO
					nfs.setColigada(coligada);
					
					daoNotaFiscalServico.incluir(nfs);
					
				}
			}
		} catch (ApplicationException e) {
			LOG.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, new String[] { "incluirNotaFiscalServicoXML" }, e);
		}
	}

}
