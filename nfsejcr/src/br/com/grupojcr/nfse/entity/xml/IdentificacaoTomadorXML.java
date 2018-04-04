package br.com.grupojcr.nfse.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class IdentificacaoTomadorXML {

	@XmlElement(name = "CpfCnpj")
	private CpfCnpjXML cpfCnpj;

	@XmlElement(name = "InscricaoMunicipal")
	private String inscricaoMunicipal;

	public CpfCnpjXML getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(CpfCnpjXML cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

}
