/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab4p2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**
 *
 * @author ljmc2
 */

class LetterOnlyFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) return;
        if (isLetter(string) && (fb.getDocument().getLength() + string.length()) <= 1) {
            super.insertString(fb, offset, string.toUpperCase(), attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text == null) return;
        if (isLetter(text) && text.length() <= 1) {
            fb.remove(0, fb.getDocument().getLength());
            super.insertString(fb, 0, text.toUpperCase(), attrs);
        }
    }

    private boolean isLetter(String text) {
        return text.matches("[a-zA-Z]");
    }
}

public class MainGUI extends JFrame {

    private JButton btnFijo, btnAgregar, btnAzar, btnVerLista;
    private JTextArea areaJuego;
    private JTextField txtLetra, txtNuevaPalabra;
    private JuegoAhorcadoBase juego;
    private AdminPalabrasSecretas admin;
    private String palabraFija;
    private int indiceReemplazo = -1;
    
    public MainGUI() {
        super("Juego del Ahorcado");
        admin = new AdminPalabrasSecretas();
        palabraFija = null;
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel();
        btnFijo = new JButton("Juego Fijo");
        btnAzar = new JButton("Jugar palabra aleatoria");
        btnVerLista = new JButton("Ver lista de palabras");
        panelTop.add(btnFijo);
        panelTop.add(btnAzar);
        panelTop.add(btnVerLista);
        add(panelTop, BorderLayout.NORTH);

        areaJuego = new JTextArea(10, 30);
        areaJuego.setText("Seleccione un modo de juego.");
        areaJuego.setEditable(false);
        add(new JScrollPane(areaJuego), BorderLayout.CENTER);

        JPanel panelBottom = new JPanel();
        txtLetra = new JTextField(5);
        ((AbstractDocument) txtLetra.getDocument()).setDocumentFilter(new LetterOnlyFilter());
        JButton btnEnviar = new JButton("Enviar Letra");
        txtNuevaPalabra = new JTextField(10);
        btnAgregar = new JButton("Agregar palabra");  // <-- MOVIDO AQUÍ

        panelBottom.add(new JLabel("Ingrese letra: "));
        panelBottom.add(txtLetra);
        panelBottom.add(btnEnviar);
        panelBottom.add(new JLabel("Nueva palabra: "));
        panelBottom.add(txtNuevaPalabra);
        panelBottom.add(btnAgregar);  // <-- MOVIDO AQUÍ

        add(panelBottom, BorderLayout.SOUTH);

        btnFijo.addActionListener(e -> iniciarFijo());
        btnAgregar.addActionListener(e -> agregarPalabra());
        btnAzar.addActionListener(e -> iniciarAzar());
        btnVerLista.addActionListener(e -> mostrarListaPalabras());
        btnEnviar.addActionListener(e -> procesarLetra());
        
        JRootPane rootPane = getRootPane();
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = rootPane.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "enviarLetra");
        actionMap.put("enviarLetra", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEnviar.doClick();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void iniciarFijo() {
        if (admin.getPalabras().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay palabras disponibles. Agrega primero algunas palabras.");
            return;
        }

        if (palabraFija == null) {
            Object[] opciones = admin.getPalabras().toArray();
            String seleccion = (String) JOptionPane.showInputDialog(
                    this,
                    "Seleccione la palabra fija:",
                    "Elegir palabra",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );
            if (seleccion == null) {
                return;
            }
            palabraFija = seleccion;
        }

        juego = new JuegoAhorcadoFijo(palabraFija);
        juego.inicializarPalabraSecreta();
        actualizarArea();
    }

    private void iniciarAzar() {
        if (admin.getPalabras().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay palabras disponibles. Agrega primero algunas palabras.");
            return;
        }
        juego = new JuegoAhorcadoAzar(admin);
        juego.inicializarPalabraSecreta();
        actualizarArea();
    }

    private void agregarPalabra() {
        String palabra = txtNuevaPalabra.getText().toUpperCase();
        if (palabra.isEmpty()) {
            return;
        }

        if (admin.getPalabras().isEmpty()) {

            admin.agregarPalabra(palabra);
            indiceReemplazo = 0;
            JOptionPane.showMessageDialog(this, "Palabra agregada correctamente.");
        } else {

            if (indiceReemplazo == -1) {

                indiceReemplazo = admin.getPalabras().size() - 1;
            }

            admin.getPalabras().set(indiceReemplazo, palabra);
            JOptionPane.showMessageDialog(this, "Palabra reemplazada en posición " + (indiceReemplazo + 1));

            indiceReemplazo--;
            if (indiceReemplazo < 0) {
                indiceReemplazo = admin.getPalabras().size() - 1;
            }
        }

        txtNuevaPalabra.setText("");
    }

    private void mostrarListaPalabras() {
        if (admin.getPalabras().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La lista de palabras está vacía.");
            return;
        }
        Object[] opciones = admin.getPalabras().toArray();
        JOptionPane.showMessageDialog(
                this,
                new JComboBox<>(opciones),
                "Lista de palabras",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void procesarLetra() {
        if (juego == null) {
            return;
        }
        String texto = txtLetra.getText().toUpperCase();
        if (texto.isEmpty()) {
            return;
        }
        char letra = texto.charAt(0);
        txtLetra.setText("");

        if (juego.letrasUsadas.contains(letra)) {
            JOptionPane.showMessageDialog(this, "Letra repetida");
            return;
        }

        juego.letrasUsadas.add(letra);
        if (!juego.verificarLetra(letra)) {
            juego.intentos++;
        }

        actualizarArea();

        if (juego.juegoTerminado()) {
            if (juego.hasGanado()) {
                JOptionPane.showMessageDialog(this, "¡Ganaste! La palabra era: " + juego.palabraSecreta);
            } else {
                JOptionPane.showMessageDialog(this, "¡Perdiste! La palabra era: " + juego.palabraSecreta);
            }
            juego = null;
            areaJuego.setText("");
        }
    }

    private void actualizarArea() {
        if (juego == null) {
            return;
        }

        areaJuego.setText("");
        areaJuego.append(juego.figuraAhorcado.get(juego.intentos) + "\n");
        areaJuego.append("\nPalabra: " + juego.palabraActual + "\n");
        areaJuego.append("Intentos restantes: " + (juego.limiteIntentos - juego.intentos) + "\n");
        areaJuego.append("Letras usadas: " + juego.letrasUsadas + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
