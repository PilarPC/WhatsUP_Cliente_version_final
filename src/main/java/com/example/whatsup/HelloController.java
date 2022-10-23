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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javax.swing.*;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloController implements Runnable{
    BusinessLogic businessLogic = BusinessLogic.getInstance();
    public List<Paquete> baseDeDatos = new ArrayList();
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789 ";


    @FXML
    private VBox mensajes;

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


    @FXML
    void ckFirma(ActionEvent event) {
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
            int llave = businessLogic.llavePrivada;
            paquete.setMensaje(textoTF.getText());

            //OPERACIÓN AL MENSAJE
            String hash = funcionHash(paquete.getMensaje());
            System.out.println("(envio mensaje)Hash obtenido de la palabra " + paquete.getMensaje()+": " +hash );
            String simetrico = cesar(hash, llave);
            System.out.println("(envio mensaje)el resultado de mi firma cifrada es  " +simetrico );
            paquete.setFirma(simetrico);
            paquete.setTipiM("firmado");
            paquete.setNumeroDeCertificado(businessLogic.numeroCertificado);

            //MANDO MENSAJE
            flujo_salida.writeObject(paquete);
            flujo_salida.close();
        }

        catch(IOException e1){
            e1.printStackTrace();
        }
    }

    @FXML
    void ckSobre(ActionEvent event) {
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
            int llave = businessLogic.llavePrivada; //llave privada para firmar el sobre
            paquete.setMensaje(textoTF.getText());

            //OPERACIÓN AL MENSAJE
            String hash = funcionHash(paquete.getMensaje());
            System.out.println("Hash obtenido de la palabra" + paquete.getMensaje()+": " +hash );
            String simetrico = cesar(hash, llave);
            paquete.setFirma(simetrico);
            //OBTENER NÚMERO ALEATORIO
            Random r = new Random();
            int numeroAleatorio = r.nextInt(37)+1;
            //cifro la firma y el mensaje
            paquete.setMensaje(cesar(paquete.getMensaje(), numeroAleatorio));
            paquete.setFirma(cesar(paquete.getFirma(), numeroAleatorio));

            //leer el segundo renglon de mi certificado para obtener la llave pública del receptor
            int llave2F =0;
            String llave2 = JOptionPane.showInputDialog("Dame la ruta del certificado receptor");
            paquete.setRutaCertificadoReceptor(llave2);
            File file = new File(llave2);
            FileReader fr = null;
            try {
                fr = new FileReader(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            BufferedReader br = new BufferedReader(fr);   // creates a buffering character input stream
            String line;
            int lineCounter = 0;
            while (true) {
                try {
                    if (!((line = br.readLine()) != null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (lineCounter == 1) {
                    llave2F = Integer.parseInt(line);
                }
                lineCounter++;
            }
            try {
                fr.close(); // closes the stream and release the resources
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            //cifro mi número aleatorio con la llave pribada del receptor
            String stringLlave = numeroAleatorio+"";
            int llaveCifradaEntera = Integer.parseInt(cesar(stringLlave, llave2F));
            paquete.setLlaveCifrada(llaveCifradaEntera);


            paquete.setTipiM("sobre");

            //MANDO MENSAJE
            flujo_salida.writeObject(paquete);
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
                            mensajes.getChildren().add(new Label(data.getMensaje()+" "+data.getTiempo()+" "+ data.getFirma()));

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
    public static String cesar(String texto, int codigo)
    {
        texto = texto.toLowerCase();

        String encryptStr = "";

        for (int i = 0; i < texto.length(); i++)
        {
            int pos = ALPHABET.indexOf(texto.charAt(i));

            int encryptPos = (codigo + pos) % 37;
            char encryptChar = ALPHABET.charAt(encryptPos);

            encryptStr += encryptChar;
        }

        return encryptStr;
    }
    public static String descencriptar(String texto, int codigo)
    {
        texto = texto.toLowerCase();

        String decryptStr = "";

        for (int i = 0; i < texto.length(); i++)
        {
            int pos = ALPHABET.indexOf(texto.charAt(i));

            int decryptPos = (pos - codigo) % 37;
            if (decryptPos < 0){
                decryptPos = ALPHABET.length() + decryptPos;
            }
            char decryptChar = ALPHABET.charAt(decryptPos);
            decryptStr += decryptChar;
        }
        return decryptStr;
    }

    public void decifrar(Paquete mensaje){
        HBox descifrar = new HBox();
        Label mnsEncrip = new Label(mensaje.getMensaje());
        JFrame jFrame = new JFrame();
        Button btnD = new Button();
        if(mensaje.getTipiM().equals("simetrico")){
            btnD.setText("DesSimetrico");
        } else if (mensaje.getTipiM().equals("firmado")) {
            btnD.setText("Verificar firma");
        } else if (mensaje.getTipiM().equals("sobre")){
            btnD.setText("Verificar sobre");
        }else{
            btnD.setText("DesAsimetrico");
        }

        btnD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                if (mensaje.getTipiM().equals("simetrico")){
                    int llaveSA = Integer.valueOf(JOptionPane.showInputDialog("llaave para desifrar: "));
                    mnsEncrip.setText(descencriptar(mensaje.getMensaje(), llaveSA));
                } else if (mensaje.getTipiM().equals("firmado")) {
                    int llave = 0;
                    String NumCertificado = mensaje.getNumeroDeCertificado()+"";
                    String rutaCertificadoEmisor =  "D:\\IJ\\proyectos\\cerificado"+NumCertificado+".txt";
                    File file = new File(rutaCertificadoEmisor);
                    FileReader fr = null;
                    try {
                        fr = new FileReader(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    BufferedReader br = new BufferedReader(fr);   // creates a buffering character input stream
                    String line;
                    int lineCounter = 0;
                    while (true) {
                        try {
                            if (!((line = br.readLine()) != null)) break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (lineCounter == 1) {
                            llave = Integer.parseInt(line);
                        }
                        lineCounter++;
                    }
                    try {
                        fr.close(); // closes the stream and release the resources
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String resumenDscifrado = cesar(mensaje.getFirma(),llave);
                    String resumen = funcionHash(mensaje.getMensaje());
                    System.out.println("Hash obtenido de la palabra " + paquete.getMensaje()+": " + resumen );
                    if (resumenDscifrado.equals(resumen)){
                        JOptionPane.showMessageDialog(jFrame, "Se verifico la firma \n firma: " + mensaje.getFirma()+"\n Numero de Certificado: "+ mensaje.getNumeroDeCertificado());
                    }else{
                        JOptionPane.showMessageDialog(jFrame, "No se verifico la firma");

                    }
                } else if (mensaje.getTipiM().equals("sobre")) {
                    int llavePriv = businessLogic.llavePrivada;
                    //desifro con mi llave privada la llave aleatórea del sobre de forma ascimétrica
                    String llaveCifrada = mensaje.getLlaveCifrada()+"";
                    String llaveDescifradaStr = cesar(llaveCifrada, llavePriv);
                    int llaveDes = Integer.parseInt(llaveDescifradaStr);

                    //Con la llave aleatórea descifrada decifro el mensaje y la firma
                    String mensajeDes = descencriptar(mensaje.getMensaje(),llaveDes);
                    String firmaDes = descencriptar(mensaje.getFirma(),llaveDes);
                    //Con la llave pública del emisor verifico la firma
                    int llave2 = 0;
                    String rutaCertificadoEmisor =  mensaje.getRutaCertificadoReceptor();
                    File file = new File(rutaCertificadoEmisor);
                    FileReader fr = null;
                    try {
                        fr = new FileReader(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    BufferedReader br = new BufferedReader(fr);   // creates a buffering character input stream
                    String line;
                    int lineCounter = 0;
                    while (true) {
                        try {
                            if (!((line = br.readLine()) != null)) break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (lineCounter == 1) {
                            llave2 = Integer.parseInt(line);
                        }
                        lineCounter++;
                    }
                    try {
                        fr.close(); // closes the stream and release the resources
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String resumenDscifrado = cesar(mensaje.getFirma(),llave2);
                    String resumen = funcionHash(mensaje.getMensaje());
                    System.out.println("Hash obtenido de la palabra " + paquete.getMensaje()+": " + resumen );
                    String mensajeFinal = cesar(firmaDes, llave2);
                    mnsEncrip.setText(mensajeDes);
                    JOptionPane.showMessageDialog(jFrame, "Se verifico la firma del sobre \n firma: " + mensajeFinal+"\n Numero de Certificado: "+ paquete.getNumeroDeCertificado());


                } else{
                    int llaveSA = Integer.valueOf(JOptionPane.showInputDialog("llaave para desifrar: "));
                    mnsEncrip.setText(cesar(mensaje.getMensaje(), llaveSA));
                }
            }
        });
        descifrar.getChildren().addAll(mnsEncrip,btnD);
        mensajes.getChildren().add(descifrar);
    }

    public String funcionHash(String mensaje){
        String resumen = " ";
        int preResumen = 0;
        for(int i = 0; i<mensaje.length(); i++){
            preResumen+=mensaje.charAt(i)*i;
        }
        resumen = String.valueOf(preResumen);
        return resumen;
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
                        mensajes.getChildren().add(new Label(cargar.getMensaje()+" "+cargar.getTiempo()));
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



