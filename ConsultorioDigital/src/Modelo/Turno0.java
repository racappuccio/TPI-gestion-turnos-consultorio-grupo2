package Modelo;

public class Turno0 {
    private String hora;
    private String nombre;
    private String motivo;
    private String dni;
    private String telefono;
    private String obraSocial;

    public Turno0(String hora, String nombre, String dni, String telefono, String obraSocial, String motivo) {
        this.hora = hora;
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.obraSocial = obraSocial;
        this.motivo = motivo;
    }

    // Getters
    public String getHora() { return hora; }
    public String getNombre() { return nombre; }
    public String getMotivo() { return motivo; }
    public String getDni() { return dni; }
    public String getTelefono() { return telefono; }
    public String getObraSocial() { return obraSocial; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public void setDni(String dni) { this.dni = dni; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setObraSocial(String obraSocial) { this.obraSocial = obraSocial; }
}
