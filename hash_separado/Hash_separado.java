//
package hash_separado;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

class Jogo {
	public int dia, mes, ano, placarSelecao1, placarSelecao2;
	public String etapa, selecao1, selecao2, local;

	public void imprimir() {
		System.out.println("[COPA " + getAno() + "] [" + getEtapa() + "] [" + getDia() + "/"
				+ getMes() + "] [" + getSelecao1() + " (" + getPlacarSelecao1() + ") x ("
				+ getPlacarSelecao2() + ") " + getSelecao2() + "] [" + getLocal() + "]");
	}

	public int getValor() {
		int store = 0, p = 0;
		for (char i : getSelecao1().toCharArray())
			store += i * ++p;
		return getDia() + getMes() + getAno() + store;
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
		setAno(1900);
		setEtapa(null);
		setDia(1);
		setMes(1);
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
			if (day == jogo.getDia() && month == jogo.getMes() && year == jogo.getAno()
					&& (country.equals(jogo.getSelecao1()) || country.equals(jogo.getSelecao2())))
				return jogo;
		return null;
	}
}

class Celula {
	private Jogo item;
	private Celula proximo;

	Celula(Jogo itemJogo) {
		item = itemJogo;
		proximo = null;
	}

	Celula() {
		item = new Jogo();
		proximo = null;
	}

	public Jogo getItem() {
		return item;
	}

	public void setItem(Jogo itemJogo) {
		item = itemJogo;
	}

	public Celula getProximo() {
		return proximo;
	}

	public void setProximo(Celula proximo) {
		this.proximo = proximo;
	}
}

class ListaEncadeada {
	private Celula primeiro;
	private int tamanho;

	ListaEncadeada() {
		primeiro = new Celula();
		tamanho = 0;
	}

	public void inserir(Jogo item, int posicao) throws Exception {
		Celula tmp, nova, proxima;
		int i;
		if ((posicao >= 0) && (posicao <= tamanho)) {
			for (i = 0, tmp = primeiro; i < posicao; i++)
				tmp = tmp.getProximo();
			nova = new Celula(item);
			proxima = tmp.getProximo();
			tmp.setProximo(nova);
			nova.setProximo(proxima);
			tamanho++;
		} else
			throw new Exception("Não foi possível inserir o item na lista: posição inválida!");
	}

	public Jogo pesquisar(int dado) {
		Celula tmp;
		tmp = primeiro.getProximo();
		while (tmp != null) {
			if (tmp.getItem().getValor() == dado)
				return tmp.getItem();
			tmp = tmp.getProximo();
		}
		return null;
	}
}

class TabelaHash {
	private ListaEncadeada tabelaHash[];
	int comp, M, valor;

	public TabelaHash(int tamanho) {
		comp = 0;
		this.M = tamanho;
		tabelaHash = new ListaEncadeada[this.M];
		for (int i = 0; i < M; i++)
			tabelaHash[i] = new ListaEncadeada();
	}

	public int funcaoHash(int chave) {
		return chave % this.M;
	}

	public void inserir(Jogo novo) throws Exception {
		int posicao = funcaoHash(novo.getValor());
		if (tabelaHash[posicao].pesquisar(novo.getValor()) == null)
			tabelaHash[posicao].inserir(novo, 0);
	}

	public Jogo pesquisar(int chave) {
		int posicao = funcaoHash(chave);
		valor = posicao;
		comp++;
		return tabelaHash[posicao].pesquisar(chave);
	}
}

public class Hash_separado {
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		FileWriter myWriter = new FileWriter("780924_hashSeparado.txt");
		TabelaHash tabelaH = new TabelaHash(761);
		Jogo[] listaTotal = new Jogo[901];
		String array[] = new String[1];
		try (BufferedReader leitor = new BufferedReader(new FileReader("tmp/partidas.txt"))) {
			for (int i = 0; (array[0] = leitor.readLine()) != null && (array = array[0].split("#")) != null; i++)
				listaTotal[i] = new Jogo(Integer.parseInt(array[0]), array[1], Integer.parseInt(array[2]),
						Integer.parseInt(array[3]), array[4], Integer.parseInt(array[5]), Integer.parseInt(array[6]),
						array[7], array[8]);
		}
		try (Scanner scan = new Scanner(System.in)) {
			while (!(array[0] = scan.nextLine()).equals("FIM") && (array = array[0].split("[/;]+")) != null)
				tabelaH.inserir(Jogo.busca(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
						Integer.parseInt(array[2]), array[3], listaTotal));
			while (!(array[0] = scan.nextLine()).equals("FIM") && (array = array[0].split("[/;]+")) != null)
				if (tabelaH.pesquisar(Jogo.busca(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
						Integer.parseInt(array[2]), array[3], listaTotal).getValor()) != null)
					System.out.println(tabelaH.valor + " SIM");
				else
					System.out.println("NAO");
		}
		myWriter.write("780924\t" + (System.currentTimeMillis() - start) + "\t" + tabelaH.comp);
		myWriter.close();
	}
}
