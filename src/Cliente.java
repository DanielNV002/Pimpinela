import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int puertoServidor = 5000;
        byte[] buffer = new byte[2048]; // Buffer para enviar el mensaje
        String mensaje = "";

        try {
            // Creamos el socket fuera del bucle para no crear y cerrar en cada iteración
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            DatagramSocket socketUDP = new DatagramSocket();

            while (!mensaje.equalsIgnoreCase("Por eso vete, olvida mi nombre, mi cara, mi casa y pega la vuelta")) {
                System.out.print("C: ");
                mensaje = in.nextLine();

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
            e.printStackTrace();
        }
    }
}
