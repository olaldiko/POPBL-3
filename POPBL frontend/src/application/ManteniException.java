package application;

public class ManteniException extends Exception{

	/**
	 * 1 = Error de conexion a BD
	 * 2 = Error al cargar el driver
	 * 3 = Error al leer datos de una tabla
	 * 4 = Error de comunicaciones serie
	 */
	private static final long serialVersionUID = 2282394114341023674L;
	int tipo;
	static ScreensController myController;
	public static void setScreenParent(ScreensController screenPage) {
		myController = screenPage;

	}
	public ManteniException(int tipo){
		super();
		this.tipo = tipo;
		
	}
	public ManteniException(int tipo, Exception e){
		super();
		this.tipo = tipo;
		e.printStackTrace();
		ControladorManteni.setException(this);
		myController.loadScreen(ScreensFramework.Manteni, ScreensFramework.Manteni_FXML);
		myController.setScreen("manteni");
	}
}
