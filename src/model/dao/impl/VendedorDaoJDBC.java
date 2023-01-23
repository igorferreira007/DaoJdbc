package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizar(Vendedor vendedor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(int id) {
		// TODO Auto-generated method stub
		
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
				Departamento departamento = new Departamento();
				departamento.setId(rs.getInt("DepartmentId"));
				departamento.setName(rs.getString("DepName"));
				
				Vendedor vendedor = new Vendedor();
				vendedor.setId(rs.getInt("Id"));
				vendedor.setNome(rs.getString("Name"));
				vendedor.setEmail(rs.getString("Email"));
				vendedor.setSalarioBase(rs.getDouble("BaseSalary"));
				vendedor.setDataNascimento(rs.getDate("BirthDate"));
				vendedor.setDepartamento(departamento);
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

	@Override
	public List<Vendedor> procurarTodos() {
		// TODO Auto-generated method stub
		return null;
	}
}
