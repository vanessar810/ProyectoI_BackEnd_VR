package dh.backend.ClinicaOdontologicaVR.service;


import dh.backend.ClinicaOdontologicaVR.entity.Odontologo;
import dh.backend.ClinicaOdontologicaVR.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {
    Odontologo registrarOdontologo(Odontologo odontologo) throws ResourceNotFoundException;

   Optional<Odontologo> buscarOdontologo(Integer id) throws ResourceNotFoundException;
    List<Odontologo> buscarTodos();
    void actualizarOdontologo(Odontologo odontologo);
    void eliminarOdontologo(Integer id) throws ResourceNotFoundException;
    // Metodos con HQL
    List<Odontologo> buscarPorApellido(String apellido);
    List<Odontologo> buscarPorNombre(String nombre);
}
