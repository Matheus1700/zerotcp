import java.io.BufferedReader;
import java.io.DataOutputStream;
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
            String sentence;
            String modifiedSentence;

            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            sentence = inFromUser.readLine();

            outToServer.writeBytes(sentence + '\n');

            modifiedSentence = inFromServer.readLine();

            if (modifiedSentence != null) {
                if (modifiedSentence.equals("Empate")) {
                    System.out.println(modifiedSentence);
                } else {
                    System.out.println("Jogador " + modifiedSentence);
                }
            }

            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String argv[]) throws Exception {
        Socket clientSocket = new Socket("localhost", 6789);
        //por enquanto ainda ta a logica de deixar em letra maiuscula, ent ignorem o 0 ou 1 kkkkkk
        System.out.println("Tentativa de conexão feita, digite zero ou um:");

        TCPClient client = new TCPClient(clientSocket);

        Thread clientThread = new Thread(client);
        clientThread.start();
    }
}
