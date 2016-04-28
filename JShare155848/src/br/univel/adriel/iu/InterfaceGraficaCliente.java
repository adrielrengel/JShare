package br.univel.adriel.iu;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.IServer;
import br.univel.adriel.util.DateFormat;
import br.univel.adriel.iu.InterfaceGraficaServidor;

public class InterfaceGraficaCliente extends JFrame implements IServer{
	
	private JPanel contentPane;
	private JTextField txtNomeUsuario;
	private JTextField txtBuscaArquivo;
	private JTable tabelaResultadoBusca;
	private JTextField txtIp;
	private JTextField txtPorta;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceGraficaCliente frame = new InterfaceGraficaCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfaceGraficaCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 557, 449);
		contentPane = new JPanel();
		contentPane.setToolTipText("Digite aqui sua busca...");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 526, 133);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 9, 39, 14);
		panel.add(lblNome);
		
		txtNomeUsuario = new JTextField();
		txtNomeUsuario.setBounds(59, 6, 136, 20);
		panel.add(txtNomeUsuario);
		txtNomeUsuario.setColumns(10);
		
		JButton btnDisponibilizarMeusArquivos = new JButton("Disponibilizar meus arquivos");
		btnDisponibilizarMeusArquivos.setBounds(205, 64, 206, 23);
		panel.add(btnDisponibilizarMeusArquivos);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.setBounds(421, 64, 95, 23);
		panel.add(btnConectar);
		
		txtBuscaArquivo = new JTextField();
		txtBuscaArquivo.setToolTipText("");
		txtBuscaArquivo.setColumns(10);
		txtBuscaArquivo.setBounds(10, 98, 309, 20);
		panel.add(txtBuscaArquivo);
		
		JButton btnBuscarArquivo = new JButton("Buscar Arquivo");
		btnBuscarArquivo.setBounds(322, 97, 194, 23);
		panel.add(btnBuscarArquivo);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(10, 40, 39, 14);
		panel.add(lblIp);
		
		txtIp = new JTextField();
		txtIp.setText("127.0.0.1");
		txtIp.setBounds(59, 37, 136, 20);
		panel.add(txtIp);
		txtIp.setColumns(10);
		
		JLabel lblPorta = new JLabel("Porta:");
		lblPorta.setBounds(205, 39, 46, 14);
		panel.add(lblPorta);
		
		txtPorta = new JTextField();
		txtPorta.setText("1818");
		txtPorta.setBounds(261, 37, 86, 20);
		panel.add(txtPorta);
		txtPorta.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 149, 526, 250);
		contentPane.add(scrollPane);
		
		tabelaResultadoBusca = new JTable();
		scrollPane.setViewportView(tabelaResultadoBusca);
		
		btnConectar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conectar();
			}
		});
	}
	
	
	/**
	 * ===================================================================================================
	 * 
	 * 										Métodos da interface	
	 * 
	 * ===================================================================================================
	 */

	@Override
	public void registrarCliente(Cliente c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publicarListaArquivos(Cliente c, List<Arquivo> lista) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Cliente, List<Arquivo>> procurarArquivo(String nome) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] baixarArquivo(Arquivo arq) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void desconectar(Cliente c) throws RemoteException {
		// TODO,. Auto-generated method stub
		
	}
	
	/**
	 * ===================================================================================================
	 * 
	 * 										Código complementar	
	 * 
	 * ===================================================================================================
	 */
	
	
	// Formato de data, utilizando a classe DateFormat.
	private SimpleDateFormat dateFormat = new DateFormat().formatoData();
	
	private IServer iServer;
	
	private Registry registry;
	
	private InterfaceGraficaServidor servidor;
	
	private String meuNome;
	
	
	/**
	 * ===================================================================================================
	 * 
	 * 										Métodos complementares	
	 * 
	 * ===================================================================================================
	 */
	
	protected void conectar() {
		
		meuNome = txtNomeUsuario.getText().trim();
		if (meuNome.length() == 0) {
			JOptionPane.showMessageDialog(this, "Informe seu nome!");
			return;
		}
		
		String host = txtIp.getText().trim();
		if (!host.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
			JOptionPane.showMessageDialog(this, "Endereço IP é inválido, por gentileza, informe outro.");
			return;
		}
		
		String strPorta = txtPorta.getText().trim();
		if (!strPorta.matches("[0-9]+") || strPorta.length() > 5){
			JOptionPane.showMessageDialog(this, "O número da porta deve ser um valor de no máximo 5 digitos!");
			return;
		}

		int intPorta = Integer.parseInt(strPorta);
		if (intPorta < 1024 || intPorta > 65535){
			JOptionPane.showMessageDialog(this, "O número da porta deve estar entre 1024 e 65535");
			return;
		}
		
		//Iniciando objetos para conexão.
		try {
			
			registry = LocateRegistry.getRegistry(host, 1818);
			
			iServer = (IServer) registry.lookup(IServer.NOME_SERVICO);
			
			Cliente cliente = new Cliente();
			
			cliente.setNome(txtNomeUsuario.getText());
			cliente.setIp(txtIp.getText());
			cliente.setPorta(Integer.parseInt(txtPorta.getText()));
			
			iServer.registrarCliente(cliente);
			
			JOptionPane.showMessageDialog(this, "conectado!");
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	

	
	
} 
