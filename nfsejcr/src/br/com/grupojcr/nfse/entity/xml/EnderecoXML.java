package br.com.grupojcr.nfse.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class EnderecoXML {
	
	@XmlElement(name = "Endereco")
	private String endereco;
	
	@XmlElement(name = "Numero")
	private String numero;
	
	@XmlElement(name = "Bairro")
	private String bairro;
	
	@XmlElement(name = "CodigoMunicipio")
	private String codigoMunicipio;
	
	@XmlElement(name = "Uf")
	private String uf;
	
	@XmlElement(name = "Cep")
	private String cep;
	
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}
	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}

}
