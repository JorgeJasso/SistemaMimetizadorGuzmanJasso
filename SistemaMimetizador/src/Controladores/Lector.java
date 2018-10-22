package Controladores;

import java.io.BufferedReader;//Lee el texto de un flujo de entrada de caracteres y almacena en búfer los caracteres para proporcionar una lectura eficiente de los caracteres, matrices y líneas.
import java.io.BufferedWriter;//Escribe texto en una secuencia de salida de caracteres, almacenando en búfer los caracteres para proporcionar la escritura eficiente de caracteres individuales, matrices y cadenas.
import java.io.File; //Libreria que se usa para trabajar con archivos de texto.
import java.io.FileReader; //Sirve para leer los archivos de texto.
import java.io.FileWriter; //Sirve para escribir en los archivos de texto.
import java.io.PrintWriter;//Imprime representaciones formateadas de objetos en un flujo de salida de texto.
import java.util.ArrayList;//Se importa para trabajar con ArrayList.
import java.util.Calendar; //Libreria para trabajar con fecha y hora.

public class Lector {

    private File archivo = null; //Se define un archivo nulo que posteriormente se le asignara un valor.
    private FileReader fr = null;//Se define un fileReader nulo que posteriormente se le asignara un valor.
    private BufferedReader br = null;//Se define un BufferedReader nulo que posteriormente se le asignara un valor.
    private BufferedWriter bw = null;//Se define un BufferedWriter nulo que posteriormente se le asignara un valor.

    public ArrayList leer() { //Este metodo devuelvo un ArrayList que sera rellenado con todos los mensajes que encuentre en el archivo de texto.
        ArrayList mensajes = new ArrayList();
        try {
            archivo = new File("/home/araceli/Descargas/SistemaMimetizador/src/Controladores/mensajes.txt"); //Especifica la ruta donde se encuentra el archivo.
            fr = new FileReader(archivo); //Pasa como parametro el archivo para crear el FileReader.
            br = new BufferedReader(fr); //Pasa como parametro el fr para crear el BufferedReader.
            String linea;
            while ((linea = br.readLine()) != null) { //Indica que mientras haya algo que leer se sigue repitiendo.
                mensajes.add(linea); //En cada iteracion se le asignan lineas al ArrayList.
            }
        } catch (Exception e) {
        } finally {
            try {
                if (null != fr) {
                    fr.close(); //Cierra el archivo para evitar futuros errores.
                }
            } catch (Exception e) {
            }
        }
        return mensajes; //Regresa el ArrayList mensajes.
    }

    public void escribir(String mensaje) { //Escribe el mensaje pasado como parametro en el archivo de texto junto con su fecha.
        try {
            archivo = new File("/home/araceli/Descargas/SistemaMimetizador/src/Controladores/mensajes.txt"); //Indica la ruta del archivo donde escribira.
            bw = new BufferedWriter(new FileWriter(archivo, true));
            bw.write(mensaje + "\n");//Agrega el mensaje y realiza un salto de linea
            Calendar c = Calendar.getInstance(); //Se crea una instancia de Calendar para poder saber la fecha y hora del sistema.
            /*
                En las siguientes 4 lineas de codigo se obtiene fecha u hora y se formatea para que se meustren con un formato de dos numero es decir si la hora es 10:5 se convierte en 10:05.
            */
            String dia = (c.get(Calendar.DAY_OF_MONTH) < 10) ? "0" + c.get(Calendar.DAY_OF_MONTH) : "" + c.get(Calendar.DAY_OF_MONTH);
            String mes = ((c.get(Calendar.MONTH) + 1) < 10) ? "0" + (c.get(Calendar.MONTH) + 1) : "" + (c.get(Calendar.MONTH) + 1);
            String hora = (c.get(Calendar.HOUR_OF_DAY) < 10) ? "0" + c.get(Calendar.HOUR_OF_DAY) : "" + c.get(Calendar.HOUR_OF_DAY);
            String minutos = (c.get(Calendar.MINUTE) < 10) ? "0" + c.get(Calendar.MINUTE) : "" + c.get(Calendar.MINUTE);
            String fechaHora = dia + "/" + mes + "/" + (c.get(Calendar.YEAR)) + " " + hora + ":" + minutos;//Se crea un String donde almacena el mensaje fecha y hora
            bw.write(fechaHora + "\n");//Agrega la fecha y hora y realiza un salto de linea
            String mensajeAEnviar = mensaje + fechaHora + "!"; //Se agrega el caracter ! para separar los mensajes
            Conexion conectar = new Conexion(2);
            conectar.EnviarDatos(mensajeAEnviar); //Mandar el mensaje a enviar datos para mandar el mensaje nuevo al arduino.
        } catch (Exception e) {
        } finally {
            try {
                if (null != archivo) {
                    bw.close(); //Cierra el documento.
                }
            } catch (Exception e) {
            }
        }
    }

    public void sobreescribir(String lineaBorrar) {
        try {
            archivo = new File("/home/araceli/Descargas/SistemaMimetizador/src/Controladores/mensajes.txt");//Especifica la direccion del archivo.
            File copia = new File(archivo.getAbsolutePath() + ".tmp");
            br = new BufferedReader(new FileReader("/home/araceli/Descargas/SistemaMimetizador/src/Controladores/mensajes.txt"));//Crea un archivo nuevo que sera la copia.
            PrintWriter pw = new PrintWriter(new FileWriter(copia));
            String linea = null;
            while ((linea = br.readLine()) != null) {//Mientras haya cosas por leer sigue repitiendo.
                if (!linea.equals(lineaBorrar)) {//Si linea no es es igual a lineaBorrar la agrega al documento copia.
                    pw.println(linea);
                    pw.flush();
                } else { //Si es igual no lo agrega y manda el mesnaje a arduino para que este lo borre de la lista de mensajes.
                    String mensajeAEnviar = linea + br.readLine() + "~";//Lo manda con ~ para identificar que se borrara ese mensaje.
                    Conexion conectar = new Conexion(2);
                    conectar.EnviarDatos(mensajeAEnviar);//Envia el mensaje a eliminar a arduino.
                }
            }
            pw.close(); //Se cieera el archivo.
            br.close();
            archivo.delete(); //El archivo original se elimina.
            copia.renameTo(archivo);//La copia se renombra como el archivo original para que simule ser el mismo archivo.
        } catch (Exception e) {
        }
    }
}
