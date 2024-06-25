package dh.backend.ClinicaOdontologicaVR.controller;


import dh.backend.ClinicaOdontologicaVR.entity.Turno;
import dh.backend.ClinicaOdontologicaVR.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
 @RequestMapping("/turno")
 public class TurnoController{
        private ITurnoService turnoService;

        public TurnoController(ITurnoService turnoService) {
            this.turnoService = turnoService;
        }

        @PostMapping
        public ResponseEntity<Turno> agregarTurno(@RequestBody Turno turno) {
            Turno turnoADevolver = turnoService.registrar(turno);
            if (turnoADevolver == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(turnoADevolver);
            }
        }

        @GetMapping
        public ResponseEntity<List<Turno>> buscarTodosTurnos() {
            return ResponseEntity.ok(turnoService.buscarTodos());
        }

     @GetMapping("/{id}")
     public ResponseEntity<Turno> buscarTurnoPorId(@PathVariable Integer id){
         Optional<Turno> turno = turnoService.buscarPorId(id);
         if(turno.isPresent()){
             return ResponseEntity.ok(turno.get());
         }else{
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
     }

        @PutMapping("/{id}")
        public ResponseEntity<String> modificarTurno(@PathVariable Integer id, @RequestBody Turno turno){
            turnoService.actualizarTurno(id, turno);
            return ResponseEntity.ok("{\"message\": \"turno modificado\"}");
        }

     @DeleteMapping("/{id}")
     public ResponseEntity<String>  borrarTurno(@PathVariable Integer id) {
         Optional<Turno> turnoBuscado = turnoService.buscarPorId(id);
         if(turnoBuscado.isPresent()){
             turnoService.eliminarTurno(id);
             return ResponseEntity.ok("{\"message\": \"turno eliminado\"}");
         } else {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
     }
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @GetMapping("/fechas")
    public ResponseEntity<List<Turno>> buscarEntreFechas(@RequestParam String inicio, @RequestParam String fin){
        LocalDate fechaInicio = LocalDate.parse(inicio, formatter);
        LocalDate fechaFinal = LocalDate.parse(fin, formatter);

        return ResponseEntity.ok(turnoService.buscarTurnoEntreFechas(fechaInicio, fechaFinal));
    }
    }


