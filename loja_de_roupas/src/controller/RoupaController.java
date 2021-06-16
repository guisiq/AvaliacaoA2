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
		private List<Roupa> listaRoupa = null;
		
		public RoupaController() {
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.keep("RoupaFlash");
			setRoupa((Roupa)flash.get("roupaFlash"));
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
		
		public void excluir() {
			excluir(getRoupa());
		}
		
		public void excluir(Roupa usu) {
			DAO<Roupa> dao = new RoupaDAO();
			if (dao.excluir(usu.getId())) {
				Util.addInfoMessage("Exclusão realizada com sucesso.");
				limpar();
			} else
				Util.addErrorMessage("Problemas ao excluir no banco de dados.");
		}
		
		public void limpar() {
			System.out.println("Limpar");
			setRoupa(null);
			setListaRoupa(null);
		}
		
		public void editar(Roupa usu) {
			DAO<Roupa> dao = new RoupaDAO();
			setRoupa(dao.obterUm(usu.getId()));
		}
		
		public List<Roupa> getListaRoupa() {
			if (listaRoupa == null) {
				DAO<Roupa> dao = new RoupaDAO();
				listaRoupa = dao.obterTodos();
				if (listaRoupa == null)
					listaRoupa = new ArrayList<Roupa>();
			}
			return listaRoupa;
		}

		public void setListaRoupa(List<Roupa> listaRoupa) {
			this.listaRoupa= listaRoupa;
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

