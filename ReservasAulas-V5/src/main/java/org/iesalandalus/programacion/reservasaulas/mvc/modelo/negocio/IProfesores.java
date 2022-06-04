package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;

public interface IProfesores {
	//Método comenzar
	void comenzar();

	//Método terminar
	void terminar();
	// Método List<Profesor> getProfesores(), coge una copia del método copiaProfunda 
	List<Profesor> getProfesores();

	//Método getNumProfesores, obtiene tamaño de la coleccion
	int getNumProfesores();

	//Método insertar Profesor
	void insertar(Profesor profesor) throws OperationNotSupportedException;

	//Método buscar Profesor
	Profesor buscar(Profesor profesor);

	//Método borrar Profesor
	void borrar(Profesor profesor) throws OperationNotSupportedException;

	//Método representar 
	List<String> representar();

}