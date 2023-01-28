package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDao;
import model.entities.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao {
	
	private Connection conn;
	
	public DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Departamento departamento) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("insert into department (Name) values (?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, departamento.getName());
			
			int linhas = st.executeUpdate();
			
			if (linhas > 0) {
				rs = st.getGeneratedKeys();
				rs.next();
				departamento.setId(rs.getInt(1));
			} else {
				throw new DbException("Erro inesperado! Nenhuma linha foi afetada");
			}
			
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void atualizar(Departamento departamento) {
		PreparedStatement st = null;
		
		if (departamento == null) {
			return;
		}
		
		try {
			st = conn.prepareStatement("update department set Name = ? where Id = ?");
			
			st.setString(1, departamento.getName());
			st.setInt(2, departamento.getId());
			
			int linhasAfetadas = st.executeUpdate();
			
			if (linhasAfetadas > 0) {
				System.out.println("Departamento atualizado com sucesso!");
			} else {
				throw new DbException("O departamento nao existe!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void excluir(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("delete from Department where Id = ?");
			
			st.setInt(1, id);
			
			int linhasAfetadas = st.executeUpdate();
			
			if (linhasAfetadas == 0) {
				throw new DbException("Exclusao falhou! O ID nao existe!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public Departamento procurarPorId(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select * from Department where Id = ?");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				Departamento departamento = new Departamento(rs.getInt(1), rs.getString(2));
				return departamento;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Departamento> procurarTodos() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		List<Departamento> departamentos = new ArrayList<>();
		
		try {
			st = conn.prepareStatement("select * from Department order by \"id\"");
			
			rs = st.executeQuery();
			
			while (rs.next()) {
				Departamento departamento = new Departamento(rs.getInt(1), rs.getString(2));
				departamentos.add(departamento);
			}
			return departamentos;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
