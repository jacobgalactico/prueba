package grupo1.caso_practico.service;

import grupo1.caso_practico.domain.Event;
import grupo1.caso_practico.domain.Sensor;
import grupo1.caso_practico.model.SensorDTO;
import grupo1.caso_practico.repos.EventRepository;
import grupo1.caso_practico.repos.SensorRepository;
import grupo1.caso_practico.util.NotFoundException;
import grupo1.caso_practico.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private final EventRepository eventRepository;

    public SensorService(final SensorRepository sensorRepository,
                         final EventRepository eventRepository) {
        this.sensorRepository = sensorRepository;
        this.eventRepository = eventRepository;
    }

    public List<SensorDTO> findAll() {
        final List<Sensor> sensors = sensorRepository.findAll(Sort.by("id"));
        return sensors.stream()
                .map(sensor -> mapToDTO(sensor, new SensorDTO()))
                .toList();
    }

    public SensorDTO get(final Long id) {
        return sensorRepository.findById(id)
                .map(sensor -> mapToDTO(sensor, new SensorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SensorDTO sensorDTO) {
        final Sensor sensor = new Sensor();
        mapToEntity(sensorDTO, sensor);
        return sensorRepository.save(sensor).getId();
    }

    public void update(final Long id, final SensorDTO sensorDTO) {
        final Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sensorDTO, sensor);
        sensorRepository.save(sensor);
    }

    public void delete(final Long id) {
        sensorRepository.deleteById(id);
    }

    private SensorDTO mapToDTO(final Sensor sensor, final SensorDTO sensorDTO) {
        sensorDTO.setId(sensor.getId());
        sensorDTO.setName(sensor.getName());
        sensorDTO.setType(sensor.getType());
        sensorDTO.setLocation(sensor.getLocation());
        sensorDTO.setStatus(sensor.getStatus());
        return sensorDTO;
    }

    private Sensor mapToEntity(final SensorDTO sensorDTO, final Sensor sensor) {
        sensor.setName(sensorDTO.getName());
        sensor.setType(sensorDTO.getType());
        sensor.setLocation(sensorDTO.getLocation());
        sensor.setStatus(sensorDTO.getStatus());
        return sensor;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Event sensorEvent = eventRepository.findFirstBySensor(sensor);
        if (sensorEvent != null) {
            referencedWarning.setKey("sensor.event.sensor.referenced");
            referencedWarning.addParam(sensorEvent.getId());
            return referencedWarning;
        }
        return null;
    }

    // Método para el procesamiento asíncrono de los datos del sensor
    @Async("taskExecutor")
    public CompletableFuture<Void> processSensorData(Sensor sensor) {
        System.out.println("Procesando sensor: " + sensor.getName());

        // Simulación de procesamiento costoso (puedes agregar aquí la lógica real)
        try {
            Thread.sleep(2000);  // Simula un procesamiento de 2 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Finalizó el procesamiento del sensor: " + sensor.getName());
        return CompletableFuture.completedFuture(null);
    }

}
