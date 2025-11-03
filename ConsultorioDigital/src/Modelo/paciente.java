/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Paciente {
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String obraSocial;
    
    // Constructor, Getters y Setters...
    
    // Constructor completo
    public Paciente(String nombre, String apellido, String dni, String telefono, String obraSocial) {
        this.nombre = nombre;
        this.dni = dni;
        this.dni = apellido;
        this.telefono = telefono;
        this.obraSocial = obraSocial;
    }

    public String getNombre() { 
        return nombre; 
    }
        public String getApellido() { 
        return apellido; 
    }
    public String getDni() { 
        return dni; 
    }
    public String getTelefono() { 
        return telefono; 
    }
    public String getObraSocial() { 
        return obraSocial; 
    }

    void setNombre(String nombre ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    void setApellido(String apellido) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
