package grupo1.caso_practico.domain;

import grupo1.caso_practico.model.SensorStatus;
import grupo1.caso_practico.model.SensorType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Sensors")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Sensor {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SensorType type;

    @Column(nullable = false)
    @NotNull
    private String location;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private SensorStatus status;

    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Event> events = new HashSet<>();    //Inicializamos el set para evitar un nullpointer mas adelante

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;



    public void addEvent(Event event){
        this.event.add(event);
        event.setSensor(this);
    }


    public void removeEvent(Event event){
        this.event.remove(event);
        events.setSensor(null);
        }



}
