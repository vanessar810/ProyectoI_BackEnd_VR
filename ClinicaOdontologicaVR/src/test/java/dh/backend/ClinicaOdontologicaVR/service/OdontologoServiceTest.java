package dh.backend.ClinicaOdontologicaVR.service;

import dh.backend.ClinicaOdontologicaVR.dao.impl.OdontologoDaoH2;
import dh.backend.ClinicaOdontologicaVR.model.Odontologo;
import dh.backend.ClinicaOdontologicaVR.service.impl.OdontologoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OdontologoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceTest.class);
    private static OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());
    @BeforeAll
    static void crearTablas(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/clinicaMVC2805;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Testear que un odontologo fue guardado")
    void testOdontologoGuardado(){
        Odontologo odontologo = new Odontologo("Alejandro","Fula","M001");
        Odontologo odontologoDesdeLaBD = odontologoService.registrarOdontologo(odontologo);

        assertNotNull(odontologoDesdeLaBD);
    }

    @Test
    @DisplayName("Testear busqueda odontologo por id")
    void testOdontologoPorId(){
        Integer id = 1;
        Odontologo OdontologoEncontrado = odontologoService.buscarOdontologo(id);
        assertEquals(id, OdontologoEncontrado.getId());
    }

    @Test
    @DisplayName("Testear busqueda todos los odontologos")
    void testBusquedaTodos() {
        Odontologo odontologo = new Odontologo("Alejandro","Fula","M001");
        odontologoService.registrarOdontologo(odontologo);

        List<Odontologo> odontologos = odontologoService.buscarTodos();

        assertTrue(odontologos.size()!=0);

    }
}
