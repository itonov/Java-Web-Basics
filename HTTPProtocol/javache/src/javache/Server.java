package javache;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.FutureTask;

import static javache.WebConstraints.PORT;
import static javache.WebConstraints.SOCKET_TIMEOUT_FIVE_SECONDS;

public class Server {
    private ServerSocket server;

    public void run() throws IOException {
        this.server = new ServerSocket(PORT);
        this.server.setSoTimeout(SOCKET_TIMEOUT_FIVE_SECONDS);

        while (true) {
            try (Socket clientSocket = this.server.accept()){
                clientSocket.setSoTimeout(SOCKET_TIMEOUT_FIVE_SECONDS);

                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket, new RequestHandler());
                FutureTask<?> task = new FutureTask<>(connectionHandler, null);
                task.run();
            } catch (SocketTimeoutException ste) {
                System.out.println("Connection timed out");
            }
        }
    }
}
