package controller;
import model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import application.Util;
import dao.DAO;
import dao.RoupaDAO;


@Named
@ViewScoped
public class CadastroRoupa  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3240461459631524348L;
	private Roupa roupa;
	public List<Categoria> getCategorias() {
		
		 
		return Arrays.asList(Categoria.values());
		
	}
	public Roupa getRoupa()
	{
		if(roupa == null)
			return roupa = new Roupa();
		return roupa;
	}
	public void setRoupa(Roupa roupa) {
		this.roupa = roupa;
	}					
	public void incluir() {
		
		DAO<Roupa> dao = new RoupaDAO();
		
		if (dao.inserir(getRoupa())) {
			Util.addInfoMessage("Inclusão realizada com sucesso.");
			limpar();
		} else
			Util.addErrorMessage("Problemas ao inserir no banco de dados.");
		
	}
	public void limpar() {
		System.out.println("Limpar");
		setRoupa(null);
	}
}
