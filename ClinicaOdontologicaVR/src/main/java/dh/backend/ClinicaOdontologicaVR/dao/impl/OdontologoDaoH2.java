package dh.backend.ClinicaOdontologicaVR.dao.impl;

import dh.backend.ClinicaOdontologicaVR.dao.IDao;
import dh.backend.ClinicaOdontologicaVR.db.H2Connection;
import dh.backend.ClinicaOdontologicaVR.model.Odontologo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
    @Component
    public class OdontologoDaoH2 implements IDao<Odontologo> {
        private static Logger LOGGER = LoggerFactory.getLogger(OdontologoDaoH2.class);
        private static String INSERT = "INSERT INTO ODONTOLOGOS VALUES (DEFAULT,?,?,?)";
        private static String BUSCAR = "SELECT * FROM ODONTOLOGOS WHERE ID = ?";
        private static String BUSCAR_ALL = "SELECT * FROM ODONTOLOGOS";
        private static String UPDATE = "UPDATE ODONTOLOGOS SET NOMBRE=?, APELLIDO=?, MATRICULA=? WHERE ID=?";
        private static String DELETE = "DELETE FROM ODONTOLOGOS WHERE ID=?";
        @Override
        public Odontologo registrar(Odontologo odontologo) {
            Connection connection = null;
            Odontologo odontologoARetornar = null;
            try{
                connection = H2Connection.getConnection();
                connection.setAutoCommit(false);

                //Domicilio domicilioGuardado = domicilioDaoH2.registrar(paciente.getDomicilio());

                PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, odontologo.getNombre());
                preparedStatement.setString(2, odontologo.getApellido());
                preparedStatement.setString(3, odontologo.getMatricula());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()){
                    int id= resultSet.getInt(1);
                    odontologoARetornar = new Odontologo(id, odontologo.getNombre(), odontologo.getApellido(),
                            odontologo.getMatricula());
                }
                LOGGER.info("Odontologo guardado: "+ odontologoARetornar);
                connection.commit();
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
                PreparedStatement preparedStatement = connection.prepareStatement(BUSCAR);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    odontologoEncontrado = crearOdontologo(resultSet);
                }
                LOGGER.info("Odontologo encontrado: "+odontologoEncontrado);

            } catch (ClassNotFoundException e) {
                LOGGER.error(e.getMessage());
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            return odontologoEncontrado;
        }

        @Override
        public List<Odontologo> buscarTodos() {
            Connection connection = null;
            List<Odontologo> odontologos = new ArrayList<>();
            Odontologo odontologoObtenido = null;
            try{
                connection = H2Connection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(BUSCAR_ALL);
                while (resultSet.next()){
                    odontologoObtenido = crearOdontologo(resultSet);
                    odontologos.add(odontologoObtenido);
                    LOGGER.info("Agregando : "+odontologoObtenido);
                }

            } catch (ClassNotFoundException e) {
                LOGGER.error(e.getMessage());
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            return odontologos;
}

        @Override
        public void actualizar(Odontologo odontologo) {
            Connection connection = null;

            try{
                connection = H2Connection.getConnection();
                connection.setAutoCommit(false);

                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
                preparedStatement.setString(1, odontologo.getNombre());
                preparedStatement.setString(2, odontologo.getApellido());
                preparedStatement.setString(3, odontologo.getMatricula());
                preparedStatement.setInt(4, odontologo.getId());
                preparedStatement.executeUpdate();

                LOGGER.info("Odontologo actualizado: ");

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
        }
        @Override
        public void eliminar(Integer id) {
            Connection connection = null;

            try{
                connection = H2Connection.getConnection();
                connection.setAutoCommit(false);

                PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

                LOGGER.info("Odontologo eliminado: ");

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
        }
        private Odontologo crearOdontologo(ResultSet resultSet) throws SQLException {
            int id = resultSet.getInt(1);
            String nombre = resultSet.getString(2);
            String apellido = resultSet.getString(3);
            String matricula = resultSet.getString(4);
            Odontologo odontologo = new Odontologo(id,nombre,apellido, matricula);
            return odontologo;
        }
    }
