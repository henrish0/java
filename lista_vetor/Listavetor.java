//
package lista_vetor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Lista {
	private static final int TAMANHO_LISTA = 1000;

	private int tam_lista;
	public Jogo[] jogos;
	private int primeiro;
	private int ultimo;

	public Lista(int tam_lista) {
		this.primeiro = 0;
		this.ultimo = 0;

		this.tam_lista = tam_lista;

		jogos = new Jogo[this.tam_lista];
	}

	public Lista() {
		this(TAMANHO_LISTA);
	}

	private boolean isVazia() {
		return (this.primeiro == this.ultimo);
	}

	private boolean isCheia() {
		return ((this.ultimo + 1) == this.tam_lista);
	}

	public void inserirInicio(Jogo jogo) {
		this.inserir(jogo, this.primeiro);
	}

	public void inserir(Jogo jogo, int posicao) {
		if (!this.isCheia()) {
			for (int i = this.ultimo; i > posicao; i--)
				this.jogos[i] = this.jogos[i - 1];
			this.jogos[posicao] = jogo;
			this.ultimo++;
		}
	}

	public void inserirFim(Jogo jogo) {
		if (!this.isCheia()) {
			this.jogos[this.ultimo++] = jogo;
		}
	}

	public Jogo removerInicio() {
		return this.remover(this.primeiro);
	}

	public Jogo remover(int posicao) {
		if (!this.isVazia()) {
			Jogo jogo = this.jogos[posicao];
			this.ultimo--;
			for (int i = posicao; i < this.ultimo; i++)  
				this.jogos[i] = this.jogos[i + 1]; 
			jogos[ultimo] = null; 
			return jogo;
		} 
		return null;
	}

	public Jogo removerFim() {
		if (!this.isVazia()) {
			Jogo jogo = this.jogos[this.ultimo - 1];
			this.jogos[--this.ultimo] = null; 
			return jogo;
		}
		return null;
	}

	public void imprimir() {
		if (!this.isVazia()) {
			for (int i = this.primeiro; i < this.ultimo; i++) {
				System.out.print("[" + i + "]");
				this.jogos[i].imprimirJogo();
			}
		}
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
		String array[];
		Jogo game;
		try (BufferedReader leitor = new BufferedReader(new FileReader("/tmp/partidas.txt"))) {
			String linha;
			for (int i = 0; (linha = leitor.readLine()) != null; i++) {
				array = linha.split("#");
				game = new Jogo(Integer.parseInt(array[0]), array[1], Integer.parseInt(array[2]),
						Integer.parseInt(array[3]), array[4], Integer.parseInt(array[5]), Integer.parseInt(array[6]),
						array[7], array[8]);
				lista[i] = game;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lista;
	}
}

public class Listavetor {
	public static void main(String[] args) {
		final int tamanho = 1000;
		Lista minhaLista = new Lista(tamanho);
		String s = null, array[] = null, s0[];
		Jogo[] listaTotal = new Jogo[901];

		listaTotal = Jogo.ler(listaTotal);

		try (Scanner scanner = new Scanner(System.in)) {
			while (!(s = scanner.nextLine()).equals("FIM")) {
				array = s.split("[/;]+");
				minhaLista.inserirFim(Jogo.busca(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
						Integer.parseInt(array[2]), array[3], listaTotal));
			}
			int total = Integer.parseInt(scanner.nextLine());
			for (int i = 0; i < total && (s = scanner.nextLine()) != null; i++)
				if (s.charAt(0) == 'R') {
					if (s.charAt(1) == 'I') {
						System.out.print("(R) ");
						minhaLista.removerInicio().imprimirJogo();
					} else if (s.charAt(1) == 'F') {
						System.out.print("(R) ");
						minhaLista.removerFim().imprimirJogo();
					} else {
						s0 = s.split(" ", 2);
						minhaLista.remover(Integer.parseInt(s0[1])).imprimirJogo();
					}
				} else {
					if (s.charAt(1) == 'F') {
						s0 = s.split(" ", 2);
						array = s0[1].split("[/;]+");
						minhaLista.inserirFim(Jogo.busca(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
								Integer.parseInt(array[2]), array[3], listaTotal));
					} else if (s.charAt(1) == 'I') {
						s0 = s.split(" ", 2);
						array = s0[1].split("[/;]+");
						minhaLista.inserirInicio(Jogo.busca(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
								Integer.parseInt(array[2]), array[3], listaTotal));
					} else {
						s0 = s.split(" ", 3);
						array = s0[2].split("[/;]+");
						minhaLista.inserir(Jogo.busca(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
								Integer.parseInt(array[2]), array[3], listaTotal), Integer.parseInt(s0[1]));
					}
				}
		}
		minhaLista.imprimir();
	}
}
