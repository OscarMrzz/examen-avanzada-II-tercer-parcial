/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examen_tercer_parcial;

import Controlador.HomeController;
import Controlador.Usuarios.UsuariosController;
import Controlador.clientes.clientesController;
import Controlador.colecciones.coleccionesController;
import Controlador.compras.comprasController;
import Controlador.decoraciones.decoracionesController;
import Controlador.proveedores.proveedoresController;
import Controlador.ventas.ventasController;
import Vista.home.Home;
import Vista.login.LoginVista;
import Vista.usuarios.FormularioAgregarUsuario;
import Vista.usuarios.UsuariosVista;
import Vista.clientes.clientesVista;
import Vista.colecciones.coleccionesVista;
import Vista.compras.comprasVista;
import Vista.decoraciones.decoracionesVista;
import Vista.inventario.inventarioVista;
import Vista.proveedores.proveedoresVista;
import Vista.ventas.ventasVista;
import Vista.clientes.clientesVista;
import Vista.colecciones.coleccionesVista;
import Vista.compras.comprasVista;
import Vista.decoraciones.decoracionesVista;
import Vista.inventario.inventarioVista;
import Vista.proveedores.proveedoresVista;
import Vista.ventas.ventasVista;

/**
 *
 * @author ossca
 */
public class Examen_tercer_parcial {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Crear todas las vistas
        LoginVista loginVista = new LoginVista(null, true);
        Home home = new Home();
        UsuariosVista usuariosVista = new UsuariosVista(null, true);
        clientesVista clientesVista = new clientesVista(null, true);
        coleccionesVista coleccionesVista = new coleccionesVista(null, true);
        comprasVista comprasVista = new comprasVista(null, true);
        decoracionesVista decoracionesVista = new decoracionesVista(null, true);
        inventarioVista inventarioVista = new inventarioVista(null, true);
        proveedoresVista proveedoresVista = new proveedoresVista(null, true);
        ventasVista ventasVista = new ventasVista(null, true);
        FormularioAgregarUsuario formularioAgregarUsuario = new FormularioAgregarUsuario(null, true);

        // Crear HomeController con todas las vistas principales
        HomeController homeController = new HomeController(home, loginVista, usuariosVista,
                clientesVista, coleccionesVista, comprasVista, decoracionesVista,
                inventarioVista, proveedoresVista, ventasVista);

        // Crear controladores específicos con sus vistas
        UsuariosController usuariosController = new UsuariosController(usuariosVista, formularioAgregarUsuario);
        clientesController clientesController = new clientesController(clientesVista);
        coleccionesController coleccionesController = new coleccionesController(coleccionesVista);
        comprasController comprasController = new comprasController(comprasVista);
        decoracionesController decoracionesController = new decoracionesController(decoracionesVista);
        proveedoresController proveedoresController = new proveedoresController(proveedoresVista);
        ventasController ventasController = new ventasController(ventasVista);

        // Iniciar aplicación
        homeController.iniciar();
        homeController.cargarBotones();
    }

}
