//
package fila_dinamica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Jogo {
	public int dia, mes, ano, placarSelecao1, placarSelecao2;
	public String etapa, selecao1, selecao2, local;

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
		try (BufferedReader leitor = new BufferedReader(new FileReader("tmp/partidas.txt"))) {
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

	public void imprimirJogo() {
		System.out.println("[COPA " + this.getAno() + "] [" + this.getEtapa() + "] [" + this.getDia() + "/"
				+ this.getMes() + "] [" + this.getSelecao1() + " (" + this.getPlacarSelecao1() + ") x ("
				+ this.getPlacarSelecao2() + ") " + this.getSelecao2() + "] [" + this.getLocal() + "]");
	}
}

class CelulaJogo {
	Jogo item;
	CelulaJogo proximoJogo;

	public CelulaJogo(Jogo novaCelula) {
		item = novaCelula;
		proximoJogo = null;
	}
}

class Fila {
	private CelulaJogo frente, tras;

	public Fila() {
		CelulaJogo celula = new CelulaJogo(new Jogo());
		frente = celula;
		tras = celula;
	}

	public Boolean filaVazia() {
		return frente == tras;
	}

	public void enfileirar(Jogo novoJogo) {
		tras.proximoJogo = new CelulaJogo(novoJogo);
		tras = tras.proximoJogo;
		tras.item = novoJogo;
	}

	public Jogo desenfileirar() {
		CelulaJogo jogoTmp;
		Jogo novaCelula;
		if (!(filaVazia())) {
			jogoTmp = frente.proximoJogo;
			frente.proximoJogo = jogoTmp.proximoJogo;
			novaCelula = jogoTmp.item;
			jogoTmp.proximoJogo = null;
			if (jogoTmp == tras)
				tras = frente;
			return (novaCelula);
		}
		return null;
	}

	public void mostrar() {
		CelulaJogo jogoTmp = frente.proximoJogo;
		int i = 0;
		if (!(filaVazia())) {
			for (i = 0; jogoTmp.proximoJogo != null; i++) {
				System.out.print("[" + i + "]");
				jogoTmp.item.imprimirJogo();
				jogoTmp = jogoTmp.proximoJogo;
			}
			System.out.print("[" + i + "]");
			jogoTmp.item.imprimirJogo();
		}
		System.out.println();
	}

	public double obterMediaGols() {
		CelulaJogo jogoTmp = frente.proximoJogo;
		double soma = 0;
		int i = 0;
		if (!(filaVazia())) {
			for (i = 0; jogoTmp.proximoJogo != null; i++) {
				soma = soma + jogoTmp.item.getPlacarSelecao1() + jogoTmp.item.getPlacarSelecao2();
				jogoTmp = jogoTmp.proximoJogo;
			}
			soma = soma + jogoTmp.item.getPlacarSelecao1() + jogoTmp.item.getPlacarSelecao2();
			return Math.round(soma / ++i);
		}
		return 0;
	}
}

public class Filadinamica {
	public static void main(String[] args) {
		Fila minhaFila = new Fila();
		String array[], s = null, s0[];
		Jogo listaTotal[] = new Jogo[901];
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
		}
		minhaFila.mostrar();
	}
}