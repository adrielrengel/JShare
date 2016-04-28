package br.univel.adriel.teste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;

public class Teste {

	public static void main(String[] args) {

		Map<String, Cliente> listaClientes                   = new HashMap<String, Cliente>();	
		Map<Cliente, List<Arquivo>> listaArquivosCliente     = new HashMap<Cliente, List<Arquivo>>();
		Map<Cliente, List<Arquivo>> listaArquivosEncontrados = new HashMap<Cliente, List<Arquivo>>();

		List<Arquivo> arquivos1 = new ArrayList<Arquivo>();
		List<Arquivo> arquivos2 = new ArrayList<Arquivo>();
		List<Arquivo> arquivos3 = new ArrayList<Arquivo>();
		List<Arquivo> arquivos4 = new ArrayList<Arquivo>();
				
		

		Cliente c1 = new Cliente();
		Cliente c2 = new Cliente();
		Cliente c3 = new Cliente();
		Cliente c4 = new Cliente();

		Arquivo a1 = new Arquivo();
		Arquivo a2 = new Arquivo();
		Arquivo a3 = new Arquivo();
		Arquivo a4 = new Arquivo();

		c1.setNome("Alex");
		c1.setIp("192.168.0.1");
		c1.setPorta(1111);

		c2.setNome("Maria");
		c2.setIp("192.168.0.2");
		c2.setPorta(2222);

		c3.setNome("Edson");
		c3.setIp("192.168.0.3");
		c3.setPorta(3333);

		c4.setNome("José");
		c4.setIp("192.168.0.4");
		c4.setPorta(4444);

		/**
		 * Arquivos
		 */

		a1.setNome("txt");
		a1.setTamanho(256);

		a2.setNome("pdf");
		a2.setTamanho(128);

		a3.setNome("docx");
		a3.setTamanho(1024);

		a4.setNome("sem nome ");
		a4.setTamanho(2048);


		arquivos1.add(a1);
		arquivos1.add(a2);
		
		arquivos2.add(a1);
		arquivos2.add(a3);
		
		arquivos3.add(a1);
		arquivos3.add(a2);
		arquivos3.add(a3);
		arquivos3.add(a4);
		
		arquivos4.add(a2);
		arquivos4.add(a4);
		

		listaArquivosCliente.put(c1, arquivos1);
		listaArquivosCliente.put(c2, arquivos2);
		listaArquivosCliente.put(c3, arquivos3);
		listaArquivosCliente.put(c4, arquivos4);



		// Percorrendo a HashMap principal.
		for(Map.Entry<Cliente, List<Arquivo>> listaProcura: listaArquivosCliente.entrySet()) {
			
			
			System.out.println("Nome do Usuário: " + listaProcura.getKey().getNome());
			
			for(Arquivo arquivo: listaArquivosCliente.get(listaProcura.getKey())){
				
				System.out.println("\tNome do Arquivos  : " + arquivo.getNome());
				System.out.println("\tTamanho do Arquivo: " + arquivo.getTamanho());
				
				if (arquivo.getNome() == "txt") {
					
					List<Arquivo> listaArquivos = new ArrayList<Arquivo>();
					Cliente novoCliente = new Cliente();
					
					novoCliente.setNome(listaProcura.getKey().getNome());
					novoCliente.setIp(listaProcura.getKey().getIp());
					novoCliente.setPorta(listaProcura.getKey().getPorta());
					
					listaArquivos.add(arquivo);
					
					listaArquivosEncontrados.put(novoCliente, listaArquivos);
					
				}else if (arquivo.getNome() == "pdf") {
					
					List<Arquivo> listaArquivos = new ArrayList<Arquivo>();
					Cliente novoCliente = new Cliente();
					
					novoCliente.setNome(listaProcura.getKey().getNome());
					novoCliente.setIp(listaProcura.getKey().getIp());
					novoCliente.setPorta(listaProcura.getKey().getPorta());
					
					listaArquivos.add(arquivo);
					
					listaArquivosEncontrados.put(novoCliente, listaArquivos);
					
				}else if (arquivo.getNome() == "docx") {
					
					List<Arquivo> listaArquivos = new ArrayList<Arquivo>();
					Cliente novoCliente = new Cliente();
					
					novoCliente.setNome(listaProcura.getKey().getNome());
					novoCliente.setIp(listaProcura.getKey().getIp());
					novoCliente.setPorta(listaProcura.getKey().getPorta());
					
					listaArquivos.add(arquivo);
					
					listaArquivosEncontrados.put(novoCliente, listaArquivos);
					
				}else if (arquivo.getNome() == "sem nome") {
					
					List<Arquivo> listaArquivos = new ArrayList<Arquivo>();
					Cliente novoCliente = new Cliente();
					
					novoCliente.setNome(listaProcura.getKey().getNome());
					novoCliente.setIp(listaProcura.getKey().getIp());
					novoCliente.setPorta(listaProcura.getKey().getPorta());
					
					listaArquivos.add(arquivo);
					
					listaArquivosEncontrados.put(novoCliente, listaArquivos);
					
				}
				
				
				
				
				
			}
			
			System.out.println("\n");

		}
		
		System.out.println("====================================================================\n\n");
		System.out.println("Arquivos encontrados");
		System.out.println("\n\n====================================================================");
		
		
		for(Map.Entry<Cliente, List<Arquivo>> listaProcura: listaArquivosEncontrados.entrySet()) {
			
			
			System.out.println("Nome do Usuário: " + listaProcura.getKey().getNome());
			
			for(Arquivo arquivo: listaArquivosEncontrados.get(listaProcura.getKey())){
				
				System.out.println("\tNome do Arquivos  : " + arquivo.getNome());
				System.out.println("\tTamanho do Arquivo: " + arquivo.getTamanho());				
				
			}
			
			
			System.out.println("\n");

		}
		
	}

}
