/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.ventas;

import Vista.ventas.ventasVista;
import Vista.ventas.FormularioAgregarVenta;
import Vista.ventas.FormularioEditarVenta;
import Vista.ventas.reportesVentas;

/**
 *
 * @author ossca
 */
public class ventasController {

    private ventasVista vista;
    private FormularioAgregarVenta formularioAgregar;
    private FormularioEditarVenta formularioEditar;
    private reportesVentas reportes;

    /**
     * Creates new form ventasController
     */
    public ventasController(ventasVista vista, FormularioAgregarVenta formularioAgregar,
            FormularioEditarVenta formularioEditar, reportesVentas reportes) {
        this.vista = vista;
        this.formularioAgregar = formularioAgregar;
        this.formularioEditar = formularioEditar;
        this.reportes = reportes;
    }

    public void iniciar() {
        vista.setVisible(true);

        // Agregar listener al botón de agregar
        vista.botonAgregar.addActionListener(e -> abrirFormularioAgregar());
    }

    public void abrirFormularioAgregar() {
        formularioAgregar.setVisible(true);
    }
}
