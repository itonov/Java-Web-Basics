package javache.http.impl;

import javache.http.interfaces.HttpResponse;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class HttpResponseImpl implements HttpResponse {
    private HashMap<String, String> headers;
    private int statusCode;
    private byte[] content;

    public HttpResponseImpl() {
        this.headers = new HashMap<>();
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public byte[] getBytes() {
        byte[] responseLine = ("HTTP/1.1 " + this.statusCode + System.lineSeparator()).getBytes();
        List<String> headersAsString = this.headers.entrySet()
                .stream()
                .map(kvp -> kvp.getKey() + ": " + kvp.getValue() + "\r\n")
                .collect(Collectors.toList());
        headersAsString.add("\r\n");

        int resultLength = responseLine.length + headersAsString.stream().mapToInt(s -> s.getBytes().length).sum() + this.content.length;

        byte[] result = new byte[resultLength];

        System.arraycopy(responseLine, 0, result, 0, responseLine.length);
        int arrayIndex = responseLine.length;
        for (String head : headersAsString) {
            System.arraycopy(head.getBytes(), 0, result, arrayIndex, head.getBytes().length);
            arrayIndex += head.length();
        }

        System.arraycopy(this.content, 0, result, arrayIndex, this.content.length);

        return result;
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }
}
