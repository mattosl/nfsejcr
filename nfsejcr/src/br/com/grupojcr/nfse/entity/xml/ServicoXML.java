package br.com.grupojcr.nfse.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ServicoXML {
	
	@XmlElement(name = "Valores")
	private ValoresXML valores;
	
	@XmlElement(name = "ItemListaServico")
	private String itemListaServico;
	
	@XmlElement(name = "CodigoCnae")
	private Integer cnae;

	@XmlElement(name = "CodigoTributacaoMunicipio")
	private String codigoTributacaoMunicipio;

	@XmlElement(name = "Discriminacao")
	private String discriminacao;

	@XmlElement(name = "CodigoMunicipio")
	private Long codigoMunicipio;

	public ValoresXML getValores() {
		return valores;
	}

	public void setValores(ValoresXML valores) {
		this.valores = valores;
	}

	public String getItemListaServico() {
		return itemListaServico;
	}

	public void setItemListaServico(String itemListaServico) {
		this.itemListaServico = itemListaServico;
	}

	public Integer getCnae() {
		return cnae;
	}

	public void setCnae(Integer cnae) {
		this.cnae = cnae;
	}

	public String getCodigoTributacaoMunicipio() {
		return codigoTributacaoMunicipio;
	}

	public void setCodigoTributacaoMunicipio(String codigoTributacaoMunicipio) {
		this.codigoTributacaoMunicipio = codigoTributacaoMunicipio;
	}

	public String getDiscriminacao() {
		return discriminacao;
	}

	public void setDiscriminacao(String discriminacao) {
		this.discriminacao = discriminacao;
	}

	public Long getCodigoMunicipio() {
		return codigoMunicipio;
	}

	public void setCodigoMunicipio(Long codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

}
