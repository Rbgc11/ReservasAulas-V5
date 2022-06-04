	package org.iesalandalus.programacion.reservasaulas.mvc.vista.texto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.utilidades.Entrada;



public class Consola {

	  private static final DateTimeFormatter FORMATO_DIA= DateTimeFormatter.ofPattern("dd/MM/yyyy");


	    private Consola() {   
	    }
	    
	    //Método mostrarMenu
	    public static void mostrarMenu() {
	            mostrarCabecera("Reservas de aulas");
	            for (Opcion opcion: Opcion.values()) {
	                    System.out.println(opcion);
	            }
	    }

	    //Método mostrarCabecera
	    public static void mostrarCabecera(String mensaje) {
	            System.out.printf("%n%s%n", mensaje);
	            String cadena = "%0" + mensaje.length() + "d%n";
	            System.out.println(String.format(cadena, 0).replace("0", "-"));
	    }

	    //Método elegirOpcion
	    public static int elegirOpcion() {
	            int ordinalOpcion;
	                
	            do {
	                    System.out.print("Escoge una opción: ");
	                    ordinalOpcion = Entrada.entero() - 1;
	            } while (!Opcion.esOrdinalValido(ordinalOpcion));
	         return ordinalOpcion;
	    }

	    //Método leerAula
	    public static Aula leerAula(){
			System.out.println("Introduce el nombre del aula y número puestos de ese aula: ");
	   		Aula aula = new Aula(leerNombreAula(), leerNumeroPuestos());
			return new Aula(aula);
		}
	    
	 // Método leerNumeroPuestos
		public static int leerNumeroPuestos() {
			System.out.println("Introduzca el número de puestos del aula");
			int puestos = Entrada.entero();
			return puestos;
		}

		// Método leerAulaFicticia
		public static Aula leerAulaFicticia() {
			System.out.println("Introduce el nombre del aula: ");
			return Aula.getAulaFicticia(Entrada.cadena());

		}
	    
	    //Método leerNombreAula
	    public static String leerNombreAula(){
	    	String nombreAula = null;
	        do{
	            System.out.println("Introduce el nombre del aula");
	            nombreAula=Entrada.cadena();
	        }while(nombreAula==null||nombreAula.trim().equals (""));
	        return nombreAula;
	    }

	    //Método leerProfesor
	    public static Profesor leerProfesor(){

	        System.out.println("Introduce el correo: ");
	        String correo = Entrada.cadena();
	        
	        System.out.println("Introduce el teléfono: ");
	        String telefono = Entrada.cadena();
	        
	        
	        if(telefono==null || telefono.trim().equals("")){
	        	return new Profesor(leerNombreProfesor(), correo);
	        }else{
	        	return new Profesor(leerNombreProfesor(), correo, telefono);
	        }
	     
	     
	    }
	    
	    //Método leerNombreProfesor
	    public static String leerNombreProfesor(){
	            System.out.println("Introduce nombre del profesor: ");
	            String nombre=Entrada.cadena();       
	        return nombre;
	    }
	    

		public static Profesor leerProfesorFicticio() {
			System.out.println("Introduzca el correo del profesor");
			return Profesor.getProfesorFicticio(Entrada.cadena());
		}

	    
	    //Método leerTramo
	    public static Tramo leerTramo(){
	        int numero= 0;
	     do{
	        System.out.println("Elige tramo horario: 1=Por la mañana y 2 = Por la tarde");
	        numero=Entrada.entero();
	     }while(numero!=1&&numero!=2);
	     
	        if(numero==1){
	            return Tramo.MANANA;
	        }
	        if(numero==2){
	        
	            return Tramo.TARDE;
	        }
	        return null;
	    }
	   
	    //Método leerDia
	    public static LocalDate leerDia(){
	     LocalDate dia=null;
	     System.out.println("Introduce una fecha con el formato dd/mm/aaaa: ");
	        	try {
	        			 dia=LocalDate.parse(Entrada.cadena(), FORMATO_DIA);
	        			 
	        	}catch (DateTimeParseException e) {
					System.out.println("Formato incorrecto");
				}


	        return dia;
	    }
	    
	 // Método elegirPermanencia
		public static int elegirPermanencia() {
			int permanenciaElegida = 0;
			do {
				System.out.println("Seleccione una permanencia:");
				System.out.println("1- Por tramo (mañana o tarde)");
				System.out.println("2- Por horas");
				permanenciaElegida = Entrada.entero();
			} while (permanenciaElegida < 1 || permanenciaElegida > 2);
			return permanenciaElegida;
		}

		//Método leerPermanencia
		public static Permanencia leerPermanencia() {
			Permanencia permanenciaFinal=null;
			int permanenciaElegida = elegirPermanencia();
			if(permanenciaElegida==1) {
				permanenciaFinal=new PermanenciaPorTramo(leerDia(),leerTramo());
			}
			else {
				permanenciaFinal=new PermanenciaPorHora(leerDia(),leerHora());
			}
			if (permanenciaFinal instanceof PermanenciaPorTramo)
				return new PermanenciaPorTramo((PermanenciaPorTramo) permanenciaFinal);
			else {
				return new PermanenciaPorHora((PermanenciaPorHora) permanenciaFinal);
			}
		}
		
		//Método leerHora
		private static LocalTime leerHora() {
			LocalTime hora = null;
			boolean problema = false;
			do {
				try {
					System.out.println("Introduzca una hora con el formato hh:00)");
					String horaIntroducida = Entrada.cadena();
					hora = LocalTime.parse(horaIntroducida);
					problema = false;
				} catch (DateTimeParseException e) {
					System.out.println(" Formato incorrecto");
					problema = true;
				}
			} while (problema == true);
			return hora;
		}


		//Método leerReserva
		public static Reserva leerReserva() {
			Profesor profesor = leerProfesorFicticio();
			Aula aula = leerAulaFicticia();
			Permanencia permanencia = leerPermanencia();
			return new Reserva(profesor, aula, permanencia);
		}
		
		//Método leerReservaFicticia
		public static Reserva leerReservaFicticia() {
			return Reserva.getReservaFicticia(leerAulaFicticia(), leerPermanencia());

		}
		
	}

