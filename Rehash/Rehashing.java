//
package Rehash;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Jogo {
	private int dia, mes, ano, placarSelecao1, placarSelecao2;
	private String etapa, selecao1, selecao2, local;

	public void imprimir() {
		System.out.println("[COPA " + ano + "] [" + etapa + "] [" + dia + "/" + mes + "] [" + selecao1 + " ("
				+ placarSelecao1 + ") x (" + placarSelecao2 + ") " + selecao2 + "] [" + local + "]");
	}

	public int getValor(int store, int p) {
		for (char i : selecao1.toCharArray())
			store += i * ++p;
		return dia + mes + ano + store;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public String getSelecao1() {
		return selecao1;
	}

	public void setSelecao1(String selecao1) {
		this.selecao1 = selecao1;
	}

	public String getSelecao2() {
		return selecao2;
	}

	public void setSelecao2(String selecao2) {
		this.selecao2 = selecao2;
	}

	public int getPlacarSelecao1() {
		return placarSelecao1;
	}

	public void setPlacarSelecao1(int placarSelecao1) {
		this.placarSelecao1 = placarSelecao1;
	}

	public int getPlacarSelecao2() {
		return placarSelecao2;
	}

	public void setPlacarSelecao2(int placarSelecao2) {
		this.placarSelecao2 = placarSelecao2;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Jogo() {
		setAno(0);
		setEtapa(null);
		setDia(0);
		setMes(0);
		setSelecao1(null);
		setPlacarSelecao1(0);
		setPlacarSelecao2(0);
		setSelecao2(null);
		setLocal(null);
	}

	public Jogo(int ano, String etapa, int dia, int mes, String selecao1, int placarSelecao1, int placarSelecao2,
			String selecao2, String local) {
		setAno(ano);
		setEtapa(etapa);
		setDia(dia);
		setMes(mes);
		setSelecao1(selecao1);
		setPlacarSelecao1(placarSelecao1);
		setPlacarSelecao2(placarSelecao2);
		setSelecao2(selecao2);
		setLocal(local);
	}

	public static Jogo busca(int day, int month, int year, String country, Jogo[] lista) {
		for (Jogo jogo : lista)
			if (day == jogo.dia && month == jogo.mes && year == jogo.ano && country.equals(jogo.selecao1))
				return jogo;
		return null;
	}
}

class TabelaHash {
	private int MAX, MAXtentativas, valor, comp;
	private Jogo tabelaHash[];

	public TabelaHash(int t0, int t1) {
		comp = 0;
		MAX = t0;
		MAXtentativas = t1;
		tabelaHash = new Jogo[MAX];
	}

	public int getvalor() {
		return valor;
	}

	public int getcomp() {
		return comp;
	}

	private int funcaoHash(int chave, int i) {
		return (chave % MAX + ((i * chave) % MAXtentativas)) % MAX;
	}

	public void inserir(Jogo jogoNovo) {
		int chaveNova = jogoNovo.getValor(0, 0), tentativa = 0, posicao;
		while (tentativa < MAX) {
			posicao = funcaoHash(chaveNova, tentativa++);
			if (tabelaHash[posicao] == null && (tabelaHash[posicao] = jogoNovo) != null)
				break;
			else if (tabelaHash[posicao].getValor(0, 0) == chaveNova)
				break;
		}
	}

	public Jogo pesquisar(Jogo jogoChave) {
		int chaveNova = jogoChave.getValor(0, 0), tentativa = 0;
		while (tentativa < MAX) {
			valor = funcaoHash(chaveNova, tentativa++);
			if (tabelaHash[valor] == null)
				return null;
			else if (tabelaHash[valor].getValor(0, 0) == chaveNova && jogoChave.getAno() == tabelaHash[valor].getAno()
					&& jogoChave.getMes() == tabelaHash[valor].getMes()
					&& jogoChave.getDia() == tabelaHash[valor].getDia())
				return tabelaHash[valor];
		}
		return null;
	}
}

class Rehashing {
	public static void main(String[] args) throws IOException {
		final long start = System.currentTimeMillis();
		final FileWriter myWriter = new FileWriter("780924_hashRehashing.txt");
		TabelaHash tabelaH = new TabelaHash(953, 311);
		String array[] = new String[1];
		Jogo[] listaTotal = new Jogo[900];
		BufferedReader leitor = new BufferedReader(new FileReader("tmp/partidas.txt"));
		for (int i = 0; (array[0] = leitor.readLine()) != null; i++) {
			array = array[0].split("#");
			listaTotal[i] = new Jogo(Integer.parseInt(array[0]), array[1], Integer.parseInt(array[2]),
					Integer.parseInt(array[3]), array[4], Integer.parseInt(array[5]), Integer.parseInt(array[6]),
					array[7], array[8]);
		}
		leitor.close();
		Scanner scan = new Scanner(System.in);
		while (!(array[0] = scan.nextLine()).equals("FIM")) {
			array = array[0].split("[/;]+");
			tabelaH.inserir(Jogo.busca(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
					Integer.parseInt(array[2]), array[3], listaTotal));
		}
		while (!(array[0] = scan.nextLine()).equals("FIM")) {
			array = array[0].split("[/;]+");
			if (tabelaH.pesquisar(Jogo.busca(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
					Integer.parseInt(array[2]), array[3], listaTotal)) != null)
				System.out.println(tabelaH.getvalor() + " SIM");
			else
				System.out.println("NAO");
		}
		scan.close();
		myWriter.write("780924\t" + (System.currentTimeMillis() - start) + "\t" + tabelaH.getcomp());
		myWriter.close();
	}
}