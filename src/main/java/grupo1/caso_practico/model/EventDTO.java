package grupo1.caso_practico.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EventDTO {

    private Long id;

    @NotNull
    private LocalDateTime time;

    @NotNull
    private EventType eventType;

    @NotNull
    @Size(max = 255)
    private String details;

    private Long sensor;

}
