package br.univel.adriel.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comum.pojos.Diretorio;

public class ListarArquivos {

	public List<Arquivo> listarArquivo() {

		File dirStart = new File("C:\\JShare\\Uploads");

		List<Arquivo> listaArquivos = new ArrayList<>();

		for (File file : dirStart.listFiles()) {
			
			if (file.isFile()) {
				Arquivo arq = new Arquivo();
				arq.setNome(file.getName());
				arq.setTamanho(file.length());
				listaArquivos.add(arq);
			} 
		}
		
		return listaArquivos;

	}
}
