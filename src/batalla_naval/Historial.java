package batalla_naval;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class Historial {
    private static final String ARCHIVO = "historial.txt";

    public static void registrar(String j1, String j2, String ganador) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, true))) {
            pw.println(j1 + " vs " + j2 + " - Ganador: " + ganador + " - Fecha: " + LocalDate.now());
        } catch (IOException e) {
            System.out.println("Error al guardar historial.");
        }
    }

    public static void mostrarRanking() {
        Map<String, Integer> victorias = new HashMap<>();

        try {
            for (String linea : Files.readAllLines(Paths.get(ARCHIVO))) {
                if (linea.contains("Ganador:")) {
                    String ganador = linea.split("Ganador: ")[1].split(" -")[0].trim();
                    victorias.put(ganador, victorias.getOrDefault(ganador, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el historial.");
            return;
        }

        ArrayList<Map.Entry<String, Integer>> lista = new ArrayList<>(victorias.entrySet());
        insertion(lista);

        System.out.println("\n===  RANKING DE JUGADORES  ===");
        for (Map.Entry<String, Integer> e : lista)
            System.out.println(e.getKey() + ": " + e.getValue() + " victorias");
    }

    private static void insertion(ArrayList<Map.Entry<String, Integer>> lista) {
        for (int i = 1; i < lista.size(); i++) {
            Map.Entry<String, Integer> key = lista.get(i);
            int j = i - 1;

            while (j >= 0 && key.getValue() > lista.get(j).getValue()) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, key);
        }
    }
}