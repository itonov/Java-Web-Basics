package javache;

import javache.http.impl.HttpRequestImpl;
import javache.http.impl.HttpResponseImpl;
import javache.http.interfaces.HttpRequest;
import javache.http.interfaces.HttpResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static javache.WebConstraints.STATUS_CODE_NOT_FOUND;
import static javache.WebConstraints.STATUS_CODE_OK;

public class RequestHandler {

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    public byte[] handleRequest(String requestContent) throws IOException {
        this.httpRequest = new HttpRequestImpl(requestContent);
        this.httpResponse = new HttpResponseImpl();

        if (this.httpRequest.isResource()) {
            displayResource();
        } else {
            switch (this.httpRequest.getRequestUrl()) {
                case "/index":
                    this.httpResponse.setStatusCode(STATUS_CODE_OK);
                    addBasicHeaders();
                    this.httpResponse.addHeader("Content-Type", "text/html");
                    this.httpResponse.addHeader("Content-Disposition", "inline");
                    this.httpResponse.setContent(Files.readAllBytes(
                            Paths.get("D:\\Java-Web-Basics\\HTTPProtocol\\javache\\src\\resources\\pages\\index.html")));
                    break;
                default:
                    this.httpResponse.setStatusCode(STATUS_CODE_NOT_FOUND);
                    addBasicHeaders();
                    break;
            }
        }

        return this.httpResponse.getBytes();
    }

    private void displayResource() throws IOException {
        String[] requestTokens = this.httpRequest.getRequestUrl().split("/");
        String resourceName = requestTokens[requestTokens.length - 1];

        switch (resourceName) {
            case "web-server.png":
                this.httpResponse.setStatusCode(STATUS_CODE_OK);
                addBasicHeaders();
                this.httpResponse.setContent(Files.readAllBytes(
                        Paths.get("D:\\Java-Web-Basics\\HTTPProtocol\\javache\\src\\resources\\assets\\web-server.png")));
                break;
            default:
                this.httpResponse.setStatusCode(STATUS_CODE_NOT_FOUND);
                addBasicHeaders();
                break;
        }
    }

    private void addBasicHeaders() {
        this.httpResponse.addHeader("Date", LocalDate.now().toString());
        this.httpResponse.addHeader("Server", "Japache/-1.0.0");
    }
}
