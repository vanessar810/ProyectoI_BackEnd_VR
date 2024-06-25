package dh.backend.ClinicaOdontologicaVR.repository;

import dh.backend.ClinicaOdontologicaVR.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {
}
