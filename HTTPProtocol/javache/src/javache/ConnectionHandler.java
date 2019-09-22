package javache;

import javache.io.Reader;
import javache.io.Writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionHandler extends Thread {

    private Socket clientSocket;
    private RequestHandler handler;
    private InputStream clientSocketInputStream;
    private OutputStream clientSocketOutputStream;

    public ConnectionHandler(Socket clientSocket, RequestHandler handler) {
        this.initializeConnection(clientSocket);
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            String requestContent = Reader.readAllLines(this.clientSocketInputStream);
            byte[] responseContent = this.handler.handleRequest(requestContent);
            Writer.writeBytes(responseContent, this.clientSocketOutputStream);
            this.clientSocketInputStream.close();
            this.clientSocketOutputStream.close();
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeConnection(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.clientSocketInputStream = this.clientSocket.getInputStream();
            this.clientSocketOutputStream = this.clientSocket.getOutputStream();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


}
