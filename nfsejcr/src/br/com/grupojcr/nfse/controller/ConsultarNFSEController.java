package br.com.grupojcr.nfse.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ConsultarNFSEController implements Serializable {

	private static final long serialVersionUID = 764194435849716691L;
	
	private Date dtInicio;
	
	private List<String> teste = new ArrayList<String>(Arrays.asList("teste", "teste", "teste", "teste"));
	private List<String> testeSelecionados = new ArrayList<String>();
	
	public String iniciarIncluir() {
		return "/pages/nfse/incluir_nfse.xhtml";
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public List<String> getTeste() {
		return teste;
	}

	public void setTeste(List<String> teste) {
		this.teste = teste;
	}

	public List<String> getTesteSelecionados() {
		return testeSelecionados;
	}

	public void setTesteSelecionados(List<String> testeSelecionados) {
		this.testeSelecionados = testeSelecionados;
	}

}
