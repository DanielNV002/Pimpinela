import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Clase Cliente que simula una comunicación UDP con un servidor.
 * En este cliente, el usuario puede enviar mensajes al servidor hasta que se ingrese
 * el mensaje de terminación: "Por eso vete, olvida mi nombre, mi cara, mi casa y pega la vuelta".
 *
 * El cliente utiliza un socket UDP para enviar y recibir mensajes desde un servidor.
 */
public class Cliente {

    /**
     * Metodo principal que inicia la comunicación con el servidor.
     *
     * Este metodo:
     * <ul>
     *   <li>Inicializa el socket UDP para la comunicación.</li>
     *   <li>Permite al usuario enviar mensajes al servidor hasta que ingrese el mensaje de salida.</li>
     *   <li>Recibe las respuestas del servidor y las muestra en consola.</li>
     * </ul>
     *
     * @param args Argumentos de la línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int puertoServidor = 5000; // Puerto del servidor al que se conecta el cliente
        byte[] buffer = new byte[2048]; // Buffer para enviar el mensaje
        String mensaje = ""; // Mensaje que el cliente va a enviar

        try {
            // Creamos el socket fuera del bucle para no crear y cerrar en cada iteración
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            DatagramSocket socketUDP = new DatagramSocket(); // Socket UDP para la comunicación

            while (!mensaje.equalsIgnoreCase("Por eso vete, olvida mi nombre, mi cara, mi casa y pega la vuelta")) {
                System.out.print("C: ");
                mensaje = in.nextLine(); // Leemos el mensaje del usuario

                // Convertimos el mensaje a bytes y lo enviamos
                buffer = mensaje.getBytes(); // Reasignamos buffer para el mensaje

                DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length, direccionServidor, puertoServidor);
                socketUDP.send(pregunta); // Enviamos el mensaje al servidor

                // Preparamos un nuevo buffer para recibir la respuesta del servidor
                DatagramPacket peticion = new DatagramPacket(new byte[2048], 2048);

                // Recibimos la respuesta del servidor
                socketUDP.receive(peticion);

                // Convertimos los datos recibidos en un String (usando getLength() para evitar caracteres no deseados)
                mensaje = new String(peticion.getData(), 0, peticion.getLength());

                System.out.println("S: " + mensaje); // Imprimimos la respuesta del servidor
            }

            // Cerramos el socket después de terminar la comunicación
            socketUDP.close();

        } catch (IOException e) {
            e.printStackTrace(); // Manejamos las excepciones de entrada/salida
        }
    }
}
