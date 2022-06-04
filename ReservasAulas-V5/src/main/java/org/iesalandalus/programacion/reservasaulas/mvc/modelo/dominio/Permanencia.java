package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Permanencia implements Serializable {
	private LocalDate dia;
    protected static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	public Permanencia(LocalDate dia) {
		setDia(dia);
	}

    public Permanencia(Permanencia permanencia){        
        if(permanencia==null){       
            throw new NullPointerException("ERROR: No se puede copiar una permanencia nula.");                
        }else{          
        	setDia(permanencia.getDia());
        }
    }

	public LocalDate getDia() {
		return dia;

	}

	private void setDia(LocalDate dia)  {
		if(dia==null) {
			throw new NullPointerException ("ERROR: El día de una permanencia no puede ser nulo.");
		}
			LocalDate fechaActual = LocalDate.now();
			if(dia.compareTo(fechaActual) < 0) {
				throw new IllegalArgumentException("ERROR: No puedes introducir una fecha anterior a la actual.");
			}
			
			this.dia = dia;

}


	//Metodos abstractos que van a heredar 
	public abstract int getPuntos();
	@Override
	public abstract int hashCode();
	@Override
	public abstract boolean equals(Object obj);

	@Override
	public String toString() {
		return "día=" + this.dia.format(FORMATO_DIA);
	}
	
	public abstract int compareTo(Permanencia otraPermanencia);

}
