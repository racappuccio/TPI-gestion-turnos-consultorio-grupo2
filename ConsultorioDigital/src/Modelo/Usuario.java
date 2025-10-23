package Modelo;

// Importar una librería para hashing (e.g., Spring Security BCrypt o similar)
// Supongamos que tienes una clase UtileriaSeguridad.hashPassword()

public class Usuario {
    private String NombreUsuario;
    private String ContrasenaHash; // Ahora es el hash de la contraseña

    // En un caso real, el hash se generaría al crear el usuario
    public Usuario(String nombre, String contrasenaPlana) {
        this.NombreUsuario = nombre;
        // La contraseña se hashea antes de guardarla
        // this.ContrasenaHash = UtileriaSeguridad.hashPassword(contrasenaPlana); 
        this.ContrasenaHash = contrasenaPlana; // Por ahora lo dejamos simple para el ejemplo
    }

    public String getUsername() { return NombreUsuario; }

    // El método ahora compara el hash almacenado con el hash de la contraseña de entrada
    public boolean checkPassword(String contrasenaPlana) { 
        // return UtileriaSeguridad.verifyPassword(contrasenaPlana, this.ContrasenaHash);
        
        // Versión simple (mantiene la funcionalidad, pero no la seguridad)
        return this.ContrasenaHash.equals(contrasenaPlana); 
    }
    
}
