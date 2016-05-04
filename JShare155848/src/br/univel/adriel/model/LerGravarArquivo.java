package br.univel.adriel.model;



public class LerGravarArquivo {
	
	public LerGravarArquivo(File arquivo){
		
		byte[] dados = ler(arquivo);
		
		gravar(new File("Cópia de " + arquivo.getName()), dados);
		

}
	
	
	
	
