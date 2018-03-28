package br.com.grupojcr.nfse.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TomadorServicoXML {
	
	@XmlElement(name = "IdentificacaoTomador")
	private IdentificacaoTomadorXML identificacao;
	
	@XmlElement(name = "RazaoSocial")
	private String razaoSocial;
	
	@XmlElement(name = "Endereco")
	private EnderecoXML endereco;
	
	@XmlElement(name = "Contato")
	private ContatoXML contato;

	public IdentificacaoTomadorXML getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(IdentificacaoTomadorXML identificacao) {
		this.identificacao = identificacao;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public EnderecoXML getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoXML endereco) {
		this.endereco = endereco;
	}

	public ContatoXML getContato() {
		return contato;
	}

	public void setContato(ContatoXML contato) {
		this.contato = contato;
	}


}
