package dh.backend.ClinicaOdontologicaVR.controller;


import dh.backend.ClinicaOdontologicaVR.entity.Odontologo;
import dh.backend.ClinicaOdontologicaVR.service.IOdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }
    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoService.registrarOdontologo(odontologo));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarPorId(@PathVariable Integer id){
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologo(id);
        if(odontologo.isPresent()){
            Odontologo odontologoARetornar = odontologo.get();
            return ResponseEntity.ok(odontologoARetornar);
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }
    @PutMapping
    public ResponseEntity<String>  actualizarOdontologo(@RequestBody Odontologo odontologo){
        Optional<Odontologo> odontologoOptional = odontologoService.buscarOdontologo(odontologo.getId());
        if(odontologoOptional.isPresent()) {
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("{\"message\": \"odontologo actualizado\"}");
        }  else {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Integer id){
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(id);
        if(odontologoBuscado.isPresent()){
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("{\"message\": \"odontologo eliminado\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Odontologo>> buscarPorApellido(@PathVariable String apellido){
        List<Odontologo> listaOdontologos =odontologoService.buscarPorApellido(apellido);
        if(listaOdontologos.size()>0){
            return ResponseEntity.ok(listaOdontologos);
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Odontologo>> buscarTodos(@PathVariable String nombre){
        return ResponseEntity.ok(odontologoService.buscarPorNombre(nombre));
    }
}


