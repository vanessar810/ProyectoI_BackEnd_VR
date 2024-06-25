package dh.backend.ClinicaOdontologicaVR.controller;

import dh.backend.ClinicaOdontologicaVR.model.Paciente;
import dh.backend.ClinicaOdontologicaVR.service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
//public class PacienteController {
//    private IPacienteService pacienteService;
//
//    public PacienteController(IPacienteService pacienteService) {
//        this.pacienteService = pacienteService;
//    }
//
//    @GetMapping("/buscarPaciente")
//    public String buscarPorId(Model model, @RequestParam Integer id){
//       Paciente paciente = pacienteService.buscarPorId(id);
//
//       model.addAttribute("nombre", paciente.getNombre());
//       model.addAttribute("apellido", paciente.getApellido());
//        return "index";
//    }
//}
@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente) {
        Paciente pacienteAretornar = pacienteService.registrarPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteAretornar);
    }

    @GetMapping
    public ResponseEntity<List<Paciente>>  buscarTodos(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Integer id){
        Paciente paciente = pacienteService.buscarPorId(id);
        if(paciente != null){
            return ResponseEntity.ok(paciente);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    public ResponseEntity<String>  actualizarPaciente(@RequestBody Paciente paciente){
     Paciente pacienteBuscado = pacienteService.buscarPorId(paciente.getId());
        if(pacienteBuscado != null) {
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok("{\"message\": \"paciente actualizado\"}");
        }  else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>  borrarPaciente(@PathVariable Integer id) {
       Paciente pacienteBuscado = pacienteService.buscarPorId(id);
       if (pacienteBuscado != null){
           return ResponseEntity.ok("{\"message\": \"paciente eliminado\"}");
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
    }
    }

