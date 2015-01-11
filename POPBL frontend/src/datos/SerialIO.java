package datos;

import application.MakinaIO;
import application.ManteniException;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialIO implements MakinaIO, SerialPortEventListener {
	
	static SerialIO serial;
	DoubleProperty dirua = new SimpleDoubleProperty(0.0);
	Double dirueeskatua;
	SerialPort puertoSerie;
	private SerialIO() throws SerialPortException{
		puertoSerie = new SerialPort("/dev/tty.usbmodem1411");
		puertoSerie.openPort();
		puertoSerie.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		puertoSerie.setEventsMask(SerialPort.MASK_RXCHAR);
		puertoSerie.addEventListener(this);
	}
	public static SerialIO getInstance() throws SerialPortException{
		if(serial == null){
			serial = new SerialIO();
			return serial;
		}else{
			return serial;
		}
	}

	@Override
	public void serialEvent(SerialPortEvent event){
		
		if(event.isRXCHAR()){
			try {
				byte buffer[] = puertoSerie.readBytes(event.getEventValue());
				for(int i = 0; i < buffer.length;i++){
					switch(buffer[i]){
					case 1:
						dirua.set(dirua.get()+0.5);
						break;
					case 2:
						dirua.set(dirua.get()+1);
						break;
					case 4:
						dirua.set(dirua.get()+2);
						break;
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
	public void bindDirua(DoubleProperty d){
		d.bindBidirectional(dirua);
	}
	public void resetDirua(){
		dirua.set(0.0);
	}
	@Override
	public void setArgiak() throws SerialPortException {
			puertoSerie.writeInt(32);
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
