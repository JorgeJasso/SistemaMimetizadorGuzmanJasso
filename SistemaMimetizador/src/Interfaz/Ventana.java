package Interfaz;

import javax.swing.JFrame; //Libreria para crear un frame.

public class Ventana extends JFrame {

    public Ventana() {
        setSize(500, 400); //Se define el tamaño de nuestro frame.
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Indicas que cuando se cierre se termine la aplicación.
        setBounds(500, 200, 500, 400);//Posiciona la ventana en el lugar deseado.
        Menu menu = new Menu(this);
        add(menu); //Añade un panel al frame.
        setVisible(true); //Muestra la pantalla.
        setResizable(false); //Indica que no se podra redimencionar.
    }
}
