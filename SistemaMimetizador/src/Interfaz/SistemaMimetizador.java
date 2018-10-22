package Interfaz;

import Controladores.Conexion; //Se importa la clase Conexion del paquete Controladores.

public class SistemaMimetizador {

    public static void main(String[] args) { //Main de nuestra aplicacion.
        Ventana ventana = new Ventana(); //Se crea la ventana que sera nuestro Frame.
        Conexion conectar = new Conexion(1); //Crea la conexion con el arduino para cuando se necesite mandarle información.
    }
}
