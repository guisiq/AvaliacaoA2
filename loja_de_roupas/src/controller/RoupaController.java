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
	public class RoupaController  implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1304667158255601678L;
		private Roupa roupa = null;
		
		public RoupaController() {
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.keep("Roupaedit");
			setRoupa((Roupa)flash.get("roupaedit"));
		}
		
		public Categoria[] getCategoria() {
			return Categoria.values();
		}
		
		public void incluir() {
			DAO<Roupa> dao = new RoupaDAO();
			if (dao.inserir(getRoupa())) {
				Util.addInfoMessage("Inclusão realizada com sucesso.");
				limpar();
			} else
				Util.addErrorMessage("Problemas ao inserir no banco de dados.");
		}
		
		public void alterar() {
			DAO<Roupa> dao = new RoupaDAO();
			if (dao.alterar(getRoupa())) {
				Util.addInfoMessage("Alteração realizada com sucesso.");
				limpar();
			} else
				Util.addErrorMessage("Problemas ao alterar no banco de dados.");
		}
		
		
		public void limpar() {
			System.out.println("Limpar");
			setRoupa(null);
		
		}
		
		
		public Roupa getRoupa() {
			if (roupa == null) {
				roupa = new Roupa();
			}
			return roupa;
		}

		public void setRoupa(Roupa Roupa) {
			this.roupa = Roupa;
		}


	}

