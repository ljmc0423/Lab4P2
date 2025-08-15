/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab4p2;

import java.util.Scanner;

/**
 *
 * @author ljmc2
 */
public class JuegoAhorcadoFijo extends JuegoAhorcadoBase {

    private String palabra;

    public JuegoAhorcadoFijo(String palabra) {
        this.palabra = palabra.toUpperCase();
    }

    @Override
    public void inicializarPalabraSecreta() {
        palabraSecreta = palabra;
        palabraActual = "_".repeat(palabraSecreta.length());
    }

    @Override
    public void jugar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Juego Ahorcado (Palabra Fija) ===");
        inicializarPalabraSecreta();

        while (!juegoTerminado()) {
            mostrarFigura();
            mostrarEstado();
            System.out.print("Ingrese una letra: ");
            char letra = sc.next().toUpperCase().charAt(0);

            if (letrasUsadas.contains(letra)) {
                System.out.println("Letra repetida. Intente otra.");
                continue;
            }

            letrasUsadas.add(letra);
            if (!verificarLetra(letra)) {
                intentos++;
            }
        }

        mostrarFigura();
        if (hasGanado()) {
            System.out.println("¡Felicidades! Ganaste. La palabra era: " + palabraSecreta);
        } else {
            System.out.println("¡Perdiste! La palabra era: " + palabraSecreta);
        }
    }

    @Override
    public void actualizarPalabraActual(char letra) {
        StringBuilder nueva = new StringBuilder(palabraActual);
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (palabraSecreta.charAt(i) == letra) {
                nueva.setCharAt(i, letra);
            }
        }
        palabraActual = nueva.toString();
    }

    @Override
    public boolean verificarLetra(char letra) {
        if (palabraSecreta.contains(String.valueOf(letra))) {
            actualizarPalabraActual(letra);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasGanado() {
        return palabraActual.equals(palabraSecreta);
    }
}
