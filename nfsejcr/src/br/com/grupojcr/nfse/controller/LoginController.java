package br.com.grupojcr.nfse.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.PrimeFaces;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = -1133855238493822199L;
	
	// Simple user database :)
    private static final String[] users = {"admin:admin"};
	
	private String login;
	private String senha;
	
	private boolean logado;
	
	@ManagedProperty(value="#{navegacaoController}")
	private NavegacaoController navegacaoController;
	
	 /**
     * Login operation.
     * @return
     */
    public String doLogin() {
        // Get every user from our sample database :)
        for (String user: users) {
            String dbUsername = user.split(":")[0];
            String dbPassword = user.split(":")[1];
             
            // Successful login
            if (dbUsername.equals(login) && dbPassword.equals(senha)) {
            	logado = true;
                return navegacaoController.redirectToWelcome();
            }
        }
         
        showMessage();
         
        // To to login page
        return navegacaoController.toLogin();
         
    }
     
    /**
     * Logout operation.
     * @return
     */
    public String doLogout() {
        // Set the paremeter indicating that user is logged in to false
        logado = false;
         
        return navegacaoController.redirectToLogin();
    }
    
    public void showMessage() {
       FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ops!", "Login ou senha incorretos.");
        
       PrimeFaces.current().dialog().showMessageDynamic(message);
    }

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public NavegacaoController getNavegacaoController() {
		return navegacaoController;
	}

	public void setNavegacaoController(NavegacaoController navegacaoController) {
		this.navegacaoController = navegacaoController;
	}

	public static String[] getUsers() {
		return users;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

}
