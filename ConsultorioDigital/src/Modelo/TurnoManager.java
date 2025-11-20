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
    
    // Verifica si existe algún turno registrado con el DNI proporcionado
    public boolean existeTurnoConDni(String dni) {
        // Itera sobre todas las listas de turnos (una por cada fecha registrada)
        for (List<Turno0> listaTurnos : turnosPorFecha.values()) {
            
            // Itera sobre cada turno dentro de la lista
            for (Turno0 turno : listaTurnos) {
                
                // Compara el DNI (asegurando que el DNI no esté vacío)
                if (dni != null && !dni.isEmpty() && dni.equals(turno.getDni())) {
                    return true; // Encontrado!
                }
            }
        }
        return false; // No se encontró en ninguna fecha
    }

    // *******************************************************************
    // *** NUEVO MÉTODO PARA OBTENER HORAS DISPONIBLES PARA MODIFICACIÓN ***
    // *******************************************************************
    
    /**
     * Obtiene una lista de todas las horas que no están reservadas en una fecha dada,
     * más la hora actual del turno que se está modificando.
     * * @param fecha La fecha a buscar.
     * @param horaActual La hora del turno que se va a modificar (debe ser incluida).
     * @return List<String> de horarios disponibles y la hora actual.
     */
    public List<String> getHorasDisponibles(LocalDate fecha, String horaActual) {
        List<Turno0> todosLosTurnos = getTurnosPorFecha(fecha);
        List<String> horasLibres = new ArrayList<>();

        for (Turno0 turno : todosLosTurnos) {
            // Un horario es "disponible" si:
            // 1. Está vacío (el DNI es vacío)
            // 2. O es la hora del turno que estamos modificando (para que el usuario pueda seleccionarla)
            
            if (turno.getDni().isEmpty() || turno.getHora().equals(horaActual)) {
                horasLibres.add(turno.getHora());
            }
        }
        return horasLibres;
    }
}