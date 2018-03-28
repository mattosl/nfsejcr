package br.com.grupojcr.nfse.entity.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Nfse")
@XmlAccessorType(XmlAccessType.FIELD)
public class NfseXML {
	
	@XmlElement(name = "InfNfse")
	private InfNfseXML informacaoNota;
	
	@XmlElementWrapper(name="ArrayOfTcCompNfse")
	@XmlElement(name = "tcCompNfse")
	private List<tcCompNfseXML> listaNfse;

	public InfNfseXML getInformacaoNota() {
		return informacaoNota;
	}

	public void setInformacaoNota(InfNfseXML informacaoNota) {
		this.informacaoNota = informacaoNota;
	}

}
