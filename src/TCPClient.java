import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient implements Runnable {

    private Socket clientSocket;

    public TCPClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader inFromConsole = new BufferedReader(new InputStreamReader(System.in));

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                String clientInput;

                clientInput = inFromConsole.readLine();

                outToServer.writeBytes(clientInput + '\n');

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String argv[]) throws Exception {
        Socket clientSocket = new Socket("localhost", 6789);
        // por enquanto ainda ta a logica de deixar em letra maiuscula, ent ignorem o 0
        // ou 1 kkkkkk
        System.out.println("Digite zero ou um:");

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        TCPClient client = new TCPClient(clientSocket);

        Thread clientThread = new Thread(client);
        clientThread.start();

        String result;

        try {
            while (true) {
                result = inFromServer.readLine();

                if (result != null) {
                    if (result.equals("Empate")) {
                        System.out.println(result);
                    } else {
                        System.out.println("Jogador " + result + " ");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
