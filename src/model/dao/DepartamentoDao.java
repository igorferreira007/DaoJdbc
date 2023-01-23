package model.dao;

import java.util.List;

import model.entities.Departamento;

public interface DepartamentoDao {

	void inserir(DepartamentoDao departamento);
	void atualizar(DepartamentoDao departamento);
	void excluir(int id);
	Departamento procurarPorId(int id);
	List<Departamento> procurarTodos();
}
