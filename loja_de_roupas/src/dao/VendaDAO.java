package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.ItemVenda;
import model.Roupa;
import model.Usuario;
import model.Venda;

public class VendaDAO implements DAO<Venda> {

	@Override
	public boolean inserir(Venda obj) {
		Connection conn = DAO.getConnection();
		
		try {
			// deixando o controle da transacao de forma manual
			conn.setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		boolean deuErro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO venda ");
		sql.append(" (data, id_usuario) ");
		sql.append("VALUES ");
		sql.append(" (?, ?) ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			stat.setInt(2, obj.getUsuario().getId());
			
			stat.execute();
				
			ResultSet rs = stat.getGeneratedKeys();
			if (rs.next()) {
				obj.setId(rs.getInt("id"));
			}
			
			// 
			salvarItens(obj, conn);

			// salvando por definitivo no banco
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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

	private void salvarItens(Venda venda, Connection conn) throws SQLException {
		// salvando os itens de venda
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO itemvenda ");
		sql.append(" (quantidade, valor_unitario, id_roupa, id_venda) ");
		sql.append("VALUES ");
		sql.append(" (?, ?, ?, ?) ");
		
		PreparedStatement stat = null;
		for (ItemVenda item : venda.getListaItemVenda()) {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, item.getQuantidade());
			stat.setDouble(2, item.getValorUnitario());
			stat.setInt(3, item.getRoupa().getId());
			stat.setInt(4, venda.getId());
			
			stat.execute();
		}
		
	}

	@Override
	public boolean alterar(Venda obj) {
		return false;
	}

	@Override
	public boolean excluir(Integer id) {
		return false;
	}
	
	@Override
	public List<Venda> obterTodos() {
		return null;
	}

	public List<Venda> obterTodos(Usuario usuario) {
		Connection conn = DAO.getConnection();
		
		List<Venda> listaVenda = new ArrayList<Venda>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id, ");
		sql.append("  v.data, ");
		sql.append("  v.id_usuario ");
		sql.append("FROM ");
		sql.append("  venda v ");
		sql.append("WHERE ");
		sql.append("  v.id_usuario = ? ");		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, usuario.getId());
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				venda.setData(rs.getDate("data").toLocalDate());
				venda.setUsuario(usuario);
				venda.setListaItemVenda(obterItensVenda(venda));
				
				listaVenda.add(venda);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaVenda = null;
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
		
		if (listaVenda == null || listaVenda.isEmpty())
			return null;
		
		return listaVenda;
	}
	

	private List<ItemVenda> obterItensVenda(Venda venda) {
		Connection conn = DAO.getConnection();
		
		List<ItemVenda> listaItem = new ArrayList<ItemVenda>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  i.id, ");
		sql.append("  i.quantidade, ");
		sql.append("  i.valor_unitario, ");
		sql.append("  i.id_roupa, ");
		sql.append("  r.nome, ");
		sql.append("  r.descricao, ");
		sql.append("  r.estoque, ");
		sql.append("  r.preco ");
		sql.append("FROM ");
		sql.append("  itemvenda i, ");
		sql.append("  roupa r ");
		sql.append("WHERE ");
		sql.append("  i.id_roupa = r.id ");
		sql.append("  AND i.id_venda = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, venda.getId());
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				ItemVenda item = new ItemVenda();
				item.setId(rs.getInt("id"));
				item.setQuantidade(rs.getInt("quantidade"));
				item.setValorUnitario(rs.getDouble("valor_unitario"));
				item.setRoupa(new Roupa());
				item.getRoupa().setId(rs.getInt("id_roupa"));
				item.getRoupa().setNome(rs.getString("nome"));
				item.getRoupa().setDescricao(rs.getString("descricao"));
				item.getRoupa().setEstoque(rs.getInt("estoque"));
				item.getRoupa().setPreco(rs.getDouble("preco"));
				
				listaItem.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaItem = null;
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
		
		if (listaItem == null || listaItem.isEmpty())
			return null;
		
		return listaItem;
	}

	@Override
	public Venda obterUm(Integer id) {
		return null;
	}

}