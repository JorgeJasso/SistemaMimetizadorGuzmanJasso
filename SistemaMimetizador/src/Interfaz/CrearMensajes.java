package Interfaz;

//Importa todas las librerias a utilizar
import Controladores.Lector; //Se importa Lector de controladores porque se llamara cuando se cree un mensaje.
import java.awt.Color; //Libreria para asignar colores a los componentes.
import java.awt.Graphics; //Libreria que permite dibujar en el panel.
import java.awt.Image; //Se utiliza para agregar imagenes.
import java.awt.event.ActionEvent; //Se declara para manejar eventos.
import java.awt.event.ActionListener; //Se declara para manejar eventos de tipo Action.
import java.net.URL; //Indica la URL donde se encuentra la imagen.
import javax.swing.ImageIcon; //Permite agregar un icono de imagen.
import javax.swing.JButton; //Se agrega para poder trabajar con JButton.
import javax.swing.JFrame; //Se agrega para poder trabajar con JFrame.
import javax.swing.JOptionPane; //Se agrega para poder trabajar con mensajes emergentes(JOptionPAne).
import javax.swing.JPanel; //Se agrega para poder trabajar con JPanel.
import javax.swing.JTextArea; //Se agrega para poder trabajar con areas de texto ()JTextArea

public class CrearMensajes extends JPanel { //Se indica que esta clase heredara de JPanel.

    private final JTextArea AREA_TEXTO; //Area de texto donde el usuario escribira.
    private final JButton CANCELAR; //Boton para Cancelar y volver al Menu.
    private final JButton CREAR; //Boton para Crear un nuevo mensaje.
    private final JFrame VENTANA; //Indica donde se agregara el panel.
    private final JPanel PANEL; //Para usar el panel en la clase Eventos.
    private final URL DIRECCION_URL = getClass().getResource("/Img/fondo1.jpg"); //Se declara la ruta donde se encuentra la imagen que se agregara al panel.
    private final Image IMAGEN = new ImageIcon(DIRECCION_URL).getImage(); //Declara la imagen con la URL especificada anteriormente.

    public CrearMensajes(JFrame ventana) {
        PANEL = this;
        VENTANA = ventana;
        setLayout(null); //Indica que no tendra un posicionamiento definido.
        setBackground(Color.yellow);

        AREA_TEXTO = new JTextArea();
        AREA_TEXTO.setBounds(30, 30, 440, 200);
        add(AREA_TEXTO); //Añade el area de texto al panel.

        EventosCrear eventos = new EventosCrear(); //Crea la clase evento para poner a la escucha los botones.
        CANCELAR = new JButton();
        CANCELAR.setText("Cancelar");
        CANCELAR.setBounds(60, 280, 170, 50);
        CANCELAR.addActionListener(eventos); //Pone a la escucha el boton CANCELAR.
        add(CANCELAR); //Añade el boton al panel.

        CREAR = new JButton();
        CREAR.setText("Crear");
        CREAR.setBounds(270, 280, 170, 50);
        CREAR.addActionListener(eventos); //Pone a la escucha el boton CANCELAR.
        add(CREAR); //Añade el boton al panel.
    }

    public void paint(Graphics g) { //Agrega la imagen al panel
        g.drawImage(IMAGEN, 0, 0, getWidth(), getHeight(), this); //Agrega la imagen al panel.
        setOpaque(false);
        super.paint(g);
    }

    class EventosCrear implements ActionListener { //Crea una clase interna para manejar los eventos de la clase CrearMensajes.

        @Override
        public void actionPerformed(ActionEvent e) {
            Menu menu = new Menu(VENTANA);
            if (e.getSource() == CREAR) { //Verifica que el evento se produjo de CREAR.
                String mensaje = AREA_TEXTO.getText();//Obtiene el contenido del area de texto.
                if (mensaje.length() <= 140) { //Verifica que la cantidad de caracteres sea menor a 140.
                    if (mensaje.length() == 0) {
                        JOptionPane.showMessageDialog(null, "No se puede crear un mensaje vacio"); //Verifica que el mensaje no este vacio.
                    } else {
                        Lector escribir = new Lector(); //Llama al Lector del paquete Controladores.
                        escribir.escribir(mensaje); //Le pasa el mensaje para que el metodo de escribir se haga cargo de el.
                        JOptionPane.showMessageDialog(null, "Mensaje creado");
                        //Una vez que termina el proceso vuelve al menu principal.
                        VENTANA.remove(PANEL);
                        VENTANA.setVisible(false);
                        VENTANA.add(menu); 
                        VENTANA.setVisible(true);
                    }
                } else { //Si es mayor de 140 caracteres manda el aviso y no puede crear el mensaje.
                    JOptionPane.showMessageDialog(null, "El mensaje cuenta con mas de 140 caracteres");
                }
            } else { //En dado caso que el evento no se produzca de CREAR significa que fue de Cancelar y debe volver al menu.
                VENTANA.remove(PANEL);
                VENTANA.setVisible(false);
                VENTANA.add(menu); //Añade el panel de menu para volver.
                VENTANA.setVisible(true);
            }
        }
    }
}
