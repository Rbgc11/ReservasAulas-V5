package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;


public class Aulas implements IAulas{
	
	private static final String NOMBRE_FICHEROS_AULAS = "datos/aulas.dat";
	private List<Aula> coleccionAulas;


		public Aulas() {
			coleccionAulas = new ArrayList<>();
		}

		// Constructor copia
		public Aulas(IAulas aulas) {
			if (aulas == null) {
				throw new NullPointerException("ERROR: No se pueden copiar unas aulas nulas.");
			} else {
				setAulas(aulas);
			}
		}
		@Override
		public void comenzar() {
			leer();
		}
		
		//Método leer
		private void leer() {
			File dtficherosaulas = new File(NOMBRE_FICHEROS_AULAS); //creación fichero
			try {
			ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(dtficherosaulas));
			Aula aula = null;
				do {
					// se asigna el profesor que viene del string
					aula = (Aula) objIn.readObject();
					if (aula!=null) {
						insertar(aula);
					}
				}while(aula != null);	
				objIn.close();
				// ordención de los catch para evitar errores
			} catch (ClassNotFoundException e)  {
				System.out.println("ERROR: No se puede encontrar la clase para leer.");	
			} catch (FileNotFoundException e)  {
				System.out.println("ERROR: No se puede abrir el fichero de aulas.");	
			} catch (EOFException e)  {
				System.out.println(" Fichero aulas leído satisfactoriamente.");	
			} catch (IOException e)  {
				System.out.println("ERROR: Error inesperado.");	
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
			File ficherosAulas = new File(NOMBRE_FICHEROS_AULAS);
			
			try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficherosAulas))) {
				for (Aula aula : coleccionAulas) {
					salida.writeObject(aula);
				}
			} catch (FileNotFoundException e) {
				System.out.println("ERROR: No se pudo crear el fichero.");
			} catch (IOException e) {
				System.out.println("Error de Entrada/Salida.");
			}
		}
		
		// Método setAulas
		private void setAulas(IAulas aulas) {
			if (aulas == null) {
				throw new NullPointerException("ERROR: No se puede copiar un aula nula.");
			} else {
				this.coleccionAulas = aulas.getAulas();
			}
		}
		// Método getAulas		
		public List<Aula> getAulas() {
			return copiaProfundaAulas(coleccionAulas);
		}

	    
		// Método copiaProfundaAulas
		private List<Aula> copiaProfundaAulas(List<Aula> listaAulas) {
			List<Aula> copiaProAulas = new ArrayList<>();
			Iterator<Aula> iterador = listaAulas.iterator();
			while (iterador.hasNext()) {
				copiaProAulas.add(new Aula(iterador.next()));
			}
			return copiaProAulas;
		}
		
		// Método getNumAulas
		public int getNumAulas() {
			return coleccionAulas.size();

		}
	    

		// Método insertar
		@Override
		public void insertar (Aula aula) throws OperationNotSupportedException {
			if (aula == null) {
				throw new NullPointerException("ERROR: No se puede insertar un aula nula.");
			}
			if (coleccionAulas.contains(aula)) {
				throw new OperationNotSupportedException("ERROR: Ya existe un aula con ese nombre.");
			} else {
				coleccionAulas.add(new Aula(aula));
			}
		}
	   
		// Método buscar
		public Aula buscar(Aula aula) {
			if(aula == null) {
				throw new NullPointerException("ERROR: No se puede buscar un aula nula.");
			} else if(coleccionAulas.isEmpty()) {
				return null;
			}
			
			int indice = coleccionAulas.indexOf(aula);
			
			if(coleccionAulas.contains(aula)) {
			  	return new Aula(coleccionAulas.get(indice));
			} 
			return null;		
		}
		
		// Método borrar
		public void borrar(Aula aula) throws OperationNotSupportedException {
			if (aula == null) {
				throw new NullPointerException("ERROR: No se puede borrar un aula nula.");
			} else if (!coleccionAulas.contains(aula))  {
				throw new OperationNotSupportedException("ERROR: No existe ningún aula con ese nombre.");
			} else {
				coleccionAulas.remove(coleccionAulas.indexOf(aula));
			}
		}
	     
		// Metodo representar
		public List<String> representar() {
			List<String> representacion = new ArrayList<String>();
			Iterator<Aula> iterador = coleccionAulas.iterator();
			while (iterador.hasNext()) {
				representacion.add(iterador.next().toString());
			}
			return representacion;
		}

	}