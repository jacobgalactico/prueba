package grupo1.caso_practico.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorEvent {
  
  private String sensorId;
  private String eventType;
  private LocalDateTime eventTime;
  private String data;

  
  public SensorEvent(String sensorId, String eventType, LocalDateTime eventTime, String data) {
    this.sensorId = sensorId;
    this.eventType = eventType;
    this.eventTime = eventTime;
    this.data = data;
  }

}
