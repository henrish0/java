//
package lista_encadeada_dinamica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class CelulaJogo {
	Jogo item;
	CelulaJogo proximo;

	CelulaJogo() {
		item = new Jogo();
		proximo = null;
	}
}

class Lista {
	private CelulaJogo primeiro, ultimo;

	public Lista() {
		primeiro = new CelulaJogo();
		ultimo = primeiro;
	}

	public void inserir(Jogo novo, int local) {
		CelulaJogo tempNovo = new CelulaJogo();
		CelulaJogo temp = primeiro;
		for (int i = 0; temp != null && i < local; i++)
			temp = temp.proximo;
		if (temp == null) {
			System.out.println("NF");
			return;
		}
		tempNovo.proximo = temp.proximo;
		temp.proximo = tempNovo;
		tempNovo.item = novo;
	}

	public void inserirInicio(Jogo novo) {
		CelulaJogo tempNovo = new CelulaJogo();
		tempNovo.item = novo;
		tempNovo.proximo = primeiro.proximo;
		primeiro.proximo = tempNovo;
	}

	public void inserirFim(Jogo novo) {
		CelulaJogo temp = new CelulaJogo();
		ultimo.proximo = temp;
		temp.item = novo;
		ultimo = ultimo.proximo;
	}

	public Jogo remover(int local) {
		CelulaJogo temp = primeiro.proximo, tempAnterior = primeiro;
		for (int i = 0; temp != null && i < local; i++) {
			temp = temp.proximo;
			tempAnterior = tempAnterior.proximo;
		}
		if (temp == null)
			return null;
		tempAnterior.proximo = tempAnterior.proximo.proximo;
		return temp.item;
	}

	public Jogo removerInicio() {
		CelulaJogo temp = primeiro.proximo;
		primeiro.proximo = primeiro.proximo.proximo;
		return temp.item;
	}

	public Jogo removerFim() {
		CelulaJogo tempRetorno = ultimo, temp = primeiro.proximo;
		while (temp.proximo.proximo != null) {
			temp = temp.proximo;
		}
		ultimo = temp;

		return tempRetorno.item;
	}

	public Boolean listaVazia() {
		if (primeiro == ultimo)
			return true;
		return false;

	}

	public void imprimir() {
		CelulaJogo tmp;
		tmp = primeiro.proximo;
		if (tmp == null)
			System.out.println("A lista de contas-correntes está vazia.");
		else
			for (int i = 0; tmp != null; i++) {
				System.out.print("[" + i + "]");
				tmp.item.imprimirJogo();
				tmp = tmp.proximo;
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

public class Listadinamica {
	public static void main(String[] args) {
		Lista minhaLista = new Lista();
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
				if (s.charAt(0) == 'R')
					if (s.charAt(1) == 'I') {
						System.out.print("(R) ");
						minhaLista.removerInicio().imprimirJogo();
					} else if (s.charAt(1) == 'F') {
						System.out.print("(R) ");
						minhaLista.removerFim().imprimirJogo();
					} else {
						s0 = s.split(" ", 2);
						System.out.print("(R) ");
						minhaLista.remover(Integer.parseInt(s0[1])).imprimirJogo();
					}
				else if (s.charAt(1) == 'F') {
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
		minhaLista.imprimir();
	}
}