package grupo1.caso_practico.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Devices")
@Getter
@Setter
public class Device {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String type;
  private String location;
  private boolean isActive;

  public Device() {
  }

  public Device(String type, String location, boolean isActive) {
    this.type = type;
    this.location = location;
    this.isActive = isActive;
  }
  
}
