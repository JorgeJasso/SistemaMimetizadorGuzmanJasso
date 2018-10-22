package Controladores;

import gnu.io.CommPortIdentifier;//Sirve para identificar el COM con el que se trabaja.
import gnu.io.SerialPort; //Para trabajar con el puerto Serial.
import java.io.IOException; //Para trabajar con excepciones entrada y salida.
import java.io.OutputStream; //Trabaja con la libreria OutputStream para mandar datos al arduino.
import java.util.ArrayList; //Se usa cuando se trabaja con listas.
import java.util.Enumeration; //Enumera el puerto.
import javax.swing.JOptionPane; //Sirve para mostrar ventanas emergentes.

public class Conexion {

    private static OutputStream Output = null; //Se define el atributo para Output.
    private static SerialPort serialPort; //Se define el atributo para SerialPort.
    private static final String PORT_NAME = "/dev/ttyACM0"; //Indica el puerto que se utilizara como comunicarse con Arduino.
    private static final int TIME_OUT = 2000; //Se especifica el tiempo en 2000.
    private static final int DATA_RATE = 9600;//Se especifica la velocidad de datos en 9600.

    public Conexion(int llamada) {
        if (llamada == 1) { //Si llamada es 1 significa que se esta llamando desde el main y debe crear la conexion, si se llama desde otro lado ya no la vuelve a crear.
            ArduinoConnection();//Se crea la conexion con arduino.
            EnviarDatos(); //Envia datos si es que existen al arduino.
        }
    }

    private void ArduinoConnection() {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers(); //Obtiene un identificador para portEnum.
        while (portEnum.hasMoreElements()) { //Mientras haya mas elementos en el puerto sigue repitiendo.
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement(); //Le asigna un identificador al puerto.
            if (PORT_NAME.equals(currPortId.getName())) { //Verifica si el nombre del puerto es igual a el identificar asignado anteriormente.
                portId = currPortId; //De serlo se asigna este puerto al portId
                break; //Rompe el ciclo while.
            }
        }
        if (portId == null) { //Verifica que el puerto no sea nulo y de serlo te da a conocer el error.
            JOptionPane.showMessageDialog(null, "Error con el puerto");
            return;
        }
        try {
            //Se le asigna el tiempo y velocidad de datos al puerto Serial que deben coincidir con los de arduino.
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            Output = serialPort.getOutputStream(); //Establece el puerto serial en el Ouyput para que se puedan comunicar la computadora y arduino.
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conector a Arduino");
        }
    }

    private void EnviarDatos() {
        try {
            Lector lector = new Lector(); //Utiliza el lector.
            ArrayList mensajesAEnviar = new ArrayList();
            mensajesAEnviar = lector.leer(); //asigna el array que devuelve el metodo leer de la clase Lector a mesnajesAEnviar.
            if (!mensajesAEnviar.isEmpty()) { //Si mensajes a enviar no esta vacio.
                int switchA = 0; //Sirve para ir intercalando entre los distintos mensajes.
                String mensaje = "";
                for (int i = 0; i < mensajesAEnviar.size(); i++) {
                    if (switchA == 2) { //Cuando sea igual a dos significa que ya agrego el mensaje y fecha porque le que tiene que volver a ser 0 para empezar de nuevo el cambio.
                        mensaje = mensaje + "!"; //A mensaje se le agrega el ! para indicar que ya es para agregar a la lista en arduino.
                        i--; //Disminuye i porque ya lo leyo pero como switchA fue igual a 2 no lee nada y tiene que volver al valor original.
                        switchA = 0; //Empieza el cambio de nuevo.
                    } else {
                        switchA++;
                        mensaje = mensaje + (String) mensajesAEnviar.get(i);//Mensaje va adquiriendo lo que tenga el ArrayList.
                    }
                }
                mensaje = mensaje + "!";
                Output.write(mensaje.getBytes()); //Se mandan todos los mensajes leidos.
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al enviar datos");
        }
    }

    public void EnviarDatos(String mensaje) { //Este metodo se ocupa desde CrearMensaje o EliminarMensajes para enviar nuevos mensajes.
        try {
            Output.write(mensaje.getBytes()); //Se envie el mensaje que fue pasado en el parametro.
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al enviar datos");
        }
    }
}
