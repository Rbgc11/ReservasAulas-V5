
package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public class Reserva implements Comparable<Reserva>, Serializable {
	//Atributos
	private Profesor profesor;
	private Aula aula;
	private Permanencia permanencia;

	public Reserva(Profesor profesor, Aula aula, Permanencia permanencia) {
		setProfesor(profesor);
		setAula(aula);
		setPermanencia(permanencia);
	}

	//Constructor copia
	public Reserva(Reserva reserva)  {
		if(reserva == null) {
			throw new NullPointerException("No se puede copiar una reserva nula.");
		} else {
	       setProfesor(reserva.getProfesor());
	       setAula(reserva.getAula());
	       setPermanencia(reserva.getPermanencia());
		}
	}



	//Setter y Getters
	private void setProfesor(Profesor profesor)  {
		if (profesor == null) {
			throw new NullPointerException("No pueden haber valores nulos");
		} else {
			this.profesor = new Profesor(profesor);
		}
	}

	public Profesor getProfesor() {
		return new Profesor(profesor);
	}

	private void setAula(Aula aula)  {
		if (aula == null) {
			throw new NullPointerException("No pueden haber valores nulos");
		} else {
			this.aula = new Aula(aula);
		}
	}

	public Aula getAula() {
		return new Aula(aula);
	}

	//Set de Permanencia
	private void setPermanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new NullPointerException("No pueden haber valores nulos");
			}
			else if (permanencia instanceof PermanenciaPorTramo) {
				this.permanencia = new PermanenciaPorTramo((PermanenciaPorTramo) permanencia);
			}
			else if (permanencia instanceof PermanenciaPorHora) {
				this.permanencia = new PermanenciaPorHora((PermanenciaPorHora) permanencia);
			}
		}
	
	//Get de Permanencia
		public Permanencia getPermanencia() {
			Permanencia permanenciaC = null;
			if (permanencia instanceof PermanenciaPorTramo) {
				permanenciaC = new PermanenciaPorTramo((PermanenciaPorTramo) permanencia); 
			}
			else if (permanencia instanceof PermanenciaPorHora) {
				permanenciaC = new PermanenciaPorHora((PermanenciaPorHora) permanencia);
			}
			return permanenciaC;
		}

		//Método getReservaFicticia
	public static Reserva getReservaFicticia(Aula aula, Permanencia permanencia) {
		
		Reserva reserva=new Reserva(Profesor.getProfesorFicticio("ruben@gmail.com"),aula,permanencia);
		return new Reserva(reserva);
	}
	//Método getPuntos, recoge los puntos de permanencia y aula
	public float getPuntos() {
		return permanencia.getPuntos() + aula.getPuntos();
	}

	


	@Override
	public int hashCode() {
		return Objects.hash(aula, permanencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Reserva))
			return false;
		Reserva other = (Reserva) obj;
		return Objects.equals(aula, other.aula) && Objects.equals(permanencia, other.permanencia);
	}

	//Método toString
		@Override
		public String toString() {
			return profesor.toString() + ", " + aula.toString() + ", " + permanencia.toString() + ", puntos=" + String.format("%.1f", getPuntos()) + "";
		}
		
		//Método compareTo
		@Override
		public int compareTo(Reserva o) {
			int comparadorAula = getAula().getNombre().compareTo(o.getAula().getNombre());

			if (comparadorAula == 0) {

				int comparadorFecha = getPermanencia().getDia().compareTo(o.getPermanencia().getDia());
				if (comparadorFecha == 0) {
					if (getPermanencia() instanceof PermanenciaPorTramo
							&& o.getPermanencia() instanceof PermanenciaPorTramo) {
						if (((PermanenciaPorTramo) getPermanencia()).getTramo() == Tramo.MANANA
								&& ((PermanenciaPorTramo) o.getPermanencia()).getTramo() == Tramo.TARDE) {
							return -1;
						} else if (((PermanenciaPorTramo) getPermanencia()).getTramo() == Tramo.TARDE
								&& ((PermanenciaPorTramo) o.getPermanencia()).getTramo() == Tramo.MANANA) {
							return 1;
						} else {
							return 0;
						}
					} else {
						return ((PermanenciaPorHora) getPermanencia()).getHora()
								.compareTo(((PermanenciaPorHora) o.getPermanencia()).getHora());
					}
				}
				return comparadorFecha;

			}
			return comparadorAula;
		}

		public String getNombre() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getTelefono() {
			// TODO Auto-generated method stub
			return null;
		}

	}