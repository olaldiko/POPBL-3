package Admin;

public class Tiempo {
	int año;
	int mes;
	int dia;
	int hora;
	int minutos;
	int segundos;
	
	public Tiempo(int año, int mes, int dia, int hora, int minutos, int segundos){
		this.año = año;
		this.mes = mes;
		this.dia = dia;
		this.hora = hora;
		this.minutos = minutos;
		this.segundos = segundos;
	}
	
	public String toString(){
		return año+"-"+mes+"-"+dia+"T"+hora+":"+minutos+":"+segundos+"Z";
	}
}