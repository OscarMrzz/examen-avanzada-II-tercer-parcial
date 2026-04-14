/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examen_tercer_parcial;

import Controlador.HomeController;
import Vista.home.Home;
import Vista.login.LoginVista;
import Vista.usuarios.UsuariosVista;

/**
 *
 * @author ossca
 */
public class Examen_tercer_parcial {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LoginVista loginVista = new LoginVista( null,true);
        Home home = new Home();
        UsuariosVista usuariosVista = new UsuariosVista( null,true);
        
        HomeController homeController = new HomeController(home, loginVista, usuariosVista);
        homeController.iniciar();
        homeController.cargarBotones();
    }
    
}
