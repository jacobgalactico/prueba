package grupo1.caso_practico.domain;

import java.time.LocalDateTime;

import groovy.transform.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="alerts")
@Getter
@Setter
public class Alert {
  
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;

  private String message;
  private LocalDateTime timestamp;

  @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User recipient;

  @OneToOne(fetch = jakarta.persistence.FetchType.LAZY)
  @JoinColumn(name = "event_id")
  private SecurityEvent event;

  public Alert() {
  }

  public Alert(String message, LocalDateTime timestamp, User recipient, SecurityEvent event) {
    this.message = message;
    this.timestamp = timestamp;
    this.recipient = recipient;
    this.event = event;
  }
}
