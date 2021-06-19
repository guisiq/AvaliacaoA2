package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class ConsultaUsuarioController implements Serializable {
	
	private static final long serialVersionUID = -7607053657604748068L;
	private List<Usuario> listaUsuario = null;
	private Integer tipoFiltro;
	private String filtro;
	private String pesquisar;
	
	public String novoUsuario() {
		return "usuario.xhtml?faces-redirect=true";
	}
	
	public String editar(Usuario usu) {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usuario = dao.obterUm(usu.getId());
		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("usuarioFlash", usuario);
		
		return "usuario.xhtml?faces-redirect=true";
	}
	
	public void excluir(Usuario obj) {
		UsuarioDAO dao = new UsuarioDAO();
		dao.excluir(obj.getId());
		listaUsuario = null;
	}
	public List<Usuario> getListaUsuarios() {
		if (listaUsuario == null) {
			DAO<Usuario> dao = new UsuarioDAO();
			listaUsuario= dao.obterTodos();
			if (listaUsuario == null)
				listaUsuario = new ArrayList<Usuario>();
		}
		return listaUsuario;
	}

	public void setListaProduto(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Integer getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(Integer tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	
}
