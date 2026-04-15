package Controlador;

import Vista.home.Home;
import Vista.login.LoginVista;
import Vista.usuarios.UsuariosVista;
import Vista.proveedores.proveedoresVista;
import Vista.clientes.clientesVista;
import Vista.decoraciones.decoracionesVista;
import Vista.colecciones.coleccionesVista;
import Vista.compras.comprasVista;
import Vista.inventario.inventarioVista;
import Vista.ventas.ventasVista;
import Type.usuarios.PrivilegioUsuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class HomeController {
    private static final Logger logger = Logger.getLogger(HomeController.class.getName());
    Home home;
    LoginVista login;
    UsuariosVista usuariosVista;
    proveedoresVista proveedoresVista;
    clientesVista clientesVista;
    decoracionesVista decoracionesVista;
    coleccionesVista coleccionesVista;
    comprasVista comprasVista;
    inventarioVista inventarioVista;
    ventasVista ventasVista;
    private PrivilegioUsuario privilegioUsuarioActual;

    public HomeController(Home home, LoginVista login, UsuariosVista usuariosVista,
            clientesVista clientesVista, coleccionesVista coleccionesVista,
            comprasVista comprasVista, decoracionesVista decoracionesVista,
            inventarioVista inventarioVista, proveedoresVista proveedoresVista,
            ventasVista ventasVista) {
        this.home = home;
        this.login = login;
        this.usuariosVista = usuariosVista;

        // Recibir vistas (no crear nuevas)
        this.clientesVista = clientesVista;
        this.coleccionesVista = coleccionesVista;
        this.comprasVista = comprasVista;
        this.decoracionesVista = decoracionesVista;
        this.inventarioVista = inventarioVista;
        this.proveedoresVista = proveedoresVista;
        this.ventasVista = ventasVista;

        // Agregar listeners para volver al Home cuando se cierren las vistas
        agregarListenersVistas();

        // Los controladores específicos se crean en el punto de entrada
    }

    /**
     * Establece el privilegio del usuario actual para controlar el acceso a módulos
     * 
     * @param privilegioUsuarioActual Privilegio del usuario que ha iniciado sesión
     */
    public void setPrivilegioUsuarioActual(PrivilegioUsuario privilegioUsuarioActual) {
        this.privilegioUsuarioActual = privilegioUsuarioActual;
    }

    public void iniciar() {
        home.setVisible(true);
    }

    public void cargarBotones() {
        home.botonIrAUsuarios.addActionListener(e -> irAUsuarios());
        home.botonIrAProveedores.addActionListener(e -> irAProveedores());
        home.botonIrAClientes.addActionListener(e -> irAClientes());
        home.botonIrAProductos.addActionListener(e -> irADecoraciones());
        home.botonirAColecciones.addActionListener(e -> irAColecciones());
        home.botonirACompras.addActionListener(e -> irACompras());
        home.botonIrAInventario.addActionListener(e -> irAInventario());
        home.botonIrAVentas.addActionListener(e -> irAVentas());
    }

    public void irAUsuarios() {
        if (validarAccesoModulo(PrivilegioUsuario.ADMIN, "usuarios")) {
            usuariosVista.setVisible(true);
            home.setVisible(false);
        }
    }

    public void irAProveedores() {
        if (validarAccesoModulo(PrivilegioUsuario.ADMIN, "proveedores")) {
            proveedoresVista.setVisible(true);
            home.setVisible(false);
        }
    }

    public void irAClientes() {
        if (validarAccesoModulo(PrivilegioUsuario.VENTAS, "clientes")) {
            clientesVista.setVisible(true);
            home.setVisible(false);
        }
    }

    public void irADecoraciones() {
        if (validarAccesoModulo(PrivilegioUsuario.INVENTARIO, "decoraciones")) {
            decoracionesVista.setVisible(true);
            home.setVisible(false);
        }
    }

    public void irAColecciones() {
        if (validarAccesoModulo(PrivilegioUsuario.INVENTARIO, "colecciones")) {
            coleccionesVista.setVisible(true);
            home.setVisible(false);
        }
    }

    public void irACompras() {
        if (validarAccesoModulo(PrivilegioUsuario.ADMIN, "compras")) {
            comprasVista.setVisible(true);
            home.setVisible(false);
        }
    }

    public void irAInventario() {
        if (validarAccesoModulo(PrivilegioUsuario.INVENTARIO, "inventario")) {
            inventarioVista.setVisible(true);
            home.setVisible(false);
        }
    }

    public void irAVentas() {
        if (validarAccesoModulo(PrivilegioUsuario.VENTAS, "ventas")) {
            ventasVista.setVisible(true);
            home.setVisible(false);
        }
    }

    /**
     * Valida si el usuario actual tiene acceso a un módulo específico
     * 
     * @param privilegioRequerido Privilegio mínimo necesario para acceder
     * @param nombreModulo        Nombre del módulo para mensajes de error
     * @return true si tiene acceso, false en caso contrario
     */
    private boolean validarAccesoModulo(PrivilegioUsuario privilegioRequerido, String nombreModulo) {
        // ADMIN tiene acceso a todos los módulos
        if (privilegioUsuarioActual == PrivilegioUsuario.ADMIN) {
            return true;
        }

        // Verificar si el usuario tiene el privilegio requerido
        if (privilegioUsuarioActual == privilegioRequerido) {
            return true;
        }

        // Si no tiene acceso, mostrar mensaje de error
        JOptionPane.showMessageDialog(home,
                "No tiene permisos para acceder al módulo de " + nombreModulo + ".\n" +
                        "Privilegio requerido: " + privilegioRequerido + "\n" +
                        "Su privilegio actual: " + privilegioUsuarioActual,
                "Acceso Denegado", JOptionPane.WARNING_MESSAGE);

        logger.log(Level.WARNING, "Acceso denegado al módulo " + nombreModulo +
                " - Usuario con privilegio " + privilegioUsuarioActual +
                " intentó acceder a módulo que requiere " + privilegioRequerido);

        return false;
    }

    private void agregarListenersVistas() {
        // Listener para clientesVista
        clientesVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                home.setVisible(true);
            }
        });

        // Listener para coleccionesVista
        coleccionesVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                home.setVisible(true);
            }
        });

        // Listener para comprasVista
        comprasVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                home.setVisible(true);
            }
        });

        // Listener para decoracionesVista
        decoracionesVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                home.setVisible(true);
            }
        });

        // Listener para inventarioVista
        inventarioVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                home.setVisible(true);
            }
        });

        // Listener para proveedoresVista
        proveedoresVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                home.setVisible(true);
            }
        });

        // Listener para ventasVista
        ventasVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                home.setVisible(true);
            }
        });

        // Listener para usuariosVista
        usuariosVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                home.setVisible(true);
            }
        });
    }

}
