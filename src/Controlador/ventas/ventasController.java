/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.ventas;

import Vista.ventas.ventasVista;
import Vista.home.Home;
import Vista.ventas.FormularioAgregarVenta;

/**
 *
 * @author ossca
 */
public class ventasController {

    private ventasVista vista;
    private Home home;

    /**
     * Creates new form ventasController
     */
    public ventasController(ventasVista vista, Home home) {
        this.vista = vista;
        this.home = home;
    }

    public void iniciar() {
        vista.setVisible(true);
        home.setVisible(false);

        // Agregar listener al botón de agregar
        vista.botonAgregar.addActionListener(e -> abrirFormularioAgregar());
    }

    public void abrirFormularioAgregar() {
        FormularioAgregarVenta formulario = new FormularioAgregarVenta(null, true);
        formulario.setVisible(true);
    }
}
