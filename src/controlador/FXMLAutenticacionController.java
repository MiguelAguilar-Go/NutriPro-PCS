/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.dao.AutenticacionDAO;
import nutricionalappfx.NutricionalAppFX;
import nutricionalappfx.dominio.AutenticacionDM;
import nutricionalappfx.dominio.pojo.Autenticacion;
import nutricionalappfx.dominio.pojo.Colaborador;
import utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @author Miguel Aguilar
 */
public class FXMLAutenticacionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfContrasenia;
    @FXML
    private Label txtErrorUsuario;
    @FXML
    private Label txtErrorContrasenia;
    @FXML
    private VBox vBoxImagen;
    @FXML
    private ImageView imagenFondo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenFondo.fitWidthProperty().bind(vBoxImagen.widthProperty());
        imagenFondo.fitHeightProperty().bind(vBoxImagen.heightProperty());
    }    

    @FXML
    private void handleBtnIngresar(ActionEvent event) {
        String noPersonal = tfUsuario.getText();
        String password = tfContrasenia.getText();
        if(sonCamposValidos()){
            validarCredenciales(noPersonal, password);
        }
    }
    
    private void validarCredenciales(String noPersonal, String password){
        AutenticacionDM autenticacionDM = new AutenticacionDM(new AutenticacionDAO());
        try {
            Autenticacion respuesta = autenticacionDM.verificarCredencialesColaborador(noPersonal, password);
            if(respuesta.isCredencialesCorrectas() && respuesta.getActivo()){
                Utilidades.mostrarAlertaSimple("Credenciales correctas", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
                irPantallaPrincipal(respuesta.getColaborador());//Se agregaaa
            } else {
                Utilidades.mostrarAlertaSimple("Lo sentimos :(", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            }
        } catch (RuntimeException e) {
            Utilidades.mostrarAlertaSimple("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    
    private boolean sonCamposValidos(){
        boolean esValido = true;
        txtErrorUsuario.setText("");
        txtErrorUsuario.setVisible(false);
        txtErrorContrasenia.setText("");
        txtErrorContrasenia.setVisible(false);
        if(tfUsuario.getText().isEmpty()){
            txtErrorUsuario.setText("Campo obligatorio");
            txtErrorUsuario.setVisible(true);
            esValido = false;
        }
        if(tfContrasenia.getText().isEmpty()){
            txtErrorContrasenia.setText("Campo obligatorio");
            txtErrorContrasenia.setVisible(true);
            esValido = false;
        }
        return esValido;
    }
    
    private void irPantallaPrincipal(Colaborador colaborador) {
        try {
            Stage escenario = (Stage) tfUsuario.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(NutricionalAppFX.class.getResource("/vista/FXMLPrincipal.fxml"));
            Parent vista = loader.load();
            FXMLPrincipalController controlador = loader.getController();
            controlador.inicializarInformacion(colaborador);
            Scene escena = new Scene(vista);
            escenario.setScene(escena);
            escenario.setTitle("Control Nutricional");
            escenario.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
