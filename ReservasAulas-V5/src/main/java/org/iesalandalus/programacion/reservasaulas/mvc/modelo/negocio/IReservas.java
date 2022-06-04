package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.time.LocalDate;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public interface IReservas {
	//Método comenzar
	void comenzar();

	//Método terminar
	void terminar();
	
	List<Reserva> getReservas();

	//Método getNumAulas 
	int getNumReservas();
	
	// Método insertar Reserva
		void insertar(Reserva reserva) throws OperationNotSupportedException;


	//Método buscar Reserva
	Reserva buscar(Reserva reserva);

	//Método borrar Reserva
	void borrar(Reserva reserva) throws OperationNotSupportedException;

	//Método representar 
	List<String> representar();

	//Método getReservasProfesor
	List<Reserva> getReservasProfesor(Profesor profesor);

	//Método getReservasAula
	List<Reserva> getReservasAula(Aula aula);

	//Método getReservasPermanencia
	List<Reserva> getReservasPermanencia(Permanencia permanencia);

	//Método consultarDisponibilidad
	boolean consultarDisponibilidad(Aula aula, Permanencia permanencia);
	
}