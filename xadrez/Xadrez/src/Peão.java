
public class Peão extends Peças {

	
	
	Peão(String cor){
		nome="Peão";
		this.cor=cor;
	}
	
	@Override
	public boolean move(int Linicio,int Cinicio,int Lfim,int Cfim,String cor) {
		if(cor=="preto") {
            if(Linicio<Lfim) {
                return false;
            }
        } else {
            if(Linicio>Lfim) {
                return false;
            }
        }
		
		if(primeiromovimento==true&&(Math.abs(Linicio-Lfim)<3)) {
			return true;
		} else if(primeiromovimento==false&&(Math.abs(Linicio-Lfim)<2)) {
			return true;
		}
		return false;
	}
	
}
