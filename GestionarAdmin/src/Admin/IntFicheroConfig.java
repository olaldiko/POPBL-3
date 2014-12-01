package Admin;

public interface IntFicheroConfig {
	void cargarDatos();
	void guardarDatos(int tamx, int tamy, String URL, int nPort, String nBD, String user, String pass);
}
