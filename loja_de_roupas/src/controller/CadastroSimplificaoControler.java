package controller;

import application.Util;
import dao.DAO;
import dao.UsuarioDAO;
import model.Perfil;
import model.Usuario;

public class CadastroSimplificaoControler extends UsuarioController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8260235999895655566L;
	@Override
	public void incluir() {
		if (!super.verificaSenha()) {
			Util.addInfoMessage("As senhas estão diferentes.");
			return;
		}
		
		DAO<Usuario> dao = new UsuarioDAO();
		
		String hash = Util.hash(getUsuario().getSenha() + getUsuario().getLogin());
		getUsuario().setSenha(hash);
		getUsuario().setPerfil(Perfil.CLIENTE);
		
		if (dao.inserir(getUsuario())) {
			Util.addInfoMessage("Inclusão realizada com sucesso.");
			limpar();
		} else
			Util.addErrorMessage("Problemas ao inserir no banco de dados.");
		
	}
	

}
