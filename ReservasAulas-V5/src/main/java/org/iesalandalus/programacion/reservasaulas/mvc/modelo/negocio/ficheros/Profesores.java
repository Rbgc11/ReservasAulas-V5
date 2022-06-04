package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;


public class Profesores implements IProfesores {
	// Atributos
	private List<Profesor> coleccionProfesores;
	private static final String NOMBRE_FICHEROS_PROFESORES = "datos/profesores.dat";

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
	//Método comenzar
			@Override
			public void comenzar() {
				leer();
			}
			
			//Método leer
			private void leer() {
				File dtFicherosProfesores = new File(NOMBRE_FICHEROS_PROFESORES);
				try {
				ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(dtFicherosProfesores));
				Profesor profesor = null;
				do {
					profesor = (Profesor) objIn.readObject();
					if (profesor != null) {
						insertar(profesor);
					}				}while(profesor != null);
				objIn.close();
					
				} catch (ClassNotFoundException e)  {
					System.out.println("No se puede encontrar la clase para leer.");	
				} catch (FileNotFoundException e)  {
					System.out.println("No se puede abrir el fichero de profesores.");	
				} catch (EOFException e)  {
					System.out.println("Fichero profesores leído satisfactoriamente.");	
				} catch (IOException e)  {
					System.out.println("Error inesperado.");	
				} catch (OperationNotSupportedException e)  {
					System.out.println(e.getMessage());	
				}
			}
			
			//Método terminar
			@Override
			public void terminar() {
				escribir();
			}
			
			//Método escribir
			private void escribir() {
				File dtFicherosProfesores = new File(NOMBRE_FICHEROS_PROFESORES);
				try {
					FileOutputStream fileOut = new FileOutputStream(dtFicherosProfesores);
					ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
					for (Profesor profesor : coleccionProfesores)
						objOut.writeObject(profesor);
					objOut.close();
				System.out.println("Fichero aulas escrito satisfactoriamente.");
				} catch (FileNotFoundException e)  {
					System.out.println("No se puede abrir el fichero de aulas.");	
				} catch (IOException e)  {
					System.out.println("Error inesperado.");	
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
		Profesor profesorEncontrado = null;
		int indice = coleccionProfesores.indexOf(profesor);
		if (indice == -1) {
			profesorEncontrado = null;
		} else {
			profesorEncontrado = new Profesor(coleccionProfesores.get(indice));
		}
		return profesorEncontrado;
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
}	