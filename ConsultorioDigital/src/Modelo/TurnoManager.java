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
            turnos.add(new Turno0(hora, "", "", "", "", ""));
        }
        
        turnosPorFecha.put(fecha, turnos);
    }

    /**
     * Agrega o sobrescribe un turno en una fecha y hora específica.
     * Si la hora no existe, se añade (lo que solo ocurriría si se añade una hora manual).
     */
    public void agregarTurno(LocalDate fecha, String hora, String nombre, String dni, 
                            String telefono, String obraSocial, String motivo) {
        List<Turno0> turnos = getTurnosPorFecha(fecha);
        Turno0 turnoExistente = null; // Variable auxiliar

        // Buscar el turno existente por hora
        for (Turno0 t : turnos) {
            if (t.getHora().equals(hora)) {
                turnoExistente = t;
                break;
            }
        }
        
        if (turnoExistente != null) {
            // Caso 1: Sobrescribir el turno existente
            turnoExistente.setNombre(nombre);
            turnoExistente.setDni(dni);
            turnoExistente.setTelefono(telefono);
            turnoExistente.setObraSocial(obraSocial);
            turnoExistente.setMotivo(motivo);
        } else {
            // Caso 2: Si la hora no existe, agregar y ordenar (para horas fuera de la lista predefinida)
            turnos.add(new Turno0(hora, nombre, dni, telefono, obraSocial, motivo));
            turnos.sort((t1, t2) -> t1.getHora().compareTo(t2.getHora()));
        }
    }

    /**
     * Elimina el turno en una fecha y hora específica, limpiando sus campos.
     */
    public void eliminarTurno(LocalDate fecha, String hora) {
        List<Turno0> turnos = getTurnosPorFecha(fecha);
        
        for (Turno0 t : turnos) {
            if (t.getHora().equals(hora)) {
                t.setNombre("");
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
    
    /**
     * Busca un turno registrado (no vacío) con el DNI proporcionado en cualquier fecha.
     */
    public Turno0 buscarTurnoPorDni(String dni) {
        // Itera sobre todas las listas de turnos 
        for (List<Turno0> listaTurnos : turnosPorFecha.values()) {
            
            // Itera sobre cada turno dentro de la lista
            for (Turno0 turno : listaTurnos) {
                
                // Compara el DNI 
                if (dni != null && !dni.isEmpty() && dni.equals(turno.getDni())) {
                    return turno; 
                }
            }
        }
        return null; 
    }

    /**
     * Obtiene una lista de todas las horas que no están reservadas en una fecha dada,
     * más la hora actual del turno que se está modificando.
     */
    public List<String> getHorasDisponibles(LocalDate fecha, String horaActual) {
        List<Turno0> todosLosTurnos = getTurnosPorFecha(fecha);
        List<String> horasLibres = new ArrayList<>();

        for (Turno0 turno : todosLosTurnos) {
            // Un horario es "disponible" si:
            // 1. Está vacío (el DNI es vacío)
            // 2. O es la hora del turno que estamos modificando (horaActual)
            
            if (turno.getDni().isEmpty() || turno.getHora().equals(horaActual)) {
                horasLibres.add(turno.getHora());
            }
        }
        return horasLibres;
    }
}