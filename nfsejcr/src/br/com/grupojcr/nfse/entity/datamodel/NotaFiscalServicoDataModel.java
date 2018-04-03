package br.com.grupojcr.nfse.entity.datamodel;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.grupojcr.nfse.business.NFSEBusiness;
import br.com.grupojcr.nfse.dto.FiltroConsultaNFSE;
import br.com.grupojcr.nfse.entity.NotaFiscalServico;
import br.com.grupojcr.nfse.util.exception.ApplicationException;
import br.com.grupojcr.nfse.util.exception.Message;

@Named
@ViewScoped
public class NotaFiscalServicoDataModel extends LazyDataModel<NotaFiscalServico> {

	private static Logger log = Logger.getLogger(NotaFiscalServicoDataModel.class);
	private static final long serialVersionUID = -4141373083662145792L;
	private final static String KEY_MENSAGEM_PADRAO = "message.default.erro";

	private FiltroConsultaNFSE filtro;
	
	private List<NotaFiscalServico> dataSource;
	
	@EJB
	private NFSEBusiness nfseBusiness;
	
	@Override
	public NotaFiscalServico getRowData(String rowKey) {
		for(NotaFiscalServico nfs : getDataSource()) {
			if(nfs.getId().toString().equals(rowKey)) {
				return nfs;
			}
		}
		return null;
	}
	
	@Override
	public Object getRowKey(NotaFiscalServico nfs) {
		return nfs.getId();
	}
	
	@Override
	public List<NotaFiscalServico> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		try {
			this.setRowCount(nfseBusiness.obterQtdNotasServico(getFiltro()));
			if(getRowCount() > 0){
				this.setDataSource(nfseBusiness.listarNotaServicoPaginada(first, pageSize, getFiltro()));
			}
		} catch(ApplicationException appEx)	{
			Message.setMessage(appEx);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			Message.setMessage(KEY_MENSAGEM_PADRAO, new String[] { "listar" }, FacesMessage.SEVERITY_ERROR);
		}
		return getDataSource();
	}

	public FiltroConsultaNFSE getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroConsultaNFSE filtro) {
		this.filtro = filtro;
	}

	public List<NotaFiscalServico> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<NotaFiscalServico> dataSource) {
		this.dataSource = dataSource;
	}

}
