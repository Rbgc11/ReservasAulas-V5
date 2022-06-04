package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.mongodb;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class FactoriaFuenteDatosMongodb implements IFuenteDatos{
	//Constructor vacío
	public FactoriaFuenteDatosMongodb() {
	}
	
	@Override
	public IAulas crearAulas() {
		IAulas aulas=new Aulas();
		return aulas;
	}
	
	@Override
	public IProfesores crearProfesores() {
		IProfesores profesores=new Profesores();
		return profesores;
	}
	
	@Override
	public IReservas crearReservas() {
		IReservas reservas=new Reservas();
		return reservas;
	}
}