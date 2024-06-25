package dh.backend.ClinicaOdontologicaVR.service;


import dh.backend.ClinicaOdontologicaVR.entity.Turno;
import dh.backend.ClinicaOdontologicaVR.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITurnoService {
    Turno registrar(Turno turno);

    Turno buscarPorID(Integer id);
    Optional<Turno> buscarPorId(Integer id);
    List<Turno> buscarTodos();
    void actualizarTurno(Integer id, Turno turno);
    void eliminarTurno(Integer id) throws ResourceNotFoundException;
    // HQL
    List<Turno> buscarTurnoEntreFechas(LocalDate startDate, LocalDate endDate);
}
