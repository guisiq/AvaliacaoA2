package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.*;
public class RoupaDAO implements DAO<Roupa> {

	@Override
	public boolean inserir(Roupa obj) {
		Connection conn = DAO.getConnection();
		boolean deuErro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO roupa ");
		sql.append(" (categoria, cor, descricao,estoque,nome,preco,tamanho ) ");
		sql.append("VALUES ");
		sql.append(" (?, ?, ?, ?, ?, ?, ?) ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setInt(1, obj.getCategoria().ordinal());
			stat.setString(2, (String)obj.getCor());
			stat.setString(3, obj.getDescricao());
			stat.setInt(4, obj.getEstoque());

			stat.setString(5, obj.getNome());

			stat.setDouble(6, obj.getPreco());

			stat.setInt(7, obj.getTamanlho());
			
			stat.execute();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			deuErro = true;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (deuErro)
			return false;
		
		return true;
	}

	@Override
	public boolean alterar(Roupa obj) {
		Connection conn = DAO.getConnection();
		boolean deuErro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE roupa SET ");
		//sql.append(" (categoria, cor, descricao,estoque,nome,preco,tamanho ) ");
		sql.append(" categoria = ?, ");
		sql.append(" cor = ?, ");
		sql.append(" descricao= ?, ");
		sql.append(" estoque = ? ");
		sql.append(" nome = ?, ");
		sql.append(" preco= ?, ");
		sql.append(" tamanho  = ? ");
		sql.append("WHERE ");
		sql.append(" id = ? ");
	
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getCategoria().ordinal());
			stat.setString(2, obj.getCor());
			stat.setString(3, obj.getDescricao());
			stat.setInt(4, obj.getEstoque());
			stat.setString(5, obj.getNome());
			stat.setDouble(6, obj.getPreco());
			stat.setInt(7, obj.getTamanlho());
			
			stat.setInt(8, obj.getId());
			
			stat.execute();
		} catch (Exception e) {
			e.printStackTrace();
			deuErro = true;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (deuErro)
			return false;
		return true;
	}

	@Override
	public boolean excluir(Integer id) {
		Connection conn = DAO.getConnection();
		boolean deuErro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM roupa WHERE id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			deuErro = true;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (deuErro)
			return false;
		
		return true;
	}

	@Override
	public List<Roupa> obterTodos() {
		Connection conn = DAO.getConnection();
		
		List<Roupa> listaRoupa = new ArrayList<Roupa>();
		//sql.append(" (categoria, cor, descricao,estoque,nome,preco,tamanho ) ");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");

		sql.append("  id, ");
		sql.append("  cor, ");
		sql.append("  descricao, ");
		sql.append("  estoque, ");
		sql.append("  preco, ");
		sql.append("  nome, ");
		sql.append("  preco, ");
		sql.append("  tamanho ,");
		sql.append("  categoria  ");
		sql.append("FROM ");
		sql.append("  roupa;");
		
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Roupa roupa = new Roupa();
				roupa.setId(rs.getInt("id"));
				roupa.setNome(rs.getString("nome"));
				roupa.setDescricao(rs.getString("descricao"));
				roupa.setEstoque(rs.getInt("estoque"));
				roupa.setPreco(rs.getDouble("preco"));
				roupa.setCor(rs.getString("cor"));
				roupa.setTamanlho(rs.getInt("tamanho"));
				roupa.setCategoria(Categoria.values()[rs.getInt("categoria")]);
				
				listaRoupa.add(roupa);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaRoupa = null;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (listaRoupa == null || listaRoupa.isEmpty())
			return null;
		
		return listaRoupa;
	}
	
/*
	public List<Roupa> obterPeloNome(String nome) {
		Connection conn = DAO.getConnection();
		
		List<Roupa> listaProduto = new ArrayList<Roupa>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.nome, ");
		sql.append("  p.descricao, ");
		sql.append("  p.estoque, ");
		sql.append("  p.preco, ");
		sql.append("  pe.id AS id_peso, ");
		sql.append("  pe.valor, ");
		sql.append("  pe.tipopeso ");
		sql.append("FROM ");
		sql.append("  produto p, ");
		sql.append("  peso pe ");
		sql.append("WHERE ");
		sql.append("  p.id = pe.id ");
		sql.append("  AND p.nome ILIKE ? ");
		sql.append("ORDER BY p.nome ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%"+nome+"%");
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setEstoque(rs.getDouble("estoque"));
				produto.setPreco(rs.getDouble("preco"));
				
				produto.setPeso(new Peso());
				produto.getPeso().setId(rs.getInt("id_peso"));
				produto.getPeso().setValor(rs.getDouble("valor"));
				produto.getPeso().setTipoPeso(TipoPeso.valueOf(rs.getInt("tipopeso")));
				
				listaProduto.add(produto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaProduto = null;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (listaProduto == null || listaProduto.isEmpty())
			return null;
		
		return listaProduto;
	}
	
	public List<Roupa> obterPelaDescricao(String descricao) {
		Connection conn = DAO.getConnection();
		
		List<Roupa> listaProduto = new ArrayList<Roupa>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.nome, ");
		sql.append("  p.descricao, ");
		sql.append("  p.estoque, ");
		sql.append("  p.preco, ");
		sql.append("  pe.id AS id_peso, ");
		sql.append("  pe.valor, ");
		sql.append("  pe.tipopeso ");
		sql.append("FROM ");
		sql.append("  produto p, ");
		sql.append("  peso pe ");
		sql.append("WHERE ");
		sql.append("  p.id = pe.id ");
		sql.append("  AND p.descricao ILIKE ? ");
		sql.append("ORDER BY p.nome ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%"+descricao+"%");
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setEstoque(rs.getDouble("estoque"));
				produto.setPreco(rs.getDouble("preco"));
				
				produto.setPeso(new Peso());
				produto.getPeso().setId(rs.getInt("id_peso"));
				produto.getPeso().setValor(rs.getDouble("valor"));
				produto.getPeso().setTipoPeso(TipoPeso.valueOf(rs.getInt("tipopeso")));
				
				listaProduto.add(produto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaProduto = null;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (listaProduto == null || listaProduto.isEmpty())
			return null;
		
		return listaProduto;
	}*/


	@Override
	public Roupa obterUm(Integer id) {
		Connection conn = DAO.getConnection();
		
		Roupa roupa = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  r.categoria, ");
		sql.append("  r.cor, ");
		sql.append("  r.descricao, ");
		sql.append("  r.estoque, ");
		sql.append("  r.preco, ");
		sql.append("  r.nome, ");
		sql.append("  r.preco, ");
		sql.append("  r.tamanho");
		sql.append("FROM ");
		sql.append("  roupa r, ");
		sql.append("WHERE ");
		sql.append("  u.id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				roupa = new Roupa();
				
				roupa.setId(rs.getInt("id"));
				roupa.setNome(rs.getString("nome"));
				roupa.setDescricao(rs.getString("descricao"));
				roupa.setEstoque(rs.getInt("estoque"));
				roupa.setPreco(rs.getDouble("preco"));
				roupa.setCor(rs.getString("cor"));
				roupa.setTamanlho(rs.getInt("Tamanlho"));
				roupa.setCategoria(Categoria.values()[rs.getInt("Categoria")]);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			roupa = null;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return roupa;
	}

}
