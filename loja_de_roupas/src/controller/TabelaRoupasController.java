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
import dao.RoupaDAO;
import dao.UsuarioDAO;
import model.Roupa;

@Named
@ViewScoped
public class TabelaRoupasController implements Serializable {
	
	private static final long serialVersionUID = -7607053657604748068L;
	private List<Roupa> listaRoupa= null;
	private Integer tipoFiltro;
	private String filtro;
	private String pesquisar;
	
	public String novaRoupa() {
		return "cadastroRoupa.xhtml?faces-redirect=true";
	}
	
	public String editar(Roupa obj) {
		RoupaDAO dao = new RoupaDAO();
		Roupa roupa = dao.obterUm(obj.getId());
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("roupaFlash", roupa);
		
		return "cadastroRoupa.xhtml?faces-redirect=true";
	}
	
	public List<Roupa> getListaRoupa() {
		if (listaRoupa == null) {
			DAO<Roupa> dao = new RoupaDAO();
			listaRoupa = dao.obterTodos();
			if (listaRoupa == null)
				listaRoupa= new ArrayList<Roupa>();
		}
		return listaRoupa;
	}

	public void excluir(Roupa obj) {
		RoupaDAO dao= new RoupaDAO();
		dao.excluir(obj.getId());
		this.listaRoupa = null;
		
		
	}
	
 	public void setListaProduto(List<Roupa> listaUsuario) {
		this.listaRoupa= listaUsuario;
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
