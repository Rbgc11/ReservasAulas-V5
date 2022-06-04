package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.List;
import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;

public interface IAulas {
	//Método comenzar
		void comenzar();

		//Método terminar
		void terminar();

		// Método List<Aula> getAulas(), coge una copia del método copiaProfunda 
		List<Aula> getAulas();

		// Método getNumAulas, obtiene tamaño de la coleccion
		int getNumAulas();

		// Método insertar Aula
		void insertar(Aula aula) throws OperationNotSupportedException;

		// Método buscar AULA
		Aula buscar(Aula aula);

		// Método borrar Aula
		void borrar(Aula aula) throws OperationNotSupportedException;

		// Metodo representar
		List<String> representar();

}