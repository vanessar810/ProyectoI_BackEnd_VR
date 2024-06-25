package dh.backend.ClinicaOdontologicaVR.service;


import dh.backend.ClinicaOdontologicaVR.entity.Paciente;
import dh.backend.ClinicaOdontologicaVR.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    Paciente registrarPaciente(Paciente paciente);

    Optional<Paciente> buscarPorId(Integer id);
    List<Paciente> buscarTodos();
    void actualizarPaciente(Paciente paciente);
    void eliminarPaciente(Integer id) throws ResourceNotFoundException;

}
