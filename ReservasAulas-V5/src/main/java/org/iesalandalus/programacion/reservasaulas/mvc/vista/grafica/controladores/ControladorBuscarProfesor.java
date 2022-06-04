package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class ControladorBuscarProfesor {

	private IControlador controladorMVC;
	ControladorVentanaPrincipal cVPrincipal=null;

	public void setControladorMVC(IControlador controlador) {
		controladorMVC = controlador;
	}
	public void setControladorVPrincipal(ControladorVentanaPrincipal controladorVPrincipal) {
		cVPrincipal=controladorVPrincipal;
	}
	
	private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
	
    @FXML private TableView<Profesor> tabProfesores;
    	@FXML private TableColumn<Profesor, String> NombreProf;
        @FXML private TableColumn<Profesor, String> TelefonoProf;
        @FXML private TableColumn<Profesor, String> CorreoProf;


        public void initialize() {
    		tabProfesores.setItems(profesores);
    		NombreProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getNombre()));
    		TelefonoProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getTelefono()));
    		CorreoProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getCorreo()));
    		
        }

    @FXML void BuscarProf(ActionEvent event) {
    	profesores.clear();
    	lbError.setText("");
    	Profesor profesorEncontrado=null;
    	try {
	    	String correoProfesor=tfBuscarProf.getText();
	    	Profesor profesorABuscar=new Profesor("ruben", correoProfesor, "678909876");
	    	profesorEncontrado=controladorMVC.buscarProfesor(profesorABuscar);
    	} catch(Exception e) {
    		Dialogos.mostrarDialogoError("Error", e.getMessage());
    	}
    }
    
    @FXML
    private Label lbError;
    
    @FXML
    private Button btnBuscarProf;
    
    @FXML
    private TitledPane vBuscarProf;
    
    @FXML
    private TextField tfBuscarProf;

}
