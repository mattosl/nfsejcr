package br.com.grupojcr.nfse.dto;

import org.primefaces.model.UploadedFile;

public class AnexoDTO {
	
	private String conteudo;
	private String nomeArquivo;
	
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

}
