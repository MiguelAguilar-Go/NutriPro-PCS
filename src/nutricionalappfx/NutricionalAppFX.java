/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package nutricionalappfx;

import java.io.IOException;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Miguel Aguilar
 */
public class NutricionalAppFX extends Application {
    
    @Override
    public void start(Stage stage){
        
        try {         
            Parent vista = FXMLLoader.load(getClass().getResource("/vista/FXMLAutenticacion.fxml"));
            Scene scene = new Scene(vista, 700, 500);
            stage.setScene(scene);
            stage.setTitle("NutriPro - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
