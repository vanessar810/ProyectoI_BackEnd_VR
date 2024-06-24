package dh.backend.ClinicaOdontologicaVR.service;

import dh.backend.ClinicaOdontologicaVR.model.Odontologo;

import java.util.List;

public interface IOdontologoService {
    Odontologo registrarOdontologo(Odontologo odontologo);

    Odontologo buscarOdontologo(Integer id);

    List<Odontologo> buscarTodos();
    void actualizarOdontologo(Odontologo odontologo);
    void eliminarOdontologo(Integer id);
}
