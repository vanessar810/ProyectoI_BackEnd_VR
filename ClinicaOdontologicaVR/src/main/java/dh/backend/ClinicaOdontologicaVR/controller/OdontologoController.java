package dh.backend.ClinicaOdontologicaVR.controller;

import dh.backend.ClinicaOdontologicaVR.model.Odontologo;
import dh.backend.ClinicaOdontologicaVR.service.IOdontologoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OdontologoController {
    private IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @GetMapping("/buscarOdontologo")
    public String buscarPorId(Model model, @RequestParam Integer id) {
        Odontologo odontologo = odontologoService.buscarOdontologo(id);

        model.addAttribute("nombre", odontologo.getNombre());
        model.addAttribute("apellido", odontologo.getApellido());
        return "index";
    }
}


