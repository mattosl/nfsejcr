package br.com.grupojcr.nfse.dao;

import java.util.Iterator;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import br.com.grupojcr.nfse.util.TreatString;

public abstract class DAOUtil {

	/**
	 * Método que monta o Sorter para consultas
	 * @param sortField - Coluna de ordenação
	 * @param sortOrder - SorterOrder ASCENDING DESCENDING
	 * @param sb - Query
	 */
	public static void sorterQuery(String aliasObject, String sortField, SortOrder sortOrder, StringBuilder sb) {
		aliasObject = StringUtils.isNotBlank(aliasObject) ? aliasObject+"." : "";
		if(TreatString.isNotBlank(sortField) && sortOrder!=null){
			sb.append(" ORDER BY ");
			if(sortOrder.equals(SortOrder.ASCENDING)){
				sb.append(aliasObject+sortField + " ASC");
			}else if (sortOrder.equals(SortOrder.DESCENDING)){
				sb.append(aliasObject+sortField + " DESC");
			}				
		}
	}
	
	/**
	 * Método usado para setar os parametros de acordo com atributos de pesquisa
	 * @param parameters
	 * @param query
	 */
	public static void setParameters(Map<String, Object> parameters, Query query) {
		Iterator<String> keySetIterator = parameters.keySet().iterator();

		while(keySetIterator.hasNext()){
		  String key = keySetIterator.next();
		  query.setParameter(key, parameters.get(key));
		}
	}
}
