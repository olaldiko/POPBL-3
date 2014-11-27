package Admin;

public class Tiempo {
	int año;
	int mes;
	int dia;
	
	public Tiempo(int año, int mes, int dia){
		this.año = año;
		this.mes = mes;
		this.dia = dia;
	}
	
	public String toString(){
		return año+"-"+mes+"-"+dia;
	}
}