import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TestClient {
    public static void main(String[] args) {
        final String SERVER_IP = "127.0.0.1"; 
        final int SERVER_PORT = 8080;         

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

           
            String msg = "EU:roberdoo";
            byte[] data = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddr, SERVER_PORT);
            socket.send(packet);
            System.out.println("Mensagem enviada: " + msg);

           
            byte[] buffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);

            String respMsg = new String(response.getData(), 0, response.getLength());
            System.out.println("Resposta do servidor: " + respMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
