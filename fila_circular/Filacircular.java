//
package fila_circular;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Fila {
	private Jogo[] fila;
	private int frente, tras, tamanho;

	public Fila(int tamanho) {
		fila = new Jogo[tamanho];
		frente = 0;
		tras = 0;
		this.tamanho = tamanho;
	}

	public boolean filaVazia() {
		if (frente == tras)
			return true;
		else
			return false;
	}

	public boolean filaCheia() {
		if (((tras + 1) % tamanho) == (frente % tamanho))
			return true;
		else
			return false;
	}

	public void enfileirar(Jogo novo) throws Exception {
		Jogo clone = novo.clone();
		int posicao;
		if (!filaCheia()) {
			posicao = tras % tamanho;
			fila[posicao] = novo;
			tras++;
		} else {
			desenfileirar();
			enfileirar(clone);
		}
	}

	public Jogo desenfileirar() throws Exception {
		int posicao;
		// Se a fila nao estiver vazia
		if (!filaVazia()) {
			posicao = frente % tamanho;
			frente++;
			// Retorna o jogo desenfileirado
			return fila[posicao];
		} else
			throw new Exception("Não foi possível desenfileirar nenhum elemento: a fila está vazia!");
	}

	public void mostrar() throws Exception {
		int posicao = tras % tamanho;
		// Se a fila nao estiver vazia
		if (!filaVazia()) {
			// Mostra os elementos da fila
			for (int i = frente; i < tras; i++) {
				posicao = i % tamanho;
				System.out.print("[" + (i - frente) + "]");
				fila[posicao].imprimirJogo();
			}
		} else
			throw new Exception("Não foi possível mostrar o conteúdo da fila: a fila está vazia!");
	}

	// Le todos os jogos do vetor e faz a media dos placares da selecao 1
	public double obterMediaGols() {
		double sum = 0;
		int i = 0, j = 0, p = 0;
		// Se a fila nao estiver vazia
		if (!filaVazia())
			// Percorre a fila
			for (i = frente, j = 0; i < tras; i++, j++) {
				// Percorre o vetor de partidas
				p = i % tamanho;
				// Soma os gols da selecao 1
				sum = sum + fila[p].getPlacarSelecao1() + fila[p].getPlacarSelecao2();
			}
		else
			// Se a fila estiver vazia retorna -1
			return -1;
		// Retorna a media dos gols
		return Math.round(sum / j);
	}
}

class Jogo {
	public int dia, mes, ano, placarSelecao1, placarSelecao2;
	public String etapa, selecao1, selecao2, local;

	public void imprimirJogo() {
		System.out.println("[COPA " + this.getAno() + "] [" + this.getEtapa() + "] [" + this.getDia() + "/"
				+ this.getMes() + "] [" + this.getSelecao1() + " (" + this.getPlacarSelecao1() + ") x ("
				+ this.getPlacarSelecao2() + ") " + this.getSelecao2() + "] [" + this.getLocal() + "]");
	}

	// Setters e getters
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

	// Construtor vazio
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

	// Construtor do tipo jogo
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

	// Metodo clone, retorna a instancia atual de jogo
	public Jogo clone() {
		return new Jogo(this.getAno(), this.getEtapa(), this.getDia(), this.getMes(), this.getSelecao1(),
				this.getPlacarSelecao1(), this.getPlacarSelecao2(), this.getSelecao2(), this.getLocal());
	}

	public static Jogo busca(int day, int month, int year, String country, Jogo[] lista) {
		// Percorre o vetor totalmente
		for (Jogo jogo : lista)
			// Caso o input seja igual ao do vetor
			if (day == jogo.getDia() && month == jogo.getMes() && year == jogo.getAno()
					&& (country.equals(jogo.getSelecao1()) || country.equals(jogo.getSelecao2())))
				// Retorno
				return jogo;
		return null;
	}

	public static Jogo[] ler(Jogo[] lista) {
		// String que recebe cada dado de um jogo
		String array[];
		Jogo game;
		// Leitura do arquivo
		try (BufferedReader leitor = new BufferedReader(new FileReader("tmp/partidas.txt"))) {
			String linha;
			// Enquanto houver linhas no arquivo, leia e armazene
			for (int i = 0; (linha = leitor.readLine()) != null; i++) {
				// Separa os dados de cada jogo
				array = linha.split("#");
				// Cria um novo jogo
				game = new Jogo(Integer.parseInt(array[0]), array[1], Integer.parseInt(array[2]),
						Integer.parseInt(array[3]), array[4], Integer.parseInt(array[5]), Integer.parseInt(array[6]),
						array[7], array[8]);
				// Adiciona o jogo no vetor
				lista[i] = game;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Retorna o vetor
		return lista;
	}
}

class Filacircular {
	public static void main(String[] args) throws Exception { 
		Fila minhaFila = new Fila(101);
		String s = null, array[] = null, s0[];
		Jogo[] listaTotal = new Jogo[901];
		listaTotal = Jogo.ler(listaTotal);
		try (Scanner scanner = new Scanner(System.in)) {
			while (!(s = scanner.nextLine()).equals("FIM")) {
				array = s.split("[/;]+");
				minhaFila.enfileirar(Jogo.busca(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
						Integer.parseInt(array[2]), array[3], listaTotal));
				System.out.println((int) minhaFila.obterMediaGols());
			}
			int total = Integer.parseInt(scanner.nextLine());
			for (int i = 0; i < total && (s = scanner.nextLine()) != null; i++)
				if (s.equals("D")) {
					System.out.print("(D) ");
					minhaFila.desenfileirar().imprimirJogo();
				} else {
					s0 = s.split(" ", 2);
					array = s0[1].split("[/;]+");
					minhaFila.enfileirar(Jogo.busca(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
							Integer.parseInt(array[2]), array[3], listaTotal));
					System.out.println((int) Math.round(minhaFila.obterMediaGols()));
				}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		minhaFila.mostrar();
	}
}