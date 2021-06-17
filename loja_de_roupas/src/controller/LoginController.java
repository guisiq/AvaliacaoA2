package controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import application.Session;
import application.Util;
import dao.UsuarioDAO;
import model.Usuario;

@Named
@RequestScoped
public class LoginController {

	private Usuario usuario;
	
	public String entrar() {
		UsuarioDAO dao = new UsuarioDAO();
		String hash = Util.hash(getUsuario().getSenha() + getUsuario().getLogin());
		getUsuario().setSenha(hash);
		Usuario usuarioLogado = dao.validarLogin(getUsuario());
		if (usuarioLogado != null) {

			Session.getInstance().set("usuarioLogado", usuarioLogado);
			
			return "usuario.xhtml";
			
		}
		Util.addErrorMessage("Login ou senha inválido.");
		return null;
		
	}
	
	public void limpar() {
		usuario = null;
	}

	public Usuario getUsuario() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
