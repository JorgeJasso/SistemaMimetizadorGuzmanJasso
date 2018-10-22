package Interfaz;

//Importa todas las librerias a utilizar
import java.awt.Graphics; //Libreria que permite dibujar en el panel.
import java.awt.Image; //Se utiliza para agregar imagenes.
import java.awt.event.ActionEvent; //Se declara para manejar eventos.
import java.awt.event.ActionListener; //Se declara para manejar eventos de tipo Action.
import java.net.URL; //Indica la URL donde se encuentra la imagen.
import javax.swing.ImageIcon; //Permite agregar un icono de imagen.
import javax.swing.JButton; //Se agrega para poder trabajar con JButton.
import javax.swing.JFrame; //Se agrega para poder trabajar con JFrame.
import javax.swing.JPanel; //Se agrega para poder trabajar con JPanel.

public class Menu extends JPanel {//Se indica que esta clase heredara de JPanel.

    private final JButton CREARMENSAJE; //Boton para ir al panel Crear un nuevo mensaje.
    private final JButton ELIMINARMENSAJE; //Boton para ir al panel Eliminar un mensaje.
    private final JPanel PANEL; //Sirve para utilizar este mismo panel en la clase eventos.
    private final JFrame VENTANA; //Indica la ventana donde se agregara el panel.
    private final URL DIRECCION_URL = getClass().getResource("/Img/fondo1.jpg"); //Se declara la ruta donde se encuentra la imagen que se agregara al panel.
    private final Image IMAGEN = new ImageIcon(DIRECCION_URL).getImage(); //Declara la imagen con la URL especificada anteriormente.

    public Menu(JFrame ventana) {
        PANEL = this;
        VENTANA = ventana;
        setLayout(null);

        EventosMenu eventos = new EventosMenu(); //Clase para manejar eventos.
        CREARMENSAJE = new JButton();
        CREARMENSAJE.setText("Crear Mensaje");
        CREARMENSAJE.setBounds(150, 130, 200, 50);
        CREARMENSAJE.addActionListener(eventos); //Se pone a la escucha el boton CREARMENSAJE.
        add(CREARMENSAJE); //Se añade al panel.

        ELIMINARMENSAJE = new JButton();
        ELIMINARMENSAJE.setText("Eliminar Mensaje");
        ELIMINARMENSAJE.setBounds(150, 200, 200, 50);
        ELIMINARMENSAJE.addActionListener(eventos); //Se pone a la escucha el boton CREARMENSAJE.
        add(ELIMINARMENSAJE); //Se añade al panel.
    }

    @Override
    public void paint(Graphics g) { //Agrega la imagen al panel
        g.drawImage(IMAGEN, 0, 0, getWidth(), getHeight(), this); //Agrega la imagen al panlel.
        setOpaque(false);
        super.paint(g);
    }

    class EventosMenu implements ActionListener { //Crea una clase interna para manejar los eventos de la clase Menu.

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == CREARMENSAJE) { //Si el evento es igual a CREARMENSAJE accede
                CrearMensajes crear = new CrearMensajes(VENTANA);
                VENTANA.remove(PANEL);
                VENTANA.setVisible(false);
                VENTANA.add(crear); //Se añade el nuevo panel  al frame para poder crear mensajes. 
                VENTANA.setVisible(true);
            } else { //Si no es CREARMENSAJE significa que el evento se produjo de ELIMINARMENSAJE
                EliminarMensajes eliminar = new EliminarMensajes(VENTANA);
                VENTANA.remove(PANEL);
                VENTANA.setVisible(false);
                VENTANA.add(eliminar); //Se añade el nuevo panel  al frame para poder crear mensajes. 
                VENTANA.setVisible(true);
            }
        }
    }
}
