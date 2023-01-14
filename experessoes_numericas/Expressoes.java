//
package experessoes_numericas;

public class Expressoes {
	public static void main(String[] args) throws Exception {
		String inpt;
		while (!(inpt = lerInput()).equals("FIM")) {
			System.out.print(inpt + " se torna ");
			System.out.print((inpt = paraPolones(inpt.replace("(", "( ").replace(")", " )").split(" "))));
			float resultado = calculaPolones(inpt.split(" "));
			if ((int) resultado == resultado)
				System.out.print(" = " + (int) resultado);
			else
				System.out.print(" = " + resultado);
			System.out.print("\n");
		}
	}

	static String lerInput() throws Exception {
		StringBuilder saida = new StringBuilder();
		int bytes;
		while ((bytes = System.in.read()) != 10)
			saida.append((char) bytes);
		return saida.toString();
	}

	public static String paraPolones(String[] in) throws Exception {
		StringBuilder saida = new StringBuilder();
		Pilha<String> minhaPilha = new Pilha<String>();
		for (String strIn : in)
			if (strIn.equals("("))
				minhaPilha.empilhar(strIn);
			else if (strIn.equals(")")) {
				while (!minhaPilha.topo().equals("("))
					saida.append(minhaPilha.desempilhar()).append(" ");
				minhaPilha.desempilhar();
			} else if (strIn.matches("-?[0-9a-fA-F]+"))
				saida.append(strIn + " ");
			else {
				while (!minhaPilha.estaVazia() && peso(minhaPilha.topo().charAt(0)) >= peso(strIn.charAt(0)))
					saida.append(minhaPilha.desempilhar()).append(" ");
				minhaPilha.empilhar(strIn);
			}
		while (!minhaPilha.estaVazia())
			saida.append(minhaPilha.desempilhar()).append(" ");
		return new String(saida);
	}

	public static int peso(char i) {
		return (i == '(') ? -1 : (i == '+' || i == '-') ? 0 : 1;
	}

	public static float calculaPolones(String[] in) throws Exception {
		Pilha<Float> minhaPilha = new Pilha<Float>();
		for (String strIn : in)
			if (strIn.equals("+"))
				minhaPilha.empilhar(minhaPilha.desempilhar() + minhaPilha.desempilhar());
			else if (strIn.equals("-"))
				minhaPilha.empilhar(-minhaPilha.desempilhar() + minhaPilha.desempilhar());
			else if (strIn.equals("*"))
				minhaPilha.empilhar(minhaPilha.desempilhar() * minhaPilha.desempilhar());
			else if (strIn.equals("/"))
				minhaPilha.empilhar(1 / minhaPilha.desempilhar() * minhaPilha.desempilhar());
			else if (Character.isAlphabetic(strIn.charAt(0)))
				minhaPilha.empilhar((float) Integer.parseInt(strIn, 16));
			else
				minhaPilha.empilhar(Float.parseFloat(strIn));
		return minhaPilha.desempilhar();
	}
}

// O cria aprendeu metodo generico 😎👊👊🔥🔥
class Pilha<e> {
	private Celula<e> topo;

	Pilha() {
		topo = new Celula<e>(null, null);
	}

	public boolean estaVazia() {
		return topo.item == null;
	}

	public void empilhar(e novoItem) {
		topo = new Celula<e>(novoItem, topo);
	}

	public e desempilhar() throws Exception {
		if (estaVazia())
			throw new Exception("Pilha vazia");
		e retorno = topo.item;
		topo = topo.anterior;
		return retorno;
	}

	public e topo() throws Exception {
		if (estaVazia())
			throw new Exception("Pilha vazia");
		return topo.item;
	}
}

class Celula<E> {
	E item;
	Celula<E> anterior;

	Celula(E valor, Celula<E> itemPrevio) {
		item = valor;
		anterior = itemPrevio;
	}
}
