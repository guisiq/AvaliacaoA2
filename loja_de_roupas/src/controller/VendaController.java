package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import application.Session;
import application.Util;
import dao.DAO;
import dao.RoupaDAO;
import dao.UsuarioDAO;
import model.ItemVenda;
import model.Roupa;
import model.Usuario;

@Named
@ViewScoped
public class VendaController implements Serializable {

	private static final long serialVersionUID = 4860609187938109341L;
	private Integer tipoFiltro;
	private String filtro;
	private List<Roupa> listaRoupa;
	
	
	public void pesquisar() {
		RoupaDAO dao = new RoupaDAO();
		if (getTipoFiltro() == 1)
			setListaRoupa(dao.obterPeloNome(filtro));
		if (getTipoFiltro() == 2)
			setListaRoupa(dao.obterPelaDescricao(filtro));
		else if (getTipoFiltro() == null)
			setListaRoupa(dao.obterTodos());
	}
	
	public void addCarrinho(Roupa roupa) {
		// obtendo o carrinho da sessao
		@SuppressWarnings("unchecked")
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().get("carrinho");
		// caso nao exista o carrinho na sessao ... criar uma nova instancia local
		if (carrinho == null) 
			carrinho = new ArrayList<ItemVenda>();
		
		
		ItemVenda iv = new ItemVenda();
		iv.setRoupa(roupa);
		iv.setQuantidade(1);
		iv.setValorUnitario(roupa.getPreco());
	
		if (carrinho.contains(iv)) {
			int index = carrinho.indexOf(iv);
			int quantidade = carrinho.get(index).getQuantidade();
			carrinho.get(index).setQuantidade(++ quantidade );
			
		} else {
			carrinho.add(iv);
		}
		
		Session.getInstance().set("carrinho", carrinho);
		
		Util.addInfoMessage("Item adicionado no carrinho.");
		
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	
	public List<Roupa> getListaRoupa() {
		return listaRoupa;
	}

	public void setListaRoupa(List<Roupa> listaRoupa) {
		this.listaRoupa = listaRoupa;
	}

	public Integer getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(Integer tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}
	
	
}
