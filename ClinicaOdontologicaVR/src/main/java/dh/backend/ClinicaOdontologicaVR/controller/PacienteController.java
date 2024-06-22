package dh.backend.ClinicaOdontologicaVR.controller;

import dh.backend.ClinicaOdontologicaVR.model.Paciente;
import dh.backend.ClinicaOdontologicaVR.service.IPacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PacienteController {
    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/buscarPaciente")
    public String buscarPorId(Model model, @RequestParam Integer id){
       Paciente paciente = pacienteService.buscarPorId(id);

       model.addAttribute("nombre", paciente.getNombre());
       model.addAttribute("apellido", paciente.getApellido());
        return "index";
    }
}
