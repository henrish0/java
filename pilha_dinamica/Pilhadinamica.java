//
package pilha_dinamica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

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

	// Metodo que le o arquivo e retorna um vetor de jogos
	public static Jogo[] ler(Jogo[] lista) {
		String array[];
		Jogo game;
		try (BufferedReader leitor = new BufferedReader(new FileReader("tmp/partidas.txt"))) {
			String linha;
			// Enquanto houver linhas no arquivo, as armazena em linha
			for (int i = 0; (linha = leitor.readLine()) != null; i++) {
				// Separa a linha em um array de strings
				array = linha.split("#");
				// Cria um novo jogo com os dados do arquivo
				game = new Jogo(Integer.parseInt(array[0]), array[1], Integer.parseInt(array[2]),
						Integer.parseInt(array[3]), array[4], Integer.parseInt(array[5]), Integer.parseInt(array[6]),
						array[7], array[8]);
				// Adiciona o jogo no vetor
				lista[i] = game;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Retorna o vetor
		return lista;
	}
}

class CelulaJogo {
	Jogo item;
	CelulaJogo anterior;

	CelulaJogo() {
		item = new Jogo();
		anterior = null;
	}
}

class Pilha {
	private CelulaJogo topo;

	Pilha() {
		topo = new CelulaJogo();
	}

	public void empilhar(Jogo novoJogo) {
		CelulaJogo novaCelula = new CelulaJogo();
		novaCelula.anterior = topo;
		novaCelula.item = novoJogo;
		topo = novaCelula;
	}

	public Jogo desempilhar() {
		if (!pilhaVazia()) {
			Jogo jogoTmp = topo.item;
			topo = topo.anterior;
			return jogoTmp;
		}
		return null;
	}

	public boolean pilhaVazia() {
		return null == topo.anterior;
	}

	public void mostrar() {
		if (!pilhaVazia()) {
			int i;
			CelulaJogo tmp = topo;
			for (i = 1; tmp.anterior != null; i++)
				tmp = tmp.anterior;
			Jogo[] vet = new Jogo[i];
			for (i = 0; tmp.anterior != null; i++) {
				vet[i] = tmp.item;
				tmp = tmp.anterior;
			}
			for (i = vet.length - 1; i >= 0; i--)
				System.out.println(vet[i]);
		} else
			System.out.print("Não foi possível mostrar o conteúdo da pilha: a pilha está vazia!");
	}
}

class Pilhadinamica {
	public static void main(String[] args) {
		// Nova pilha
		Pilha minhaPilha = new Pilha();
		// Strings temporarias
		String s = null, array[] = null, s0[];
		// Novo vetor de jogos
		Jogo[] listaTotal = new Jogo[901];
		// Le o arquivo e armazena no vetor
		listaTotal = Jogo.ler(listaTotal);
		//
		boolean tof = false;
		// Novo scanner
		try (Scanner scanner = new Scanner(System.in)) {
			// Enquanto houver linhas no input, as armazena em s
			while (!(s = scanner.nextLine()).equals("FIM")) {
				// Separa a string em um array de strings
				array = s.split("[/;]+");
				// Empilha o jogo buscado
				minhaPilha.empilhar(Jogo.busca(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
						Integer.parseInt(array[2]), array[3], listaTotal));
			}
			int total = Integer.parseInt(scanner.nextLine());
			for (int i = 0; i < total && (s = scanner.nextLine()) != null; i++)
				if (s.equals("D")) {
					System.out.print("(D) ");
					minhaPilha.desempilhar().imprimirJogo();
					tof = false;
				} else {
					s0 = s.split(" ", 2);
					array = s0[1].split("[/;]+");
					minhaPilha.empilhar(Jogo.busca(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
							Integer.parseInt(array[2]), array[3], listaTotal));
					tof = true;
				}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		minhaPilha.mostrar();
	}
}