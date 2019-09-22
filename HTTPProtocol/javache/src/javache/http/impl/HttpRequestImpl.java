package javache.http.impl;

import javache.http.interfaces.HttpRequest;

import java.util.HashMap;

public class HttpRequestImpl implements HttpRequest {
    private String method;
    private String requestUrl;
    private HashMap<String, String> headers;
    private HashMap<String, String> bodyParameters;

    public HttpRequestImpl(String requestContent) {
        this.headers = new HashMap<>();
        this.bodyParameters = new HashMap<>();
        this.intializeProperties(requestContent);
    }

    private void intializeProperties(String requestContent) {
        String[] requestLines = requestContent.split("\r\n");
        String[] firstLine = requestLines[0].split("\\s+");
        this.method = firstLine[0];
        this.requestUrl = firstLine[1];

        for (int i = 1; i < requestLines.length - 2; i++) {
            String[] headerTokens = requestLines[i].split(": ");
            this.headers.put(headerTokens[0], headerTokens[1]);
        }

        String[] bodyParams = requestLines[requestLines.length - 1].split("&");

        for (String param : bodyParams) {
            String[] paramTokens = param.split("=");
            this.bodyParameters.put(paramTokens[0], paramTokens[1]);
        }

    }

    @Override
    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public HashMap<String, String> getBodyParameters() {
        return this.bodyParameters;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getRequestUrl() {
        return this.requestUrl;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    @Override
    public void addBodyParameter(String parameter, String value) {
        this.bodyParameters.put(parameter, value);
    }

    @Override
    public boolean isResource() {
        return this.requestUrl.contains(".");
    }
}
