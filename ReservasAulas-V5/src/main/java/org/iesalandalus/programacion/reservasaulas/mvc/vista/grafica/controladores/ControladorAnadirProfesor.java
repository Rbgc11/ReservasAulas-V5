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
	
    @FXML private TextField tfNombreProf;
    @FXML private TextField tfApellidosProf;
    @FXML private TextField tfTelefonoProf;
    @FXML private TextField tfEmailProf;
	ControladorVentanaPrincipal cVentanaPrincipal=null;
	private IControlador controladorMVC;
	
	public void setControladorVPrincipal(ControladorVentanaPrincipal controladorVPrincipal) {
		cVentanaPrincipal=controladorVPrincipal;
	}
	
	public void setControladorMVC(IControlador controlador) {
		controladorMVC=controlador;
	}

	public void initialize() {
		tfNombreProf.textProperty().addListener((ob, ov, nv) -> compruebaNombre(ov, nv));
		tfApellidosProf.textProperty().addListener((ob, ov, nv) -> compruebaApellido(ov, nv));
		tfEmailProf.textProperty().addListener((ob, ov, nv) -> compruebaEMail(ov, nv));
		tfTelefonoProf.textProperty().addListener((ob, ov, nv) -> compruebaTelefono(ov, nv));
	}

    @FXML
    void anadirProfesor(ActionEvent event) {
    	Profesor profesor=null;
    	try {
    		profesor=crearProfesor();
    		controladorMVC.insertarProfesor(profesor);
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

    private void compruebaNombre (String ov, String nv) {
		if (nv.matches("[^0-9]*")) {
			tfNombreProf.setStyle("-fx-border-color: green");
		} else{
			tfNombreProf.setText(ov);
			tfNombreProf.setStyle("-fx-border-color: red");
		}
    }
    
    private void compruebaApellido (String ov, String nv) {
		if (nv.matches("[^0-9]*")) {
			tfApellidosProf.setStyle("-fx-border-color: green");
		} else{
			tfApellidosProf.setText(ov);
			tfApellidosProf.setStyle("-fx-border-color: red");
		}
    }
    
    private void compruebaEMail (String ov, String nv) {
		if (nv.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
			tfEmailProf.setStyle("-fx-border-color: green");
		} else{
			tfEmailProf.setStyle("-fx-border-color: red");
		}
    }
    
    private void compruebaTelefono (String ov, String nv) {
		if (nv.matches("([0-9]{9})")) {
			tfTelefonoProf.setStyle("-fx-border-color: green");
		} else{
			// inputTelfProf.setText(ov);
			tfTelefonoProf.setStyle("-fx-border-color: red");
		}
    }
    
    private Profesor crearProfesor() {
    	String nombre=tfNombreProf.getText();
    	String apellido=tfApellidosProf.getText();
    	String email=tfEmailProf.getText();
    	String telefono=tfTelefonoProf.getText();
    	Profesor profesor= new Profesor(nombre,email,telefono);
    	return new Profesor(profesor);
    }
    
    
    @FXML private Button btnAceptar;
    @FXML private Button btnCancelar;
    // @FXML private TitledPane vAnadirProf;
}
