package grupo1.caso_practico.rest;

import grupo1.caso_practico.model.AccessLogDTO;
import grupo1.caso_practico.service.AccessLogService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/accessLogs", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccessLogResource {

    private final AccessLogService accessLogService;

    public AccessLogResource(final AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    @GetMapping
    public ResponseEntity<List<AccessLogDTO>> getAllAccessLogs() {
        return ResponseEntity.ok(accessLogService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessLogDTO> getAccessLog(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(accessLogService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAccessLog(
            @RequestBody @Valid final AccessLogDTO accessLogDTO) {
        final Long createdId = accessLogService.create(accessLogDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAccessLog(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final AccessLogDTO accessLogDTO) {
        accessLogService.update(id, accessLogDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAccessLog(@PathVariable(name = "id") final Long id) {
        accessLogService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
