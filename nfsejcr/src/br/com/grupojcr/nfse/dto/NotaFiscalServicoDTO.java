package br.com.grupojcr.nfse.dto;

public class NotaFiscalServicoDTO {
	
	private String municipio;
	private String numeroNota;
	private String dtEmissao;
	private String codigoVerificacao;
	private String razaoSocialPrestador;
	private String cnpjPrestador;
	private String inscricaoMunicipalPrestador;
	private String enderecoPrestador;
	
	private String razaoSocialTomador;
	private String cnpjTomador;
	private String inscricaoMunicipalTomador;
	private String enderecoTomador;
	
	private String discriminacao;
	
	private String valorDeducoes;
	private String baseCalculo;
	private String aliquota;
	private String valorISS;
	private String credAbatimentoIPTU;
	private String valorTotal;
	
	public String getDtEmissao() {
		return dtEmissao;
	}
	public void setDtEmissao(String dtEmissao) {
		this.dtEmissao = dtEmissao;
	}
	public String getCodigoVerificacao() {
		return codigoVerificacao;
	}
	public void setCodigoVerificacao(String codigoVerificacao) {
		this.codigoVerificacao = codigoVerificacao;
	}
	public String getRazaoSocialPrestador() {
		return razaoSocialPrestador;
	}
	public void setRazaoSocialPrestador(String razaoSocialPrestador) {
		this.razaoSocialPrestador = razaoSocialPrestador;
	}
	public String getCnpjPrestador() {
		return cnpjPrestador;
	}
	public void setCnpjPrestador(String cnpjPrestador) {
		this.cnpjPrestador = cnpjPrestador;
	}
	public String getInscricaoMunicipalPrestador() {
		return inscricaoMunicipalPrestador;
	}
	public void setInscricaoMunicipalPrestador(String inscricaoMunicipalPrestador) {
		this.inscricaoMunicipalPrestador = inscricaoMunicipalPrestador;
	}
	public String getEnderecoPrestador() {
		return enderecoPrestador;
	}
	public void setEnderecoPrestador(String enderecoPrestador) {
		this.enderecoPrestador = enderecoPrestador;
	}
	public String getRazaoSocialTomador() {
		return razaoSocialTomador;
	}
	public void setRazaoSocialTomador(String razaoSocialTomador) {
		this.razaoSocialTomador = razaoSocialTomador;
	}
	public String getCnpjTomador() {
		return cnpjTomador;
	}
	public void setCnpjTomador(String cnpjTomador) {
		this.cnpjTomador = cnpjTomador;
	}
	public String getInscricaoMunicipalTomador() {
		return inscricaoMunicipalTomador;
	}
	public void setInscricaoMunicipalTomador(String inscricaoMunicipalTomador) {
		this.inscricaoMunicipalTomador = inscricaoMunicipalTomador;
	}
	public String getEnderecoTomador() {
		return enderecoTomador;
	}
	public void setEnderecoTomador(String enderecoTomador) {
		this.enderecoTomador = enderecoTomador;
	}
	public String getDiscriminacao() {
		return discriminacao;
	}
	public void setDiscriminacao(String discriminacao) {
		this.discriminacao = discriminacao;
	}
	public String getValorDeducoes() {
		return valorDeducoes;
	}
	public void setValorDeducoes(String valorDeducoes) {
		this.valorDeducoes = valorDeducoes;
	}
	public String getBaseCalculo() {
		return baseCalculo;
	}
	public void setBaseCalculo(String baseCalculo) {
		this.baseCalculo = baseCalculo;
	}
	public String getAliquota() {
		return aliquota;
	}
	public void setAliquota(String aliquota) {
		this.aliquota = aliquota;
	}
	public String getValorISS() {
		return valorISS;
	}
	public void setValorISS(String valorISS) {
		this.valorISS = valorISS;
	}
	public String getCredAbatimentoIPTU() {
		return credAbatimentoIPTU;
	}
	public void setCredAbatimentoIPTU(String credAbatimentoIPTU) {
		this.credAbatimentoIPTU = credAbatimentoIPTU;
	}
	public String getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getNumeroNota() {
		return numeroNota;
	}
	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

}
