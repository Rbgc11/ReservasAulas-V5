	package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros.FactoriaFuentesDatosFicheros;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria.FactoriaFuentesDatosMemoria;


public enum FactoriaFuenteDatos {
	MEMORIA {
		public IFuenteDatos crear() {
			IFuenteDatos memoria=new FactoriaFuentesDatosMemoria();
			return memoria;
		}
	},
	FICHEROS {
		public IFuenteDatos crear() {
			IFuenteDatos ficheros=new FactoriaFuentesDatosFicheros();
			return ficheros;
		}
	};
	
	FactoriaFuenteDatos() {
		
	}
	
	public abstract IFuenteDatos crear();
}