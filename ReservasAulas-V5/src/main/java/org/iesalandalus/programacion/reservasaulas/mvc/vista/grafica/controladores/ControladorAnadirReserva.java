package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.time.LocalDate;
import java.time.LocalTime;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ControladorAnadirReserva {
	
    @FXML private TextField tfNombreProf;
    @FXML private TextField tfTelefonoProf;
    @FXML private TextField tfEmailProf;
    @FXML private TextField tfNombre;
    @FXML private TextField tfPuestos;
    
    @FXML
    private RadioButton selHoras;

    @FXML
    private RadioButton selTramo;
    
    @FXML private Button btnAnadir;
    @FXML private Button btnCancelar;

	ControladorVentanaPrincipal cVentanaPrincipal=null;
	private IControlador controlador;  
	private ToggleGroup grupo;

    
	public void setControladorVPrincipal(ControladorVentanaPrincipal controladorVPrincipal) {
		cVentanaPrincipal=controladorVPrincipal;
	}
	
	public void setControladorMVC(IControlador controlador) {
		this.controlador = controlador;
	}
	
	private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
	private ObservableList<Aula> aulas = FXCollections.observableArrayList();
	
	public void inicializa() {
		tfNombreProf.setText("");
		tfEmailProf.setText("");
		tfTelefonoProf.setText("");
		tfNombre.setText("");
		tfPuestos.setText("");
		grupo = new ToggleGroup();
		selHoras.setToggleGroup(grupo);
		selTramo.setToggleGroup(grupo);
		Horas.setVisibleRowCount(5);
		Tramo.setItems(FXCollections.observableArrayList("Mañana","Tarde", "08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00"));
		Fecha.setValue(LocalDate.now().plusMonths(1));
	}
	
	private void rbSeleccionado() {
		  RadioButton seleccionado = (RadioButton)grupo.getSelectedToggle();
		  if (seleccionado==selHoras) {
			  Horas.setDisable(false);
			  Tramo.setDisable(true);
		  }
		  else if (seleccionado==selTramo) {
			  Horas.setDisable(true);
			  Tramo.setDisable(false);
		  }
	}

    @FXML
    private DatePicker Fecha;

    @FXML
    private ComboBox<String> Horas;

    @FXML
    private ComboBox<String> Tramo;

    @FXML
    void anadirReserva(ActionEvent event) {
    	try {
        	RadioButton seleccionado = (RadioButton)grupo.getSelectedToggle();
           	Profesor profesor=null;
        	Aula aula=null;
        	Permanencia permanencia=null;
        	Tramo tramo=null;
         	controlador.realizarReserva(new Reserva(profesor,aula,permanencia));	
			Stage propietario = ((Stage) btnAnadir.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Reserva", "Reserva añadida correctamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Error", e.getMessage());
			tfNombreProf.clear();
			tfEmailProf.clear();
			tfTelefonoProf.clear();
			tfNombre.clear();
			tfPuestos.clear();
		}
    
    }
 
	
    @FXML
    void Cancelar(ActionEvent event) {
    	((Stage) btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    void acPorHoras(ActionEvent event) {
  	  rbSeleccionado();
    }

    @FXML
    void acPorTramo(ActionEvent event) {
  	  rbSeleccionado();
    }
    

    
  
}
