//
package arvore_binaria;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Jogo {
	public int dia, mes, ano, placarSelecao1, placarSelecao2;
	public String etapa, selecao1, selecao2, local;

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

	public Jogo clone() {
		return new Jogo(this.getAno(), this.getEtapa(), this.getDia(), this.getMes(), this.getSelecao1(),
				this.getPlacarSelecao1(), this.getPlacarSelecao2(), this.getSelecao2(), this.getLocal());
	}

	public static Jogo busca(int day, int month, int year, String country, Jogo[] lista) {
		for (Jogo jogo : lista)
			if (day == jogo.getDia() && month == jogo.getMes() && year == jogo.getAno()
					&& (country.equals(jogo.getSelecao1()) || country.equals(jogo.getSelecao2())))
				return jogo;
		return null;
	}
}

class NodoJogo {
	Jogo item;
	NodoJogo direita, esquerda;

	public NodoJogo(Jogo registro) {
		item = registro;
		direita = null;
		esquerda = null;
	}
}

public class Arvorebin {
	private NodoJogo raiz;
	private int comp;

	public Arvorebin() {
		raiz = null;
		comp = 0;
	}

	public void adicionar(Jogo jogoNovo) {
		this.raiz = adiciona(this.raiz, jogoNovo);
	}

	public NodoJogo adiciona(NodoJogo raizArvore, Jogo jogoNovo) {
		if (raizArvore == null)
			raizArvore = new NodoJogo(jogoNovo);
		else if (raizArvore.item.getAno() > jogoNovo.getAno()) {
			comp++;
			raizArvore.esquerda = adiciona(raizArvore.esquerda, jogoNovo);
		} else if (raizArvore.item.getAno() == jogoNovo.getAno() && raizArvore.item.getMes() > jogoNovo.getMes()) {
			comp = comp + 2;
			raizArvore.esquerda = adiciona(raizArvore.esquerda, jogoNovo);
		} else if (raizArvore.item.getAno() == jogoNovo.getAno() && raizArvore.item.getMes() == jogoNovo.getMes()
				&& raizArvore.item.getDia() > jogoNovo.getDia()) {
			comp = comp + 3;
			raizArvore.esquerda = adiciona(raizArvore.esquerda, jogoNovo);
		} else if (raizArvore.item.getAno() == jogoNovo.getAno() && raizArvore.item.getMes() == jogoNovo.getMes()
				&& raizArvore.item.getDia() == jogoNovo.getDia()
				&& (raizArvore.item.getSelecao1()).compareTo((jogoNovo.getSelecao1())) > 0) {
			comp = comp + 4;
			raizArvore.esquerda = adiciona(raizArvore.esquerda, jogoNovo);
		} else if (raizArvore.item.getAno() < jogoNovo.getAno()) {
			comp = comp + 5;
			raizArvore.direita = adiciona(raizArvore.direita, jogoNovo);
		} else if (raizArvore.item.getAno() == jogoNovo.getAno() && raizArvore.item.getMes() < jogoNovo.getMes()) {
			comp = comp + 6;
			raizArvore.direita = adiciona(raizArvore.direita, jogoNovo);
		} else if (raizArvore.item.getAno() == jogoNovo.getAno() && raizArvore.item.getMes() == jogoNovo.getMes()
				&& raizArvore.item.getDia() < jogoNovo.getDia()) {
			comp = comp + 7;
			raizArvore.direita = adiciona(raizArvore.direita, jogoNovo);
		} else if (raizArvore.item.getAno() == jogoNovo.getAno() && raizArvore.item.getMes() == jogoNovo.getMes()
				&& raizArvore.item.getDia() == jogoNovo.getDia()
				&& (raizArvore.item.getSelecao1()).compareTo((jogoNovo.getSelecao1())) < 0) {
			comp = comp + 8;
			raizArvore.direita = adiciona(raizArvore.direita, jogoNovo);
		} else
			System.out.println("O jogo já foi inserido anteriormente na árvore.");
		return raizArvore;
	}

	public void pesquisar(int dia, int mes, int ano, String selecao) {
		pesquisa(this.raiz, dia, mes, ano, selecao);
	}

	public NodoJogo pesquisa(NodoJogo raizArvore, int dia, int mes, int ano, String selecao) {
		if (raizArvore == null) {
			System.out.println("NAO");
			return raizArvore;
		}
		System.out.print("[" + raizArvore.item.getDia() + "/" + raizArvore.item.getMes() + "/"
				+ raizArvore.item.getAno() + ";" + raizArvore.item.getSelecao1() + "] - ");
		if (raizArvore.item.getAno() == ano && raizArvore.item.getMes() == mes && raizArvore.item.getDia() == dia
				&& (raizArvore.item.getSelecao1()).compareTo(selecao) == 0) {
			comp++;
			System.out.println("SIM");
			return raizArvore;
		} else if (raizArvore.item.getAno() < ano) {
			comp++;
			return pesquisa(raizArvore.direita, dia, mes, ano, selecao);
		} else if (raizArvore.item.getAno() == ano && raizArvore.item.getMes() < mes) {
			comp = comp + 2;
			return pesquisa(raizArvore.direita, dia, mes, ano, selecao);
		} else if (raizArvore.item.getAno() == ano && raizArvore.item.getMes() == mes
				&& raizArvore.item.getDia() < dia) {
			comp = comp + 3;
			return pesquisa(raizArvore.direita, dia, mes, ano, selecao);
		} else if (raizArvore.item.getAno() == ano && raizArvore.item.getMes() == mes && raizArvore.item.getDia() == dia
				&& (raizArvore.item.getSelecao1()).compareTo(selecao) < 0) {
			comp = comp + 4;
			return pesquisa(raizArvore.direita, dia, mes, ano, selecao);
		}
		return pesquisa(raizArvore.esquerda, dia, mes, ano, selecao);
	}

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		FileWriter myWriter = new FileWriter("780924_arvoreBinaria.txt");
		Jogo[] listaTotal = new Jogo[901];
		listaTotal = Jogo.ler(listaTotal);
		Arvorebin copa = new Arvorebin();
		String s = null, array[] = null;
		try (Scanner scanner = new Scanner(System.in)) {
			while (!(s = scanner.nextLine()).equals("FIM")) {
				array = s.split("[/;]+");
				copa.adicionar(Jogo.busca(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
						Integer.parseInt(array[2]), array[3], listaTotal));
			}
			while (!(s = scanner.nextLine()).equals("FIM")) {
				array = s.split("[/;]+");
				copa.pesquisar(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]),
						array[3]);
			}
		}
		myWriter.write("780924\t" + (System.currentTimeMillis() - start) + "\t" + copa.comp);
		myWriter.close();
	}
}
