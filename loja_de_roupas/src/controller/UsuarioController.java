package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import application.Util;
import dao.DAO;
import dao.UsuarioDAO;
import model.Perfil;
import model.Usuario;

@Named
@ViewScoped
public class UsuarioController implements Serializable{
	
	private static final long serialVersionUID = 1304667158255601678L;
	private Usuario usuario = null;
	private String confirmarSenha;
	private List<Usuario> listaUsuario = null;
	private UIComponent uicCpf;
	
	public UIComponent getUicCpf() {
		return uicCpf;
	}

	public void setUicCpf(UIComponent uicCpf) {
		this.uicCpf = uicCpf;
	}

	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}
	
	public UsuarioController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("usuarioFlash");
		setUsuario((Usuario)flash.get("usuarioFlash"));
	}
	
	public void validarCpf() {
		if (getUsuario().getCpf().equals("111.111.111-11")) {
			Util.addErrorMessage("O cpf: 111.111.111-11 é inválido.", uicCpf.getClientId());
			;
		}
	}
	
	public boolean verificaSenha() {
		if (getUsuario().getSenha().equals(getConfirmarSenha())) {
			return true;
		}
		Util.addErrorMessage("As senhas estão diferentes.");
		return false;
	}

	public void incluir() {
		if (!verificaSenha()) {
			Util.addInfoMessage("As senhas estão diferentes.");
			return;
		}
		
		DAO<Usuario> dao = new UsuarioDAO();
		
		String hash = Util.hash(getUsuario().getSenha() + getUsuario().getLogin());
		getUsuario().setSenha(hash);
		
		if (dao.inserir(getUsuario())) {
			Util.addInfoMessage("Inclusão realizada com sucesso.");
			limpar();
		} else
			Util.addErrorMessage("Problemas ao inserir no banco de dados.");
		
	}
	
	public void alterar() {
		if (!verificaSenha()) {
			Util.addInfoMessage("As senhas estão diferentes.");
			return;
		}
		
		String hash = Util.hash(getUsuario().getSenha() + getUsuario().getLogin());
		getUsuario().setSenha(hash);
		
		DAO<Usuario> dao = new UsuarioDAO();
		if (dao.alterar(getUsuario())) {
			Util.addInfoMessage("Alteração realizada com sucesso.");
			limpar();
		} else
			Util.addErrorMessage("Problemas ao alterar no banco de dados.");
	}
	
	public void excluir() {
		excluir(getUsuario());
	}
	
	public void excluir(Usuario usu) {
		DAO<Usuario> dao = new UsuarioDAO();
		if (dao.excluir(usu.getId())) {
			Util.addInfoMessage("Exclusão realizada com sucesso.");
			limpar();
		} else
			Util.addErrorMessage("Problemas ao excluir no banco de dados.");
	}
	
	public void limpar() {
		System.out.println("Limpar");
		setUsuario(null);
		setListaUsuario(null);
	}
	
	public void editar(Usuario usu) {
		DAO<Usuario> dao = new UsuarioDAO();
		setUsuario(dao.obterUm(usu.getId()));
	}
	
	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			DAO<Usuario> dao = new UsuarioDAO();
			listaUsuario = dao.obterTodos();
			if (listaUsuario == null)
				listaUsuario = new ArrayList<Usuario>();
		}
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
	
}
