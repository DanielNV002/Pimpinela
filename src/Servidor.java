import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Clase Servidor que simula un servidor UDP para recibir y responder mensajes
 * de un cliente. El servidor responde a ciertas preguntas con respuestas predefinidas
 * hasta que se recibe un mensaje específico, tras lo cual continuará escuchando
 * y respondiendo mensajes.
 *
 * El servidor utiliza un socket UDP para la comunicación.
 */
public class Servidor {

    /**
     * Metodo principal que inicia el servidor y permite recibir y responder
     * mensajes a través de UDP.
     *
     * El servidor permanece en un bucle infinito, esperando recibir mensajes
     * de cualquier cliente, procesando esos mensajes y respondiendo con mensajes
     * predefinidos.
     *
     * @param args Argumentos de la línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        int puerto = 5000; // Puerto en el que el servidor escucha
        byte[] buffer = new byte[2048];  // Buffer para recibir datos

        try {
            // Servidor
            System.out.println("Iniciado el servidor UDP");
            DatagramSocket socketUDP = new DatagramSocket(puerto); // Se crea el socket UDP en el puerto especificado

            while (true) {
                // Creo un paquete de información para recibir los datos
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);

                // El servidor recibe el mensaje
                socketUDP.receive(peticion);
                System.out.println("RECIBO LA INFORMACION DEL CLIENTE");

                // Convertimos los datos del paquete a un String
                String mensaje = new String(peticion.getData(), 0, peticion.getLength());

                // Obtenemos el puerto y la dirección del cliente
                int puertoCliente = peticion.getPort();
                InetAddress direccion = peticion.getAddress();

                // Se verifica el mensaje recibido y se genera la respuesta
                String respuestaMensaje;
                byte[] respuestaBuffer;

                // Se asignan las respuestas correspondientes a ciertos mensajes predefinidos
                if (mensaje.equalsIgnoreCase("¿Quien es?")) {
                    respuestaMensaje = "Soy yo";
                } else if (mensaje.equalsIgnoreCase("¿Que vienes a buscar?")) {
                    respuestaMensaje = "A ti";
                } else if (mensaje.equalsIgnoreCase("Ya es tarde")) {
                    respuestaMensaje = "¿Por que?";
                } else if (mensaje.equalsIgnoreCase("Porque ahora soy yo la que quiere estar sin ti")) {
                    respuestaMensaje = "Por eso vete, olvida mi nombre, mi cara, mi casa y pega la vuelta";
                } else {
                    respuestaMensaje = "Error";
                }

                // Convertimos la respuesta a bytes
                respuestaBuffer = respuestaMensaje.getBytes();

                // Enviamos la respuesta al cliente
                System.out.println("ENVIO LA INFORMACION AL CLIENTE: " + respuestaMensaje);
                DatagramPacket respuesta = new DatagramPacket(respuestaBuffer, respuestaBuffer.length, direccion, puertoCliente);
                socketUDP.send(respuesta);
            }

        } catch (IOException e) {
            throw new RuntimeException(e); // Manejo de excepciones de entrada/salida
        }
    }
}
