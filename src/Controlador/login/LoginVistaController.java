package Controlador.login;

import Vista.login.LoginVista;
import Vista.home.Home;
import Modelo.Conexion;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Controlador para la vista de Login
 * 
 * @author ossca
 */
public class LoginVistaController {

    private LoginVista vista;
    private Connection conexion;

    public LoginVistaController(LoginVista vista) {
        this.vista = vista;
        Conexion conexionObj = new Conexion();
        this.conexion = conexionObj.getConxion();
        inicializarEventos();
    }

    private void inicializarEventos() {
        // Evento del botón Entrar
        vista.botonEntrada.addActionListener(this::iniciarSesion);

        // Evento del botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);

        // Evento Enter en el campo de contraseña
        vista.inputPassword.addActionListener(this::iniciarSesion);
    }

    /**
     * Inicia sesión validando las credenciales del usuario
     */
    private void iniciarSesion(ActionEvent e) {
        String nombre = vista.inputNombre.getText().trim();
        String password = vista.inputPassword.getText().trim();

        // Validar campos vacíos
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor ingrese su nombre de usuario",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            vista.inputNombre.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor ingrese su contraseña",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            vista.inputPassword.requestFocus();
            return;
        }

        // Validar credenciales en la base de datos
        String sql = "SELECT id_usuario, nombre, rol FROM usuarios WHERE nombre = ? AND password = ? AND estado = 'ACTIVO'";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Usuario encontrado y activo
                    String nombreUsuario = rs.getString("nombre");
                    String rol = rs.getString("rol");

                    // Cerrar ventana de login
                    vista.dispose();

                    // Abrir la ventana principal (Home)
                    Home home = new Home();
                    home.setVisible(true);

                    // Mostrar mensaje de bienvenida
                    JOptionPane.showMessageDialog(null,
                            "¡Bienvenido " + nombreUsuario + "!\nRol: " + rol,
                            "Inicio de Sesión Exitoso",
                            JOptionPane.INFORMATION_MESSAGE);

                } else {
                    // Usuario no encontrado o credenciales incorrectas
                    JOptionPane.showMessageDialog(vista,
                            "Nombre de usuario o contraseña incorrectos",
                            "Error de Autenticación",
                            JOptionPane.ERROR_MESSAGE);

                    // Limpiar campos y enfocar nombre
                    vista.inputNombre.setText("");
                    vista.inputPassword.setText("");
                    vista.inputNombre.requestFocus();
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error al conectar con la base de datos: " + ex.getMessage(),
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cierra la aplicación al presionar Cancelar
     */
    private void cancelar(ActionEvent e) {
        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de que desea salir de la aplicación?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
