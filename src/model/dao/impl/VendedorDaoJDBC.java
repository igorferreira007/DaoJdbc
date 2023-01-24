package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {
	
	private Connection conn;
	
	public VendedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Vendedor vendedor) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("insert into seller (Name, Email, BirthDate, BaseSalary, DepartmentId) "
										+ "values (?, ?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, vendedor.getNome());
			st.setString(2, vendedor.getEmail());
			st.setDate(3, new java.sql.Date(vendedor.getDataNascimento().getTime()));
			st.setDouble(4, vendedor.getSalarioBase());
			st.setInt(5, vendedor.getDepartamento().getId());
			
			int linhasAfetadas = st.executeUpdate();
			
			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					vendedor.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro inesperado! Nenhuma linha foi afetada");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void atualizar(Vendedor vendedor) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("update seller "
										+ "set Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
										+ "where Id = ?");
			
			st.setString(1, vendedor.getNome());
			st.setString(2, vendedor.getEmail());
			st.setDate(3, new java.sql.Date(vendedor.getDataNascimento().getTime()));
			st.setDouble(4, vendedor.getSalarioBase());
			st.setInt(5, vendedor.getDepartamento().getId());
			st.setInt(6, vendedor.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void excluir(int id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("delete from seller where Id = ?");
			
			st.setInt(1, id);
			
			int linhas = st.executeUpdate();
			
			if (linhas == 0) {
				throw new DbException("Exclusao falhou! O ID nao existe!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Vendedor procurarPorId(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("select seller.*, department.Name as DepName "
										+ "from seller inner join department "
										+ "on seller.DepartmentId = department.Id "
										+ "where seller.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Departamento departamento = instanciarDepartamento(rs);
				Vendedor vendedor = instanciarVendedor(rs, departamento);
				return vendedor;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Vendedor instanciarVendedor(ResultSet rs, Departamento departamento) throws SQLException {
		Vendedor vendedor =  new Vendedor();
		vendedor.setId(rs.getInt("Id"));
		vendedor.setNome(rs.getString("Name"));
		vendedor.setEmail(rs.getString("Email"));
		vendedor.setSalarioBase(rs.getDouble("BaseSalary"));
		vendedor.setDataNascimento(rs.getDate("BirthDate"));
		vendedor.setDepartamento(departamento);
		return vendedor;
	}

	private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
		Departamento departamento = new Departamento();
		departamento.setId(rs.getInt("DepartmentId"));
		departamento.setName(rs.getString("DepName"));
		return departamento;
	}

	@Override
	public List<Vendedor> procurarTodos() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("select seller.*, department.Name as DepName "
										+ "from seller inner join department "
										+ "on seller.DepartmentId = department.Id "
										+ "order by Name");
			
			rs = st.executeQuery();
			
			List<Vendedor> listaVendedores = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();
			
			while (rs.next()) {
				Departamento departamento2 = map.get(rs.getInt("DepartmentId"));
				
				if (departamento2 == null) {
					departamento2 = instanciarDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), departamento2);
				}
				
				Vendedor vendedor = instanciarVendedor(rs, departamento2);
				listaVendedores.add(vendedor);
			}
			return listaVendedores;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Vendedor> procurarPorDepartamento(Departamento departamento) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("select seller.*, department.Name as DepName "
										+ "from seller inner join department "
										+ "on seller.DepartmentId = department.Id "
										+ "where DepartmentId = ? "
										+ "order by Name");
			
			st.setInt(1, departamento.getId());
			rs = st.executeQuery();
			
			List<Vendedor> listaVendedores = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();
			
			while (rs.next()) {
				Departamento departamento2 = map.get(rs.getInt("DepartmentId"));
				
				if (departamento2 == null) {
					departamento2 = instanciarDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), departamento2);
				}
				
				Vendedor vendedor = instanciarVendedor(rs, departamento2);
				listaVendedores.add(vendedor);
			}
			return listaVendedores;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
