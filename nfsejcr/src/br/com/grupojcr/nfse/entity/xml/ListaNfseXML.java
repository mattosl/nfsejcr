package br.com.grupojcr.nfse.entity.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ArrayOfTcCompNfse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListaNfseXML {
	
	@XmlElement(name = "tcCompNfse")
	private List<tcCompNfseXML> listaNfse;

	public List<tcCompNfseXML> getListaNfse() {
		return listaNfse;
	}

	public void setListaNfse(List<tcCompNfseXML> listaNfse) {
		this.listaNfse = listaNfse;
	}


}
