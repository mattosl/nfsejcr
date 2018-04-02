package br.com.grupojcr.nfse.dto;

import java.util.Date;

import br.com.grupojcr.nfse.entity.Coligada;
import br.com.grupojcr.nfse.enumerator.MunicipioIBGE;

public class FiltroConsultaNFSE {

	private Coligada coligada;
	private MunicipioIBGE municipio;
	private String numeroNota;
	private Date dtInicial;
	private Date dtFinal;
	private Integer situacao;

	public Coligada getColigada() {
		return coligada;
	}

	public void setColigada(Coligada coligada) {
		this.coligada = coligada;
	}

	public MunicipioIBGE getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioIBGE municipio) {
		this.municipio = municipio;
	}

	public String getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}

	public Date getDtInicial() {
		return dtInicial;
	}

	public void setDtInicial(Date dtInicial) {
		this.dtInicial = dtInicial;
	}

	public Date getDtFinal() {
		return dtFinal;
	}

	public void setDtFinal(Date dtFinal) {
		this.dtFinal = dtFinal;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}
}
