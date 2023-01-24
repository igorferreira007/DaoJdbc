package model.dao;

import java.util.List;

import model.entities.Departamento;
import model.entities.Vendedor;

public interface VendedorDao {

	void inserir(Vendedor vendedor);
	void atualizar(Vendedor vendedor);
	void excluir(int id);
	Vendedor procurarPorId(int id);
	List<Vendedor> procurarTodos();
	List<Vendedor> procurarPorDepartamento(Departamento departamento);
}
