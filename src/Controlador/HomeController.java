package Controlador;

import Vista.home.Home;
import Vista.login.LoginVista;
import Vista.usuarios.UsuariosVista;
import Vista.proveedores.proveedoresVista;
import Controlador.proveedores.proveedoresController;
import Vista.clientes.clientesVista;
import Controlador.clientes.clientesController;

public class HomeController {
    Home home;
    LoginVista login;
    UsuariosVista usuariosVista;
    proveedoresVista proveedoresVista;
    proveedoresController proveedoresController;
    clientesVista clientesVista;
    clientesController clientesController;

    public HomeController(Home home, LoginVista login, UsuariosVista usuariosVista) {
        this.home = home;
        this.login = login;
        this.usuariosVista = usuariosVista;
        this.proveedoresVista = new proveedoresVista(null, true);
        this.proveedoresController = new proveedoresController(proveedoresVista, home);
        this.clientesVista = new clientesVista(null, true);
        this.clientesController = new clientesController(clientesVista, home);
    }

    public void iniciar() {
        home.setVisible(true);
    }

    public void cargarBotones() {
        home.botonIrAUsuarios.addActionListener(e -> irAUsuarios());
        home.botonIrAProveedores.addActionListener(e -> irAProveedores());
        home.botonIrAClientes.addActionListener(e -> irAClientes());
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

}
