package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Program {

	public static void main(String[] args) {

		Departamento departamento = new Departamento(1, "Livros");
		
		Vendedor vendedor = new Vendedor(21, "Bob", "bob@gmail.com", new Date(), 3000.0, departamento);
		
		VendedorDao vendedorDao = DaoFactory.criarVendedorDao();
		
		System.out.println(departamento);
		System.out.println(vendedor);
	}

}
