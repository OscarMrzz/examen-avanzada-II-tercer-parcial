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

public class HomeController {
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

        // Los controladores específicos se crean en el punto de entrada
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
        proveedoresVista.setVisible(true);
        home.setVisible(false);
    }

    public void irAClientes() {
        clientesVista.setVisible(true);
        home.setVisible(false);
    }

    public void irADecoraciones() {
        decoracionesVista.setVisible(true);
        home.setVisible(false);
    }

    public void irAColecciones() {
        coleccionesVista.setVisible(true);
        home.setVisible(false);
    }

    public void irACompras() {
        comprasVista.setVisible(true);
        home.setVisible(false);
    }

    public void irAInventario() {
        inventarioVista.setVisible(true);
        home.setVisible(false);
    }

    public void irAVentas() {
        ventasVista.setVisible(true);
        home.setVisible(false);
    }

}
