package br.univel.adriel.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;


public class ModeloTabela extends AbstractTableModel implements TableModel {

		private Object[][] matriz;
		private int linhas;

		public ModeloTabela(Map<Cliente, List<Arquivo>> mapa) {

			linhas = 0;
			for (Entry<Cliente, List<Arquivo>> e : mapa.entrySet()) {
				linhas+=e.getValue().size();
			}

			matriz = new Object[linhas][5];

			int linha = 0;

			for (Entry<Cliente, List<Arquivo>> e : mapa.entrySet()) {
				for (Arquivo arq : e.getValue()) {
					matriz[linha][0] = e.getKey().getNome();
					matriz[linha][1] = e.getKey().getIp();
					matriz[linha][2] = e.getKey().getPorta();
					matriz[linha][3] = arq.getNome();
					matriz[linha][4] = arq.getTamanho();
					linha++;
				}
			}
		}
		
		@Override
		public String getColumnName(int columnIndex) {

			switch (columnIndex) {
			case 0:
				return "USER";
			case 1:
				return "IP";
			case 2:
				return "Porta";
			case 3:
				return "Arquivo";
			case 4:
				return "Tamanho";
			}
			return null;;

		}

		@Override
		public int getColumnCount() {
			return 5;
		}

		@Override
		public int getRowCount() {
			return linhas;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return matriz[rowIndex][columnIndex];
		}
		
}
