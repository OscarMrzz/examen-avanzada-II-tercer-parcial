package Controlador;

import Vista.home.Home;
import Vista.login.LoginVista;
import Vista.usuarios.UsuariosVista;

public class HomeController {
   Home home;
   LoginVista login;
   UsuariosVista usuariosVista;


   public HomeController(Home home, LoginVista login, UsuariosVista usuariosVista) {
       this.home = home;
       this.login = login;
       this.usuariosVista = usuariosVista;
   }

   public void iniciar() {
       home.setVisible(true);
   }

   public void cargarBotones() {
    home.botonIrAUsuarios.addActionListener(e -> irAUsuarios());
       
   }

   public void irAUsuarios() {
       usuariosVista.setVisible(true);
       home.setVisible(false);
   }
   
    
}
