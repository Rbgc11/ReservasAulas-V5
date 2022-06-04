	package org.iesalandalus.programacion.reservasaulas.mvc.controlador;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

public class Controlador implements IControlador{

	 private IModelo Imodelo;
	 private IVista Ivista;

	public Controlador(IModelo modelo, IVista vista) 
	{
		if (modelo == null) {
			throw new NullPointerException("El modelo no puede ser nulo.");
		}
		
		if (vista == null) {
			throw new NullPointerException(" La vista no puede ser nula.");
		}
		
		this.Imodelo = modelo;
		this.Ivista = vista;
		this.Ivista.setControlador(this);
	}

	// Método comenzar
		@Override
		public void comenzar()  {
			Imodelo.comenzar();
			Ivista.comenzar();
		}

		// Método terminar
		@Override
		public void terminar() {
			Imodelo.terminar();
			System.out.println("¡Adiós!");
		}
		
		@Override
		public List<Aula> getAulas() {
			return Imodelo.getAulas();
		}

		// Método insertarAula
		@Override
		public void insertarAula(Aula aula) throws OperationNotSupportedException {
			Imodelo.insertarAula(aula);
		}
		
		// Método insertarProfesor
		@Override
		public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException {
			Imodelo.insertarProfesor(profesor);
		}
		
		// Método borrarAula
		@Override
		public void borrarAula(Aula aula) throws OperationNotSupportedException {
			Imodelo.borrarAula(aula);
		}
		
		// Método borrarProfesor
		@Override
		public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
			Imodelo.borrarProfesor(profesor);
		}
		
		// Método buscarAula
		@Override
		public Aula buscarAula(Aula aula) {
			return Imodelo.buscarAula(aula);
		}
		
		// Método buscarProfesor
		@Override
		public Profesor buscarProfesor(Profesor profesor) {
			return Imodelo.buscarProfesor(profesor);
		}

		// Método representarAulas
		@Override
		public List<String> representarAulas() {
			return Imodelo.representarAulas();
		}

		// Método representarProfesores
		@Override
		public List<String> representarProfesores() {
			return Imodelo.representarProfesores();
		}

		// Método representarReservas
		@Override
		public List<String>representarReservas() {
			return Imodelo.representarReservas();
		}
		
		// Método realizarReservas
		@Override
		public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
			Imodelo.realizarReserva(reserva);
		}

		// Método anularReservas
		@Override
		public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
			Imodelo.anularReserva(reserva);

		}

		// Método getReservasProfesor(Profesor)
		@Override
		public List<Reserva> getReservasProfesor(Profesor profesor) {
			return Imodelo.getReservasProfesor(profesor);
		}

		// Método getReservasAula(Aula)
		@Override
		public List<Reserva> getReservasAula(Aula aula) {
			return Imodelo.getReservasAula(aula);
		}

		// Método getReservasPermanencia(Permanencia)
		@Override
		public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
			return Imodelo.getReservasPermanencia(permanencia);
		}

		// Método consultarDisponibilidad(Aula,Permanencia)
		@Override
		public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
			return Imodelo.consultarDisponibilidad(aula, permanencia);
		}

		
		@Override
		public List<Profesor> getProfesores() {
			return Imodelo.getProfesores();
		}

		@Override
		public List<Reserva> getReservas() {
			return Imodelo.getReservas();
		}
	}