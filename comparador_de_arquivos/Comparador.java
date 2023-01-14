package comparador_de_arquivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Comparador {
	public static void log(String in, BufferedWriter w) throws IOException {
		System.out.println(in);
		w.write(in + "\n");
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String fl1 = "meu.txt", fl2 = "fessor.txt", out = "diferencas.txt", ln1, ln2;
		boolean tof = true;
		BufferedReader rd[] = { new BufferedReader(new FileReader(fl1)), new BufferedReader(new FileReader(fl2)) };
		BufferedWriter wr = new BufferedWriter(new FileWriter(out));
		for (int q = 1; (ln1 = rd[0].readLine()) != null && (ln2 = rd[1].readLine()) != null; q++)
			if (!ln1.equals(ln2) && !(tof = false))
				log(" Diferença encontrada na linha " + q, wr);
		if (tof)
			log(" Os arquivos " + fl1 + " e " + fl2 + " são iguais", wr);
		if (ln1 != null)
			log(" " + fl1 + " é maior do que " + fl2, wr);
		else if (rd[1].readLine() != null)
			log(" " + fl2 + " é maior do que " + fl1, wr);
		else
			log(" Os arquivos têm o mesmo tamanho em linhas", wr);
	}
}
