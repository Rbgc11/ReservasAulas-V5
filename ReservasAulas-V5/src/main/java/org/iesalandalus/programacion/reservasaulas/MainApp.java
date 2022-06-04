package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.FactoriaVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

public class MainApp {

	public static void main(String[] args) {
		IModelo modelo = new Modelo(procesarArgumentosModelo(args));
		IVista vista = procesarArgumentosVista(args);
		IControlador controlador = new Controlador(modelo, vista);
		controlador.comenzar();
	}

	private static IFuenteDatos procesarArgumentosModelo(String[] args) {
		IFuenteDatos fDatos = FactoriaFuenteDatos.MONGODB.crear();
		for (String argumento : args) {
			if (argumento.equals("-mMemoria")) {
				fDatos = FactoriaFuenteDatos.MEMORIA.crear();
			} else if (argumento.equals("-mFicheros")) {
				fDatos = FactoriaFuenteDatos.FICHEROS.crear();
			} else if (argumento.equals("-mMongoDB")) {
				fDatos = FactoriaFuenteDatos.MONGODB.crear();
			}
		}
		return fDatos;
	}

	private static IVista procesarArgumentosVista(String[] args) {
		IVista vista= FactoriaVista.GRAFICA.crear();
		for(String argumento : args) {
			if(argumento.equals("-vGrafica")) {
				vista=FactoriaVista.GRAFICA.crear();
			}
			else if (argumento.equals("-vTexto")) {
				vista=FactoriaVista.TEXTO.crear();
			}
		}
		return vista;
	}
	
	
}