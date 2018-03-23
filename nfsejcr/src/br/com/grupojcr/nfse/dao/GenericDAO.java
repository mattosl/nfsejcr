package br.com.grupojcr.nfse.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import br.com.grupojcr.nfse.util.exception.ApplicationException;

@Stateless
public class GenericDAO<T>  {
	
	private static Logger log = Logger.getLogger(GenericDAO.class);
	private final static String KEY_ERRO = "ERRO:";
	private final static String KEY_MENSAGEM_PADRAO = "message.default";
	
	@PersistenceContext(unitName = "nfsejcr")
	protected EntityManager manager;
	
	/**
	 * Torna a entidade gerenciada (managed) e persistente.
	 *
	 * @param obj Objeto a ser salvo
	 * @throws ApplicationException
	 */
	public void incluir(T obj) throws ApplicationException {
		try {
			
			this.manager.persist(obj);
			
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, e);
		}
	}

	/**
	 * Atualiza o estado da entidade para o contexto de persistência corrente.
	 *
	 * @param obj Objeto a ser atualizado
	 * @throws ApplicationException
	 */
	public void alterar(T obj) throws ApplicationException {
		try {
			
			this.manager.merge(obj);
			
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, e);
		}
	}

	/**
	 * Remove a entidade do contexto de persistência e da base de dados.
	 *
	 * @param id Objeto a ser removido
	 * @throws ApplicationException
	 */
	public void excluir(Object obj) throws ApplicationException {
		try {
			obj = this.manager.merge(obj);
			
			this.manager.remove(obj);
			
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, e);
		}
	}

	/**
	 * Procura por uma entidade, da classe específica, por chave primária.
	 *
	 * @param id
	 * @return
	 * @throws ApplicationException
	 */
	public T obter(Object id) throws ApplicationException {
		try {
			if (id == null) {
				return null;
			}
			return this.manager.find(getThisClass(), id);
			
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, e);
		}
	}

	/**
	 * Verificar existencia por chave primária.
	 *
	 * @param id
	 * @return
	 * @throws ApplicationException
	 */
	public boolean verificarExistencia(Object id) throws ApplicationException {
		try {
			Object obj = this.obter(id);
			
			return obj != null;
			
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, e);
		}
	}

	/**
	 * Retorna uma lista com todos os objetos cadastrados
	 *
	 * @return
	 * @throws ApplicationException
	 */
	public List<T> listar() throws ApplicationException {
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(getThisClass());
			Root<T> rootEntry = cq.from(getThisClass());
			CriteriaQuery<T> all = cq.select(rootEntry);
			TypedQuery<T> allQuery = manager.createQuery(all);
			
			return allQuery.getResultList();
			
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, e);
		}
	}

	/**
	 * Retorna uma lista com todos os objetos cadastrados ordenados
	 *
	 *      Parametro de ordenacao :  ex: new String[]{"atributo","ordem(asc, desc)"}
	 *
	 *
	 * @param ordem: String[]
	 * @return
	 * @throws ApplicationException
	 */
	public List<T> listar(String... ordem) throws ApplicationException {
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(getThisClass());
			Root<T> rootEntry = cq.from(getThisClass());
			CriteriaQuery<T> all = cq.select(rootEntry);


			if (ordem != null) {
				List<Order> orders = new ArrayList<Order>();
				for (int i = 0; i < ordem.length; i += 2) {
					if (ordem[i+1].equalsIgnoreCase("asc")) {
						orders.add(cb.asc(rootEntry.get(ordem[i])));
					}
					else if (ordem[i+1].equalsIgnoreCase("desc")) {
						orders.add(cb.desc(rootEntry.get(ordem[i])));
					}
				}
				cq.orderBy(orders);
			}

			TypedQuery<T> allQuery = manager.createQuery(all);

			return allQuery.getResultList();
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_PADRAO, e);
		}
	}

	@SuppressWarnings("unchecked")
	private Class<T> getThisClass() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) (type).getActualTypeArguments()[0];
	}

}
