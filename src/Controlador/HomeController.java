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

    public HomeController(Home home, LoginVista login, UsuariosVista usuariosVista) {
        this.home = home;
        this.login = login;
        this.usuariosVista = usuariosVista;
        this.proveedoresVista = new proveedoresVista(null, true);
        this.proveedoresController = new proveedoresController(proveedoresVista, home);
        this.clientesVista = new clientesVista(null, true);
        this.clientesController = new clientesController(clientesVista, home);
        this.decoracionesVista = new decoracionesVista(null, true);
        this.decoracionesController = new decoracionesController(decoracionesVista, home);
        this.coleccionesVista = new coleccionesVista(null, true);
        this.coleccionesController = new coleccionesController(coleccionesVista, home);
        this.comprasVista = new comprasVista(null, true);
        this.comprasController = new comprasController(comprasVista, home);
        this.inventarioVista = new inventarioVista(null, true);
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

}
