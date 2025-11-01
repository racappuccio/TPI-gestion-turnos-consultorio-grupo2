package Modelo;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TurnoManager {
    private static TurnoManager instancia;
    private List<Turno> turnos;
    
    private TurnoManager() {
        turnos = new ArrayList<>();
        cargarTurnosIniciales();
    }
    
    public static TurnoManager getInstancia() {
        if (instancia == null) {
            instancia = new TurnoManager();
        }
        return instancia;
    }
    
    private void cargarTurnosIniciales() {        
        // Horarios libres
        String[] horarios = {
            "08:00", "08:30", "09:00", "09:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
            "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"
        };
        
        for (String hora : horarios) {
            turnos.add(new Turno(hora, "Libre", "", "", "", ""));
        }
    }
    
    public void agregarTurno(String hora, String nombre, String dni, String telefono, String obraSocial, String motivo) {
        // Buscar si existe y actualizar
        for (Turno t : turnos) {
            if (t.getHora().equals(hora)) {
                t.setNombre(nombre);
                t.setDni(dni);
                t.setTelefono(telefono);
                t.setObraSocial(obraSocial);
                t.setMotivo(motivo);
                return;
            }
        }
        // Si no existe, agregar nuevo
        turnos.add(new Turno(hora, nombre, dni, telefono, obraSocial, motivo));
        ordenarTurnos();
    }
    
    public void eliminarTurno(String hora) {
        for (Turno t : turnos) {
            if (t.getHora().equals(hora)) {
                t.setNombre("Libre");
                t.setDni("");
                t.setTelefono("");
                t.setObraSocial("");
                t.setMotivo("");
                
                return;
            }
        }
    }
    
    public List<Turno> getTurnos() {
        return turnos;
    }
    
    private void ordenarTurnos(){
        turnos.sort((t1, t2) -> t1.getHora().compareTo(t2.getHora()));
    }
    
    //metodo para obtener un turno especifico
    public Turno getTurnoPorHora(String hora){
        for (Turno t : turnos){
            if (t.getHora().equals(hora)) {
                return t;
            }
        }
        return null;
    }
}
