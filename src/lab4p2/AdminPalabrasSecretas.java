/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab4p2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author ljmc2
 */
public class AdminPalabrasSecretas {

    private ArrayList<String> palabras;

    public AdminPalabrasSecretas() {
        palabras = new ArrayList<>(Arrays.asList(
                "PECHUGA", "LOMO", "PAVO", "QUESO", "FRIJOLES",
                "SARDINAS", "LECHE", "HUEVOS", "POLVO", "CHIA"
        ));
    }

    public boolean agregarPalabra(String palabra) {
        palabra = palabra.toUpperCase();
        if (!palabras.contains(palabra)) {
            palabras.add(palabra);
            return true;
        }
        return false; // palabra duplicada
    }

    public String obtenerPalabraAzar() {
        if (palabras.isEmpty()) {
            return null;
        }
        Random rnd = new Random();
        return palabras.get(rnd.nextInt(palabras.size()));
    }

    public ArrayList<String> getPalabras() {
        return palabras;
    }
}
