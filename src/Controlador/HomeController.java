package Controlador;

import Vista.home.Home;
import Vista.login.LoginVista;
import Vista.usuarios.UsuariosVista;
import Vista.proveedores.proveedoresVista;
import Controlador.proveedores.proveedoresController;
import Vista.clientes.clientesVista;
import Controlador.clientes.clientesController;
import Vista.decoraciones.decoracionesVista;
import Controlador.decoraciones.decoracionesController;
import Vista.colecciones.coleccionesVista;
import Controlador.colecciones.coleccionesController;
import Vista.compras.comprasVista;
import Controlador.compras.comprasController;
import Vista.inventario.inventarioVista;
import Vista.ventas.ventasVista;
import Controlador.ventas.ventasController;

public class HomeController {
    Home home;
    LoginVista login;
    UsuariosVista usuariosVista;
    proveedoresVista proveedoresVista;
    proveedoresController proveedoresController;
    clientesVista clientesVista;
    clientesController clientesController;
    decoracionesVista decoracionesVista;
    decoracionesController decoracionesController;
    coleccionesVista coleccionesVista;
    coleccionesController coleccionesController;
    comprasVista comprasVista;
    comprasController comprasController;
    inventarioVista inventarioVista;
    ventasVista ventasVista;
    ventasController ventasController;

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

        // Crear controladores específicos con sus vistas
        this.proveedoresController = new proveedoresController(proveedoresVista);
        this.clientesController = new clientesController(clientesVista);
        this.decoracionesController = new decoracionesController(decoracionesVista);
        this.coleccionesController = new coleccionesController(coleccionesVista);
        this.comprasController = new comprasController(comprasVista);
        this.ventasController = new ventasController(ventasVista);
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
        usuariosVista.setVisible(true);
        home.setVisible(false);
    }

    public void irAProveedores() {
        proveedoresController.iniciar();
    }

    public void irAClientes() {
        clientesController.iniciar();
    }

    public void irADecoraciones() {
        decoracionesController.iniciar();
    }

    public void irAColecciones() {
        coleccionesController.iniciar();
    }

    public void irACompras() {
        comprasController.iniciar();
    }

    public void irAInventario() {
        inventarioVista.setVisible(true);
        home.setVisible(false);
    }

    public void irAVentas() {
        ventasController.iniciar();
    }

}
