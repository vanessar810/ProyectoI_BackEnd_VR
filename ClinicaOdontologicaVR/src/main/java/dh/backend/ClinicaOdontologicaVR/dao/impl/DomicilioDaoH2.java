package dh.backend.ClinicaOdontologicaVR.dao.impl;

import dh.backend.ClinicaOdontologicaVR.dao.IDao;
import dh.backend.ClinicaOdontologicaVR.db.H2Connection;
import dh.backend.ClinicaOdontologicaVR.model.Domicilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class DomicilioDaoH2 implements IDao<Domicilio> {
    private static Logger LOGGER = LoggerFactory.getLogger(DomicilioDaoH2.class);
    private static String SQL_INSERT = "INSERT INTO DOMICILIOS VALUES (DEFAULT,?,?,?,?)";
    private static String SQL_SELECT_ID = "SELECT * FROM DOMICILIOS WHERE ID = ?";
    private static String SQL_UPDATE = "UPDATE DOMICILIOS SET CALLE=?, NUMERO=?, LOCALIDAD=?, PROVINCIA=? WHERE ID=?";
    private static String SQL_DELETE = "DELETE FROM DOMICILICIOS WHERE ID=?";
    @Override
    public Domicilio registrar(Domicilio domicilio) {
        Connection connection = null;
        Domicilio domilicioARetornar = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, domicilio.getCalle());
            preparedStatement.setInt(2, domicilio.getNumero());
            preparedStatement.setString(3, domicilio.getLocalidad());
            preparedStatement.setString(4, domicilio.getProvincia());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                domilicioARetornar = new Domicilio(id, domicilio.getCalle(), domicilio.getNumero(),
                        domicilio.getLocalidad(), domicilio.getProvincia());
            }
            LOGGER.info("Domicilio persistido: " + domilicioARetornar);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    LOGGER.info(ex.getMessage());
                    ex.printStackTrace();
                }
            }
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }
        }

        return domilicioARetornar;
    }

    @Override
    public Domicilio buscarPorId(Integer id) {
        Connection connection = null;
        Domicilio domicilioEncontrado = null;
        try {
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer idEncontrado = resultSet.getInt(1);
                String calle = resultSet.getString(2);
                Integer numero = resultSet.getInt(3);
                String localidad = resultSet.getString(4);
                String provincia = resultSet.getString(5);
                domicilioEncontrado = new Domicilio(idEncontrado, calle, numero, localidad, provincia);
            }
            LOGGER.info("domicilio encontrado: " + domicilioEncontrado);

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }
        }
        return domicilioEncontrado;
    }

    @Override
    public List<Domicilio> buscarTodos() {
        return null;
    }

    @Override
    public void actualizar(Domicilio domicilio) {
        Connection connection = null;

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, domicilio.getCalle());
            preparedStatement.setInt(2, domicilio.getNumero());
            preparedStatement.setString(3, domicilio.getLocalidad());
            preparedStatement.setString(4, domicilio.getProvincia());
            preparedStatement.setInt(5, domicilio.getId());
            preparedStatement.executeUpdate();

            LOGGER.info("Domicilio actualizado: ");
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    LOGGER.info(ex.getMessage());
                    ex.printStackTrace();
                }
            }
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        } finally {
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
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            LOGGER.info("Domicilio eliminado: ");
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    LOGGER.info(ex.getMessage());
                    ex.printStackTrace();
                }
            }
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
