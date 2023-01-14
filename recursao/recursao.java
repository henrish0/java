package recursao;

import java.lang.String;
import java.util.Scanner;

public class recursao {

	// Definir a leitura do input
	static Scanner readInput = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println(
				"\n 1: Contar maiusculas, 2: Fatorial, 3: Fibonacci, 4: Mergesort\n Digite a funcao desejada: ");
		int choice = Integer.parseInt(readInput.nextLine());
		while (choice != -1) {
			if (choice == 1)
				callcountUppercase();
			if (choice == 2)
				callFactorial();
			if (choice == 3)
				callFibonacci();
			if (choice == 4)
				callMergesort();
			choice = -1;
		}
	}

	private static void callMergesort() {
		System.out.println(" Digite o tamanho do seu vetor: ");
		int n = Integer.parseInt(readInput.nextLine());
		System.out.println(" Digite " + n + " numeros para serem sortidos: ");
		int[] vector = new int[n];
		for (int i = 0; i < n; i++) {
			System.out.println(i + ": ");
			vector[i] = Integer.parseInt(readInput.nextLine());
		}
		sort(vector, 0, n - 1);
		for (int i = 0; i < n; i++)
			System.out.println(vector[i]);
	}

	private static void merge(int[] arr, int l, int m, int r) {

		// Find sizes of two subarrays to be merged
		int n1 = m - l + 1;
		int n2 = r - m;

		/* Create temp arrays */
		int L[] = new int[n1];
		int R[] = new int[n2];

		/* Copy data to temp arrays */
		for (int i = 0; i < n1; ++i)
			L[i] = arr[l + i];
		for (int j = 0; j < n2; ++j)
			R[j] = arr[m + 1 + j];

		/* Merge the temp arrays */

		// Initial indexes of first and second subarrays
		int i = 0, j = 0;

		// Initial index of merged subarray array
		int k = l;
		while (i < n1 && j < n2) {
			if (L[i] <= R[j]) {
				arr[k] = L[i];
				i++;
			} else {
				arr[k] = R[j];
				j++;
			}
			k++;
		}

		/* Copy remaining elements of L[] if any */
		while (i < n1) {
			arr[k] = L[i];
			i++;
			k++;
		}

		// Copy remaining elements of R[] if any
		while (j < n2) {
			arr[k] = R[j];
			j++;
			k++;
		}
	}

	public static void sort(int arr[], int l, int r) {
		if (l < r) {
			// Find the middle point
			int m = l + (r - l) / 2;

			// Sort first and second halves
			sort(arr, l, m);
			sort(arr, m + 1, r);

			// Merge the sorted halves
			merge(arr, l, m, r);
		}
	}

	public static void callFibonacci() {
		int inpt = Integer.parseInt(readInput.nextLine());
		System.out.println(fibonacciRecursion(inpt));
	}

	public static int fibonacciRecursion(int in) {
		if (in == 1 || in == 2)
			in = 1;
		else
			return fibonacciRecursion(in - 1) + fibonacciRecursion(in - 2);
		return in;
	}

	public static void callFactorial() {
		int inpt = Integer.parseInt(readInput.nextLine());
		System.out.println(factorialRecursion(inpt));
	}

	public static int factorialRecursion(int in) {
		if (in == 1)
			return in;
		else
			return in * factorialRecursion(in - 1);
	}

	public static void callcountUppercase() {
		// Definir saida da funcao e imprimi-la
		int m = 0;
		// Ler input
		String linha = readInput.nextLine();
		// Definir fim da funcao
		while (linha.equals("FIM") == false) {
			// Tratamento de excecao em caso de string vazia
			if (linha != "")
				// Usar input e gerar output com a funcao recursiva
				m = countUppercaseRecursion(linha, 0, 0);
			// Tratando excecao
			else
				m = 0;
			// Print output
			System.out.println(m);
			// Ler outro input e terminar while caso usuario digite FIM
			linha = readInput.nextLine();
		}

	}

	/*
	 * Nesta funcao, a primeira entrada define o input do usuario, a segunda, o
	 * total de letras maiusculas e a terceira recebe o ponteiro que percorre cada
	 * caracter da string gerada pelo input.
	 */
	public static int countUppercaseRecursion(String in, int total, int i) {
		// Caso a letra seja maiuscula
		if (in.charAt(i) >= 'A' && in.charAt(i) <= 'Z')
			// Adiciona ao total de letras maiusculas
			total++;
		// Caso a string nao tenha sido percorrida ate seu fim
		if (i < in.length() - 1)
			// Executa a funcao novamente adicionando uma unidade ao ponteiro
			return countUppercaseRecursion(in, total, i + 1);
		// Caso a string tenha sido percorrida, retorna o total de letras maiusculas
		return total;
	}
}
