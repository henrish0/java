package arvore_avl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Arvoreavl {
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		FileWriter myWriter = new FileWriter("780924_arvoreAVL.txt");
		AVL copa = new AVL();
		String[] array = new String[1];
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
			copa.inserir(Jogo.busca(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]),
					array[3], listaTotal));
		}
		while (!(array[0] = scan.nextLine()).equals("FIM")) {
			array = array[0].split("[/;]+");
			if (copa.pesquisar(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]),
					array[3]) == null)
				System.out.println("NAO");
			else
				System.out.println("SIM");
		}
		scan.close();
		myWriter.write("780924\t" + (System.currentTimeMillis() - start) + "\t" + copa.getComp());
		myWriter.close();
	}

	static String lerInput() throws Exception {
		StringBuilder saida = new StringBuilder();
		int bytes;
		while ((bytes = System.in.read()) != 10)
			saida.append((char) bytes);
		return saida.toString();
	}
}

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

class AVL {
	private No raiz;
	private int comp;

	public AVL() {
		raiz = null;
		comp = 0;
	}

	public int getComp() {
		return comp;
	}

	public Jogo pesquisar(int dia, int mes, int ano, String selecao) {
		return pesquisar(raiz, dia, mes, ano, selecao);
	}

	private Jogo pesquisar(No raizSubarvore, int dia, int mes, int ano, String selecao) {
		if (raizSubarvore == null)
			return null;
		comp++;
		System.out.print("[" + raizSubarvore.getItem().getDia() + "/" + raizSubarvore.getItem().getMes() + "/"
				+ raizSubarvore.getItem().getAno() + ";" + raizSubarvore.getItem().getSelecao1() + "] - ");
		if (ano == raizSubarvore.getItem().getAno() && mes == raizSubarvore.getItem().getMes()
				&& dia == raizSubarvore.getItem().getDia() && selecao.equals(raizSubarvore.getItem().getSelecao1()))
			return raizSubarvore.getItem();
		if (ano > raizSubarvore.getItem().getAno()
				|| ano == raizSubarvore.getItem().getAno() && mes > raizSubarvore.getItem().getMes()
				|| ano == raizSubarvore.getItem().getAno() && mes == raizSubarvore.getItem().getMes()
						&& dia > raizSubarvore.getItem().getDia()
				|| ano == raizSubarvore.getItem().getAno() && mes == raizSubarvore.getItem().getMes()
						&& dia == raizSubarvore.getItem().getDia()
						&& selecao.compareTo(raizSubarvore.getItem().getSelecao1()) > 0)
			return pesquisar(raizSubarvore.getDireita(), dia, mes, ano, selecao);
		return pesquisar(raizSubarvore.getEsquerda(), dia, mes, ano, selecao);
	}

	public void inserir(Jogo novo) throws Exception {
		raiz = inserir(raiz, novo);
	}

	private No inserir(No raizSubarvore, Jogo novo) throws Exception {
		if (raizSubarvore == null)
			raizSubarvore = new No(novo);
		else if (novo.getAno() == raizSubarvore.getItem().getAno() && novo.getMes() == raizSubarvore.getItem().getMes()
				&& novo.getDia() == raizSubarvore.getItem().getDia()
				&& novo.getSelecao1().equals(raizSubarvore.getItem().getSelecao1()))
			System.out.println("Não foi possível inserir o item na árvore: chave já inseriada anteriormente!");
		else if (novo.getAno() < raizSubarvore.getItem().getAno()
				|| novo.getAno() == raizSubarvore.getItem().getAno() && novo.getMes() < raizSubarvore.getItem().getMes()
				|| novo.getAno() == raizSubarvore.getItem().getAno()
						&& novo.getMes() == raizSubarvore.getItem().getMes()
						&& novo.getDia() < raizSubarvore.getItem().getDia()
				|| novo.getAno() == raizSubarvore.getItem().getAno()
						&& novo.getMes() == raizSubarvore.getItem().getMes()
						&& novo.getDia() == raizSubarvore.getItem().getDia()
						&& novo.getSelecao1().compareTo(raizSubarvore.getItem().getSelecao1()) < 0)
			raizSubarvore.setEsquerda(inserir(raizSubarvore.getEsquerda(), novo));
		else
			raizSubarvore.setDireita(inserir(raizSubarvore.getDireita(), novo));
		return balancear(raizSubarvore);
	}

	private No balancear(No raizSubarvore) {
		int fator = raizSubarvore.getFator(), fatorFilho;
		if (fator == 2) {
			fatorFilho = raizSubarvore.getEsquerda().getFator();
			if (fatorFilho == -1)
				raizSubarvore.setEsquerda(rotacionarEsquerda(raizSubarvore.getEsquerda()));
			raizSubarvore = rotacionarDireita(raizSubarvore);
		} else if (fator == -2) {
			fatorFilho = raizSubarvore.getDireita().getFator();
			if (fatorFilho == 1)
				raizSubarvore.setDireita(rotacionarDireita(raizSubarvore.getDireita()));
			raizSubarvore = rotacionarEsquerda(raizSubarvore);
		} else
			raizSubarvore.setAltura();
		return raizSubarvore;
	}

	private No rotacionarDireita(No p) {
		No u = p.getEsquerda();
		No filhoEsquerdaDireita = u.getDireita();
		u.setDireita(p);
		p.setEsquerda(filhoEsquerdaDireita);
		p.setAltura();
		u.setAltura();
		return u;
	}

	private No rotacionarEsquerda(No p) {
		No z = p.getDireita();
		No filhoDireitaEsquerda = z.getEsquerda();
		z.setEsquerda(p);
		p.setDireita(filhoDireitaEsquerda);
		p.setAltura();
		z.setAltura();
		return z;
	}
}

class No {
	private Jogo item;
	private int altura;
	private No esquerda, direita;

	public No(Jogo registro) {
		this.item = registro;
		this.altura = 0;
		this.esquerda = null;
		this.direita = null;
	}

	public Jogo getItem() {
		return this.item;
	}

	public void setItem(Jogo item) {
		this.item = item;
	}

	public No getEsquerda() {
		return this.esquerda;
	}

	public void setEsquerda(No esquerda) {
		this.esquerda = esquerda;
	}

	public No getDireita() {
		return this.direita;
	}

	public void setDireita(No direita) {
		this.direita = direita;
	}

	public int getAltura() {
		return this.altura;
	}

	public void setAltura() {
		int alturaEsq, alturaDir;
		if (this.direita == null)
			alturaDir = -1;
		else
			alturaDir = this.direita.getAltura();
		if (esquerda == null)
			alturaEsq = -1;
		else
			alturaEsq = this.esquerda.getAltura();
		if (alturaEsq > alturaDir)
			altura = alturaEsq + 1;
		else
			this.altura = alturaDir + 1;
	}

	public int getFator() {
		int alturaEsq, alturaDir;
		if (this.direita == null)
			alturaDir = -1;
		else
			alturaDir = this.direita.getAltura();
		if (this.esquerda == null)
			alturaEsq = -1;
		else
			alturaEsq = this.esquerda.getAltura();
		return (alturaEsq - alturaDir);
	}
}
