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
            BufferedReader inFromConsole = new BufferedReader(new InputStreamReader(System.in));

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                if (clientSocket.isClosed()) {
                    break;
                }
                String clientInput;

                clientInput = inFromConsole.readLine();
                if (!clientInput.equals("0") & !clientInput.equals("1")){
                    System.out.println("Opção inválida. Escolha 0 ou 1.");
                    continue;
                }
                outToServer.writeBytes(clientInput + '\n');

            }
        } catch (Exception e) {
        } finally {
        }
    }

    public static void main(String argv[]) throws Exception {
        Socket clientSocket = new Socket("localhost", 6789);
        // por enquanto ainda ta a logica de deixar em letra maiuscula, ent ignorem o 0
        // ou 1 kkkkkk

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        TCPClient client = new TCPClient(clientSocket);

        Thread clientThread = new Thread(client);
        clientThread.start();

        String result;

        try {
            while (true) {
                System.out.println("Digite zero ou um: ");
                result = inFromServer.readLine();

                if (result != null) {
                    System.out.println(result);
                } else {
                    System.out.println("Conexão com o servidor encerrada.");
                    clientSocket.close();
                    
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }
}
