package Controlador.home;

import Vista.home.Home;
import Vista.usuarios.UsuariosVista;
import Vista.clientes.clientesVista;
import Vista.proveedores.proveedoresVista;
import Vista.colecciones.coleccionesVista;
import Vista.decoraciones.decoracionesVista;
import Vista.compras.comprasVista;
import Vista.inventario.inventarioVista;
import Vista.ventas.ventasVista;
import java.awt.event.ActionEvent;

/**
 * Controlador para la vista Home
 * @author ossca
 */
public class HomeVistaController {
    
    private Home vista;
    
    public HomeVistaController(Home vista) {
        this.vista = vista;
        inicializarEventos();
    }
    
    private void inicializarEventos() {
        // Evento del botón Usuarios
        vista.botonIrAUsuarios.addActionListener(this::abrirUsuarios);
        
        // Evento del botón Clientes
        vista.botonIrAClientes.addActionListener(this::abrirClientes);
        
        // Evento del botón Proveedores
        vista.botonIrAProveedores.addActionListener(this::abrirProveedores);
        
        // Evento del botón Colecciones
        vista.botonirAColecciones.addActionListener(this::abrirColecciones);
        
        // Evento del botón Productos (Decoraciones)
        vista.botonIrAProductos.addActionListener(this::abrirProductos);
        
        // Evento del botón Compras
        vista.botonirACompras.addActionListener(this::abrirCompras);
        
        // Evento del botón Inventario
        vista.botonIrAInventario.addActionListener(this::abrirInventario);
        
        // Evento del botón Ventas
        vista.botonIrAVentas.addActionListener(this::abrirVentas);
        
        // Evento del botón Reportes
        vista.botonIrAReportes.addActionListener(this::abrirReportes);
    }
    
    /**
     * Abre la vista de Usuarios
     */
    private void abrirUsuarios(ActionEvent e) {
        UsuariosVista usuariosVista = new UsuariosVista(vista, true);
        usuariosVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Clientes
     */
    private void abrirClientes(ActionEvent e) {
        clientesVista clientesVista = new clientesVista(vista, true);
        clientesVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Proveedores
     */
    private void abrirProveedores(ActionEvent e) {
        proveedoresVista proveedoresVista = new proveedoresVista(vista, true);
        proveedoresVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Colecciones
     */
    private void abrirColecciones(ActionEvent e) {
        coleccionesVista coleccionesVista = new coleccionesVista(vista, true);
        coleccionesVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Productos (Decoraciones)
     */
    private void abrirProductos(ActionEvent e) {
        decoracionesVista decoracionesVista = new decoracionesVista(vista, true);
        decoracionesVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Compras
     */
    private void abrirCompras(ActionEvent e) {
        comprasVista comprasVista = new comprasVista(vista, true);
        comprasVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Inventario
     */
    private void abrirInventario(ActionEvent e) {
        inventarioVista inventarioVista = new inventarioVista(vista, true);
        inventarioVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Ventas
     */
    private void abrirVentas(ActionEvent e) {
        ventasVista ventasVista = new ventasVista(vista, true);
        ventasVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Reportes
     */
    private void abrirReportes(ActionEvent e) {
        // Aquí puedes implementar la lógica para abrir reportes
        // Por ahora, puedes mostrar un mensaje o abrir una ventana de reportes general
        javax.swing.JOptionPane.showMessageDialog(vista, 
                "Módulo de Reportes en desarrollo", 
                "Reportes", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
}
