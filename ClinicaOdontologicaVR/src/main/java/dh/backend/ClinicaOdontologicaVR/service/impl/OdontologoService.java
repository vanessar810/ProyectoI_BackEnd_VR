package dh.backend.ClinicaOdontologicaVR.service.impl;

import dh.backend.ClinicaOdontologicaVR.dao.IDao;
import dh.backend.ClinicaOdontologicaVR.model.Odontologo;
import dh.backend.ClinicaOdontologicaVR.model.Paciente;
import dh.backend.ClinicaOdontologicaVR.service.IOdontologoService;
import dh.backend.ClinicaOdontologicaVR.service.IPacienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {

    private IDao<Odontologo> odontologoIDao;

    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }
    public Odontologo registrarOdontologo(Odontologo odontologo) {
        return odontologoIDao.registrar(odontologo);
    }

    public Odontologo buscarOdontologo(Integer id) {
        return odontologoIDao.buscarPorId(id);
    }
    public List<Odontologo> buscarTodos() {
        return odontologoIDao.buscarTodos();
    }

    @Override
    public void actualizarOdontologo(Odontologo odontologo) {
     odontologoIDao.actualizar(odontologo);
    }
    @Override
    public void eliminarOdontologo(Integer id) {
  odontologoIDao.eliminar(id);
    }


}

