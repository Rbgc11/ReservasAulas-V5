package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public class Aula implements Serializable { 
	//Atributos
	private static final float PUNTOS_POR_PUESTO = (float) 0.5f;
	private static final int MIN_PUESTOS = 10;
	private static final int MAX_PUESTOS = 100;
	private String nombre;
	private int puestos;

    public Aula(String nombre, int puestos) {
        setNombre(nombre);
        setPuestos(puestos);
    }
    
    //Constructor Copia
    public Aula(Aula aula) {
        {
            if (aula == null) {
    			throw new NullPointerException("No se puede copiar un aula nula.");
    		}
    		setNombre(aula.nombre);
    		setPuestos(aula.puestos);
        }
    }
    
	private void setNombre(String nombre) {
		if(nombre==null) {
			throw new NullPointerException("El nombre del aula no puede ser nulo.");
		}else if (nombre.trim().equals("")) {
			throw new IllegalArgumentException("El nombre del aula no puede estar vacío.");
		} else {
		this.nombre = nombre;
		}
	}
	
    public int getPuestos() {
        return puestos;
    }
    

	//Set Puestos
	private void setPuestos(int puestos) {
		if (puestos > MAX_PUESTOS || puestos < MIN_PUESTOS) {
			throw new IllegalArgumentException ("El puesto no es correcto, debe de ser entre 10 y 100 .");
		} else {
			this.puestos = puestos;
		}
	}

    
    public String getNombre() {
        return nombre;
    }
    
    public float getPuntos() {
		float puntos = puestos * PUNTOS_POR_PUESTO;
        return puntos;
    }
    
	public static Aula getAulaFicticia(String aula){		
		Aula aulafic = new Aula("DAW", 20);
		return new Aula(aulafic);
	}

    
	@Override
	public int hashCode() {
		return Objects.hash(nombre, puestos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Aula))
			return false;
		Aula other = (Aula) obj;
		return Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "nombre=" + getNombre() + ", puestos=" + getPuestos();
	}
	

}
