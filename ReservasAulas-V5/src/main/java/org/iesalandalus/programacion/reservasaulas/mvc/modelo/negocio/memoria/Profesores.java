package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;


public class Profesores implements IProfesores {
	// Atributos
	private List<Profesor> coleccionProfesores;

	// Constructor por defecto
	public Profesores() {
		coleccionProfesores = new ArrayList<>();
	}

	// Constructor copia
	public Profesores(IProfesores profesores) {
		if (profesores == null) {
			throw new NullPointerException(" No se pueden copiar profesores nulos.");
		} else {
			setProfesores(profesores);
		}
	}
	
	// Método setProfesores(Profesores)
	private void setProfesores(IProfesores profesores) {
		if (profesores == null) {
			throw new NullPointerException(" No se puede copiar un profesor nulo.");
		} else {
			this.coleccionProfesores = profesores.getProfesores();
		}
	}
	
	// Método copiaProfundaProfesores
	private List<Profesor> copiaProfundaProfesores(List<Profesor> listaProfesores) {
		List<Profesor> copiaProProfesores = new ArrayList<>();
		Comparator<Profesor> comparador=Comparator.comparing(Profesor::getCorreo);
		Collections.sort(coleccionProfesores, comparador);
		Iterator<Profesor> iterador = listaProfesores.iterator();
		while (iterador.hasNext()) {
			copiaProProfesores.add(new Profesor(iterador.next()));
		}
		return copiaProProfesores;
	}

	// Método List<Profesor> getProfesores()
	public List<Profesor> getProfesores() {
		return copiaProfundaProfesores(coleccionProfesores);
	}

	// Método getNumProfesores
	public int getNumProfesores() {
		return coleccionProfesores.size();

	}

	// Método insertar
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("No se puede insertar un profesor nulo.");
		} else if  (!coleccionProfesores.contains(profesor)) {
			coleccionProfesores.add(new Profesor(profesor));
		} else {
			throw new OperationNotSupportedException("Ya existe un profesor con ese nombre.");
		}
	}

	// Método buscar
	public Profesor buscar(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException(" No se puede buscar un profesor nulo.");
		}
		Iterator<Profesor> iterador = coleccionProfesores.iterator();

		while (iterador.hasNext()) {
			Profesor profesorBuscado = iterador.next();
			if (profesor.equals(profesorBuscado)) {
				return new Profesor(profesorBuscado);
			}
		}
		return null;
	}

	// Método borrar
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("No se puede borrar un profesor nulo.");
		} else if  (!coleccionProfesores.contains(profesor)) {
			throw new OperationNotSupportedException("No existe ningún profesor con ese nombre.");
		} else {
			coleccionProfesores.remove(profesor);
		}
	}

	// Metodo representar
	public List<String> representar() {
		List<String> representacion = new ArrayList<String>();
		Iterator<Profesor> iterador = coleccionProfesores.iterator();
		while (iterador.hasNext()) {
			representacion.add(iterador.next().toString());
		}
		return representacion;
	}

	@Override
	public void comenzar() {
		
	}

	@Override
	public void terminar() {
		
	}
}	