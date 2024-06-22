package dh.backend.ClinicaOdontologicaVR.dao.impl;

import dh.backend.ClinicaOdontologicaVR.dao.IDao;
import dh.backend.ClinicaOdontologicaVR.db.H2Connection;
import dh.backend.ClinicaOdontologicaVR.model.Domicilio;
import dh.backend.ClinicaOdontologicaVR.model.Odontologo;
import dh.backend.ClinicaOdontologicaVR.model.Paciente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

    @Component
    public class OdontologoDaoH2 implements IDao<Odontologo> {
        private static Logger LOGGER = LoggerFactory.getLogger(OdontologoDaoH2.class);
        private static String SQL_INSERT = "INSERT INTO ODONTOLOGOS VALUES (DEFAULT,?,?,?)";
        private static String SQL_SELECT_ID = "SELECT * FROM ODONTOLOGOS WHERE ID=?";
        private static String SQL_SELECT_ALL = "SELECT * FROM ODONTOLOGOS";
        @Override
        public Odontologo registrar(Odontologo odontologo) {
            Connection connection = null;
            Odontologo odontologoARetornar = null;
            try{
                connection = H2Connection.getConnection();
                connection.setAutoCommit(false);

                //Domicilio domicilioGuardado = domicilioDaoH2.registrar(paciente.getDomicilio());

                PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, odontologo.getNombre());
                preparedStatement.setString(2, odontologo.getApellido());
                preparedStatement.setString(3, odontologo.getMatricula());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()){
                    Integer id= resultSet.getInt(1);
                    odontologoARetornar = new Odontologo(id, odontologo.getNombre(), odontologo.getApellido(),
                            odontologo.getMatricula());
                }
                LOGGER.info("Odontologo guardado: "+ odontologoARetornar);

                connection.commit();
                connection.setAutoCommit(true);
            }catch (Exception e){
                if(connection!=null){
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        LOGGER.info(ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.info(e.getMessage());
                    e.printStackTrace();
                }
            }

            return odontologoARetornar;
        }

        @Override
        public Odontologo buscarPorId(Integer id) {
            Connection connection = null;
            Odontologo odontologoEncontrado = null;

            try {
                connection = H2Connection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ID);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                    Integer idDevuelto = resultSet.getInt(1);
                    String nombre = resultSet.getString(2);
                    String apellido = resultSet.getString(3);
                    String matricula = resultSet.getString(4);
                    odontologoEncontrado = new Odontologo(idDevuelto, nombre, apellido, matricula);
                }
                LOGGER.info("paciente encontrado: "+ odontologoEncontrado);

            }catch (Exception e){
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.info(e.getMessage());
                    e.printStackTrace();
                }
            }
            return odontologoEncontrado;
        }

        @Override
        public List<Odontologo> buscarTodos() {
            List<Odontologo> odontologos = new ArrayList<>();
            Connection connection = null;
            try{
                connection = H2Connection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
                while (resultSet.next()){
                    Integer idDevuelto = resultSet.getInt(1);
                    String apellido = resultSet.getString(2);
                    String nombre = resultSet.getString(3);
                    String matricula = resultSet.getString(4);
                    Odontologo odontologo = new Odontologo(idDevuelto, apellido, nombre,matricula);

                    LOGGER.info("Odontologo listado: "+ odontologo);
                    odontologos.add(odontologo);
                }

            }catch (Exception e){
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.info(e.getMessage());
                    e.printStackTrace();
                }
            }
            return odontologos;
}
    }
