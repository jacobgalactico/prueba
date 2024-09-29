package grupo1.caso_practico.config;

import grupo1.caso_practico.model.SensorType;
import grupo1.caso_practico.model.EventType;
import grupo1.caso_practico.domain.Sensor;
import grupo1.caso_practico.model.SensorStatus;
import grupo1.caso_practico.repos.SensorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SensorDataInitializer {

    @Bean
    public CommandLineRunner loadData(SensorRepository sensorRepository) {
        return (args) -> {
            // Verificar si ya hay sensores en la base de datos
            if (sensorRepository.count() == 0) {
                // Crear 5 sensores predeterminados
                Sensor sensor1 = new Sensor();
                sensor1.setName("Sensor Movimiento - Cuarto 1");
                sensor1.setType(SensorType.MOTION);
                sensor1.setLocation("Cuarto 1");
                sensor1.setStatus(SensorStatus.ACTIVE);
                sensorRepository.save(sensor1);

                Sensor sensor2 = new Sensor();
                sensor2.setName("Sensor Temperatura - Cuarto 2");
                sensor2.setType(SensorType.TEMPERATURE);
                sensor2.setLocation("Cuarto 2");
                sensor2.setStatus(SensorStatus.ACTIVE);
                sensorRepository.save(sensor2);

                Sensor sensor3 = new Sensor();
                sensor3.setName("Sensor Acceso - Entrada Principal");
                sensor3.setType(SensorType.ACCESS);
                sensor3.setLocation("Entrada Principal");
                sensor3.setStatus(SensorStatus.ACTIVE);
                sensorRepository.save(sensor3);

                Sensor sensor4 = new Sensor();
                sensor4.setName("Sensor Movimiento - Cuarto 3");
                sensor4.setType(SensorType.MOTION);
                sensor4.setLocation("Cuarto 3");
                sensor4.setStatus(SensorStatus.ACTIVE);
                sensorRepository.save(sensor4);

                Sensor sensor5 = new Sensor();
                sensor5.setName("Sensor Temperatura - Sala de Control");
                sensor5.setType(SensorType.TEMPERATURE);
                sensor5.setLocation("Sala de Control");
                sensor5.setStatus(SensorStatus.ACTIVE);
                sensorRepository.save(sensor5);

                System.out.println("Sensores predefinidos creados correctamente.");
            }
        };
    }
}
