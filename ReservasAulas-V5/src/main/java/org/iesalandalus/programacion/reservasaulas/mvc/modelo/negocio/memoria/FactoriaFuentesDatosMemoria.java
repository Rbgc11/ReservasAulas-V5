package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IFuenteDatos;


public class FactoriaFuentesDatosMemoria implements IFuenteDatos {
	public FactoriaFuentesDatosMemoria() {
	}
	
	public IAulas crearAulas() {
		IAulas aulas=(IAulas) new Aulas();
		return aulas;
	}
	
	public IProfesores crearProfesores() {
		IProfesores profesores=(IProfesores) new Profesores();
		return profesores;
	}
	
	public IReservas crearReservas() {
		IReservas reservas=(IReservas) new Reservas();
		return reservas;
	}
}