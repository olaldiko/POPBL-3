package Admin;

public class Partido {
	public Partido(String nombreEquipoL, String nombreEquipoV){
		Equipo eLocal = new Equipo(nombreEquipoL);
		Equipo eVisitante = new Equipo(nombreEquipoV);
	}
}