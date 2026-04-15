package Controlador.Usuarios;

import Vista.usuarios.FormularioAgregarUsuario;
import Vista.usuarios.UsuariosVista;

public class UsuariosController {
    private UsuariosVista usuariosVista;
    private FormularioAgregarUsuario formularioAgregarUsuario;


    public UsuariosController(UsuariosVista usuariosVista, FormularioAgregarUsuario formularioAgregarUsuario) {
        this.usuariosVista = usuariosVista;
        this.formularioAgregarUsuario = formularioAgregarUsuario;
        cargarBotones();
    }

    public void cargarBotones(){
        usuariosVista.botonAgregar.addActionListener(e -> abrirFormularioAgregarUsuario());
    }

    public void abrirFormularioAgregarUsuario(){
        formularioAgregarUsuario.setVisible(true);
    }
}
