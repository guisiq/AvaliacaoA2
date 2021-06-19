package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DAO;
import model.Tecido;

public class TecidoDAO implements DAO<Tecido> {

	@Override
	public boolean inserir(Tecido obj) {
		Connection conn = DAO.getConnection();
		boolean deuErro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tecido ");
		sql.append(" (id, tipotecido) ");
		sql.append("VALUES ");
		sql.append(" (?, ?) ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());
			stat.setString(2, obj.getTipoTecido());
			
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
	public boolean alterar(Tecido obj) {
		Connection conn = DAO.getConnection();
		boolean deuErro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tecido SET ");
		sql.append(" tipotecido = ? ");
		sql.append("WHERE ");
		sql.append(" id = ? ");
	
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());
			stat.setString(2, obj.getTipoTecido());
			
			stat.setInt(3, obj.getId());
			
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
		sql.append("DELETE FROM tecido WHERE id = ? ");
		
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
	public List<Tecido> obterTodos() {
		Connection conn = DAO.getConnection();
		
		List<Tecido> listaTecido = new ArrayList<Tecido>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  t.id, ");
		sql.append("  t.tipotecido ");
		sql.append("FROM ");
		sql.append("  tecido t ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Tecido tecido = new Tecido();
				tecido.setId(rs.getInt("id"));
				tecido.setTipoTecido(rs.getString("tipopedo"));
				listaTecido.add(tecido);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaTecido = null;
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
		
		if (listaTecido == null || listaTecido.isEmpty())
			return null;
		
		return listaTecido;
	}

	@Override
	public Tecido obterUm(Integer id) {
		Connection conn = DAO.getConnection();
		
		Tecido tecido = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  t.id, ");
		sql.append("  t.tipotecido ");
		sql.append("FROM ");
		sql.append("  tecido t ");
		sql.append("WHERE ");
		sql.append("  t.id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				tecido = new Tecido();
				tecido.setId(rs.getInt("id"));
				tecido.setTipoTecido(rs.getString("tipotecido"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			tecido = null;
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
		
		return tecido;
	}

}


