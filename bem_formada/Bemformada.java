package bem_formada;

class Bemformada {
	public static void main(String[] args) throws Exception {
		String s;
		while (!(s = readString()).equals("FIM"))
			if (bemFormada(s))
				System.out.println("correto");
			else
				System.out.println("incorreto");
	}

	static String readString() throws Exception {
		StringBuilder out = new StringBuilder();
		int bytes;
		while ((bytes = System.in.read()) != 10)
			out.append((char) bytes);
		return out.toString();
	}

	public static boolean bemFormada(String str) {
		char c;
		Pilha minhaPilha = new Pilha();
		for (int i = 0; i < str.length() && (c = str.charAt(i)) != 0; i++)
			if (c == '(' || c == '[')
				minhaPilha.empilharAlocar(c);
			else if ((c == ')' || c == ']') && !minhaPilha.desempilhar(c))
				return false;
		return minhaPilha.frente == 0;
	}
}

class Pilha {
	private char[] pilha;
	public int frente;

	public Pilha() {
		pilha = new char[1];
		frente = 0;
	}

	public void empilharAlocar(char novo) {
		if (frente == pilha.length) {
			char novaPilha[] = new char[frente * 2];
			System.arraycopy(pilha, 0, novaPilha, 0, frente);
			pilha = novaPilha;
		}
		pilha[frente++] = novo;
	}

	public boolean desempilhar(char des) {
		return frente != 0 && (pilha[--frente] == '[' && des == ']' || pilha[frente] == '(' && des == ')');
	}
}