
public class  Peças {
	protected boolean capturada=false;
	protected String nome;
	protected String cor;
	protected boolean primeiromovimento=true; //Controla se a peça já se moveu, caso não o tenha fica true
	protected boolean en_passan=true;
	
	public boolean move(int Linicio,int Cinicio,int Lfim,int Cfim,String cor) {
		return false;
	}
	
	public String getcor() {
		return cor;
	}
	
	public boolean getprimeiromovimento() {
		return primeiromovimento;
	}
	
	public void set_en_passan() {
		en_passan=false;
	}
	
	public boolean get_en_passan() {
		return en_passan;
	}
		
	public void setprimeiromovimento() {
		primeiromovimento=false;
	}
	
	public String getnome() {
		return nome;
	}
	
	
}
