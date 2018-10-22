package Interfaz;

//Importa todas las librerias a utilizar
import java.awt.event.ActionEvent; //Se declara para manejar eventos.
import java.awt.event.ActionListener; //Se declara para manejar eventos de tipo Action.
import javax.swing.JButton; //Se agrega para poder trabajar con JButton.
import javax.swing.JFrame; //Se agrega para poder trabajar con JFrame.
import javax.swing.JOptionPane; //Se agrega para poder trabajar con mensajes emergentes(JOptionPAne).
import javax.swing.JPanel; //Se agrega para poder trabajar con JPanel.
import javax.swing.JTextArea; //Se agrega para poder trabajar con areas de texto ()JTextArea

public class EliminarMensajes extends JPanel {

    private final JTextArea AREA_TEXTO; //Area de texto donde se mostraran los mensajes existentes.
    private final JButton CANCELAR; //Para regresar al menu.
    private final JButton ANTERIOR; //Ir al mensaje anterior.
    private final JButton SIGUIENTE; //Ir al mensaje siguiente.
    private final JButton ELIMINAR; //Para eliminar el mensaje en donde se encuentre posicionado.
    private final JFrame VENTANA; //Frame donde se agregara el panel.
    private final JPanel PANEL; //Para usar el panel en la clase Eventos
    private byte contador; //Sirve para avanzar y retroceder entre los mensajes.
    private ArrayList mensajes; //Aqui se almacenaran los mensajes leidos desde la clase Leer para mostrarlos en el area de texto.

    public EliminarMensajes(JFrame ventana) {
        PANEL = this;
        VENTANA = ventana;
        setLayout(null); //Indica que no tendra un posicionamiento definido.
        setBackground(Color.yellow);

        AREA_TEXTO = new JTextArea();
        AREA_TEXTO.setBounds(90, 60, 320, 200);
        AREA_TEXTO.setEditable(false);
        add(AREA_TEXTO); //Añade el area de texto al panel.

        EventosEliminar eventos = new EventosEliminar(); //Crea la clase evento para poner a la escucha los botones.

        ANTERIOR = new JButton();
        ANTERIOR.setText("<");
        ANTERIOR.setBounds(20, 100, 50, 30);
        ANTERIOR.addActionListener(eventos); //Pone el boton a la escucha.
        add(ANTERIOR); //Añade el boton al panel.

        SIGUIENTE = new JButton();
        SIGUIENTE.setText(">");
        SIGUIENTE.setBounds(430, 100, 50, 30);
        SIGUIENTE.addActionListener(eventos); //Pone el boton a la escucha.
        add(SIGUIENTE); //Añade el boton al panel.

        CANCELAR = new JButton();
        CANCELAR.setText("Cancelar");
        CANCELAR.setBounds(90, 320, 150, 50); 
        CANCELAR.addActionListener(eventos); //Pone el boton a la escucha.
        add(CANCELAR); //Añade el boton al panel.

        ELIMINAR = new JButton();
        ELIMINAR.setText("Eliminar");
        ELIMINAR.setBounds(260, 320, 150, 50);
        ELIMINAR.addActionListener(eventos); //Pone el boton a la escucha.
        add(ELIMINAR); //Añade el boton al panel.

        leerMensajes(); //Va al metodo privado para leer los mensajes.
    }

    private void leerMensajes() { //Este metodo sirve 
       System.out.println("Estoy leyendo");
    }

    class EventosEliminar implements ActionListener { //Crea una clase interna para manejar los eventos de la clase EliminarMensajes.

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == CANCELAR) { //Si el evento es Cancelar vuelve al menu.
                Menu menu = new Menu(VENTANA);
                VENTANA.remove(PANEL);
                VENTANA.setVisible(false);
                VENTANA.add(menu);
                VENTANA.setVisible(true);
            } else {
                if (e.getSource() == ANTERIOR) { 
                    if (contador == 0) { //Si contador ya es 0 e intenta ir un mensaje anterior se indica que no hay mensajes anteriores.
                        JOptionPane.showMessageDialog(null, "No hay mensajes anteriores");
                    } else {
                        contador -= 2; //Se decrementan 2 porque un mensaje esta compuesto por el mensaje y la fecha.
                        AREA_TEXTO.setText((String) mensajes.get(contador)); //Muestra el mensaje que se encuentra en el arrayList en la posicion contador.
                    }
                } else {
                    if (e.getSource() == SIGUIENTE) { 
                        if ((mensajes.isEmpty()) || (contador == mensajes.size() - 2)) { //Si ya es la ultima posicion o la lista esta vacia e intenta ir a la siguiente posicion indica que ya no hay mas mensajes.
                            JOptionPane.showMessageDialog(null, "No hay mas mensajes");
                        } else {
                            contador += 2; //Se aumentan 2 porque un mensaje esta compuesto por el mensaje y la fecha.
                            AREA_TEXTO.setText((String) mensajes.get(contador)); //Muestra el mensaje que se encuentra en el arrayList en la posicion contador.
                        }
                    } else { //Si no es ninguna opcion anterior entra al else que significa que desea eliminar un mensaje.
                        if (!mensajes.isEmpty()) { //Verifica que aun existan mensajes a eliminar.
                            if (JOptionPane.showConfirmDialog(null, "Estas seguro que desesas eliminar el mensaje") == 0) { //si esta seguro de eliminar entra.
                                JOptionPane.showMessageDialog(null, "Mensaje eliminado");
                            }
                        } else {//Si la lista esta vacia indica que no hay mensajes que eliminar.
                            JOptionPane.showMessageDialog(null, "No hay mensajes a eliminar");
                        }
                    }
                }
            }
        }
    }
}
