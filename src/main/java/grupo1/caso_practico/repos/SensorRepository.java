package grupo1.caso_practico.repos;

import grupo1.caso_practico.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
