package grupo1.caso_practico.domain;

import java.time.LocalDateTime;

import javax.annotation.processing.Generated;
import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "security_events")
@Getter
@Setter
public class SecurityEvent {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;

  private String description;
  private LocalDateTime timestamp;
  private String severity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "device_id")
  private Device device;

  public SecurityEvent() {
  }

  public SecurityEvent(String description, LocalDateTime timestamp, String severity, User user, Device device) {
    this.description = description;
    this.timestamp = timestamp;
    this.severity = severity;
    this.user = user;
    this.device = device;
  }

}
