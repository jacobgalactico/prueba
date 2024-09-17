package grupo1.caso_practico.repos;

import grupo1.caso_practico.domain.Event;
import grupo1.caso_practico.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, Long> {

    Event findFirstBySensor(Sensor sensor);

}
