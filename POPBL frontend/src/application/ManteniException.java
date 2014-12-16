package application;

public class ManteniException extends Exception{

	/**
	 * 1 = Error de conexion a BD
	 * 2 = Error al cargar el driver
	 * 3 = Error al leer datos de una tabla
	 */
	private static final long serialVersionUID = 2282394114341023674L;
	int tipo;
	public ManteniException(int tipo){
		super();
		this.tipo = tipo;
	}
}