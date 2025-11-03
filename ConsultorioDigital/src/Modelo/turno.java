package Modelo;

import java.time.LocalTime; // Deja el import por si luego usas LocalTime

public class Turno {
    private String hora;
    private Paciente paciente; // Debe ser un objeto Paciente
    private String motivo;

    // 1. Constructor para TURNO LIBRE (usado en TurnoManager.cargarTurnosIniciales)
    public Turno(String hora) {
        this.hora = hora;
        this.paciente = null; // Un turno libre no tiene paciente
        this.motivo = "";
    }

    // 2. Constructor para TURNO OCUPADO (usado cuando se agenda uno)
    public Turno(String hora, Paciente paciente, String motivo) {
        this.hora = hora;
        this.paciente = paciente;
        this.motivo = motivo;
    }

    // ----------------------------------------------------
    // MÉTODOS DE ACCIÓN Y ESTADO
    // ----------------------------------------------------

    public boolean estaLibre() {
        return this.paciente == null;
    }
    
    /**
     * Libera el turno, quitando la asignación del paciente.
     */
    public void liberarTurno() {
        this.paciente = null;
        this.motivo = "";
    }

    // ----------------------------------------------------
    // SETTERS (Usados por TurnoManager para AGENDAR/MODIFICAR)
    // ----------------------------------------------------
    
    /**
     * Asigna un objeto Paciente y un motivo al turno.
     * Usado para AGENDAR un turno libre o MODIFICAR uno existente.
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    // ----------------------------------------------------
    // GETTERS
    // ----------------------------------------------------

    public String getHora() { return hora; }
    public Paciente getPaciente() { return paciente; }
    public String getMotivo() { return motivo; }
}