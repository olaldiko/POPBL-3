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
	
	DoubleProperty dirua = new SimpleDoubleProperty(0.0);
	Double dirueeskatua;
	SerialPort puertoSerie;
	public SerialIO(String puerto) throws SerialPortException{
		puertoSerie = new SerialPort(puerto);
		puertoSerie.openPort();
		puertoSerie.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		puertoSerie.setEventsMask(SerialPort.MASK_RXCHAR);
		puertoSerie.addEventListener(this);
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
						System.out.println("Recibe 50 cent "+dirua.get());
						break;
					case 2:
						dirua.set(dirua.get()+1);
						System.out.println("Recibe 1 euro"+dirua.get());
						break;
					case 4:
						dirua.set(dirua.get()+2);
						System.out.println("Recibe 2 euros"+dirua.get());
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
