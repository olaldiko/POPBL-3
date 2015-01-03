package datos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class Estadisticas {
 int ganadas = 0;
 int pendientes = 0;
 int perdidas = 0;
	 public ObservableList<PieChart.Data> estadisToPie(){
		 ObservableList<PieChart.Data> estadis =
	             FXCollections.observableArrayList(
	             new PieChart.Data("Irabaziak", ganadas),
	             new PieChart.Data("Ez jokatuak", pendientes),
	             new PieChart.Data("Galduak", perdidas));
		 return estadis;
	 }
	public int getGanadas() {
		return ganadas;
	}
	public void setGanadas(int ganadas) {
		this.ganadas = ganadas;
	}
	public int getPendientes() {
		return pendientes;
	}
	public void setPendientes(int pendientes) {
		this.pendientes = pendientes;
	}
	public int getPerdidas() {
		return perdidas;
	}
	public void setPerdidas(int perdidas) {
		this.perdidas = perdidas;
	}
}
