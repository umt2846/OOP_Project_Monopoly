package network.client;

import network.client.clientFacade.ClientFacade;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientReceiver extends Thread {
    private DataInputStream dis;
    private Socket socket;
    private ConnectionChecker connectionChecker;

    public ClientReceiver(DataInputStream dis, Socket socket, ConnectionChecker connectionChecker) {
        this.dis = dis;
        this.socket = socket;
        this.connectionChecker = connectionChecker;

    }

    @Override
    public void run() {
        String received;
        while (true) {
            if (socket.isClosed()) break;
            try {
                received = receive();

                if (received.equals("Connected")) {
                    connectionChecker.setConnected();
                    continue;
                }

            } catch (IOException e) {
                System.out.println("Server is down!!!");
                if (socket.isClosed()) break;
//                ClientFacade.getInstance().reconnect();
                break;
            }


            ClientFacade.getInstance().sendReceivedMessage(received);


        }
        System.out.println("Nothing is received now connection lost!!!!!");
    }

    private synchronized String receive() throws IOException {
        String received = dis.readUTF();
        System.out.println("Client class received Message:\n" + received + "\n\n");

        return received;
    }
}
