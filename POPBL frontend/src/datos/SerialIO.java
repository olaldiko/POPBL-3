package datos;

import application.MakinaIO;
import application.ManteniException;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
/**
 * Clase para organizar la conexion por puerto serial a la placa, necesita jssc
 * @author gorkaolalde
 *
 */
public class SerialIO implements MakinaIO, SerialPortEventListener {
	
	DoubleProperty dirua = new SimpleDoubleProperty(0.0);
	Double dirueeskatua;
	SerialPort puertoSerie;
	/**
	 * Constructor que abre el puerto serial indicado por parametro
	 * @param puerto nombre del puerto de serie, COMx en windows, /dev/tty.x en linux y mac
	 * @throws SerialPortException
	 */
	public SerialIO(String puerto) throws SerialPortException{
		puertoSerie = new SerialPort(puerto);
		puertoSerie.openPort();
		puertoSerie.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		puertoSerie.setEventsMask(SerialPort.MASK_RXCHAR);
		puertoSerie.addEventListener(this);
	}

	
	@Override
	/**
	 * EventListener que trata los datos recibidos por puerto de serie
	 */
	public void serialEvent(SerialPortEvent event){
		
		if(event.isRXCHAR()){
			try {
				byte buffer[] = puertoSerie.readBytes(event.getEventValue());
				for(int i = 0; i < buffer.length;i++){
					switch(buffer[i]){
					case 1:
						Platform.runLater(() -> {
						dirua.set(dirua.get()+0.5);
						});
						System.out.println("Recibe 50 cent "+dirua.get());
						break;
					case 2:
						Platform.runLater(() -> {
						dirua.set(dirua.get()+1);
						});
						System.out.println("Recibe 1 euro"+dirua.get());
						break;
					case 4:
						Platform.runLater(() -> {
						dirua.set(dirua.get()+2);
						});
						System.out.println("Recibe 2 euros"+dirua.get());
						break;
					default:
						System.out.println("Algo llega");
					}
				}
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	@Override
	public Double getDirua() {
		return dirua.get();
	}
	/**
	 * Hace un bind entre la ObjectProperty pasada por parametro y dirua de esta clase
	 * @param d DoubleProperty dirua
	 */
	public void bindDirua(DoubleProperty d){
		d.bindBidirectional(dirua);
	}
	/**
	 * Pone el dinero a 0
	 */
	public void resetDirua(){
		dirua.set(0.0);
	}
	public void setDiruaProperty(DoubleProperty d){
		this.dirua = d;
	}
	@Override
	/**
	 * Activa las luces en la placa
	 */
	public void setArgiak() throws SerialPortException {
			puertoSerie.writeInt(3);
			puertoSerie.writeInt(3);
	}
	/**
	 * Anade otro objeto para que sea SerialLister del puerto
	 * @param e
	 */
	public void addSerialListener(SerialPortEventListener e){
		try {
			puertoSerie.addEventListener(e);
		} catch (SerialPortException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * Activa o desactiva la recepcion de dinero en placa
	 * @throws SerialPortException
	 */
	public void activaDesactivaPlaca() throws SerialPortException{
		puertoSerie.writeInt(1);
		puertoSerie.writeInt(1);
	}
	@Override
	public void addListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}
	
	

}
