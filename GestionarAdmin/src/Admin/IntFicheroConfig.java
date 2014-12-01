package Admin;

public interface IntFicheroConfig {
	void cargarDatos(int tamx, int tamy, String URL, int nPort, String nBD,
			String user, String pass);

	void guardarDatos(int tamx, int tamy, String URL, int nPort, String nBD,
			String user, String pass);
}
