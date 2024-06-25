package dh.backend.ClinicaOdontologicaVR.service.impl;

import dh.backend.ClinicaOdontologicaVR.entity.Odontologo;
import dh.backend.ClinicaOdontologicaVR.exception.ResourceNotFoundException;
import dh.backend.ClinicaOdontologicaVR.repository.IOdontologoRepository;
import dh.backend.ClinicaOdontologicaVR.service.IOdontologoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {

    private IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo registrarOdontologo(Odontologo odontologo) {return odontologoRepository.save(odontologo);}

    public Optional<Odontologo> buscarOdontologo(Integer id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoOptional = odontologoRepository.findById(id);
        if(odontologoOptional.isEmpty()) throw new ResourceNotFoundException("Odontologo no encontrado");
        return odontologoOptional;
    }

    @Override
    public List<Odontologo> buscarTodos() { return odontologoRepository.findAll();}

    @Override
    public void actualizarOdontologo(Odontologo odontologo) {
     odontologoRepository.save(odontologo);
    }
    @Override
    public void eliminarOdontologo(Integer id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoOptional = buscarOdontologo(id);
        if(odontologoOptional.isPresent())
            odontologoRepository.deleteById(id);
        else throw new ResourceNotFoundException("{\"message\": \"odontologo no encontrado\"}");
    }

    @Override
    public List<Odontologo> buscarPorApellido(String apellido) {
        return odontologoRepository.buscarPorApellido(apellido);
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) {
        return odontologoRepository.findByNombreLike(nombre);
    }

}

