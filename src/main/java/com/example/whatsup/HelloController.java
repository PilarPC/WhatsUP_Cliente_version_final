package com.example.whatsup;

import com.example.paquete.Paquete;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HelloController implements Runnable{
    public List<Paquete> baseDeDatos = new ArrayList();

    @FXML
    private Button btAsc;

    @FXML
    private Button btPlano;

    @FXML
    private Button btPlano1;
    @FXML
    private VBox mensajes;

    @FXML
    private Button btsim;

    @FXML
    private PasswordField llabelb;

    @FXML
    private Button regreso;
    @FXML
    private TextField textoTF;
    @FXML
    private Label nombre;

    public Paquete paquete;//objeto de la clase paquete



    public void userChat(String n){
        nombre.setText(n);
    }

    @FXML
    void ckregreso(ActionEvent event) throws IOException {
        servidor.close();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("contactos.fxml"));
        root=fxmlLoader.load();
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ckPlano(ActionEvent event) {
        // mensajes.getChildren().add(new Label(textoTF.getText()));
        try{
            Socket misocket = new Socket("127.0.0.1",8000);//IPv4, puerto
            //DataOutputStream flujo_salida = new DataOutputStream(misocket.getOutputStream());// convierte los datos a binario
            ObjectOutputStream flujo_salida = new ObjectOutputStream(misocket.getOutputStream());
            HBox Hbmandado = new HBox();
            Hbmandado.setAlignment(Pos.CENTER_RIGHT);
            paquete.setTiempo();
            TextFlow lbHb = new TextFlow(new Text(textoTF.getText()+ " " + paquete.getTiempo()));
            Hbmandado.getChildren().add(lbHb);
            mensajes.getChildren().add(Hbmandado);
            paquete.setTipiM("plano");
            paquete.setMensaje(textoTF.getText());
            escribir(paquete);
            flujo_salida.writeObject(paquete);
            //flujo_salida.writeUTF(textoTF.getText());//UTF para String's, especifacas el tipo de datos, writeFloat...
            flujo_salida.close();
        }

        catch(IOException e1){
            e1.printStackTrace();
        }

    }
    @FXML
    void cksim(ActionEvent event) {
        try{
            Socket misocket = new Socket("127.0.0.1",8000);//IPv4, puerto
            //DataOutputStream flujo_salida = new DataOutputStream(misocket.getOutputStream());// convierte los datos a binario
            ObjectOutputStream flujo_salida = new ObjectOutputStream(misocket.getOutputStream());
            HBox Hbmandado = new HBox();
            Hbmandado.setAlignment(Pos.CENTER_RIGHT);
            paquete.setTiempo();
            TextFlow lbHb = new TextFlow(new Text(textoTF.getText()+ " " + paquete.getTiempo()));
            Hbmandado.getChildren().add(lbHb);
            mensajes.getChildren().add(Hbmandado);
            paquete.setMensaje(textoTF.getText());
            escribir(paquete);
            int llave = Integer.valueOf(JOptionPane.showInputDialog("Llave"));
            //int llave = Integer.valueOf(dialog.showInputDialog(stage, "llave: ", "Sifrado Cimétrico"));
            paquete.setMensaje(cesar(textoTF.getText(),llave));
            paquete.setTipiM("simetrico");
            //paquete.setMensaje(textoTF.getText());
            flujo_salida.writeObject(paquete);
            //flujo_salida.writeUTF(textoTF.getText());//UTF para String's, especifacas el tipo de datos, writeFloat...
            flujo_salida.close();
        }

        catch(IOException e1){
            e1.printStackTrace();
        }
    }
    @FXML
    void ckAsc(ActionEvent event) {
        try{
            Socket misocket = new Socket("127.0.0.1",8000);//IPv4, puerto
            //DataOutputStream flujo_salida = new DataOutputStream(misocket.getOutputStream());// convierte los datos a binario
            ObjectOutputStream flujo_salida = new ObjectOutputStream(misocket.getOutputStream());
            HBox Hbmandado = new HBox();
            Hbmandado.setAlignment(Pos.CENTER_RIGHT);
            paquete.setTiempo();
            TextFlow lbHb = new TextFlow(new Text(textoTF.getText()+ " " + paquete.getTiempo()));
            Hbmandado.getChildren().add(lbHb);
            mensajes.getChildren().add(Hbmandado);
            paquete.setMensaje(textoTF.getText());
            escribir(paquete);
            int llave = Integer.valueOf(JOptionPane.showInputDialog("Llave"));
            //int llave = Integer.valueOf(dialog.showInputDialog(stage, "llave: ", "Sifrado Cimétrico"));
            paquete.setTipiM("ascimetrico");
            paquete.setMensaje(cesar(textoTF.getText(),llave));

            //paquete.setMensaje(textoTF.getText());
            flujo_salida.writeObject(paquete);
            //flujo_salida.writeUTF(textoTF.getText());//UTF para String's, especifacas el tipo de datos, writeFloat...
            flujo_salida.close();
        }

        catch(IOException e1){
            e1.printStackTrace();
        }
    }
    public  void Establecer(Paquete p){
        this.paquete = p; leer();
    }

    public void initialize(){
        Thread hilo1 = new Thread(this);
        hilo1.start();
    }
    //hios
    ServerSocket servidor;
    public void run(){
        try{
            servidor=new ServerSocket(9003);
            //ahora que acepte cualquier conexion que venga del exterior con el metodo accept

            while(true){
                Socket misocket=servidor.accept();//aceptara las conexiones que vengan del exterior
                ObjectInputStream flujo_entrada=new ObjectInputStream(misocket.getInputStream());
                Paquete data=(Paquete)flujo_entrada.readObject();
                if(paquete.getpPuertoR() == data.getPuertoE()){
                    if(!data.getTipiM().equals("plano")){
                        Platform.runLater(()->{
                            decifrar(data);
                        });
                        escribir(data);
                    }else{
                        //CONTENIDO CHAT
                        System.out.println(data.getMensaje());
                        Platform.runLater(()->{
                            //mensajes.setText(mensaje);
                            mensajes.getChildren().add(new Label(data.getMensaje()+" "+data.getTiempo()));

                        });
                        escribir(data);
                    }
                }
                // misocket.close();
                misocket.close();
            }

        }
        catch(IOException|ClassNotFoundException e){
            System.out.println(e);
        }



    }
    private Stage stage;
    private Scene scene;
    private Parent root;
    public String cesar(String texto, int codigo){

        StringBuilder cifrado = new StringBuilder();
        codigo = codigo % 26;
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) >= 'a' && texto.charAt(i) <= 'z') {
                if ((texto.charAt(i) + codigo) > 'z') {
                    cifrado.append((char) (texto.charAt(i) + codigo - 26));
                } else {
                    cifrado.append((char) (texto.charAt(i) + codigo));
                }
            } else if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z') {
                if ((texto.charAt(i) + codigo) > 'Z') {
                    cifrado.append((char) (texto.charAt(i) + codigo - 26));
                } else {
                    cifrado.append((char) (texto.charAt(i) + codigo));
                }
            }
        }
        return cifrado.toString();
    }
    public String descencriptar(String texto, int codigo){
        StringBuilder cifrado = new StringBuilder();
        codigo = codigo % 26;
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) >= 'a' && texto.charAt(i) <= 'z') {
                if ((texto.charAt(i) - codigo) < 'a') {
                    cifrado.append((char) (texto.charAt(i) - codigo + 26));
                } else {
                    cifrado.append((char) (texto.charAt(i) - codigo));
                }
            } else if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z') {
                if ((texto.charAt(i) - codigo) < 'A') {
                    cifrado.append((char) (texto.charAt(i) - codigo + 26));
                } else {
                    cifrado.append((char) (texto.charAt(i) - codigo));
                }
            }
        }
        return cifrado.toString();
    }

    public void decifrar(Paquete mensaje){
        HBox descifrar = new HBox();
        Label mnsEncrip = new Label(mensaje.getMensaje());
        Button btnD = new Button();
        if(mensaje.getTipiM().equals("simetrico")){
            btnD.setText("DesSimetrico");
        }else{
            btnD.setText("DesAsimetrico");
        }

        btnD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int llave = Integer.parseInt((JOptionPane.showInputDialog("Introducir llave")));
                if (mensaje.getTipiM().equals("simetrico")){
                    mnsEncrip.setText(descencriptar(mensaje.getMensaje(), llave));
                }else{
                    mnsEncrip.setText(cesar(mensaje.getMensaje(), llave));
                }
            }
        });
        descifrar.getChildren().addAll(mnsEncrip,btnD);
        mensajes.getChildren().add(descifrar);
    }

    public void escribir(Paquete paquete)  throws IOException{
        // FileWriter escritura = new FileWriter("D:/IJ/proyectos/nuevo.txt");
        try{
            FileOutputStream escribe=new FileOutputStream("D:/IJ/proyectos/chatM.txt");
            ObjectOutputStream flujo_salida=new ObjectOutputStream(escribe);
            Paquete paqueteEnviar = new Paquete(paquete.getMensaje(),paquete.getPuertoE(),paquete.getpPuertoR());
            paqueteEnviar.setNuevoTiempo(paquete.getTiempo());
            baseDeDatos.add(paqueteEnviar);
            flujo_salida.writeObject(baseDeDatos);
            flujo_salida.close();
            escribe.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    public void leer(){
        try{
            FileInputStream cargarDatos=new FileInputStream("D:/IJ/proyectos/chatM.txt");
            ObjectInputStream entrada=new ObjectInputStream(cargarDatos);
            baseDeDatos =(List<Paquete>)entrada.readObject();
            for(Paquete cargar: baseDeDatos){
                if (paquete.getpPuertoR() == cargar.getPuertoE() | ((paquete.getPuertoE() == cargar.getPuertoE()) & (paquete.getpPuertoR() == cargar.getpPuertoR())) ){
                    System.out.println(cargar.getMensaje());
                    if(paquete.getPuertoE() == cargar.getPuertoE())
                    {

                        if(paquete.getPuertoE()==cargar.getPuertoE()){
                            HBox Hbmandado = new HBox();
                            Hbmandado.setAlignment(Pos.CENTER_RIGHT);
                            TextFlow lbHb = new TextFlow(new Text(cargar.getMensaje()+ " " + cargar.getTiempo()));
                            Hbmandado.getChildren().add(lbHb);
                            mensajes.getChildren().add(Hbmandado);
                        }
                        else{
                            mensajes.getChildren().add(new Label(cargar.getMensaje()));
                        }

                    }else if(paquete.getpPuertoR() == cargar.getPuertoE()){
                        mensajes.getChildren().add(new Label(cargar.getMensaje()));
                    }
                }

            }
            entrada.close();
            cargarDatos.close();
        }
        catch(IOException e){
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}



