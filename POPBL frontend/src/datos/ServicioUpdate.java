package datos;

import application.ManteniException;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
/**
 * Servicio que ejecuta los metodos para actualizar los datos desde la base de datos contenidas en ModeloApuestas
 * @author gorkaolalde
 *
 */
public class ServicioUpdate extends ScheduledService<Void> {
	
	@Override
	protected Task<Void> createTask() {
			return new Task<Void>(){
				protected Void call(){
					ModeloApuestas modelo;
					try {
						modelo = ModeloApuestas.getInstance();
						Platform.runLater(() -> {
								try{
								modelo.updatePartidosPr(modelo.defaultLiga);
								}catch(ManteniException e){
									
								}
							});
						Platform.runLater(() -> {
								try{
									modelo.updatePartidosEmaitzak(modelo.defaultLiga);
								}catch(ManteniException e){
									
								}
							});
					} catch (ManteniException e) {
					}
					return null;
				}
			};
	}

}
