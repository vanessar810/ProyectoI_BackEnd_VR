package dh.backend.ClinicaOdontologicaVR.controller;


import dh.backend.ClinicaOdontologicaVR.model.Turno;
import dh.backend.ClinicaOdontologicaVR.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
         Turno turno = turnoService.buscarPorId(id);
         if(turno != null){
             return ResponseEntity.ok(turno);
         }else{
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
     }

        @PutMapping
        public ResponseEntity<String> modificarTurno(@RequestBody Turno turno) {
            Turno turnoModificar = turnoService.buscarPorId(turno.getId());
            if(turnoModificar != null){
                turnoService.actualizarTurno(turno);
                return ResponseEntity.ok("{\"message\": \"turno actualizado\"}");
            } else {
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }

     @DeleteMapping("/{id}")
     public ResponseEntity<String>  borrarTurno(@PathVariable Integer id) {
         Turno turnoBuscado = turnoService.buscarPorId(id);
         if(turnoBuscado!= null){
             turnoService.eliminarTurno(id);
             return ResponseEntity.ok("{\"message\": \"turno eliminado\"}");
         } else {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
     }
    }


