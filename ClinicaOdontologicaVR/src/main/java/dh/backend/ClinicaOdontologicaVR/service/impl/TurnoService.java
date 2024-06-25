package dh.backend.ClinicaOdontologicaVR.service.impl;

import dh.backend.ClinicaOdontologicaVR.entity.Odontologo;
import dh.backend.ClinicaOdontologicaVR.entity.Paciente;
import dh.backend.ClinicaOdontologicaVR.entity.Turno;
import dh.backend.ClinicaOdontologicaVR.exception.ResourceNotFoundException;
import dh.backend.ClinicaOdontologicaVR.repository.IOdontologoRepository;
import dh.backend.ClinicaOdontologicaVR.repository.IPacienteRepository;
import dh.backend.ClinicaOdontologicaVR.repository.ITurnoRepository;
import dh.backend.ClinicaOdontologicaVR.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
    private IOdontologoRepository odontologoRepository;
    private IPacienteRepository pacienteRepository;
    private ITurnoRepository turnoRepository;

    private ModelMapper modelMapper;

    public TurnoService(IOdontologoRepository odontologoRepository, IPacienteRepository pacienteRepository, ITurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.pacienteRepository = pacienteRepository;
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Turno registrar(Turno turno) {
        Paciente paciente = pacienteRepository.findById(turno.getPaciente_id());
        Odontologo odontologo = odontologoRepository.findById(turno.getOdontologo_id());
        Turno turnoARegistrar = new Turno();
        Turno turnoADevolver = null;
        if(paciente!=null && odontologo!=null){
            turnoARegistrar.setOdontologo(odontologo);
            turnoARegistrar.setPaciente(paciente);
            turnoARegistrar.setFecha(turno.getFecha());
            turnoADevolver = turnoRepository.save(turnoARegistrar);
        }
        return turnoADevolver;
    }

    @Override
    public Turno buscarPorId(Integer id) {
        Optional<Turno> turnoOptional = turnoRepository.findById(id);
        if(turnoOptional.isPresent()){
            Turno turnoEncontrado = turnoOptional.get();
            Turno turnoADevolver = mapToResponseDto(turnoEncontrado);
            return turnoADevolver;
        }
        return null;
    }
    @Override
    public List<Turno> buscarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<Turno> turnosADevolver = new ArrayList<>();
        Turno turnoAuxiliar = null;
        for(Turno turno: turnos){
            turnoAuxiliar = mapToResponseDto(turno);
            turnosADevolver.add(turnoAuxiliar);
        }
        return turnosADevolver;
    }

    @Override
    public void actualizarTurno(Turno turno) {

    }

    @Override
    public void actualizarTurno(Integer id, Turno turno) {
        Optional<Paciente> paciente = pacienteRepository.findById(turno.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoRepository.findById(turno.getOdontologo_id());
        Optional<Turno> turno = turnoRepository.findById(id);
        Turno turnoAModificar = new Turno();
        if(paciente.isPresent() && odontologo.isPresent() && turno.isPresent()){
            turnoAModificar.setId(id);
            turnoAModificar.setOdontologo(odontologo.get());
            turnoAModificar.setPaciente(paciente.get());
            turnoAModificar.setFecha(LocalDate.parse(turno.getFecha()));
            turnoRepository.save(turnoAModificar);
        }
    }
    @Override
    public void eliminarTurno(Integer id) throws ResourceNotFoundException {
        Turno turnoResponseDto = buscarPorId(id);
        if(turnoResponseDto !=null)
            turnoRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("{\"message\": \"turno no encontrado\"}");
    }

    @Override
    public List<Turno> buscarTurnoEntreFechas(LocalDate startDate, LocalDate endDate) {
        List<Turno> listadoTurnos = turnoRepository.buscarTurnoEntreFechas(startDate, endDate);
        List<Turno> listadoARetornar = new ArrayList<>();
        Turno turnoAuxiliar = null;
        for (Turno turno : listadoTurnos) {
            turnoAuxiliar = mapToResponseDto(turno);
            listadoARetornar.add(turnoAuxiliar);
        }
        return listadoARetornar;
    }

    // metodo que mapea turno en turnoResponseDto
    private Turno mapToResponseDto(Turno turno){
        Turno turnoResponseDto = modelMapper.map(turno, Turno.class);
        turnoResponseDto.setOdontologo(modelMapper.map(turno.getOdontologo(), Odontologo.class));
        turnoResponseDto.setPaciente(modelMapper.map(turno.getPaciente(), Paciente.class));
        return  turnoResponseDto;
    }
}
