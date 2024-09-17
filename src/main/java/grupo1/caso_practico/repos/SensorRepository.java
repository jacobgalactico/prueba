package grupo1.caso_practico.repos;

import grupo1.caso_practico.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
