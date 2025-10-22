package Modelo;

public class Usuario {
    private String NombreUsuario;
    private String Contraseña; 

    public Usuario(String nombre, String contraseña) {
        this.NombreUsuario = nombre;
        this.Contraseña = contraseña;
    }

    public String getUsername() { return NombreUsuario; }
    public boolean checkPassword(String pass) { return Contraseña.equals(pass); }
}
