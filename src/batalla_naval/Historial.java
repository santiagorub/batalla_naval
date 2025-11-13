package batalla_naval;

import java.io.*;
import java.util.*;

/**
 * Gestiona el historial de partidas del juego Batalla Naval.
 * 
 * <p>Esta clase permite:</p>
 * <ul>
 *     <li>Registrar partidas jugadas en un archivo de texto.</li>
 *     <li>Contar la cantidad de victorias por jugador.</li>
 *     <li>Mostrar el historial completo leído desde el archivo.</li>
 *     <li>Mostrar un ranking ordenado por número de victorias.</li>
 *     <li>Buscar si un jugador ya existe en el historial.</li>
 * </ul>
 *
 * <p>El archivo usado es <strong>historial.txt</strong>, generado en el directorio raíz del proyecto.</p>
 *
 * @author Valentina Diaz
 * @author Santiago Opazo
 * @author Tobias Villarroel
 * @version 1.0
 */
public class Historial {

    /** Nombre del archivo donde se guardan las partidas. */
    private static final String ARCHIVO = "historial.txt";

    /** Mapa con el total de victorias por jugador. */
    private final Map<String, Integer> victorias = new HashMap<>();

    /**
     * Constructor: carga el historial existente si el archivo ya fue creado.
     * Si no existe, muestra un mensaje indicando que se generará automáticamente.
     */
    public Historial() {

        // Intentar leer el archivo existente
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                // Filtrar solo líneas que contienen ganador
                if (linea.contains("Ganador: ")) {

                    // Extraer el nombre del ganador
                    String ganador = linea.split("Ganador: ")[1].split(" -")[0].trim();

                    // Incrementar contador de victorias
                    victorias.put(ganador, victorias.getOrDefault(ganador, 0) + 1);
                }
            }

        } catch (IOException e) {
            // El archivo probablemente no existe todavía
            System.out.println("Aún no existe historial, se creará automáticamente.");
        }
    }

    /**
     * Registra una nueva partida en el archivo historial.txt.
     *
     * @param alias1 nombre del jugador 1
     * @param alias2 nombre del jugador 2
     * @param ganador alias del jugador ganador
     */
    public void registrarPartida(String alias1, String alias2, String ganador) {

        try (FileWriter fw = new FileWriter(ARCHIVO, true)) {

            // Generar la línea a escribir
            String linea = String.format(
                    "%s vs %s - Ganador: %s - Fecha: %s%n",
                    alias1, alias2, ganador, new Date()
            );

            fw.write(linea);   // Escribir en el archivo
            fw.flush();        // Forzar guardado inmediato

            // Actualizar mapa de victorias
            victorias.put(ganador, victorias.getOrDefault(ganador, 0) + 1);

            System.out.println("Partida registrada correctamente.");
            System.out.println("Guardando historial en: " + new File(ARCHIVO).getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error al guardar el historial: " + e.getMessage());
        }
    }

    /**
     * Busca si un jugador existe en el historial.
     *
     * @param alias nombre del jugador a buscar
     * @return true si el jugador existe, false si no
     */
    public boolean buscarJugador(String alias) {

        // Recorrer claves del mapa de victorias
        for (String nombre : victorias.keySet()) {
            if (nombre.equalsIgnoreCase(alias)) {
                return true; // Jugador encontrado
            }
        }

        return false; // No existe
    }

    /**
     * Muestra por consola todas las partidas registradas en historial.txt.
     * Si el archivo está vacío o no existe, muestra un mensaje adecuado.
     */
    public void mostrarHistorial() {

        System.out.println("\n=== HISTORIAL DE PARTIDAS ===");

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {

            String linea;
            boolean vacio = true;

            // Leer línea por línea del archivo
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                vacio = false;
            }

            if (vacio)
                System.out.println("No hay partidas registradas aún.");

        } catch (IOException e) {
            System.out.println("No se pudo leer el historial (aún no existe el archivo).");
        }
    }

    /**
     * Muestra un ranking de jugadores ordenado por cantidad de victorias.
     * Se utiliza un algoritmo de ordenamiento Insertion Sort (descendente).
     */
    public void mostrarRanking() {

        System.out.println("\n=== RANKING DE JUGADORES ===");

        // Convertir mapa a lista para poder ordenarlo
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(victorias.entrySet());

        // ---------- INSERTION SORT DESCENDENTE ----------
        for (int i = 1; i < lista.size(); i++) {

            Map.Entry<String, Integer> actual = lista.get(i);
            int j = i - 1;

            // Mover elementos mayores a la derecha
            while (j >= 0 && lista.get(j).getValue() < actual.getValue()) {
                lista.set(j + 1, lista.get(j));
                j--;
            }

            lista.set(j + 1, actual);
        }
        // ------------------------------------------------

        if (lista.isEmpty()) {
            System.out.println("No hay partidas registradas.");
            return;
        }

        // Mostrar ranking ordenado
        for (Map.Entry<String, Integer> e : lista) {
            System.out.println(e.getKey() + ": " + e.getValue() + " victoria(s)");
        }
    }
}
