package combinador_de_strings;

import java.util.Scanner;

public class Combinator {

	static Scanner scanner = new Scanner(System.in);

	public static void combinador(String str1, String str2) {
		char[] str1Array = str1.toCharArray(), str2Array = str2.toCharArray(),
				str3Array = new char[str1Array.length + str2Array.length];
		int i;
		for (i = 0; i < str1Array.length && i < str2Array.length; i++) {
			str3Array[2 * i] = str1Array[i];
			str3Array[2 * i + 1] = str2Array[i];
		}
		if (i >= str1Array.length)
			for (int j = i; j < str2Array.length; j++)
				str3Array[i + j] = str2Array[j];
		if (i >= str2Array.length)
			for (int j = i; j < str1Array.length; j++)
				str3Array[i + j] = str1Array[j];
		System.out.println(str3Array);
	}

	public static void main(String[] args) {
		String str, array[];
		while (!(str = scanner.nextLine()).equals("FIM")) {
			array = str.split("\\s+");
			combinador(array[0], array[1]);
		}
	}

}