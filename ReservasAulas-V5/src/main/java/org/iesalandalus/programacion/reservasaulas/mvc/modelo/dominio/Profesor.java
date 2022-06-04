package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.util.Objects;
import java.io.Serializable;


public class Profesor implements Serializable {
    private static final String ER_TELEFONO = "[6-9][0-9]{8}";
    private static final String ER_CORREO = "([a-zA-z0-9.-_]{1,})(\\@[a-zA-z]{1,})(\\.[a-z]{1,3})";
    private String nombre;
    private String correo;
    private String telefono;
    
    public Profesor(String nombre, String correo) {
        setNombre (nombre);
        setCorreo (correo);
    }
    
    public Profesor(String nombre, String correo, String telefono) {
        setNombre(nombre);
        setCorreo(correo);
        setTelefono(telefono);
    }
    //Constructor Copia
    public Profesor (Profesor profesor) {
        if (profesor == null) {
			throw new NullPointerException("No se puede copiar un profesor nulo.");
		} else {
		setNombre(profesor.getNombre());
		setTelefono(profesor.getTelefono());
		setCorreo(profesor.getCorreo());
		}
    }
    
    //Set y Get de los atributos. El set usa el formateaNombre
    private void setNombre(String nombre) {        
        if(nombre==null){       
            throw new NullPointerException("El nombre del profesor no puede ser nulo.");
        }
        else if(nombre.trim() .equals("")){        
            throw new IllegalArgumentException("El nombre del profesor no puede estar vacío.");       
        }
            this.nombre = formateaNombre(nombre);
    }
    
 
  //Método formateaNombre
  		private String formateaNombre(String nombre) {
  			nombre = nombre.trim().replaceAll("\\s{2,}", " ").toLowerCase();
  			String [] formatea = nombre.split(" ");
  			String Nombre2 = "";
  			for (int i=0; i<=formatea.length-1; i++) {
  				formatea[i] = formatea[i].substring(0,1).toUpperCase() + formatea[i].substring(1).toLowerCase();
  				Nombre2 = Nombre2 + formatea[i] + " ";
  			}
  			nombre = Nombre2.trim();
  			return nombre;
  		}


    
    //Set de Correo
    public void setCorreo(String correo) {       
        if(correo==null){
            throw new NullPointerException("El correo del profesor no puede ser nulo.");      
        }
        if(correo.trim().equals("") || !correo.matches(ER_CORREO)){        
            throw new IllegalArgumentException("El correo del profesor no es válido.");         
        }
            this.correo=correo;        
    }
    
    //Set de Telefono
	public void setTelefono(String telefono){
		if (telefono == null) {
			this.telefono = null;
		} 
		else if (telefono.trim().equals("") || !telefono.matches(ER_TELEFONO)) {
		      throw new IllegalArgumentException("El teléfono del profesor no es válido.");
		    }

		    this.telefono = telefono;
		  }

    public String getNombre() {
        return nombre;

    }

    
    public String getCorreo() {
        return correo;
    }



    public String getTelefono() {
        return telefono;
    }
    
    //Método getProfesorFicticio
  	public static Profesor getProfesorFicticio (String correo) {
  		Profesor profesor=new Profesor("Profesor",correo,"678909876");
  		return new Profesor(profesor);
  	}
	@Override
	public int hashCode() {
		return Objects.hash(correo, nombre, telefono);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Profesor))
			return false;
		Profesor other = (Profesor) obj;
		return Objects.equals(correo, other.correo);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("nombre=");
		sb.append(this.nombre);
		sb.append(", correo=");
		sb.append(this.correo);
		if (this.telefono !=null) {
			sb.append(", teléfono=");
			sb.append(this.telefono);
		}
		return sb.toString();
	}

}


