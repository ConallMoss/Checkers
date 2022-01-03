package Client;

import java.io.*;
import java.net.Socket;

public class Client {
    private Player player;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private InputStream inputStream;
    private ObjectInputStream objectInputStream;

    public static void main(String[] args) throws IOException {
        Client client = new Client();

    }

    private Client() throws IOException {
        socket = new Socket("localhost", 7777);
        System.out.println("Connection");

        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        inputStream = socket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);

        player = new Player();

    }

    private void receiveBoard() throws IOException, ClassNotFoundException {
        int[][] receivedState = (int[][]) objectInputStream.readObject();
        player.updatePlayerState(receivedState);

    }
}
