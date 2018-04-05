package br.com.grupojcr.nfse.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.grupojcr.nfse.dto.FiltroConsultaNFSE;
import br.com.grupojcr.nfse.entity.NotaFiscalServico;
import br.com.grupojcr.nfse.enumerator.Status;
import br.com.grupojcr.nfse.util.Util;
import br.com.grupojcr.nfse.util.exception.ApplicationException;

@Stateless
public class NotaFiscalServicoDAO extends GenericDAO<NotaFiscalServico> {
	
	private static Logger log = Logger.getLogger(NotaFiscalServicoDAO.class);
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Integer obterQtdNota(FiltroConsultaNFSE filtro) throws ApplicationException {
		try{
			StringBuilder sb = new StringBuilder("SELECT nfs FROM NotaFiscalServico nfs ");
			sb.append("WHERE nfs.dtEmissao BETWEEN :dtInicial AND :dtFinal ");
			
			if(Util.isNotNull(filtro.getColigada())) {
				sb.append("AND nfs.coligada.id = :idColigada ");
			}
			
			if(Util.isNotNull(filtro.getNumeroNota())) {
				sb.append("AND nfs.numeroNota = :numNota ");
			}
			
			if(!filtro.getSituacao().equals(3)) {
				sb.append("AND nfs.status = :status ");
			}
			
			TypedQuery<NotaFiscalServico> query = manager.createQuery(sb.toString(), NotaFiscalServico.class);
			query.setParameter("dtInicial", filtro.getDtInicial());
			query.setParameter("dtFinal", filtro.getDtFinal());
			
			if(Util.isNotNull(filtro.getColigada())) {
				query.setParameter("idColigada", filtro.getColigada().getId());
			}
			
			if(Util.isNotNull(filtro.getNumeroNota())) {
				query.setParameter("numNota", filtro.getNumeroNota());
			}
			
			if(!filtro.getSituacao().equals(3)) {
				query.setParameter("status", Status.obterPorCodigo(filtro.getSituacao()));
			}
			
			return query.getResultList().size();
		} catch (NoResultException nR) {
			return null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ApplicationException("message.default.erro", new String[] { "obterQtdNota" }, e);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<NotaFiscalServico> listarNotaServicoPaginada(int first, int pageSize, FiltroConsultaNFSE filtro) throws ApplicationException {
		try{
			StringBuilder sb = new StringBuilder("SELECT nfs FROM NotaFiscalServico nfs ");
			sb.append("WHERE nfs.dtEmissao BETWEEN :dtInicial AND :dtFinal ");
			
			if(Util.isNotNull(filtro.getColigada())) {
				sb.append("AND nfs.coligada.id = :idColigada ");
			}
			
			if(Util.isNotNull(filtro.getNumeroNota())) {
				sb.append("AND nfs.numeroNota = :numNota ");
			}
			
			if(!filtro.getSituacao().equals(3)) {
				sb.append("AND nfs.status = :status ");
			}
			
			TypedQuery<NotaFiscalServico> query = manager.createQuery(sb.toString(), NotaFiscalServico.class);
			query.setParameter("dtInicial", filtro.getDtInicial());
			query.setParameter("dtFinal", filtro.getDtFinal());
			
			if(Util.isNotNull(filtro.getColigada())) {
				query.setParameter("idColigada", filtro.getColigada().getId());
			}
			
			if(Util.isNotNull(filtro.getNumeroNota())) {
				query.setParameter("numNota", filtro.getNumeroNota());
			}
			
			if(!filtro.getSituacao().equals(3)) {
				query.setParameter("status", Status.obterPorCodigo(filtro.getSituacao()));
			}
			
			if(Util.isNotNull(first) && Util.isNotNull(pageSize)){
				query.setFirstResult(first);
				query.setMaxResults(pageSize);
			}
			return query.getResultList();
		} catch (NoResultException nR) {
			return null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ApplicationException("message.default.erro", new String[] { "listarNotaServicoPaginada" }, e);
		}
	}

}
