package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartamentoDao;
import model.entities.Departamento;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner ler = new Scanner(System.in);

		Departamento departamento = new Departamento();
		DepartamentoDao departamentoDao = DaoFactory.criarDepartamentoDao();
		
		List<Departamento> departamentos = new ArrayList<>();
		
		System.out.println("=== Teste 1: departamento procurarPorId ===");
		departamento = departamentoDao.procurarPorId(3);
		System.out.println(departamento);
		
		System.out.println("\n=== Teste 2: departamento procurarTodos ===");
		departamentos = departamentoDao.procurarTodos();
		departamentos.forEach(System.out::println);
		
		System.out.println("\n=== Teste 3: departamento inserir ===");
		departamento = new Departamento(null, "Vendas");
		departamentoDao.inserir(departamento);
		System.out.println("Departamento inserido! Novo ID = " + departamento.getId());
		
		System.out.println("\n=== Teste 4: departamento atualizar ===");
		departamento = departamentoDao.procurarPorId(7);
		departamento.setName(ler.nextLine());
		departamentoDao.atualizar(departamento);
		
		System.out.println("\n=== Teste 5: departamento excluir ===");
		departamentoDao.excluir(ler.nextInt());
		
		System.out.println("\n=== Teste 2: departamento procurarTodos ===");
		departamentos = departamentoDao.procurarTodos();
		departamentos.forEach(System.out::println);
		
		ler.close();
	}

}
