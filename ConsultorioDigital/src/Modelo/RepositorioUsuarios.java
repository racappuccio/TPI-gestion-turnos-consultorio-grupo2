//esta clase simula una BD. Más adelante cuando esté todo probado conectamos la de verdad.
package Modelo;

import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios {
    private static List<Usuario> usuarios = new ArrayList<>();
    
   static {
       //usuarios de prueba (nombre, contrasenaPlana)
       usuarios.add(new Usuario("admin", "1234")); 
   }
   
   public static Usuario buscarUsuario(String nombre) {
       for (Usuario u : usuarios){
           if (u.getUsername().equalsIgnoreCase(nombre)) {
               return u;
           }
       }
       return null;
   }
   
   //metodos auxiliares (opcional): agregar usuario apra pruebas
    public static void agregarUsuario(Usuario u){
        usuarios.add(u);
    }
}
