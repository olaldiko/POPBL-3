package Admin;

import javax.swing.ImageIcon;

public class Equipo {
	int id;
	String name;
	String shortName;
	String crestUrl;
	
	public Equipo(int id, String name, String shortName, String crestUrl){
		this.id = id;
		this.name = name;
		this.shortName = shortName;
		this.crestUrl = crestUrl;
	}
	
	public String toString(){
		return id+" - EQUIPO: "+name+" - ESTADIO: "+estadio;
	}
}