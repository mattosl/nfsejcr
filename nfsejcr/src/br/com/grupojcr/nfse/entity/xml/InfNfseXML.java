package br.com.grupojcr.nfse.entity.xml;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class InfNfseXML {
	
	@XmlElement(name = "Numero", required = true)
	private Integer numero;
	
	@XmlElement(name = "CodigoVerificacao", required = true)
	private String codigoVerificacao;
	
	@XmlElement(name = "DataEmissao")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dataEmissao;
	
	@XmlElement(name = "IdentificacaoRps")
	private IdentificacaoRpsXML identificacaoRps;
	
	@XmlElement(name = "DataEmissaoRps")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dataEmissaoRps;
	
	@XmlElement(name = "NaturezaOperacao")
	private Integer naturezaOperacao;

	@XmlElement(name = "RegimeEspecialTributacao")
	private Integer regimeEspecialTributacao;

	@XmlElement(name = "OptanteSimplesNacional")
	private Integer optanteSimplesNacional;

	@XmlElement(name = "IncentivadorCultural")
	private Integer incentivadorCultural;
	
	@XmlElement(name = "Competencia")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date competencia;
	
	@XmlElement(name = "NfseSubstituida")
	private Integer nfseSubstituida;
	
	@XmlElement(name = "Servico")
	private ServicoXML servico;
	
	@XmlElement(name = "ValorCredito")
	private Integer valorCredito;
	
	@XmlElement(name = "PrestadorServico")
	private PrestadorServicoXML prestadorServico;

	@XmlElement(name = "TomadorServico")
	private TomadorServicoXML tomadorServico;
	
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getCodigoVerificacao() {
		return codigoVerificacao;
	}

	public void setCodigoVerificacao(String codigoVerificacao) {
		this.codigoVerificacao = codigoVerificacao;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public IdentificacaoRpsXML getIdentificacaoRps() {
		return identificacaoRps;
	}

	public void setIdentificacaoRps(IdentificacaoRpsXML identificacaoRps) {
		this.identificacaoRps = identificacaoRps;
	}

	public Date getDataEmissaoRps() {
		return dataEmissaoRps;
	}

	public void setDataEmissaoRps(Date dataEmissaoRps) {
		this.dataEmissaoRps = dataEmissaoRps;
	}

	public Integer getNaturezaOperacao() {
		return naturezaOperacao;
	}

	public void setNaturezaOperacao(Integer naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}

	public Integer getRegimeEspecialTributacao() {
		return regimeEspecialTributacao;
	}

	public void setRegimeEspecialTributacao(Integer regimeEspecialTributacao) {
		this.regimeEspecialTributacao = regimeEspecialTributacao;
	}

	public Integer getOptanteSimplesNacional() {
		return optanteSimplesNacional;
	}

	public void setOptanteSimplesNacional(Integer optanteSimplesNacional) {
		this.optanteSimplesNacional = optanteSimplesNacional;
	}

	public Integer getIncentivadorCultural() {
		return incentivadorCultural;
	}

	public void setIncentivadorCultural(Integer incentivadorCultural) {
		this.incentivadorCultural = incentivadorCultural;
	}

	public Date getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Date competencia) {
		this.competencia = competencia;
	}

	public Integer getNfseSubstituida() {
		return nfseSubstituida;
	}

	public void setNfseSubstituida(Integer nfseSubstituida) {
		this.nfseSubstituida = nfseSubstituida;
	}

	public ServicoXML getServico() {
		return servico;
	}

	public void setServico(ServicoXML servico) {
		this.servico = servico;
	}

	public Integer getValorCredito() {
		return valorCredito;
	}

	public void setValorCredito(Integer valorCredito) {
		this.valorCredito = valorCredito;
	}

	public PrestadorServicoXML getPrestadorServico() {
		return prestadorServico;
	}

	public void setPrestadorServico(PrestadorServicoXML prestadorServico) {
		this.prestadorServico = prestadorServico;
	}

	public TomadorServicoXML getTomadorServico() {
		return tomadorServico;
	}

	public void setTomadorServico(TomadorServicoXML tomadorServico) {
		this.tomadorServico = tomadorServico;
	}


}
