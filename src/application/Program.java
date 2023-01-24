package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Program {

	public static void main(String[] args) {
		
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
	}

}
