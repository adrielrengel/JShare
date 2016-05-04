package br.univel.adriel.iu;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.IServer;
import br.univel.adriel.util.DateFormat;
import br.univel.adriel.util.ListarIp;
import br.univel.adriel.iu.InterfaceGraficaServidor;

public class InterfaceGraficaCliente extends JFrame implements IServer{
	
	private JPanel contentPane;
	private JTextField txtPorta;
	private JButton btnConectar;
	private JButton btnDesconectar;
	private JComboBox cbnIp;
	private IServer server;
	private Registry registro;
	private JLabel lblNomeArquivo;
	private JButton btnPesquisar;
	private JTextField txtNomeArquivo;
	private JLabel lblUsurio;
	private JTextField txtUsuario;
	private String nome;
	private Cliente cliente;
	private File[] listaArquivo;
	private Arquivo listaArquivos;
	private List<Arquivo> lista = new ArrayList<>();
	private Map<String, Cliente> mapaConectados = new HashMap<>();
	private Map<Cliente, List<Arquivo>> mapaArquivos = new HashMap<>();
	private List<String> arquivoEncontrado = new ArrayList<>();
	private JScrollPane scrollPane;
	private JList relacaoArquivos;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceGraficaServidor frame = new InterfaceGraficaServidor();
					frame.setVisible(true);
					frame.configurar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
protected void configurar() {
		
		btnDesconectar.setEnabled(false);
		btnPesquisar.setEnabled(false);
		List<String> lista = getIpDisponivel();
		cbnIp.setModel(new DefaultComboBoxModel<String>(new Vector<String>(lista)));
		cbnIp.setSelectedIndex(0);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
			}
		});
		
		btnConectar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				conectar();
			}
		});
		
		btnDesconectar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				desconectar();
			}
		});
		
		btnPesquisar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pesquisar();
			}
		});
	}
	

protected void pesquisar() {
	try {
		buscarArquivo(txtNomeArquivo.getText());
	} catch (RemoteException e1) {
		e1.printStackTrace();
	}
}

protected void desconectar() {
	
	try {
		UnicastRemoteObject.unexportObject(this, true);
		UnicastRemoteObject.unexportObject(registro, true);

		cbnIp.setEnabled(true);
		txtPorta.setEnabled(true);
		txtUsuario.setEnabled(true);
		btnConectar.setEnabled(true);
		btnPesquisar.setEnabled(false);
		btnDesconectar.setEnabled(false);

	} catch (RemoteException e) {
		e.printStackTrace();
	}
}

protected void conectar() {
	
	String porta = txtPorta.getText().trim();
	
	if(!porta.matches("[0-9]+") || porta.length() >5){
		JOptionPane.showMessageDialog(this, "Porta Inválida");
		return;
	}
	
	int portaInteira = Integer.parseInt(porta);
	
	if(portaInteira < 1024 || portaInteira > 65535){
		JOptionPane.showMessageDialog(this, "Porta Inválida");
		return;
	}
	
	try{
		servidor = (InterfaceServidor) UnicastRemoteObject.exportObject(this, 0);
		registro = LocateRegistry.createRegistry(portaInteira);
		registro.rebind(InterfaceServidor.NOME_SERVICO, servidor);
		
		cbnIp.setEnabled(false);
		txtPorta.setEnabled(false);
		txtUsuario.setEnabled(false);
		btnConectar.setEnabled(false);
		btnPesquisar.setEnabled(true);
		btnDesconectar.setEnabled(true);
	}catch(RemoteException e){
		JOptionPane.showMessageDialog(this, "Erro ao Iniciar o Serviço");
		e.printStackTrace();
	}
	
	try {
		cliente = new Cliente();
		cliente.setIp(cbnIp.getSelectedItem().toString());
		cliente.setNome(txtUsuario.getText());
		cliente.setPorta(Integer.parseInt(txtPorta.getText()));
		RegistrarNovoCliente(cliente);
	} catch (RemoteException e) {
		e.printStackTrace();
	}
	
	try {
		String diretorioDownload = "C:/Users/Ronaldo Zuchi/Documents/Documentos Ronaldo/Meus Arquivos/Faculdade TADS/5º Ano/Download";
		File arquivoDownload = new File(diretorioDownload);
		listaArquivo = arquivoDownload.listFiles();
		for (int i=0; i<listaArquivo.length; i++){
			listaArquivos = new ArquivoDownload();
			File arquivo = listaArquivo[i];
			listaArquivos.setNomeArquivo(arquivo.getName());
			listaArquivos.setTamanhoArquivo(arquivo.length());
			lista.add(listaArquivos);
		}
		informarListaArquivo(cliente, lista);
	} catch (RemoteException e) {
		e.printStackTrace();
	}		
}

private List<String> getIpDisponivel() {
	
	List<String> lista = new ArrayList<String>();
	
	try{
		Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
		while(ifaces.hasMoreElements()){
			NetworkInterface ifc = ifaces.nextElement();
			if(ifc.isUp()){
				Enumeration<InetAddress> endereco = ifc.getInetAddresses();
				while(endereco.hasMoreElements()){
					InetAddress iddr = endereco.nextElement();
					String ip = iddr.getHostAddress();
					if (ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
						lista.add(ip);
					}
				}
			}
		}
	}catch(SocketException e){
		e.printStackTrace();
	}
	return lista;
}
	
	
	} 
