package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirAula {

	@FXML private Button btnAnadir;
	@FXML private Button btnCancelar;
	
	@FXML private TextField tfNombre;
	@FXML private TextField tfPuestos;
	
	ControladorVentanaPrincipal cVentanaPrincipal=null;
	private IControlador controlador;

	
	public void setControladorMVC(IControlador controlador) {
		this.controlador = controlador;
	}

	public void inicializa() {
		tfNombre.setText("");
		tfPuestos.setText("");
	}

	public void setControladorVentanaPrincipal(ControladorVentanaPrincipal cVentanaPrincipal) {
		this.cVentanaPrincipal = cVentanaPrincipal;
	}


	
	@FXML
	void acAnadirAula(ActionEvent event) {
		try {
			Aula aula = null;
			aula = crearAula();
			controlador.insertarAula(aula);
			cVentanaPrincipal.actualizaTablas();
			Stage propietario = ((Stage) btnAnadir.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Aula", "Aula añadida correctamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Error", e.getMessage());
			tfNombre.clear();
			tfPuestos.clear();
		}
	}

	//Botón cancelar
	@FXML
	void acCancelar(ActionEvent event) {
		((Stage) btnCancelar.getScene().getWindow()).close();
	}

	private Aula crearAula() {
		Aula aula = null;
		int puestos = 0;
			String nombre = tfNombre.getText();
			try {
				puestos = Integer.valueOf(tfPuestos.getText());
			} catch (Exception e){}
			aula = new Aula(nombre, puestos);
		return new Aula(aula);
	}

}
