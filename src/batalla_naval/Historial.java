package batalla_naval;

import java.io.*;
import java.util.*;

public class Historial {
    private static final String ARCHIVO = "historial.txt";
    private final Map<String, Integer> victorias = new HashMap<>();

    public void registrarPartida(String alias1, String alias2, String ganador) {
        try (FileWriter fw = new FileWriter(ARCHIVO, true)) {
            String linea = String.format("%s vs %s - Ganador: %s - Fecha: %s%n",
                    alias1, alias2, ganador, new Date());
            fw.write(linea);
            victorias.put(ganador, victorias.getOrDefault(ganador, 0) + 1);
        } catch (IOException e) {
            System.out.println("Error al guardar el historial: " + e.getMessage());
        }
    }

    public void mostrarRanking() {
        System.out.println("\n=== Ranking de Jugadores ===");
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(victorias.entrySet());

        // 🔹 Ordenamos con Insertion Sort (sencillo)
        for (int i = 1; i < lista.size(); i++) {
            Map.Entry<String, Integer> actual = lista.get(i);
            int j = i - 1;
            while (j >= 0 && lista.get(j).getValue() < actual.getValue()) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, actual);
        }

        for (Map.Entry<String, Integer> e : lista) {
            System.out.println(e.getKey() + ": " + e.getValue() + " victorias");
        }
    }
}