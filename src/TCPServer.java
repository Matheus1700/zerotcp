import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TCPServer {

    private static final int MAX_CLIENTS = 3;
    private static final ThreadPoolExecutor executorService =
            (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_CLIENTS);
    private static final CountDownLatch latch = new CountDownLatch(MAX_CLIENTS);

    private static final List<String> userResponses = new ArrayList<>();
    private static final List<InetAddress> userAdresses = new ArrayList<java.net.InetAddress>();
    private static final Map<InetAddress, Socket> addressToSocketMap = new HashMap<>();

    public static void main(String argv[]) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6789);
        System.out.println("TCP server rodando!");

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();

            if (executorService.getActiveCount() < MAX_CLIENTS) {
                System.out.println("Cliente conectado ao TCP server");
                executorService.execute(new ClientHandler(connectionSocket, latch));
            } else {
                System.out.println("Limite máximo de clientes atingido. Rejeitando a conexão.");
                connectionSocket.close();
            }
        }
    }

    static class ClientHandler implements Runnable {
        private Socket connectionSocket;
        private CountDownLatch latch;

        public ClientHandler(Socket connectionSocket, CountDownLatch latch) {
            this.connectionSocket = connectionSocket;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                String clientSentence;
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                clientSentence = inFromClient.readLine();
                System.out.println("Recebido do cliente: " + clientSentence);

                // Salva a resposta e o endereço IP de cada usuário
                userResponses.add(clientSentence);
                userAdresses.add(connectionSocket.getInetAddress());
                addressToSocketMap.put(connectionSocket.getInetAddress(), connectionSocket);

                // Contagem que + um cliente forneceu uma entrada
                latch.countDown();
                // Aguarda a entrada de todos os clientes
                latch.await();


                // Envia a resposta depois que todos deram input | Aqui vai o código do vencedor
                int indiceVencedor = determinarIndiceVencedor(userResponses);

                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                String response = "Jogador " + (indiceVencedor + 1) + "venceu!" + '\n';
                if (indiceVencedor == -1) { response = "Empate" + '\n'; }
                outToClient.writeBytes(response);
                connectionSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public int determinarIndiceVencedor(List<String> userResponses) {
            for (int i = 0; i < userResponses.size(); i++) {
                String jogadorAtual = userResponses.get(i);
                boolean jogadorEliminado = false;

                for (int j = 0; j < userResponses.size(); j++) {
                    if (i != j && jogadorAtual.equals(userResponses.get(j))) {
                        jogadorEliminado = false;
                        break;
                    } else {
                        jogadorEliminado = true;
                    }
                }

                if (jogadorEliminado) {
                    return i;
                }
            }

            return -1; // em caso de empate
        }

        // Criei essa função aqui mas acabei nem usando
        public InetAddress obterEnderecoVencedor(List<InetAddress> userAddresses, int indiceVencedor) {
            if (indiceVencedor >= 0 && indiceVencedor < userAddresses.size()) {
                return userAddresses.get(indiceVencedor);
            }
            return null;
        }
    }
}
