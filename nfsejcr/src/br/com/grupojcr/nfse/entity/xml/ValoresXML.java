package br.com.grupojcr.nfse.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ValoresXML {
	
	@XmlElement(name = "ValorServicos")
	private Double servico;
	
	@XmlElement(name = "ValorDeducoes")
	private Double deducoes;
	
	@XmlElement(name = "ValorPis")
	private Double pis;
	
	@XmlElement(name = "ValorCofins")
	private Double cofins;
	
	@XmlElement(name = "ValorInss")
	private Double inss;
	
	@XmlElement(name = "ValorIr")
	private Double ir;

	@XmlElement(name = "ValorCsll")
	private Double csll;

	@XmlElement(name = "IssRetido")
	private Integer retidoISS;

	@XmlElement(name = "ValorIss")
	private Double iss;

	@XmlElement(name = "ValorIssRetido")
	private Double issRetido;

	@XmlElement(name = "OutrasRetencoes")
	private Double outrasRetencoes;

	@XmlElement(name = "BaseCalculo")
	private Double baseCalculo;

	@XmlElement(name = "Aliquota")
	private Double aliquota;

	@XmlElement(name = "ValorLiquidoNfse")
	private Double liquidoNfse;
	
	@XmlElement(name = "DescontoIncondicionado")
	private Double descontoIncondicionado;

	@XmlElement(name = "DescontoCondicionado")
	private Double descontoCondicionado;

	public Double getServico() {
		return servico;
	}

	public void setServico(Double servico) {
		this.servico = servico;
	}

	public Double getDeducoes() {
		return deducoes;
	}

	public void setDeducoes(Double deducoes) {
		this.deducoes = deducoes;
	}

	public Double getPis() {
		return pis;
	}

	public void setPis(Double pis) {
		this.pis = pis;
	}

	public Double getCofins() {
		return cofins;
	}

	public void setCofins(Double cofins) {
		this.cofins = cofins;
	}

	public Double getInss() {
		return inss;
	}

	public void setInss(Double inss) {
		this.inss = inss;
	}

	public Double getIr() {
		return ir;
	}

	public void setIr(Double ir) {
		this.ir = ir;
	}

	public Double getCsll() {
		return csll;
	}

	public void setCsll(Double csll) {
		this.csll = csll;
	}

	public Integer getRetidoISS() {
		return retidoISS;
	}

	public void setRetidoISS(Integer retidoISS) {
		this.retidoISS = retidoISS;
	}

	public Double getIss() {
		return iss;
	}

	public void setIss(Double iss) {
		this.iss = iss;
	}

	public Double getIssRetido() {
		return issRetido;
	}

	public void setIssRetido(Double issRetido) {
		this.issRetido = issRetido;
	}

	public Double getOutrasRetencoes() {
		return outrasRetencoes;
	}

	public void setOutrasRetencoes(Double outrasRetencoes) {
		this.outrasRetencoes = outrasRetencoes;
	}

	public Double getBaseCalculo() {
		return baseCalculo;
	}

	public void setBaseCalculo(Double baseCalculo) {
		this.baseCalculo = baseCalculo;
	}

	public Double getAliquota() {
		return aliquota;
	}

	public void setAliquota(Double aliquota) {
		this.aliquota = aliquota;
	}

	public Double getLiquidoNfse() {
		return liquidoNfse;
	}

	public void setLiquidoNfse(Double liquidoNfse) {
		this.liquidoNfse = liquidoNfse;
	}

	public Double getDescontoIncondicionado() {
		return descontoIncondicionado;
	}

	public void setDescontoIncondicionado(Double descontoIncondicionado) {
		this.descontoIncondicionado = descontoIncondicionado;
	}

	public Double getDescontoCondicionado() {
		return descontoCondicionado;
	}

	public void setDescontoCondicionado(Double descontoCondicionado) {
		this.descontoCondicionado = descontoCondicionado;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
