package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Program {

	public static void main(String[] args) {
		
		Scanner ler = new Scanner(System.in);
		
		VendedorDao vendedorDao = DaoFactory.criarVendedorDao();
		
		System.out.println("=== Teste 1: vendedor procurarPorId ===");
		Vendedor vendedor = vendedorDao.procurarPorId(3);
		System.out.println(vendedor);
		
		System.out.println("\n=== Teste 2: vendedor procurarPorDepartamento ===");
		Departamento departamento = new Departamento(2, null);
		List<Vendedor> listaVendedores = vendedorDao.procurarPorDepartamento(departamento);
		for (Vendedor vendedor2 : listaVendedores) {
			System.out.println(vendedor2);
		}
		
		System.out.println("\n=== Teste 3: vendedor procurarTodos ===");
		listaVendedores = vendedorDao.procurarTodos();
		for (Vendedor vendedor2 : listaVendedores) {
			System.out.println(vendedor2);
		}
		
		System.out.println("\n=== Teste 4: vendedor inserir ===");
		vendedor = new Vendedor(null, "Greg", "greg@gmail.com", new Date(), 4000.0, departamento);
		vendedorDao.inserir(vendedor);
		System.out.println("Inserido com sucesso! Novo ID = " + vendedor.getId());
		
		System.out.println("\n=== Teste 5: vendedor atualizar ===");
		vendedor = vendedorDao.procurarPorId(1);
		vendedor.setNome("Martha Waine");
		vendedorDao.atualizar(vendedor);
		System.out.println("Atualizacao concluida!");
		
		System.out.println("\n=== Teste 6: vendedor excluir ===");
		System.out.print("Entre com um ID para exclusao: ");
		int id = ler.nextInt();
		vendedorDao.excluir(id);
		System.out.println("Exclusao concluida!");
		
		ler.close();
	}

}
