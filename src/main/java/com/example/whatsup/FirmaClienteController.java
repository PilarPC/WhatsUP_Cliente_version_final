package com.example.whatsup;
import com.example.paquete.Paquete;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FirmaClienteController {
    BusinessLogic businessLogic = BusinessLogic.getInstance();
    @FXML
    private Button miguel;

    @FXML
    private Button pilar;

    @FXML
    private Button santiago;

    @FXML
    void ckMiguel(ActionEvent event) throws IOException {
        //D:\IJ\proyectos\cerificado10002.txt
        String fd = JOptionPane.showInputDialog("Ruta del certificado: ");

        File file = new File(fd);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);   // creates a buffering character input stream
        String line;
        int lineCounter = 0;
        while ((line = br.readLine()) != null) {
            if (lineCounter == 0) {
                businessLogic.numeroCertificado = Integer.parseInt(line);
            }
            lineCounter++;
        }
        fr.close(); // closes the stream and release the resources

//----------------------------llave privada ---------------- D:\IJ\proyectos\llavePrivada10002.key
        fd = JOptionPane.showInputDialog("Ruta de la llave privada: ");

        file = new File(fd);
        fr = new FileReader(file);
        br = new BufferedReader(fr);   // creates a buffering character input stream

        lineCounter = 0;
        while ((line = br.readLine()) != null) {
            if (lineCounter == 0) {
                businessLogic.llavePrivada = Integer.parseInt(line);
            }
            lineCounter++;
        }
        fr.close(); // closes the stream and release the resources

        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("contactos.fxml"));
        root=fxmlLoader.load();


        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ckPilar(ActionEvent event) throws IOException {
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("contactos.fxml"));
        root=fxmlLoader.load();
        ContactosController contactosController=fxmlLoader.getController();
        //contactosController.userChat("Miguel");
        //instanciar paquet
        //contactosController.businessLogic(new BusinessLogic());
//stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ckSantiago(ActionEvent event) throws IOException {
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("contactos.fxml"));
        root=fxmlLoader.load();
        //Contactos Controller contactosController=fxmlLoader.getController();
        //contactosController.userChat("Miguel");
        //instanciar paquet
        //.Establecer(new Paquete("",9001,9003));
//stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private Stage stage;
    private Scene scene;
    private Parent root;
}

class BusinessLogic {

    private static BusinessLogic INSTANCE;

    public HashMap<String, Integer> userPorts = new HashMap<>();

    int llavePrivada;
    int numeroCertificado;


    private BusinessLogic() {
        this.userPorts.put("Miguel", 9003);
        this.userPorts.put("Pilar", 9002);
        this.userPorts.put("Santiago", 9001);
    }

    public static BusinessLogic getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BusinessLogic();
        }
        return INSTANCE;
    }

}