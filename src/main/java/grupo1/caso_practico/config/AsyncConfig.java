package grupo1.caso_practico.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
  
  @Bean(name = "sensorExecutor")
  public Executor sensorExecutor(){ //Creamos un bean para ejecutar la gestion de sensores en la aplicacion
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(50);
    executor.setQueueCapacity(100);
    executor.setThreadNamePrefix("SensorThread- ");
    return executor;  
  }

  @Bean(name = "emailExecutor")
  public Executor emailExecutor(){  //Este bean lo usamos para ejecutar tareas relacionadas con el envio de emails. Nos permite que la operacion de envio no bloquee el flujo principal de la aplicacion.
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(5);
    executor.setMaxPoolSize(20);
    executor.setQueueCapacity(50);
    executor.setThreadNamePrefix("EmailThread- ");
    return executor;
  }
}
