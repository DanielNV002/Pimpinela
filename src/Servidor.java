import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 5000;
        byte[] buffer = new byte[2048];  // Buffer para recibir datos

        try {
            // Servidor
            System.out.println("Iniciado el servidor UDP");
            DatagramSocket socketUDP = new DatagramSocket(puerto);

            while (true) {
                // Creo un paquete de información
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);

                // No sabemos quien nos contacta
                socketUDP.receive(peticion);
                System.out.println("RECIBO LA INFORMACION DEL CLIENTE");

                // A partir del mensaje, convertimos a String solo con los datos recibidos
                String mensaje = new String(peticion.getData(), 0, peticion.getLength());

                // En el mensaje me viene el puerto
                int puertoCliente = peticion.getPort();
                // Conseguimos la dirección
                InetAddress direccion = peticion.getAddress();

                // Verificamos si el mensaje recibido es uno de los conocidos
                String respuestaMensaje;
                byte[] respuestaBuffer;

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

                // Convertir la respuesta a bytes
                respuestaBuffer = respuestaMensaje.getBytes();

                // Respuesta del servidor al cliente
                System.out.println("ENVIO LA INFORMACION DEL CLIENTE: " + respuestaMensaje);
                DatagramPacket respuesta = new DatagramPacket(respuestaBuffer, respuestaBuffer.length, direccion, puertoCliente);
                socketUDP.send(respuesta);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
