package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.LocalizadorRecursos;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;



public class ControladorVentanaPrincipal {

	private IControlador controladorMVC;
	
	public void setControladorMVC(IControlador controlador) {
		controladorMVC=controlador;
	}

	private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
	private ObservableList<Aula> aulas = FXCollections.observableArrayList();
	private ObservableList<Reserva> reservas = FXCollections.observableArrayList();

	@FXML private TableView<Profesor> tabProfesores;
		@FXML private TableColumn<Profesor, String> NombreProf;
		@FXML private TableColumn<Profesor, String> TelefonoProf;
		@FXML private TableColumn<Profesor, String> CorreoProf;
		
	@FXML private TableView<Aula> tabAulas;
    	@FXML private TableColumn<Aula, String> NombreAula;
    	@FXML private TableColumn<Aula, String> PuestosAula;

	@FXML private TableView<Reserva> tabReservas;
		@FXML private TableColumn<Reserva, String> ProfesorReservas;
		@FXML private TableColumn<Reserva, String> AulaReservas;
		@FXML private TableColumn<Reserva, String> PermanenciaReservas;
		
	private Stage anadirProfesor;
	private ControladorAnadirProfesor cAnadirProf;
	private Stage buscarProfesor;
	private ControladorBuscarProfesor cBuscarProf;
	private Stage anadirAula;
	private ControladorAnadirAula cAnadirAula;
	private Stage buscarAula;
	private ControladorBuscarAula cBuscarAula;
	private Stage anadirReserva;
	private ControladorAnadirReserva cAnadirReserva;
	private Stage buscarReserva;
	private ControladorBuscarReserva cBuscarReserva;
	
 
	@FXML private void initialize() {
		tabProfesores.setItems(profesores);
		NombreProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getNombre()));
		TelefonoProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getTelefono()));
		CorreoProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getCorreo()));

		
		tabAulas.setItems(aulas);
		NombreAula.setCellValueFactory(aula -> new SimpleStringProperty(aula.getValue().getNombre()));
		PuestosAula.setCellValueFactory(aula -> new SimpleStringProperty(String.valueOf(aula.getValue().getPuestos())));
		
		tabReservas.setItems(reservas);
		ProfesorReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getNombre()));
		AulaReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getAula().getNombre()));
		PermanenciaReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getPermanencia().toString()));
	}
    
	
	public void actualizaTablas() {
		profesores.setAll(controladorMVC.getProfesores());
		aulas.setAll(controladorMVC.getAulas());
		reservas.setAll(controladorMVC.getReservas());
	}
	
	
    @FXML void acEliminarAula(ActionEvent event) {
    	Aula aula=null;
    	try {
    		aula=tabAulas.getSelectionModel().getSelectedItem();
    		if (aula!=null && Dialogos.mostrarDialogoConfirmacion("Vas a Borrar el Aula", "¿Estás seguro de que quieres borrar el aula?", null)) {
    			controladorMVC.borrarAula(aula);
    			actualizaTablas();
    		}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Error", e.getMessage());
    	}
    }
    @FXML void acEliminarProf(ActionEvent event) {
    	Profesor profesor=null;
    	try {
    		profesor=tabProfesores.getSelectionModel().getSelectedItem();
    		if (profesor!=null && Dialogos.mostrarDialogoConfirmacion("Vas a Borrar al Profesor", "¿Estás seguro de que quieres borrar el profesor?", null)) {
    			controladorMVC.borrarProfesor(profesor);
    			actualizaTablas();
    		}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Error", e.getMessage());
    	}
    }
    @FXML void acEliminarReserva(ActionEvent event) {
    	Reserva reserva=null;
    	try {
    		reserva=tabReservas.getSelectionModel().getSelectedItem();
    		if (reserva!=null && Dialogos.mostrarDialogoConfirmacion("Vas a Borrar la Reserva", "¿Estás seguro de que quieres borrar la reserva?", null)) {
    			controladorMVC.anularReserva(reserva);;
    			actualizaTablas();
    		}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Error", e.getMessage());
    	}
    }

    @FXML
    void acAnadirAula(ActionEvent event) throws IOException {
    	acAnadirAula();
    	anadirAula.showAndWait();
    }
    private void acAnadirAula() throws IOException {
    	anadirAula = new Stage();
		FXMLLoader cVentanaAnadirAula=new FXMLLoader(LocalizadorRecursos.class.getResource("org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.vistas/AnadirAula.fxml"));
		TitledPane raizAnadirAula=cVentanaAnadirAula.load();
		cAnadirAula=cVentanaAnadirAula.getController();
		cAnadirAula.setControladorMVC(controladorMVC);
		cAnadirAula.setControladorVentanaPrincipal(this);
		
		Scene escenaAnadirAula=new Scene(raizAnadirAula);
		anadirAula.setTitle("Añadir Aula");
		anadirAula.setScene(escenaAnadirAula);
		anadirAula.setResizable(false);
		anadirAula.initModality(Modality.APPLICATION_MODAL);
    }
    
    @FXML
    void acAnadirProf(ActionEvent event) throws IOException {
    	acAnadirProf();
    	anadirProfesor.showAndWait();
    }
    private void acAnadirProf() throws IOException {
    	anadirProfesor = new Stage();
		FXMLLoader cVentanaAnadirProf=new FXMLLoader(LocalizadorRecursos.class.getResource("org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.vistas/AnadirProfesor.fxml"));
		TitledPane raizAnadirProf=cVentanaAnadirProf.load();
		cAnadirProf=cVentanaAnadirProf.getController();
		cAnadirProf.setControladorMVC(controladorMVC);
		cAnadirProf.setControladorVPrincipal(this);
		
		Scene escenaAnadirProf=new Scene(raizAnadirProf);
		anadirProfesor.setTitle("Añadir Profesor");
		anadirProfesor.setScene(escenaAnadirProf);
		anadirProfesor.setResizable(false);
		anadirProfesor.initModality(Modality.APPLICATION_MODAL);
    }
    
    @FXML
    void acAnadirReserva(ActionEvent event) throws IOException {
    	acAnadirReserva();
    	anadirReserva.showAndWait();
    }
    private void acAnadirReserva() throws IOException {
    	anadirReserva = new Stage();
		FXMLLoader cVentanaAnadirReserva=new FXMLLoader(LocalizadorRecursos.class.getResource("org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.vistas/AnadirReserva.fxml"));
		VBox raizAnadirReserva=cVentanaAnadirReserva.load();
		cAnadirReserva=cVentanaAnadirReserva.getController();
		cAnadirReserva.setControladorMVC(controladorMVC);
		cAnadirReserva.setControladorVPrincipal(this);
		
		Scene escenaAnadirReserva=new Scene(raizAnadirReserva);
		anadirReserva.setTitle("Añadir Reserva");
		anadirReserva.setScene(escenaAnadirReserva);
		anadirReserva.setResizable(false);
		anadirReserva.initModality(Modality.APPLICATION_MODAL);
    }

    @FXML
    void acBuscarAula(ActionEvent event) throws IOException {
    	acBuscarAula();
    	buscarAula.showAndWait();
    }
    
    private void acBuscarAula() throws IOException {
    	buscarAula = new Stage();
		FXMLLoader cVentanaBuscarAula=new FXMLLoader(LocalizadorRecursos.class.getResource("org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.vistas/BuscarAula.fxml"));
		VBox raizBuscarAula=cVentanaBuscarAula.load();
		cBuscarAula=cVentanaBuscarAula.getController();
		cBuscarAula.setControladorMVC(controladorMVC);
		// cBuscarAula.setControladorVPrincipal(this);
		
		Scene escenaBuscarAula=new Scene(raizBuscarAula);
		buscarAula.setTitle("Buscar Aula");
		buscarAula.setScene(escenaBuscarAula);
		buscarAula.setResizable(false);
		buscarAula.initModality(Modality.APPLICATION_MODAL);
    }

    @FXML
    void acBuscarProf(ActionEvent event) throws IOException {
    	acBuscarProf();
    	buscarProfesor.showAndWait();
    }
    
    private void acBuscarProf() throws IOException{
    	buscarProfesor=new Stage();
		FXMLLoader cVentanaBuscarProf=new FXMLLoader(LocalizadorRecursos.class.getResource("org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.vistas/BuscarProfesor.fxml"));
		VBox raizBuscarProf=cVentanaBuscarProf.load();
		cBuscarProf=cVentanaBuscarProf.getController();
		cBuscarProf.setControladorMVC(controladorMVC);
		cBuscarProf.setControladorVPrincipal(this);
		
		Scene escenaBuscarProf=new Scene(raizBuscarProf);
		buscarProfesor.setTitle("Buscar Profesor");
		buscarProfesor.setScene(escenaBuscarProf);
		buscarProfesor.setResizable(false);
		buscarProfesor.initModality(Modality.APPLICATION_MODAL);
    }
    @FXML
    void acBuscarReserva(ActionEvent event) throws IOException {
    	acBuscarReserva();
    	buscarReserva.showAndWait();
    }
    
    private void acBuscarReserva() throws IOException {
    	buscarReserva=new Stage();
		FXMLLoader cVentanaBuscarReserva=new FXMLLoader(LocalizadorRecursos.class.getResource("org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.vistas/BuscarReserva.fxml"));
		VBox raizBuscarReserva=cVentanaBuscarReserva.load();
		cBuscarReserva=cVentanaBuscarReserva.getController();
		cBuscarReserva.setControladorMVC(controladorMVC);
		cBuscarReserva.setControladorVPrincipal(this);
		
		Scene escenaBuscarReserva=new Scene(raizBuscarReserva);
		buscarReserva.setTitle("Buscar Reserva");
		buscarReserva.setScene(escenaBuscarReserva);
		buscarReserva.setResizable(false);
		buscarReserva.initModality(Modality.APPLICATION_MODAL);
    }


    @FXML
    void acSalir(ActionEvent event) {
    	if(Dialogos.mostrarDialogoConfirmacion("Salir", "¿Seguro que desea cerrar la aplicación?", null)) {
    		controladorMVC.terminar();
    		System.exit(0);
    	}
    }
    
 
  
}