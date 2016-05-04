package br.univel.adriel.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comum.pojos.Diretorio;


public class ListaDiretorioDownload {
	
	public static void main(String args[]){
		
		File diretorio = new File(".\\");
		
		List<Arquivo> listaArquivos = new ArrayList<>();
		List<Diretorio> listaDiretorio = new ArrayList<>();
		
		for(File arquivo : diretorio.listFiles()){
			if(arquivo.isFile()){
				Arquivo novo = new Arquivo();
				novo.setNome(arquivo.getName());
				novo.setTamanho(arquivo.length());
				listaArquivos.add(novo);
			} else {
				Diretorio novo = new Diretorio();
				novo.setNome(arquivo.getName());
				listaDiretorio.add(novo);
			}
		}
		
		System.out.println("Lista de Diretórios");
		for(Diretorio diretorioNovo : listaDiretorio){
			System.out.println("\t" + diretorioNovo.getNome());
		}
		
		System.out.println("Lista de Arquivos");
		for(Arquivo arquivoNovo : listaArquivos){
			System.out.println("\t" + arquivoNovo.getNome() + "\t" + arquivoNovo.getTamanho());
		}
		
	}

}
