/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examen_tercer_parcial;

import Controlador.HomeController;
import Controlador.login.LoginVistaController;
import Controlador.Usuarios.UsuarioController;
import Controlador.clientes.clientesVistaController;
import Controlador.colecciones.coleccionesVistaController;
import Controlador.compras.comprasVistaController;
import Controlador.decoraciones.decoracionesVistaController;
import Controlador.inventario.inventarioVistaController;
import Controlador.proveedores.proveedoresVistaController;
import Controlador.ventas.ventasVistaController;
import Vista.home.Home;
import Vista.login.LoginVista;
import Vista.usuarios.FormularioAgregarUsuario;
import Vista.usuarios.FormularioEditarUsuario;
import Vista.usuarios.UsuariosVista;
import Vista.clientes.clientesVista;
import Vista.clientes.FormularioAgregarCliente;
import Vista.clientes.FormularioEditarCliente;
import Vista.clientes.reportesClientes;
import Vista.colecciones.coleccionesVista;
import Vista.colecciones.FormularioAgregarColeccion;
import Vista.colecciones.FormularioEditarColeccion;
import Vista.colecciones.reportesColecciones;
import Vista.compras.comprasVista;
import Vista.compras.FormularioAgregarCompra;
import Vista.compras.FormularioEditarCompra;
import Vista.compras.reportesCompras;
import Vista.decoraciones.decoracionesVista;
import Vista.decoraciones.FormularioAgregarDecoracion;
import Vista.decoraciones.FormularioEditarDecoracion;
import Vista.decoraciones.reportesDecoraciones;
import Vista.inventario.inventarioVista;
import Vista.inventario.FormularioAgregarInventario;
import Vista.inventario.reportesInventario;
import Vista.proveedores.proveedoresVista;
import Vista.proveedores.FormularioAgregarProveedor;
import Vista.proveedores.FormularioEditarProveedor;
import Vista.proveedores.reportesProveedores;
import Vista.ventas.ventasVista;
import Vista.ventas.FormularioAgregarVenta;
import Vista.ventas.FormularioEditarVenta;
import Vista.ventas.reportesVentas;
import Type.usuarios.PrivilegioUsuario;

/**
 *
 * @author ossca
 */
public class Examen_tercer_parcial {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println(" Cargando tema FlatLaf...");
            javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
            System.out.println(" FlatLaf cargado exitosamente!");
        } catch (Exception e) {
            System.out.println(" ERROR: No se pudo cargar el tema.");
            e.printStackTrace();
        }

        // Crear todas las vistas
        LoginVista loginVista = new LoginVista(null, true);
        Home home = new Home();
        UsuariosVista usuariosVista = new UsuariosVista(null, true);
        clientesVista clientesVista = new clientesVista(null, true);
        FormularioAgregarCliente formularioAgregarCliente = new FormularioAgregarCliente(null, true);
        FormularioEditarCliente formularioEditarCliente = new FormularioEditarCliente(null, true);
        reportesClientes reportesClientes = new reportesClientes(null, true);
        coleccionesVista coleccionesVista = new coleccionesVista(null, true);
        FormularioAgregarColeccion formularioAgregarColeccion = new FormularioAgregarColeccion(null, true);
        FormularioEditarColeccion formularioEditarColeccion = new FormularioEditarColeccion(null, true);
        reportesColecciones reportesColecciones = new reportesColecciones(null, true);
        comprasVista comprasVista = new comprasVista(null, true);
        FormularioAgregarCompra formularioAgregarCompra = new FormularioAgregarCompra(null, true);
        FormularioEditarCompra formularioEditarCompra = new FormularioEditarCompra(null, true);
        reportesCompras reportesCompras = new reportesCompras(null, true);
        decoracionesVista decoracionesVista = new decoracionesVista(null, true);
        FormularioAgregarDecoracion formularioAgregarDecoracion = new FormularioAgregarDecoracion(null, true);
        FormularioEditarDecoracion formularioEditarDecoracion = new FormularioEditarDecoracion(null, true);
        reportesDecoraciones reportesDecoraciones = new reportesDecoraciones(null, true);
        inventarioVista inventarioVista = new inventarioVista(null, true);
        FormularioAgregarInventario formularioAgregarInventario = new FormularioAgregarInventario(null, true);
        reportesInventario reportesInventario = new reportesInventario(null, true);
        proveedoresVista proveedoresVista = new proveedoresVista(null, true);
        FormularioAgregarProveedor formularioAgregarProveedor = new FormularioAgregarProveedor(null, true);
        FormularioEditarProveedor formularioEditarProveedor = new FormularioEditarProveedor(null, true);
        reportesProveedores reportesProveedores = new reportesProveedores(null, true);
        ventasVista ventasVista = new ventasVista(null, true);
        FormularioAgregarVenta formularioAgregarVenta = new FormularioAgregarVenta(null, true);
        FormularioEditarVenta formularioEditarVenta = new FormularioEditarVenta(null, true);
        reportesVentas reportesVentas = new reportesVentas(null, true);
        FormularioAgregarUsuario formularioAgregarUsuario = new FormularioAgregarUsuario(null, true);
        FormularioEditarUsuario formularioEditarUsuario = new FormularioEditarUsuario(null, true);

        // Crear HomeController con todas las vistas principales
        HomeController homeController = new HomeController(home, loginVista, usuariosVista,
                clientesVista, coleccionesVista, comprasVista, decoracionesVista,
                inventarioVista, proveedoresVista, ventasVista);

        // Establecer privilegio del usuario actual (por defecto ADMIN para desarrollo)
        // En producción, esto debería obtenerse del sistema de login
        homeController.setPrivilegioUsuarioActual(PrivilegioUsuario.ADMIN);

        // Crear controladores específicos con sus vistas y formularios
        UsuarioController usuarioController = new UsuarioController(usuariosVista, formularioAgregarUsuario,
                formularioEditarUsuario);
        clientesVistaController clientesVistaController = new clientesVistaController(clientesVista, formularioAgregarCliente,
                formularioEditarCliente);
        coleccionesVistaController coleccionesVistaController = new coleccionesVistaController(coleccionesVista,
                formularioAgregarColeccion, formularioEditarColeccion);
        comprasVistaController comprasVistaController = new comprasVistaController(comprasVista, formularioAgregarCompra,
                formularioEditarCompra, reportesCompras);
        decoracionesVistaController decoracionesVistaController = new decoracionesVistaController(decoracionesVista,
                formularioAgregarDecoracion, formularioEditarDecoracion, reportesDecoraciones);
        inventarioVistaController inventarioVistaController = new inventarioVistaController(inventarioVista,
                formularioAgregarInventario, reportesInventario);
        proveedoresVistaController proveedoresVistaController = new proveedoresVistaController(proveedoresVista,
                formularioAgregarProveedor, formularioEditarProveedor, reportesProveedores);
        ventasVistaController ventasVistaController = new ventasVistaController(ventasVista, formularioAgregarVenta,
                formularioEditarVenta, reportesVentas);

        LoginVistaController loginVistaController = new LoginVistaController(loginVista, home);

        // Iniciar aplicación
        homeController.iniciar();
        homeController.cargarBotones();
    }

}
