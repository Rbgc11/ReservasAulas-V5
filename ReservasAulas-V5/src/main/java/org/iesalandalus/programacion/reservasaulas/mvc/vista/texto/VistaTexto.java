package org.iesalandalus.programacion.reservasaulas.mvc.vista.texto;

import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

public class VistaTexto implements IVista {
	private IControlador Icontrolador;

//Constructor
	public VistaTexto() {
		Opcion.setVista(this);
	}

//Método setControlador
	@Override
	public void setControlador(IControlador controlador) {
		this.Icontrolador = controlador;
	}

//Método comenzar, 
	@Override
	public void comenzar() {
		int ordinalOpcion = 0;
		do {
			try {
				Consola.mostrarMenu();
				ordinalOpcion = Consola.elegirOpcion();
				Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinalOpcion);
				opcion.ejecutar();
			} catch (NullPointerException | IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while (ordinalOpcion != Opcion.SALIR.ordinal());
	}


	@Override
	public void salir() {
		Icontrolador.terminar();
	}

//Método insertarAula
	public void insertarAula() {
		Consola.mostrarCabecera("Insertar aula");
		try {
			Aula aula = Consola.leerAula();
			Icontrolador.insertarAula(aula);
			System.out.println("Aula insertada correctamente.");
		} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}

	}

//Método borrarAula
	public void borrarAula() {
		Consola.mostrarCabecera("Borrar aula");
		List<String> listaAulas = Icontrolador.representarAulas();
		if (listaAulas == null) {
			System.out.println("No existen aulas que borrar");
		} else {
			try {
				Icontrolador.borrarAula(Consola.leerAulaFicticia());
				System.out.println("Aula borrada correctamente.");
			} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

//Método buscarAula
	public void buscarAula() {
		Consola.mostrarCabecera("Buscar aula");
		Aula aula = null;
		try {
			aula = Consola.leerAula();
			aula = Icontrolador.buscarAula(aula);
			if (aula != null) {
				System.out.println("El aula buscado es: " + aula);
			} else {
				System.out.println("No existe ningún aula con ese nombre");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}


//Método listarAulas
	public void listarAulas() {
		Consola.mostrarCabecera("Listar aulas");
		List<String> listaAulas = null;
		try {
			listaAulas = Icontrolador.representarAulas();
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		if (listaAulas == null) {
			System.out.println("No hay aulas que mostrar");
		} else {
			Iterator<String> iterador = listaAulas.iterator();
			while (iterador.hasNext()) {
				System.out.println(iterador.next().toString());
			}
		}
	}

//Método insertarProfesor
	public void insertarProfesor() {
		Consola.mostrarCabecera("Insertar profesor");
		try {
			Icontrolador.insertarProfesor(Consola.leerProfesor());
			System.out.println("Profesor insertado correctamente.");
		} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

//Método borrarProfesor
	public void borrarProfesor() {
		Consola.mostrarCabecera("Borrar profesor");
		List<String> listaProfesores = Icontrolador.representarProfesores();
		if (listaProfesores == null) {
			System.out.println("No existen profesores que borrar");
		} else {
			try {
				Profesor profesorABorrar = new Profesor(Consola.leerProfesorFicticio());
				Icontrolador.borrarProfesor(profesorABorrar);
				System.out.println("Profesor borrado correctamente.");
			} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

//Método buscarProfesor
	public void buscarProfesor() {
		Consola.mostrarCabecera("Buscar profesor");
		Profesor profesor = null;
		List<String> listaProfesores = Icontrolador.representarProfesores();
		if (listaProfesores == null) {
			System.out.println("No existen profesores que buscar");
		} else {
			try {
				profesor = Icontrolador.buscarProfesor( Consola.leerProfesor());
			} catch (NullPointerException | IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
			if (profesor == null) {
				System.out.println("El profesor buscado no existe");
			} else {
				System.out.println(profesor.toString());
			}
		}
	}

//Método listarProfesores
	public void listarProfesores() {
		Consola.mostrarCabecera("Listar profesores");
		List<String> profesores = Icontrolador.representarProfesores();
		if (profesores.size() != 0) {
			for (String profesor : profesores) {
				System.out.println(profesor);
			}
		} else {
			System.out.println("No hay profesores que listar.");
		}
	}

//Método realizarReserva
	public void realizarReserva() {
		Consola.mostrarCabecera("Realizar reserva");
		Reserva reservaARealizar = null;
		try {
			reservaARealizar = Consola.leerReserva();
			if (Icontrolador.buscarProfesor(reservaARealizar.getProfesor()) == null) {
				System.out.println("ERROR: El profesor introducido no existe. Por favor, creélo antes de intentar realizar una reserva con él.");
			} 
			else if (Icontrolador.buscarAula(reservaARealizar.getAula()) == null) {
				System.out.println("ERROR: El aula introducida no existe. Por favor, creéla antes de intentar realizar una reserva con ella.");
			} 
			else {
				Profesor profesor= new Profesor(Icontrolador.buscarProfesor(reservaARealizar.getProfesor()));
				Aula aula= new Aula(Icontrolador.buscarAula(reservaARealizar.getAula()));
				Reserva reservaFinal=new Reserva (profesor,aula,reservaARealizar.getPermanencia());
				Icontrolador.realizarReserva(reservaFinal);
				System.out.println("Reserva realizada correctamente.");

			}
		} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

//Método anularReserva
	public void anularReserva() {
		Consola.mostrarCabecera("Anular reserva");
		Reserva reserva = null;
		List<String> reservas = Icontrolador.representarReservas();
		if (reservas == null) {
			System.out.println("No hay reservas que borrar");
		} else {
			try {
				reserva = Consola.leerReservaFicticia();
				Icontrolador.anularReserva(reserva);
				System.out.println("Reserva anulada correctamente.");
			} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

//Método listarReservas
	public void listarReservas() {
		Consola.mostrarCabecera("Listar Reservas");
		List<Reserva> listaReservas = null;
		try {
			Permanencia permanencia=null;
			permanencia=new PermanenciaPorTramo(Consola.leerDia(),Tramo.TARDE);
			listaReservas = Icontrolador.getReservasPermanencia(permanencia);	
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		if (listaReservas == null) {
			System.out.println("No existe Reservas");
		} else {
			Iterator<Reserva> iterador = listaReservas.iterator();
			while (iterador.hasNext()) {
				System.out.println(iterador.next().toString());
			}
		}
	}

//Método listarReservasAula
	public void listarReservasAula() {
		Consola.mostrarCabecera("Listar reservas aula");
		List<Reserva> reservasAula = null;
		try {
			Aula aula = Consola.leerAula();
			reservasAula = Icontrolador.getReservasAula(aula);
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		if (reservasAula == null) {
			System.out.println("No hay listas de aulas");
		} else {
			Iterator<Reserva> iterador = reservasAula.iterator();
			while (iterador.hasNext()) {
				System.out.println(iterador.next().toString());
			}
		}
	}

//Método listarReservasProfesor
	public void listarReservasProfesor() {
		Consola.mostrarCabecera("Listar reservas profesor");
		List<Reserva> reservasProfesor = null;
		try {
			Profesor profesor = Consola.leerProfesor();
			reservasProfesor = Icontrolador.getReservasProfesor(profesor);
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		if (reservasProfesor == null) {
			System.out.println("No hay listas de profesores");
		} else {
			Iterator<Reserva> iterador = reservasProfesor.iterator();
			while (iterador.hasNext()) {
				System.out.println(iterador.next().toString());
			}
		}
	}

//Método consultarDisponibilidad
	public void consultarDisponibilidad() {
		Consola.mostrarCabecera("Consultar disponibilidad");
		try {
			Permanencia permanencia = Consola.leerPermanencia();
			Aula aulaBuscar = Icontrolador.buscarAula(Consola.leerAula());
			if (aulaBuscar == null) {
				System.out.println("El aula introducida no existe.");
			} else {
				if (Icontrolador.consultarDisponibilidad(aulaBuscar, permanencia)) {
					System.out.println("El aula está disponible.");
				} else {
					System.out.println("El aula ya está reservada.");
				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

}
