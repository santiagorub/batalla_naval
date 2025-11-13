package batalla_naval;

import java.io.*;
import java.util.*;

public class Historial {
    private static final String ARCHIVO = "historial.txt";
    private final Map<String, Integer> victorias = new HashMap<>();

    public Historial() {
        // Cargar victorias previas si el archivo existe
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains("Ganador: ")) {
                    String ganador = linea.split("Ganador: ")[1].split(" -")[0].trim();
                    victorias.put(ganador, victorias.getOrDefault(ganador, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("ℹAún no existe historial, se creará automáticamente.");
        }
    }

    public void registrarPartida(String alias1, String alias2, String ganador) {
        try (FileWriter fw = new FileWriter(ARCHIVO, true)) {
            String linea = String.format("%s vs %s - Ganador: %s - Fecha: %s%n",
                    alias1, alias2, ganador, new Date());
            fw.write(linea);
            fw.flush(); //asegura que se escriba en disco

            victorias.put(ganador, victorias.getOrDefault(ganador, 0) + 1);
            System.out.println("Partida registrada correctamente.");
            System.out.println("Guardando historial en: " + new File(ARCHIVO).getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al guardar el historial: " + e.getMessage());
        }
    }

    public void mostrarHistorial() {
        System.out.println("\n=== HISTORIAL DE PARTIDAS ===");
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            boolean vacio = true;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                vacio = false;
            }
            if (vacio) System.out.println("No hay partidas registradas aún.");
        } catch (IOException e) {
            System.out.println("No se pudo leer el historial (aún no existe el archivo).");
        }
    }

    public void mostrarRanking() {
        System.out.println("\n=== RANKING DE JUGADORES ===");
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(victorias.entrySet());

        // Insertion Sort descendente
        for (int i = 1; i < lista.size(); i++) {
            Map.Entry<String, Integer> actual = lista.get(i);
            int j = i - 1;
            while (j >= 0 && lista.get(j).getValue() < actual.getValue()) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, actual);
        }

        if (lista.isEmpty()) {
            System.out.println("No hay partidas registradas.");
            return;
        }

        for (Map.Entry<String, Integer> e : lista) {
            System.out.println(e.getKey() + ": " + e.getValue() + " victoria(s)");
        }
    }
}