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
    private UsuariosVista usuariosVista;
    private clientesVista clientesVista;
    private proveedoresVista proveedoresVista;
    private coleccionesVista coleccionesVista;
    private decoracionesVista decoracionesVista;
    private comprasVista comprasVista;
    private inventarioVista inventarioVista;
    private ventasVista ventasVista;
    
    public HomeVistaController(Home vista, UsuariosVista usuariosVista, clientesVista clientesVista,
            proveedoresVista proveedoresVista, coleccionesVista coleccionesVista,
            decoracionesVista decoracionesVista, comprasVista comprasVista,
            inventarioVista inventarioVista, ventasVista ventasVista) {
        this.vista = vista;
        this.usuariosVista = usuariosVista;
        this.clientesVista = clientesVista;
        this.proveedoresVista = proveedoresVista;
        this.coleccionesVista = coleccionesVista;
        this.decoracionesVista = decoracionesVista;
        this.comprasVista = comprasVista;
        this.inventarioVista = inventarioVista;
        this.ventasVista = ventasVista;
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
        usuariosVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Clientes
     */
    private void abrirClientes(ActionEvent e) {
        clientesVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Proveedores
     */
    private void abrirProveedores(ActionEvent e) {
        proveedoresVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Colecciones
     */
    private void abrirColecciones(ActionEvent e) {
        coleccionesVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Productos (Decoraciones)
     */
    private void abrirProductos(ActionEvent e) {
        decoracionesVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Compras
     */
    private void abrirCompras(ActionEvent e) {
        comprasVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Inventario
     */
    private void abrirInventario(ActionEvent e) {
        inventarioVista.setVisible(true);
    }
    
    /**
     * Abre la vista de Ventas
     */
    private void abrirVentas(ActionEvent e) {
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
