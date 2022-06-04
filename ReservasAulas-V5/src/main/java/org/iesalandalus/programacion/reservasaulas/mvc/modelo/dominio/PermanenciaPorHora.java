package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class PermanenciaPorHora  extends Permanencia implements Comparable<Permanencia> {
	private final static int PUNTOS = 3;
	private final static LocalTime HORA_INICIO = LocalTime.of(8, 0);
	private final static LocalTime HORA_FIN = LocalTime.of(22, 0);
	protected final static DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
	private LocalTime hora;
	
//	Constructor 
	public PermanenciaPorHora(LocalDate dia, LocalTime hora) {
		super(dia);
		setHora(hora);
	}

//	Constructor copia 
	public PermanenciaPorHora(PermanenciaPorHora permanencia) {
		super(permanencia);
		if (permanencia == null) {
			throw new NullPointerException (" No se puede copiar una permanencia nula.");
		} else {
		setHora(permanencia.getHora());
		}
	}


//	Getters y setters
	public LocalTime getHora() {
		return hora;
	}

	private void setHora(LocalTime hora) {
		if (hora == null) {
			throw new NullPointerException(" La hora de una permanencia no puede ser nula.");
		} else if (hora.isBefore(HORA_INICIO) || hora.isAfter(HORA_FIN)) {
			throw new IllegalArgumentException("La hora de una permanencia no es válida.");
		} else if (hora.getMinute() != 0) {
			throw new IllegalArgumentException(" La hora de una permanencia debe ser una hora en punto.");
		}
		this.hora = hora;
	}


	@Override
	public int getPuntos() {
		return PUNTOS;
	}

//	Métodos hashChode y equals
	@Override
	public int hashCode() {
		return Objects.hash(hora);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermanenciaPorHora other = (PermanenciaPorHora) obj;
		return Objects.equals(getDia(), other.getDia()) && hora == other.hora;
	}

	//Método toString
	@Override
	public String toString() {
		return super.toString() + ", hora=" + hora + "";
	}

	// Método compareTo realizado como en PermanenciaPorTramo, en este caso se realiza un casting implícito 
	// con PermanenciaPorHora
	@Override
	public int compareTo(Permanencia otraPermanencia) {
		return this.getHora().compareTo(((PermanenciaPorHora)otraPermanencia).getHora());
	}
}