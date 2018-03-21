package br.com.grupojcr.nfse.controller;

import java.io.Serializable;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ConsultarNFSEController implements Serializable {

	private static final long serialVersionUID = 764194435849716691L;
	
	private Date dtInicio;

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

}
