package br.com.grupojcr.nfse.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.grupojcr.nfse.enumerator.Status;

@Entity
@Table(name = "TB_NFSE")
public class NotaFiscalServico {
	
	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "MUNICIPIO", length = 50, nullable = false)
	private String municipio;

	@Column(name = "CNPJ_PRESTADOR", length = 14, nullable = false)
	private String cnpjPrestador;

	@Column(name = "PRESTADOR", length = 100, nullable = false)
	private String prestador;

	@Column(name = "CNPJ_TOMADOR", length = 14, nullable = false)
	private String cnpjTomador;

	@Column(name = "TOMADOR", length = 100, nullable = false)
	private String tomador;

	@Column(name = "NUMERO", nullable = false)
	private Long numeroNota;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_EMISSAO", nullable = false)
	private Date dtEmissao;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "STATUS", nullable = false)
	private Status status;

	@Column(name = "VALOR", nullable = false, precision = 10, scale = 2)
	private Double valor;

	@Column(name = "XML", nullable = false)
	private String xml;

	@Column(name = "IDENTIFICADOR_RM")
	private Long identificadorRM;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_EXPORTACAO")
	private Date dataExportacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_INCLUSAO", nullable = false)
	private Date dataInclusao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_COLIGADA")
	private Coligada coligada;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USR_EXPORTACAO")
	private Usuario usuarioExportacao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USR_INCLUSAO")
	private Usuario usuarioInclusao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCnpjPrestador() {
		return cnpjPrestador;
	}

	public void setCnpjPrestador(String cnpjPrestador) {
		this.cnpjPrestador = cnpjPrestador;
	}

	public String getPrestador() {
		return prestador;
	}

	public void setPrestador(String prestador) {
		this.prestador = prestador;
	}

	public String getCnpjTomador() {
		return cnpjTomador;
	}

	public void setCnpjTomador(String cnpjTomador) {
		this.cnpjTomador = cnpjTomador;
	}

	public String getTomador() {
		return tomador;
	}

	public void setTomador(String tomador) {
		this.tomador = tomador;
	}

	public Long getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(Long numeroNota) {
		this.numeroNota = numeroNota;
	}

	public Date getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Date dtEmissao) {
		this.dtEmissao = dtEmissao;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Long getIdentificadorRM() {
		return identificadorRM;
	}

	public void setIdentificadorRM(Long identificadorRM) {
		this.identificadorRM = identificadorRM;
	}

	public Date getDataExportacao() {
		return dataExportacao;
	}

	public void setDataExportacao(Date dataExportacao) {
		this.dataExportacao = dataExportacao;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Coligada getColigada() {
		return coligada;
	}

	public void setColigada(Coligada coligada) {
		this.coligada = coligada;
	}

	public Usuario getUsuarioExportacao() {
		return usuarioExportacao;
	}

	public void setUsuarioExportacao(Usuario usuarioExportacao) {
		this.usuarioExportacao = usuarioExportacao;
	}

	public Usuario getUsuarioInclusao() {
		return usuarioInclusao;
	}

	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpjPrestador == null) ? 0 : cnpjPrestador.hashCode());
		result = prime * result + ((cnpjTomador == null) ? 0 : cnpjTomador.hashCode());
		result = prime * result + ((coligada == null) ? 0 : coligada.hashCode());
		result = prime * result + ((dataExportacao == null) ? 0 : dataExportacao.hashCode());
		result = prime * result + ((dataInclusao == null) ? 0 : dataInclusao.hashCode());
		result = prime * result + ((dtEmissao == null) ? 0 : dtEmissao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((identificadorRM == null) ? 0 : identificadorRM.hashCode());
		result = prime * result + ((municipio == null) ? 0 : municipio.hashCode());
		result = prime * result + ((numeroNota == null) ? 0 : numeroNota.hashCode());
		result = prime * result + ((prestador == null) ? 0 : prestador.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tomador == null) ? 0 : tomador.hashCode());
		result = prime * result + ((usuarioExportacao == null) ? 0 : usuarioExportacao.hashCode());
		result = prime * result + ((usuarioInclusao == null) ? 0 : usuarioInclusao.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		result = prime * result + ((xml == null) ? 0 : xml.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotaFiscalServico other = (NotaFiscalServico) obj;
		if (cnpjPrestador == null) {
			if (other.cnpjPrestador != null)
				return false;
		} else if (!cnpjPrestador.equals(other.cnpjPrestador))
			return false;
		if (cnpjTomador == null) {
			if (other.cnpjTomador != null)
				return false;
		} else if (!cnpjTomador.equals(other.cnpjTomador))
			return false;
		if (coligada == null) {
			if (other.coligada != null)
				return false;
		} else if (!coligada.equals(other.coligada))
			return false;
		if (dataExportacao == null) {
			if (other.dataExportacao != null)
				return false;
		} else if (!dataExportacao.equals(other.dataExportacao))
			return false;
		if (dataInclusao == null) {
			if (other.dataInclusao != null)
				return false;
		} else if (!dataInclusao.equals(other.dataInclusao))
			return false;
		if (dtEmissao == null) {
			if (other.dtEmissao != null)
				return false;
		} else if (!dtEmissao.equals(other.dtEmissao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (identificadorRM == null) {
			if (other.identificadorRM != null)
				return false;
		} else if (!identificadorRM.equals(other.identificadorRM))
			return false;
		if (municipio == null) {
			if (other.municipio != null)
				return false;
		} else if (!municipio.equals(other.municipio))
			return false;
		if (numeroNota == null) {
			if (other.numeroNota != null)
				return false;
		} else if (!numeroNota.equals(other.numeroNota))
			return false;
		if (prestador == null) {
			if (other.prestador != null)
				return false;
		} else if (!prestador.equals(other.prestador))
			return false;
		if (status != other.status)
			return false;
		if (tomador == null) {
			if (other.tomador != null)
				return false;
		} else if (!tomador.equals(other.tomador))
			return false;
		if (usuarioExportacao == null) {
			if (other.usuarioExportacao != null)
				return false;
		} else if (!usuarioExportacao.equals(other.usuarioExportacao))
			return false;
		if (usuarioInclusao == null) {
			if (other.usuarioInclusao != null)
				return false;
		} else if (!usuarioInclusao.equals(other.usuarioInclusao))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		if (xml == null) {
			if (other.xml != null)
				return false;
		} else if (!xml.equals(other.xml))
			return false;
		return true;
	}

}
