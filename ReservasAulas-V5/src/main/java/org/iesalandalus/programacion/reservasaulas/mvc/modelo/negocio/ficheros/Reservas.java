package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;



import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class Reservas implements IReservas{
	// Atributos
	private static final float MAX_PUNTOS_PROFESOR_MES = 200.0f;
	private List<Reserva> coleccionReservas;
	private static final String NOMBRE_FICHEROS_RESERVAS = "datos/reservas.dat";

	// Constructor por defecto
	public Reservas() {
		coleccionReservas = new ArrayList<>();
	}

	// Constructor copia
	public Reservas(IReservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas.");
		} else {
			setReservas(reservas);
		}
	}
	//Método comenzar
	@Override
	public void comenzar() {
		leer();
	}
	
	//Método leer
	private void leer() {
		File dtFicherosReservas = new File(NOMBRE_FICHEROS_RESERVAS);
		try {
		ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(dtFicherosReservas));
		Reserva reserva = null;
		do {
			reserva = (Reserva) objIn.readObject();
			insertar(reserva);
		}while(reserva != null);
		objIn.close();
			
		} catch (ClassNotFoundException e)  {
			System.out.println("ERROR: No se puede encontrar la clase para leer.");	
		} catch (FileNotFoundException e)  {
			System.out.println("ERROR: No se puede abrir el fichero de reservas.");	
		} catch (EOFException e)  {
			System.out.println("Fichero reservas leído satisfactoriamente.");	
		} catch (IOException e)  {
			System.out.println("ERROR inesperado de Entrada/Salida.");	
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
		File dtFicherosReservas = new File(NOMBRE_FICHEROS_RESERVAS);
		try {
			FileOutputStream fileOut=new FileOutputStream(dtFicherosReservas);
			ObjectOutputStream objOut=new ObjectOutputStream(fileOut);
			for (Reserva reserva : coleccionReservas)
				objOut.writeObject(reserva);
			objOut.close();
		System.out.println("Fichero reservas escrito satisfactoriamente.");
		} catch (FileNotFoundException e)  {
			System.out.println(" ERROR: No se puede abrir el fichero de reservas.");	
		} catch (IOException e)  {
			System.out.println("Erro inesperado.");	
		}
	}

	// Método setReservas
	private void setReservas(IReservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se puede copiar una reserva nula.");
		} else {
			this.coleccionReservas = reservas.getReservas();
		}
	}

	// Método copiaProfundaReservas
	private List<Reserva> copiaProfundaReservas(List<Reserva> listaReservas) {
		List<Reserva> copiaProReservas = new ArrayList<Reserva>();
		Iterator<Reserva> iterador = listaReservas.iterator();
		while (iterador.hasNext()) {
			copiaProReservas.add(new Reserva(iterador.next()));
		}
		//Collections.sort(copiaProReservas);
		return copiaProReservas;
	}

	// Método List<Reservas> getReservas()
	public List<Reserva> getReservas() {
		return copiaProfundaReservas(coleccionReservas);
	}

	// Método getNumReservas
	public int getNumReservas() {
		return coleccionReservas.size();

	}

	// Método insertar
	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
				throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
			}

			Reserva reservaNueva = getReservaAulaDia(reserva.getAula(), reserva.getPermanencia().getDia());
			if (reservaNueva != null) {

				if (reservaNueva.getPermanencia() instanceof PermanenciaPorTramo
						&& reserva.getPermanencia() instanceof PermanenciaPorHora) {
					throw new OperationNotSupportedException(
							" ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este día.");
				}
				if (reservaNueva.getPermanencia() instanceof PermanenciaPorHora
						&& reserva.getPermanencia() instanceof PermanenciaPorTramo) {
					throw new OperationNotSupportedException(
							" ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este día.");
				}
			}
			if (!esMesSiguienteOPosterior(reserva)) {
				throw new OperationNotSupportedException(
						" ERROR: Sólo se pueden hacer reservas para el mes que viene o posteriores.");
			}
			if (getPuntosGastadosReserva(reserva) > MAX_PUNTOS_PROFESOR_MES) {
				throw new OperationNotSupportedException(
						" ERROR: Esta reserva supera los puntos máximos por mes para dicho profesor.");
			}
			if (coleccionReservas.contains(reserva)) {
				throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
			}
			else {
				coleccionReservas.add(new Reserva(reserva));
			}

		}

	// Método esMesSiguienteOPosterior
	private boolean esMesSiguienteOPosterior(Reserva reserva) {
		if (reserva == null) {
			throw new NullPointerException("ERROR: La reserva no puede ser nula");
		}
		boolean mesSiguiente = false;
		if (reserva.getPermanencia().getDia().compareTo(LocalDate.now().plusMonths(1).withDayOfMonth(1)) != -1) {
			mesSiguiente = true;
		}
		return mesSiguiente;


	}
	
	
	//Método getPuntosGastadosReserva
	private float getPuntosGastadosReserva(Reserva reserva) {
		return reserva.getPuntos();
	}

	// Método List<Reserva> getReservasProfesorMes
	private List<Reserva> getReservasProfesorMes(Profesor profesor, LocalDate fecha) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo");
		} else if (fecha == null) {
			throw new NullPointerException(" ERROR:La fecha no puede ser nula");
		}
		List<Reserva> reservasMes = new ArrayList<>();
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva reserva = iterador.next();
			Month mes = reserva.getPermanencia().getDia().getMonth();
			Month mesFecha = fecha.getMonth();
			if (profesor.equals(reserva.getProfesor()) && mes.getValue() == mesFecha.getValue()) {
				reservasMes.add(new Reserva(reserva));
			}
		}
		return reservasMes;
	}

	
	// Método Reserva getReservaAulaDia(Aula, LocalDate)
	public Reserva getReservaAulaDia(Aula aula, LocalDate fecha) {
		if (aula == null) {
			throw new NullPointerException("ERROR:El aula no puede ser nula");
		} else if (fecha == null) {
			throw new NullPointerException(" ERROR: La fecha no puede ser nula");
		}
		Reserva reservaADia = null;
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva reserva = iterador.next();
			if (aula.equals(reserva.getAula()) && fecha.equals(reserva.getPermanencia().getDia())) {
				reservaADia = new Reserva(reserva);
			}
		}
		return reservaADia;
	}

	// Método buscar
	public Reserva buscar(Reserva reserva) {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
		}
		if (coleccionReservas.isEmpty()) {
			return null;
		}

		int indice = coleccionReservas.indexOf(reserva);

		if (coleccionReservas.contains(reserva)) {
			return new Reserva(coleccionReservas.get(indice));
		} else {
			return null;
		}
	}
	// Método borrar
	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede anular una reserva nula.");
		} else if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException("ERROR: La reserva a anular no existe.");
		} else if (coleccionReservas.contains(reserva)) {
			coleccionReservas.remove(reserva);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como esa.");
		}
	}

	// Metodo representar
	public List<String> representar() {
		List<String> representacion = new ArrayList<String>();
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			representacion.add(iterador.next().toString());
		}
		return representacion;
	}

	// Método List<Reserva> getReservasProfesor(Profesor)
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		List<Reserva> reservas = new ArrayList<>();
		Iterator<Reserva> it = coleccionReservas.iterator();

		while (it.hasNext()) {
			Reserva proxReserva = it.next();
			if (profesor.equals(proxReserva.getProfesor())) {
				reservas.add(new Reserva(proxReserva));
			}
		}
		Comparator<Profesor> comparadorProfesor = Comparator.comparing(Profesor::getCorreo);
		Comparator<Aula> comparadorAula = Comparator.comparing(Aula::getNombre);
		Comparator<Permanencia> comparadorPermanencia = (Permanencia p1, Permanencia p2) -> {
			int comparacion = -1;
			if (p1.getDia().equals(p2.getDia())) {
				if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
					comparacion = Integer.compare(((PermanenciaPorTramo) p1).getTramo().ordinal(),
							((PermanenciaPorTramo) p2).getTramo().ordinal());
				} else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
					comparacion = ((PermanenciaPorHora) p1).getHora().compareTo(((PermanenciaPorHora) p2).getHora());
				}
			} else {
				comparacion = p1.getDia().compareTo(p2.getDia());
			}
			return comparacion;
		};
		reservas.sort(Comparator.comparing(Reserva::getProfesor, comparadorProfesor)
				.thenComparing(Reserva::getAula, comparadorAula)
				.thenComparing(Reserva::getPermanencia, comparadorPermanencia));
		return reservas;
	}

	// Método List<Reserva> getReservasAula(Aula)
	public List<Reserva> getReservasAula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException(" ERROR: No se puede reservar un aula nula.");
		}
		List<Reserva> listaResAula = new ArrayList<Reserva>();
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva reserva = iterador.next();
			if (aula.equals(reserva.getAula())) {
				listaResAula.add(new Reserva(reserva));
			}
		}
		Collections.sort(listaResAula);
		return listaResAula;
	}

	// Método List<Reserva> getReservasPermanencia(Permanencia)
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new NullPointerException("No se puede reservar con una permanencia nula.");
		}
		List<Reserva> listaResPermanencia = new ArrayList<>();
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva reserva = iterador.next();
			if (permanencia.equals(reserva.getPermanencia())) {
				listaResPermanencia.add(new Reserva(reserva));
			}
		}
		Collections.sort(listaResPermanencia);
		return listaResPermanencia;
	}

	// Método consultarDisponibilidad(Aula,Permanencia)
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		
		boolean disponible = true;
		
		if(aula == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un aula nula.");
		}
		if(permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}
			for (Iterator<Reserva> I = getReservas().iterator(); I.hasNext();) {
				
				Reserva reserva = I.next();
				if (reserva.getAula().equals(aula)&& reserva.getPermanencia().equals(permanencia)) {
					disponible = false;
				} 
		}
		return disponible;
	}
}