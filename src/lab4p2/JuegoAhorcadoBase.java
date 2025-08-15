/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab4p2;

import java.util.ArrayList;

/**
 *
 * @author Mayra Bardales
 */
public abstract class JuegoAhorcadoBase implements JuegoAhorcado {

    protected String palabraSecreta;
    protected String palabraActual;
    protected int intentos;
    protected final int limiteIntentos = 6;
    protected ArrayList<Character> letrasUsadas;
    protected ArrayList<String> figuraAhorcado;//personaje se guarda en arraylist

    public JuegoAhorcadoBase() {
        letrasUsadas = new ArrayList<>();
        figuraAhorcado = new ArrayList<>();
        intentos = 0;
        inicializarFigura();
    }

    private void inicializarFigura() {
        String espacio = "                                                                               ";
        figuraAhorcado.add(espacio + "+---+\n"
                + espacio + "      |\n"
                + espacio + "      |\n"
                + espacio + "      |\n"
                + espacio + "     ===");

        figuraAhorcado.add(espacio + "+---+\n"
                + espacio + "O   |\n"
                + espacio + "      |\n"
                + espacio + "      |\n"
                + espacio + "     ===");

        figuraAhorcado.add(espacio + "+---+\n"
                + espacio + "O   |\n"
                + espacio + " |   |\n"
                + espacio + "     |\n"
                + espacio + "    ===");

        figuraAhorcado.add(espacio + "+---+\n"
                + espacio + "O   |\n"
                + espacio + "/|   |\n"
                + espacio + "     |\n"
                + espacio + "    ===");

        figuraAhorcado.add(espacio + "+---+\n"
                + espacio + "O   |\n"
                + espacio + "/|\\  |\n"
                + espacio + "     |\n"
                + espacio + "    ===");

        figuraAhorcado.add(espacio + "+---+\n"
                + espacio + "O   |\n"
                + espacio + "/|\\  |\n"
                + espacio + "/    |\n"
                + espacio + "    ===");

        figuraAhorcado.add(espacio + "+---+\n"
                + espacio + "O   |\n"
                + espacio + "/|\\  |\n"
                + espacio + "/ \\  |\n"
                + espacio + "    ===");
    }

    public void mostrarFigura() {
        System.out.println(figuraAhorcado.get(intentos));
    }

    public void mostrarEstado() {
        System.out.println("\nPalabra: " + palabraActual);
        System.out.println("Intentos: " + (limiteIntentos - intentos));
        System.out.println("Letras usadas: " + letrasUsadas);
    }

    public boolean juegoTerminado() {
        return intentos >= limiteIntentos || hasGanado();
    }

    public abstract void actualizarPalabraActual(char letra);

    public abstract boolean verificarLetra(char letra);

    public abstract boolean hasGanado();
}
