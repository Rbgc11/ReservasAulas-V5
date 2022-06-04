package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.util.List;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
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

public class ControladorBuscarAula {

	private IControlador controladorMVC;

	public void setControladorMVC(IControlador controlador) {
		controladorMVC = controlador;
	}
	private ObservableList<Aula> aulas = FXCollections.observableArrayList();
	
    @FXML private TableView<Aula> tabAulas;
    	@FXML private TableColumn<Aula, String> NombreAula;
        @FXML private TableColumn<Aula, String> PuestosAula;
        


    public void inicializa() {
		tabAulas.setItems(aulas);
		NombreAula.setCellValueFactory(aula -> new SimpleStringProperty(aula.getValue().getNombre()));
		PuestosAula.setCellValueFactory(aula -> new SimpleStringProperty(String.valueOf(aula.getValue().getPuestos())));
		
    }

    @FXML
    void BuscarAula(ActionEvent event) {
    	aulas.clear();
    	lbError.setText("");
    	Aula aulaEncontrada=null;
    	try {
	    	String nombreAula=tfBuscarAula.getText();
	    	Aula aulaABuscar=new Aula(nombreAula,20);
	    	aulaEncontrada=controladorMVC.buscarAula(aulaABuscar);
    	} catch(Exception e) {
    		Dialogos.mostrarDialogoError("Error", e.getMessage());
    	}
  
    }

    @FXML private Label lbError;
    
    @FXML
    private Button btnBuscarAula;
    
    @FXML
    private TextField tfBuscarAula;
    
   
    
}
