import java.util.ArrayList;

public class Jogadores {
	private String nome;
	private String cor;
	private ArrayList<Peças> peçascapturadas = new ArrayList<>();
	static private ArrayList<String> jogadas = new ArrayList<>();
	
	public Jogadores(String nome,String cor) {
	this.nome=nome;
	this.cor=cor;
	}
	
	public String getnome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome=nome;
	}
	
	public String getcor() {
		return cor;
	}
	
	public void set_peças_capturadas(Peças peça) {
		peçascapturadas.add(peça);
	}
	
	static public void set_jogadas(String jogada) {
		jogadas.add(jogada);
	}
	
	static public void replace_jogadas(int aux,String jogada) {
		jogadas.set(aux, jogada);
	}
	
	static public String get_jogadas(int aux) {
		return jogadas.get(aux);
	}
	
	static public int tamanho_vetor_jogadas() {
		return jogadas.size();
	}
	
	public int tamanho_vetor_capturadas() {
		return peçascapturadas.size();
	}	
}
