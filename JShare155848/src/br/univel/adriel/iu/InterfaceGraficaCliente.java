package br.univel.adriel.iu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

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
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
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
import javax.swing.JTable;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.IServer;
import br.univel.adriel.model.ListarArquivos;
import br.univel.adriel.model.ModeloTabela;
import br.univel.adriel.util.DateFormat;
import br.univel.adriel.util.ListarIp;
import br.univel.adriel.iu.InterfaceGraficaServidor;

public class InterfaceGraficaCliente extends JFrame implements IServer {

	private JPanel contentPane;
	private JTextField txtNomeUsuario;
	private JTextField txtBuscaArquivo;
	private JTable tabelaResultadoBusca;
	private JTextField txtIpServidor;
	private JTextField txtMinhaPorta;

	private int flagEnvioArquivo = 0;
	private int flagInicioServico = 0;

	private ListarIp listaIP = new ListarIp();

	private ListarArquivos listarArquivos = new ListarArquivos();
	private List<Arquivo> listaArquivos;

	private Map<Cliente, List<Arquivo>> listaArquivosEncontrados;

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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 627, 449);
		contentPane = new JPanel();
		contentPane.setToolTipText("Digite aqui sua busca...");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 596, 191);
		contentPane.add(panel);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(32, 9, 39, 14);

		txtNomeUsuario = new JTextField();
		txtNomeUsuario.setText("HUGO");
		txtNomeUsuario.setBounds(81, 6, 312, 20);
		txtNomeUsuario.setColumns(10);

		btnDisponibilizarMeusArquivos = new JButton("Disponibilizar meus arquivos");
		btnDisponibilizarMeusArquivos.setBounds(0, 110, 278, 23);
		btnDisponibilizarMeusArquivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnConectar = new JButton("Conectar");
		btnConectar.setBounds(298, 110, 175, 23);
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flagInicioServico = 1;
			}
		});

		txtBuscaArquivo = new JTextField();
		txtBuscaArquivo.setBounds(0, 159, 336, 20);
		txtBuscaArquivo.setEnabled(false);
		txtBuscaArquivo.setToolTipText("");
		txtBuscaArquivo.setColumns(10);

		btnBuscarArquivo = new JButton("Buscar Arquivo");
		btnBuscarArquivo.setBounds(346, 158, 250, 23);
		btnBuscarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnBuscarArquivo.setEnabled(false);

		JLabel lblIp = new JLabel("IP Servidor:");
		lblIp.setBounds(32, 40, 89, 14);

		txtIpServidor = new JTextField();
		txtIpServidor.setBounds(119, 37, 124, 20);
		txtIpServidor.setText("127.0.0.1");
		txtIpServidor.setColumns(10);

		JLabel lblPorta = new JLabel("Porta Servidor:");
		lblPorta.setBounds(284, 40, 95, 14);

		txtMinhaPorta = new JTextField();
		txtMinhaPorta.setBounds(378, 78, 95, 20);
		txtMinhaPorta.setText("1415");
		txtMinhaPorta.setColumns(10);

		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.setBounds(483, 110, 113, 23);
		btnDesconectar.setEnabled(false);
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				flagInicioServico = 0;
			}
		});

		JLabel lblSeuIp = new JLabel("Seu IP:");
		lblSeuIp.setBounds(32, 81, 46, 14);

		cbxMeuIP = new JComboBox();
		cbxMeuIP.setBounds(119, 79, 124, 20);

		List<String> lista = listaIP.buscarIp();
		cbxMeuIP.setModel(new DefaultComboBoxModel<String>(new Vector<String>(lista)));
		cbxMeuIP.setSelectedIndex(0);

		JLabel lblPortaDisponvel = new JLabel("Porta Disponivel:");
		lblPortaDisponvel.setBounds(276, 81, 103, 14);

		txtPortaServidor = new JTextField();
		txtPortaServidor.setBounds(378, 37, 95, 20);
		txtPortaServidor.setText("1818");
		txtPortaServidor.setColumns(10);
		panel.setLayout(null);
		panel.add(lblNome);
		panel.add(txtNomeUsuario);
		panel.add(btnDisponibilizarMeusArquivos);
		panel.add(btnConectar);
		panel.add(txtBuscaArquivo);
		panel.add(btnBuscarArquivo);
		panel.add(lblIp);
		panel.add(txtIpServidor);
		panel.add(lblPorta);
		panel.add(txtMinhaPorta);
		panel.add(btnDesconectar);
		panel.add(lblSeuIp);
		panel.add(cbxMeuIP);
		panel.add(lblPortaDisponvel);
		panel.add(txtPortaServidor);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 207, 596, 192);
		contentPane.add(scrollPane);

		tabelaResultadoBusca = new JTable();
		tabelaResultadoBusca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mev) {

				String ipServidor = (String) tabelaResultadoBusca.getValueAt(tabelaResultadoBusca.getSelectedRow(), 1);

				int portaServidor = (int) tabelaResultadoBusca.getValueAt(tabelaResultadoBusca.getSelectedRow(), 2);

				String nomeArquivo = (String) tabelaResultadoBusca.getValueAt(tabelaResultadoBusca.getSelectedRow(), 3);

				try {
					registry = LocateRegistry.getRegistry(ipServidor, portaServidor);

					IServer servicoCliente = (IServer) registry.lookup(IServer.NOME_SERVICO);

					servicoCliente.registrarCliente(informacoesCliente());

					Arquivo arquivo = new Arquivo();
					arquivo.setNome((String) nomeArquivo);

					byte[] baixarArquivo = servicoCliente.baixarArquivo(arquivo);

					writeFile(new File("C:\\JShare\\Downloads\\" + arquivo.getNome()), baixarArquivo);

					JOptionPane.showMessageDialog(null, "Download concluido!");

				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}

			}

		});
		scrollPane.setViewportView(tabelaResultadoBusca);

		btnConectar.addActionListener(e -> {
			conectar();
		});

		btnDesconectar.addActionListener(e -> {
			desconectarUsuario();
		});

		btnDisponibilizarMeusArquivos.addActionListener(e -> acoes());

		btnBuscarArquivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				perquisarArquivos();
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// caixa de dialogo retorna um inteiro
				int resposta = JOptionPane.showConfirmDialog(null, "Deseja finalizar essa operação?", "Finalizar",
						JOptionPane.YES_NO_OPTION);

				// sim = 0, nao = 1
				if (resposta == 0 && flagInicioServico == 1) {
					desconectarUsuario();

					System.exit(0);

				} else {
					System.exit(0);
				}

			}
		});

	}

	/**
	 * =========================================================================
	 * ==========================
	 * 
	 * Métodos da interface
	 * 
	 * =========================================================================
	 * ==========================
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

		for (Arquivo arquivo : listaArquivos) {
			if (arquivo.getNome().contains(arq.getNome())) {
				byte[] readFile = lerArquivo(new File("C:\\JShare\\Uploads\\" + arq.getNome()));

				return readFile;
			}
		}

		return null;
	}

	@Override
	public void desconectar(Cliente c) throws RemoteException {
		// TODO,. Auto-generated method stub

	}

	// Formato de data, utilizando a classe DateFormat.
	private SimpleDateFormat dateFormat = new DateFormat().formatoData();

	private IServer iServer;

	private Registry registry;

	private String meuNome;
	private JTextField txtPortaServidor;
	private JComboBox cbxMeuIP;
	private JButton btnConectar;
	private JButton btnDisponibilizarMeusArquivos;
	private JButton btnDesconectar;
	private JButton btnBuscarArquivo;

	/**
	 * =========================================================================
	 * ==========================
	 * 
	 * Métodos complementares
	 * 
	 * =========================================================================
	 * ==========================
	 */

	protected void conectar() {

		meuNome = txtNomeUsuario.getText().trim();
		if (meuNome.length() == 0) {
			JOptionPane.showMessageDialog(this, "Informe seu nome!");
			return;
		}

		String host = txtIpServidor.getText().trim();
		if (!host.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
			JOptionPane.showMessageDialog(this, "Endereço IP é inválido, por gentileza, informe outro.");
			return;
		}

		String strPorta = txtMinhaPorta.getText().trim();
		String strPortaServidor = txtPortaServidor.getText().trim();
		if (!strPorta.matches("[0-9]+") || strPorta.length() > 5 || !strPortaServidor.matches("[0-9]+")
				|| strPortaServidor.length() > 5) {
			JOptionPane.showMessageDialog(this, "O número da porta deve ser um valor de no máximo 5 digitos!");
			return;
		}

		int intPorta = Integer.parseInt(strPorta);
		int intPortaServidor = Integer.parseInt(strPortaServidor);
		if ((intPorta < 1024 || intPorta > 65535) || (intPortaServidor < 1024 || intPortaServidor > 65535)) {
			JOptionPane.showMessageDialog(this, "O número da porta deve estar entre 1024 e 65535");
			return;
		}

		// Verifica se o usuário já disponibilizou seus arquivos.
		if (flagEnvioArquivo == 0) {

			JOptionPane.showMessageDialog(null, "Antes de se conectar, disponibilize seus arquivos!");
			return;
		} else {

			flagEnvioArquivo = 0;

			// Iniciando objetos para conexão.
			try {

				registry = LocateRegistry.getRegistry(host, intPortaServidor);

				iServer = (IServer) registry.lookup(IServer.NOME_SERVICO);

				enviarListaArquivos(informacoesCliente());

				lancarMeuServico();

				JOptionPane.showMessageDialog(this, "conectado!");

			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		configuraBotoes(false);
	}

	protected void desconectarUsuario() {

		flagInicioServico = 0;

		JOptionPane.showMessageDialog(this, "Você se desconectou do Servidor...");

		configuraBotoes(true);

		try {
			iServer.desconectar(informacoesCliente());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void lancarMeuServico() {

		int intPorta = Integer.parseInt(txtMinhaPorta.getText());

		try {

			IServer meuServer = (IServer) UnicastRemoteObject.exportObject(this, 0);
			registry = LocateRegistry.createRegistry(intPorta);

			registry.rebind(IServer.NOME_SERVICO, meuServer);

		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this,
					"Erro criando registro, verifique se a porta jÃ¡ nÃ£o estÃ¡ sendo usada.");
			e.printStackTrace();
		}

	}

	private void configuraBotoes(Boolean status) {

		btnConectar.setEnabled(status);
		btnDisponibilizarMeusArquivos.setEnabled(status);

		btnBuscarArquivo.setEnabled(!status);
		btnDesconectar.setEnabled(!status);

		txtNomeUsuario.setEnabled(status);
		txtIpServidor.setEnabled(status);
		txtPortaServidor.setEnabled(status);
		cbxMeuIP.setEnabled(status);
		txtMinhaPorta.setEnabled(status);

		txtBuscaArquivo.setEnabled(!status);
	}

	private void enviarListaArquivos(Cliente cliente) {

		try {
			iServer.publicarListaArquivos(cliente, listaArquivos);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// Método utilizado no botão Disponibilizar meus Arquivos.
	private void acoes() {

		listaArquivos = new ArrayList<Arquivo>(listarArquivos.listarArquivo());

		if (listaArquivos == null) {
			JOptionPane.showMessageDialog(this, "Você não possui arquivos! Adicione algum na suas pasta Uploads!");
		} else {

			JOptionPane.showMessageDialog(null, "Lista de arquivos foi publicada!");

			btnDisponibilizarMeusArquivos.setEnabled(false);

			flagEnvioArquivo = 1;
		}
	}

	private void perquisarArquivos() {

		String nomeArquivo = txtBuscaArquivo.getText();

		System.out.println(nomeArquivo);

		try {
			listaArquivosEncontrados = iServer.procurarArquivo(nomeArquivo);

			if (listaArquivosEncontrados == null) {
				JOptionPane.showMessageDialog(this, "Não existem arquivos!");
			} else {

				TableModel modelBusca = new br.univel.adriel.model.ModeloTabela(listaArquivosEncontrados);

				tabelaResultadoBusca.setModel(modelBusca);

				listaArquivosEncontrados = null;
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Cliente informacoesCliente() {

		Cliente cliente = new Cliente();

		cliente.setNome(txtNomeUsuario.getText());
		cliente.setIp(cbxMeuIP.getSelectedItem().toString());
		cliente.setPorta(Integer.parseInt(txtMinhaPorta.getText()));

		return cliente;
	}

	protected void writeFile(File arquivo, byte[] dados) {
		try {
			Files.write(Paths.get(arquivo.getPath()), dados, StandardOpenOption.CREATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected byte[] lerArquivo(File arq) {

		Path path = Paths.get(arq.getPath());

		try {
			byte[] dados = Files.readAllBytes(path);
			return dados;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}
