package br.com.grupojcr.nfse.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.grupojcr.nfse.entity.Coligada;
import br.com.grupojcr.nfse.util.exception.ApplicationException;

@Stateless
public class ColigadaDAO extends GenericDAO<Coligada> {
	
	private static Logger log = Logger.getLogger(ColigadaDAO.class);
	private final static String KEY_ERRO = "ERRO:";

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Coligada obterColigadaPorCNPJ(String cnpj) throws ApplicationException {
		try{
			StringBuilder sb = new StringBuilder("SELECT coligada FROM Coligada coligada ");
			sb.append("WHERE coligada.cnpj like :cnpj ");
			
			TypedQuery<Coligada> query = manager.createQuery(sb.toString(), Coligada.class);
			query.setParameter("cnpj", cnpj);
			
			return query.getSingleResult();
		} catch (NoResultException nR) {
			return null;
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException("message.default.erro", new String[] { "obterColigadaPorCNPJ" }, e);
		}
	}
}
