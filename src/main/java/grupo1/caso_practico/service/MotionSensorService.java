package grupo1.caso_practico.service;
import org.hibernate.grammars.hql.HqlParser.LocalDateTimeContext;
import org.springframework.stereotype.Service;

import grupo1.caso_practico.repos.EventRepository;
import grupo1.caso_practico.repos.SensorRepository;

import java.time.LocalDateTime;

import org.slf4j.*;
@Service
public class MotionSensorService extends SensorService {

  private static final Logger logger = LoggerFactory.getLogger(MotionSensorService.class);
  private boolean motionDetected;
  private LocalDateTime lastDetectionTime;
  
  public MotionSensorService(SensorRepository sensorRepository, EventRepository eventRepository){
      super(sensorRepository, eventRepository); 
      this.motionDetected = false;
      this.lastDetectionTime = null;
  }

  public void startMonitoring(){
    logger.info("Iniciando la monitorizacion del sensor de movimiento");
    monitorSensor();
  }
  public void stopMonitoring(){
    logger.info("Deteniendo la monitorizacion del sensor de movimiento");
    monitorSensor();
  }
  
  public String getSensorStatus(){
    if(motionDetected){
      return "Movimiento fue detectado por ultima vez en: " + lastDetectionTime;
    } else {
      return "El sensor esta en espera, no se ha detectado nada recientemente";
    }
  }
  
  public LocalDateTime getlastDetectionTime(){
    return lastDetectionTime;
  }

  public boolean detectMotion(){
    logger.debug("Verificando si hay movimiento...");

    this.motionDetected = checkSensorForMotion();

    if(motionDetected){
      lastDetectionTime = LocalDateTime.now();
      logger.info("Se ha detectado movimiento en el sensor!");
    } else {
      logger.debug("No se ha detectado movimiento");
    }
    return motionDetected;
  }

  private boolean checkSensorForMotion(){
    return Math.random() > 0.5;
  }

  private void monitorSensor(){
    for (int i = 0; i < 5; i++) {
      detectMotion();
      try{
        Thread.sleep(2000);
      } catch(InterruptedException e){
        logger.error("Error en la monitorizacion del sensor: {}", e.getMessage());
        Thread.currentThread().interrupt();
      }
    }
  }
}
