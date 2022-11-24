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
import javax.swing.JOptionPane;
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
            ObjectOutputStream flujo_salida = new ObjectOutputStream(misocket.getOutputStream()); // convierte mi calse a binario
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
            Socket misocket = new Socket("127.0.0.1",8000);//IP, puerto
            //DataOutputStream flujo_salida = new DataOutputStream(misocket.getOutputStream());// convierte los datos a binario
            ObjectOutputStream flujo_salida = new ObjectOutputStream(misocket.getOutputStream());// convierte un objeto a binario
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
            int llave = businessLogic.llavePrivada; //ingreso el valor de la llave privada del santiago
            paquete.setMensaje(textoTF.getText());

            //OPERACIÓN AL MENSAJE
            String hash = funcionHash(paquete.getMensaje());
            System.out.println("Hash obtenido de la palabra" + paquete.getMensaje()+": " +hash );
            String simetrico = cesar(hash, llave);
            paquete.setFirma(simetrico);
            paquete.setTipiM("firmado");
            paquete.setNumeroDeCertificado(businessLogic.numeroCertificado);
            paquete.setCodigoDeOperación('C');
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
            int numeroAleatorio = r.nextInt(36)+1;//37
            //cifro la firma y el mensaje
            paquete.setMensaje(cesar(paquete.getMensaje(), numeroAleatorio));
            paquete.setFirma(cesar(paquete.getFirma(), numeroAleatorio));

            //leer el segundo renglon de mi certificado para obtener la llave pública del receptor
            int llave2F = solicitudAR(paquete.getNumeroDeCertificadoR());
            System.out.println("obtengo mi llabe publica del receptor con valor: " + llave2F);

            //cifro mi número aleatorio con la llave publica del receptor
            String stringLlave = numeroAleatorio+"";
            String llaveCifradaEntera = cesar(stringLlave, llave2F);
            paquete.setLlaveCifrada(llaveCifradaEntera);
            paquete.setNumeroDeCertificado(businessLogic.numeroCertificado);
            paquete.setCodigoDeOperación('C');

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
                            mensajes.getChildren().add(new Label(data.getMensaje()+" "+data.getTiempo()+" "+data.getFirma()));

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
                }

                else if (mensaje.getTipiM().equals("firmado")) {//    PARA LA VERIFICACIÓN DE LA FIRMA
                    int llave;
                    //buscar sertificado en mi AR
                    llave = solicitudAR(mensaje.getNumeroDeCertificado()); // se obtiene la llave pública del certificado del cliente
                    System.out.println("mi número de certificado que abri es: " + mensaje.getNumeroDeCertificado()+ " mi llave del certificado es: " +llave);
                    //socket que escucha a AR

                    String resumenDscifrado = cesar(mensaje.getFirma(), llave);
                    String resumen = funcionHash(mensaje.getMensaje());
                    System.out.println("Hash obtenido de la palabra " + paquete.getMensaje()+": " + resumen );
                    System.out.println("el resumen decifrado es " + resumenDscifrado + " mi resumen es " + resumen);
                    if (resumenDscifrado.equals(resumen)){
                        JOptionPane.showMessageDialog(jFrame, "Se verifico la firma \n firma: " + mensaje.getFirma()+"\n Numero de Certificado: "+ mensaje.getNumeroDeCertificado());
                    }else{
                        JOptionPane.showMessageDialog(jFrame, "No se verifico la firma");

                    }
                }


                else if (mensaje.getTipiM().equals("sobre")) {
                    int llavePriv = businessLogic.llavePrivada;
                    //desifro con mi llave privada la llave aleatórea del sobre de forma ascimétrica
                    String llaveCifrada = mensaje.getLlaveCifrada()+"";
                    String llaveDescifradaStr = cesar(llaveCifrada, llavePriv);
                    int llaveDes = Integer.parseInt(llaveDescifradaStr);

                    //Con la llave aleatórea descifrada decifro el mensaje y la firma
                    String mensajeDes = descencriptar(mensaje.getMensaje(),llaveDes);
                    String firmaDes = descencriptar(mensaje.getFirma(),llaveDes);
                    //Con la llave pública del emisor verifico la firma
                    int llave2 = solicitudAR(mensaje.getNumeroDeCertificado());
                    System.out.println("necesito la llave publica del emisor con valor de: "+llave2);


                    String resumen = funcionHash(mensaje.getMensaje());
                    System.out.println("Hash obtenido de la palabra " + paquete.getMensaje()+": " + resumen );
                    String mensajeFinal = cesar(firmaDes, llave2);
                    mnsEncrip.setText(mensajeDes);
                    JOptionPane.showMessageDialog(jFrame, "Se verifico la firma del sobre \n firma: " + mensajeFinal+"\n Numero de Certificado: "+ mensaje.getNumeroDeCertificado());


                } else{
                    int llaveSA = Integer.valueOf(JOptionPane.showInputDialog("llaave para desifrar: "));
                    mnsEncrip.setText(cesar(mensaje.getMensaje(), llaveSA));
                }
            }
        });
        descifrar.getChildren().addAll(mnsEncrip,btnD);
        mensajes.getChildren().add(descifrar);
    }


    //VERIFICAR CERTIFICADOS CON LA AGENCIA CERTIFICADORA
    public int solicitudAR(int numeroCertificado){
        int llave = 0;
        try{
            Socket misocket = new Socket("127.0.0.1",5001);//IPv4, puerto
            ObjectOutputStream flujo_salida = new ObjectOutputStream(misocket.getOutputStream()); // convierte mi calse a binario
            paquete.setNumeroDeCertificado(numeroCertificado);
            paquete.setCodigoDeOperación('C');
            flujo_salida.writeObject(paquete); // mando el núemero de certificado
            flujo_salida.close();
        }
        catch(IOException e1){
            e1.printStackTrace();
        }
        try{
            ServerSocket escuchoAR = new ServerSocket(6001);
            Socket misocket = escuchoAR.accept(); //aceptamos todas las conecciones del exterior, abrimos mi socket
            ObjectInputStream paqueteEntreda = new ObjectInputStream(misocket.getInputStream());
            Paquete paqueteRecibido = (Paquete) paqueteEntreda.readObject();

            System.out.println("Se recibio la llave publica de AC: " + paqueteRecibido.getLlavePuclica());
            llave = paqueteRecibido.getLlavePuclica();
            misocket.close();
            escuchoAR.close();

        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return llave;
    }

    //valor ASCII de cada caracter * la posición relativa de ese caracter
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
            Paquete paqueteEnviar = new Paquete(paquete.getMensaje(),paquete.getPuertoE(),paquete.getpPuertoR(), paquete.getNumeroDeCertificadoR());
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
                //Si el mensaje es resivido y pertenece a la conversación, si el mensaje salio de aqui y pertenece a está conversación
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
                            mensajes.getChildren().add(new Label(cargar.getMensaje()+" "+cargar.getTiempo()));
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



