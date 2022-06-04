package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

public class ControladorAnadirProfesor {
	
    @FXML private Button btnAceptar;
    @FXML private Button btnCancelar;
	
    @FXML private TextField tfNombreProf;
    @FXML private TextField tfApellidosProf;
    @FXML private TextField tfTelefonoProf;
    @FXML private TextField tfEmailProf;
	ControladorVentanaPrincipal cVentanaPrincipal=null;
	private IControlador controlador;
	
	
	public void setControladorVPrincipal(ControladorVentanaPrincipal controladorVPrincipal) {
		cVentanaPrincipal=controladorVPrincipal;
	}
	
	public void setControladorMVC(IControlador controlador) {
		this.controlador = controlador;
	}

	public void inicializa() {
		tfNombreProf.setText("");
		tfApellidosProf.setText("");
		tfEmailProf.setText("");
		tfTelefonoProf.setText("");
	}


	@FXML
    void anadirProfesor(ActionEvent event) {
    	try {
        	Profesor profesor=null;
    		profesor=crearProfesor();
    		controlador.insertarProfesor(profesor);
			cVentanaPrincipal.actualizaTablas();
			Stage propietario = ((Stage) btnAceptar.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Profesor", "Profesor añadido correctamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Error", e.getMessage());
			tfNombreProf.clear();
			tfApellidosProf.clear();
			tfEmailProf.clear();
			tfTelefonoProf.clear();
		}
    
    }
	
	

    @FXML
    void Cancelar(ActionEvent event) {
    	((Stage) btnCancelar.getScene().getWindow()).close();
    }

    
    private Profesor crearProfesor() {
    	String nombre=tfNombreProf.getText();
    	String apellido=tfApellidosProf.getText();
    	String email=tfEmailProf.getText();
    	String telefono=tfTelefonoProf.getText();
    	Profesor profesor= new Profesor(nombre,email,telefono);
    	return new Profesor(profesor);
    }
    
    
}
