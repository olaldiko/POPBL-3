package datos;

import application.ManteniException;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class ServicioUpdate extends ScheduledService<Void> {

	@Override
	protected Task<Void> createTask() {
			return new Task<Void>(){
				protected Void call(){
					ModeloApuestas modelo;
					try {
						modelo = ModeloApuestas.getInstance();
						modelo.updatePartidosPr(modelo.defaultLiga);
						modelo.updatePartidosEmaitzak(modelo.defaultLiga);
					} catch (ManteniException e) {
					}
					return null;
				}
			};
	}

}
