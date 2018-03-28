package br.com.grupojcr.nfse.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PrestadorServicoXML {
	
	@XmlElement(name = "IdentificacaoPrestador")
	private IdentificacaoPrestadorXML identificacao;
	
	@XmlElement(name = "NomeFantasia")
	private String nomeFantasia;
	
	@XmlElement(name = "Endereco")
	private EnderecoXML endereco;

	public IdentificacaoPrestadorXML getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(IdentificacaoPrestadorXML identificacao) {
		this.identificacao = identificacao;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public EnderecoXML getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoXML endereco) {
		this.endereco = endereco;
	}

}
