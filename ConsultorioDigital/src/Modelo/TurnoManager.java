/*  CODIGO ANTERIOR - SI EL NUEVO FUNCIONA, BORRAR ESTE
package Modelo;


import Modelo.Turno;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections; // Importación útil para ordenar

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
        // Horarios base
        String[] horarios = {
            "08:00", "08:30", "09:00", "09:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
            "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"
        };
        
        for (String hora : horarios) {
            // Creamos turnos libres. Asumimos que el constructor de Turno 
            // solo necesita la hora para crear un turno libre.
            turnos.add(new Turno(hora)); 
        }
        
        // Agregar algunos turnos ocupados de ejemplo para la tabla:
        // Suponiendo que el constructor de Paciente requiere 5 Strings (nombre, apellido, dni, telefono, obraSocial)
        // Y el constructor de Turno requiere (hora, Paciente, motivo)
        turnos.add(new Turno("10:00", new Paciente("Gustavo", "Gnazzo", "12345678", "1122334455", "OSDE"), "Revisión"));
        turnos.add(new Turno("10:30", new Paciente("Hugo", "Ramón", "87654321", "5544332211", "Particular"), "Consulta"));
        
        ordenarTurnos();
    }
    
    /**
     * Agenda un turno asignando un objeto Paciente y un motivo a una hora específica.
     * @param hora La hora del turno.
     * @param paciente El objeto Paciente a asignar.
     * @param motivo El motivo de la consulta.
     */
  /*  public void agregarTurno(String hora, Paciente paciente, String motivo) {
        // Buscar si existe y actualizar
        for (Turno t : turnos) {
            if (t.getHora().equals(hora)) {
                t.setPaciente(paciente); // Usamos el setter de objeto Paciente
                t.setMotivo(motivo);
                return;
            }
        }
        // Si no existe, agregar nuevo (Aunque en este modelo siempre debería existir la hora)
        turnos.add(new Turno(hora, paciente, motivo));
        ordenarTurnos();
    }
    
    /**
     * Libera un turno existente en una hora específica.
     * @param hora La hora del turno a liberar.
     */
    /*public void eliminarTurno(String hora) {
        for (Turno t : turnos) {
            if (t.getHora().equals(hora)) {
                t.liberarTurno(); // Nuevo método en Turno para dejar paciente = null
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
    
    public Turno getTurnoPorHora(String hora){
        for (Turno t : turnos){
            if (t.getHora().equals(hora)) {
                return t;
            }
        }
        return null;
    }
}
*/


package Modelo;

import java.time.LocalDate;
import java.util.*;


public class TurnoManager {
    private static TurnoManager instancia;
    private Map<LocalDate, List<Turno0>> turnosPorFecha;

    private TurnoManager() {
        turnosPorFecha = new HashMap<>();
    }
   


    public static TurnoManager getInstancia() {
        if (instancia == null) {
            instancia = new TurnoManager();
        }
        return instancia;
    }

    // Obtener turnos de una fecha específica
    public List<Turno0> getTurnosPorFecha(LocalDate fecha) {
        if (!turnosPorFecha.containsKey(fecha)) {
            cargarHorariosVacios(fecha);
        }
        return turnosPorFecha.get(fecha);
    }

    // Cargar horarios vacíos para una fecha
    private void cargarHorariosVacios(LocalDate fecha) {
        List<Turno0> turnos = new ArrayList<>();
        
        String[] horarios = {
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
            "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
            "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
            "17:00", "17:30"
        };
        
        for (String hora : horarios) {
            turnos.add(new Turno0(hora, "Libre", "", "", "", ""));
        }
        
        turnosPorFecha.put(fecha, turnos);
    }

    // Agregar turno en una fecha específica
    public void agregarTurno(LocalDate fecha, String hora, String nombre, String dni, 
                            String telefono, String obraSocial, String motivo) {
        List<Turno0> turnos = getTurnosPorFecha(fecha);
        
        for (Turno0 t : turnos) {
            if (t.getHora().equals(hora)) {
                t.setNombre(nombre);
                t.setDni(dni);
                t.setTelefono(telefono);
                t.setObraSocial(obraSocial);
                t.setMotivo(motivo);
                return;
            }
        }
        
        // Si no existe, agregar y ordenar
        turnos.add(new Turno0(hora, nombre, dni, telefono, obraSocial, motivo));
        turnos.sort((t1, t2) -> t1.getHora().compareTo(t2.getHora()));
    }

    // Eliminar turno en una fecha específica
    public void eliminarTurno(LocalDate fecha, String hora) {
        List<Turno0> turnos = getTurnosPorFecha(fecha);
        
        for (Turno0 t : turnos) {
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

    // Obtener turno específico por fecha y hora
    public Turno0 getTurnoPorFechaYHora(LocalDate fecha, String hora) {
        List<Turno0> turnos = getTurnosPorFecha(fecha);
        
        for (Turno0 t : turnos) {
            if (t.getHora().equals(hora)) {
                return t;
            }
        }
        return null;
    }

    
}