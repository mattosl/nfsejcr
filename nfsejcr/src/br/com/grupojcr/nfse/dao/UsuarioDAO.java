package br.com.grupojcr.nfse.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.grupojcr.nfse.entity.Usuario;
import br.com.grupojcr.nfse.util.exception.ApplicationException;

@Stateless
public class UsuarioDAO extends GenericDAO<Usuario> {
	
	private static Logger log = Logger.getLogger(UsuarioDAO.class);
	private final static String KEY_ERRO = "ERRO:";
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Usuario obterUsuarioPorLoginSenha(String usuario, String senha) throws ApplicationException {
		try{
			StringBuilder sb = new StringBuilder("SELECT usuario FROM Usuario usuario ");
			sb.append("WHERE usuario.usuario like :usuario ");
			sb.append("AND usuario.senha like :senha ");
			
			TypedQuery<Usuario> query = manager.createQuery(sb.toString(), Usuario.class);
			query.setParameter("usuario", usuario);
			query.setParameter("senha", senha);
			
			return query.getSingleResult();
		} catch (NoResultException nR) {
			return null;
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException("message.default.erro", new String[] { "obterUsuarioPorLoginSenha" }, e);
		}
	}

}
