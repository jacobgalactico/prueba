package grupo1.caso_practico.service;

import grupo1.caso_practico.domain.Event;
import grupo1.caso_practico.domain.Notification;
import grupo1.caso_practico.domain.Sensor;
import grupo1.caso_practico.model.EventDTO;
import grupo1.caso_practico.model.EventType;
import grupo1.caso_practico.repos.EventRepository;
import grupo1.caso_practico.repos.NotificationRepository;
import grupo1.caso_practico.repos.SensorRepository;
import grupo1.caso_practico.util.NotFoundException;
import grupo1.caso_practico.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import grupo1.caso_practico.model.SensorType;
import grupo1.caso_practico.model.EventType;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static grupo1.caso_practico.model.EventType.MOTION;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final SensorRepository sensorRepository;
    private final NotificationRepository notificationRepository;

    public EventService(final EventRepository eventRepository,
                        final SensorRepository sensorRepository,
                        final NotificationRepository notificationRepository) {
        this.eventRepository = eventRepository;
        this.sensorRepository = sensorRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<EventDTO> findAll() {
        final List<Event> events = eventRepository.findAll(Sort.by("id"));
        return events.stream()
                .map(event -> mapToDTO(event, new EventDTO()))
                .toList();
    }

    public EventDTO get(final Long id) {
        return eventRepository.findById(id)
                .map(event -> mapToDTO(event, new EventDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EventDTO eventDTO) {
        final Event event = new Event();
        mapToEntity(eventDTO, event);
        return eventRepository.save(event).getId();
    }

    public void update(final Long id, final EventDTO eventDTO) {
        final Event event = eventRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventDTO, event);
        eventRepository.save(event);
    }

    public void delete(final Long id) {
        eventRepository.deleteById(id);
    }

    private EventDTO mapToDTO(final Event event, final EventDTO eventDTO) {
        eventDTO.setId(event.getId());
        eventDTO.setTime(event.getTime());
        eventDTO.setEventType(event.getEventType());
        eventDTO.setDetails(event.getDetails());
        eventDTO.setSensor(event.getSensor() == null ? null : event.getSensor().getId());
        return eventDTO;
    }

    private Event mapToEntity(final EventDTO eventDTO, final Event event) {
        event.setTime(eventDTO.getTime());
        event.setEventType(eventDTO.getEventType());
        event.setDetails(eventDTO.getDetails());
        final Sensor sensor = eventDTO.getSensor() == null ? null : sensorRepository.findById(eventDTO.getSensor())
                .orElseThrow(() -> new NotFoundException("sensor not found"));
        event.setSensor(sensor);
        return event;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Event event = eventRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Notification eventNotification = notificationRepository.findFirstByEvent(event);
        if (eventNotification != null) {
            referencedWarning.setKey("event.notification.event.referenced");
            referencedWarning.addParam(eventNotification.getId());
            return referencedWarning;
        }
        return null;
    }

    // Lógica para generar eventos aleatorios cada 30 segundos
    @Scheduled(fixedRate = 30000)  // Ejecuta cada 30 segundos
    public void generateRandomEvent() {
        List<Sensor> sensors = sensorRepository.findAll();
        if (!sensors.isEmpty()) {
            // Selecciona un sensor aleatorio
            Sensor sensor = sensors.get(new Random().nextInt(sensors.size()));

            // Crea un nuevo evento para el sensor seleccionado
            Event event = new Event();
            event.setSensor(sensor);
            event.setTime(LocalDateTime.now());

            // Define el tipo de evento según el tipo de sensor
            // Define el tipo de evento según el tipo de sensor
            switch (sensor.getType()) {
                case MOTION:
                    event.setEventType(EventType.INTRUSION);  // Cambiado a EventType.INTRUSION_DETECTED
                    event.setDetails("Movimiento detectado en " + sensor.getLocation());
                    break;
                case TEMPERATURE:
                    event.setEventType(EventType.TEMPERATURE_CHANGE);  // Cambiado a EventType.TEMPERATURE_RISE
                    event.setDetails("Aumento de temperatura en " + sensor.getLocation());
                    break;
                case ACCESS:
                    event.setEventType(EventType.ACCESS_DENIED);  // Cambiado a EventType.UNAUTHORIZED_ACCESS
                    event.setDetails("Acceso no autorizado en " + sensor.getLocation());
                    break;
            }


            // Guarda el evento en la base de datos
            eventRepository.save(event);
            System.out.println("Evento generado: " + event.getEventType() + " en " + sensor.getLocation());
        }
    }

}
