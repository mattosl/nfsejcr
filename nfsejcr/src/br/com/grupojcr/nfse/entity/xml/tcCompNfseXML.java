package br.com.grupojcr.nfse.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class tcCompNfseXML {
	
	@XmlElement(name = "Nfse")
	private NfseXML nfse;

	public NfseXML getNfse() {
		return nfse;
	}

	public void setNfse(NfseXML nfse) {
		this.nfse = nfse;
	}

}
