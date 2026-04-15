package Modelo.usuarios;

import Modelo.Conexion;
import Type.usuarios.UsuarioType;
import Type.usuarios.PrivilegioUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class UsuarioModel extends Conexion {

    PreparedStatement preparedStatement = null;
    Connection connection;
    String sentenciaSQL;
    ResultSet resultSet;
    UsuarioType usuario;

    /**
     * Crea un nuevo registro de usuario
     * 
     * @param objeto - Objeto UsuarioType con los datos a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean create(UsuarioType objeto) {
        connection = getConxion();
        sentenciaSQL = "INSERT INTO usuarios (id_usuario, nombre_usuario, user_usuario, pass_usuario, privilegio_usuario, foto_usuario, estado_usuario) VALUES (?,?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdUsuario());
            preparedStatement.setString(2, objeto.getNombreUsuario());
            preparedStatement.setString(3, objeto.getUserUsuario());
            preparedStatement.setString(4, objeto.getPassUsuario());
            preparedStatement.setString(5, objeto.getPrivilegioUsuario().toString());
            preparedStatement.setString(6, objeto.getFotoUsuario());
            preparedStatement.setBoolean(7, objeto.isEstadoUsuario());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(UsuarioModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return false;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de crear

    /**
     * Obtiene un registro específico por su ID
     * 
     * @param id - Identificador del registro
     * @return Objeto UsuarioType o null si no existe
     */
    public UsuarioType getById(String id) {
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM usuarios WHERE id_usuario = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id_usuario = resultSet.getString(1);
                String nombre_usuario = resultSet.getString(2);
                String user_usuario = resultSet.getString(3);
                String pass_usuario = resultSet.getString(4);
                String privilegio_usuario = resultSet.getString(5);
                String foto_usuario = resultSet.getString(6);
                java.sql.Timestamp fecha_creacion = resultSet.getTimestamp(7);
                boolean estado_usuario = resultSet.getBoolean(8);

                UsuarioType usuarioBuscado = new UsuarioType();

                usuarioBuscado.setIdUsuario(id_usuario);
                usuarioBuscado.setNombreUsuario(nombre_usuario);
                usuarioBuscado.setUserUsuario(user_usuario);
                usuarioBuscado.setPassUsuario(pass_usuario);
                usuarioBuscado.setPrivilegioUsuario(PrivilegioUsuario.valueOf(privilegio_usuario));
                usuarioBuscado.setFotoUsuario(foto_usuario);
                usuarioBuscado.setFechaCreacion(fecha_creacion);
                usuarioBuscado.setEstadoUsuario(estado_usuario);
                return usuarioBuscado;
            }

            return null;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer" + e.getMessage());
            Logger.getLogger(UsuarioModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return null;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de getById

    /**
     * Obtiene todos los registros de la tabla
     * 
     * @return ArrayList con todos los objetos
     */
    public ArrayList<UsuarioType> getAll() {
        ArrayList<UsuarioType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM usuarios WHERE estado_usuario = 1";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_usuario = resultSet.getString(1);
                String nombre_usuario = resultSet.getString(2);
                String user_usuario = resultSet.getString(3);
                String pass_usuario = resultSet.getString(4);
                String privilegio_usuario = resultSet.getString(5);
                String foto_usuario = resultSet.getString(6);
                java.sql.Timestamp fecha_creacion = resultSet.getTimestamp(7);
                boolean estado_usuario = resultSet.getBoolean(8);

                UsuarioType usuario = new UsuarioType();

                usuario.setIdUsuario(id_usuario);
                usuario.setNombreUsuario(nombre_usuario);
                usuario.setUserUsuario(user_usuario);
                usuario.setPassUsuario(pass_usuario);
                usuario.setPrivilegioUsuario(PrivilegioUsuario.valueOf(privilegio_usuario));
                usuario.setFotoUsuario(foto_usuario);
                usuario.setFechaCreacion(fecha_creacion);
                usuario.setEstadoUsuario(estado_usuario);
                lista.add(usuario);

            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos" + e.getMessage());
        }
        return lista;
    }// Fin de getAll

    /**
     * Actualiza un registro existente
     * 
     * @param objeto - Objeto UsuarioType con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(UsuarioType objeto) {
        connection = getConxion();
        sentenciaSQL = "UPDATE usuarios SET nombre_usuario=?, user_usuario=?, pass_usuario=?, privilegio_usuario=?, foto_usuario=?, estado_usuario=? WHERE id_usuario=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getNombreUsuario());
            preparedStatement.setString(2, objeto.getUserUsuario());
            preparedStatement.setString(3, objeto.getPassUsuario());
            preparedStatement.setString(4, objeto.getPrivilegioUsuario().toString());
            preparedStatement.setString(5, objeto.getFotoUsuario());
            preparedStatement.setBoolean(6, objeto.isEstadoUsuario());
            preparedStatement.setString(7, objeto.getIdUsuario());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(UsuarioModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return false;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de update

    /**
     * Elimina un registro por su ID (eliminación lógica)
     * 
     * @param id - Identificador del registro a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean delete(String id) {
        connection = getConxion();
        sentenciaSQL = "UPDATE usuarios SET estado_usuario = 0 WHERE id_usuario=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(UsuarioModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return false;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de delete

    /**
     * Autentica un usuario en el sistema
     * 
     * @param usuario  - Nombre de usuario
     * @param password - Contraseña del usuario
     * @return Objeto UsuarioType si las credenciales son correctas, null si no
     */
    public UsuarioType auth(String usuario, String password) {
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM usuarios WHERE user_usuario = ? AND pass_usuario = ? AND estado_usuario = true";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String id_usuario = resultSet.getString(1);
                String nombre_usuario = resultSet.getString(2);
                String user_usuario = resultSet.getString(3);
                String pass_usuario = resultSet.getString(4);
                String privilegio_usuario = resultSet.getString(5);
                String foto_usuario = resultSet.getString(6);
                java.sql.Timestamp fecha_creacion = resultSet.getTimestamp(7);
                boolean estado_usuario = resultSet.getBoolean(8);

                UsuarioType usuarioAutenticado = new UsuarioType();
                usuarioAutenticado.setIdUsuario(id_usuario);
                usuarioAutenticado.setNombreUsuario(nombre_usuario);
                usuarioAutenticado.setUserUsuario(user_usuario);
                usuarioAutenticado.setPassUsuario(pass_usuario);
                usuarioAutenticado.setPrivilegioUsuario(PrivilegioUsuario.valueOf(privilegio_usuario));
                usuarioAutenticado.setFotoUsuario(foto_usuario);
                usuarioAutenticado.setFechaCreacion(fecha_creacion);
                usuarioAutenticado.setEstadoUsuario(estado_usuario);
                return usuarioAutenticado;
            }

            return null;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en autenticación" + e.getMessage());
            Logger.getLogger(UsuarioModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return null;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de auth
}
