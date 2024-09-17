package grupo1.caso_practico.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SensorDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private SensorType type;

    @NotNull
    @Size(max = 255)
    private String location;

    @NotNull
    private SensorStatus status;

}
