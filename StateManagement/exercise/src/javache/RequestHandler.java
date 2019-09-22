package javache;

import com.sun.javaws.HtmlOptions;
import db.entities.User;
import db.repositories.UserRepository;
import javache.http.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RequestHandler {
    private static final String HTML_EXTENSION_AND_SEPARATOR = ".html";

    private HttpRequest httpRequest;

    private HttpResponse httpResponse;

    private HttpSessionStorage sessionStorage;

    private UserRepository userRepository;

    public RequestHandler(HttpSessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
        this.userRepository = new UserRepository();
    }

    public byte[] handleRequest(String requestContent) {
        this.httpRequest = new HttpRequestImpl(requestContent);
        this.httpResponse = new HttpResponseImpl();

        byte[] result = null;

        if(this.httpRequest.getMethod().equals("GET")) {
            result = this.processGetRequest();
        } if (this.httpRequest.getMethod().equals("POST")) {
            result = this.processPostRequest();
        }

        this.sessionStorage.refreshSessions();

        return result;
    }

    private byte[] ok(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.Ok);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] badRequest(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.BadRequest);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] notFound(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.NotFound);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] redirect(byte[] content, String location) {
        this.httpResponse.setStatusCode(HttpStatus.SeeOther);
        this.httpResponse.addHeader("Location", location);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] internalServerError(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.InternalServerError);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private String getMimeType(File file) {
        String fileName = file.getName();

        if(fileName.endsWith("css")) {
            return "text/css";
        } else if (fileName.endsWith("html")) {
            return "text/html";
        } else if (fileName.endsWith("jpg") || fileName.endsWith("jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith("png")) {
            return "image/png";
        }

        return "text/plain";
    }

    private byte[] processResourceRequest() {
        String assetPath = WebConstants.ASSETS_FOLDER_PATH +
                this.httpRequest.getRequestUrl();

        File file = new File(assetPath);

        if(!file.exists() || file.isDirectory()) {
            return this.notFound(("Asset not found!").getBytes());
        }

        byte[] result = null;

        try {
            result = Files.readAllBytes(Paths.get(assetPath));
        } catch (IOException e) {
            return this.internalServerError(("Something went wrong!").getBytes());
        }

        this.httpResponse.addHeader("Content-Type", this.getMimeType(file));
        this.httpResponse.addHeader("Content-Length", result.length + "");
        this.httpResponse.addHeader("Content-Disposition", "inline");


        return this.ok(result);
    }

    private byte[] processPageRequest(String page) {
        String pagePath = WebConstants.PAGES_FOLDER_PATH +
                page
                + HTML_EXTENSION_AND_SEPARATOR;

        File file = new File(pagePath);

        if(!file.exists() || file.isDirectory()) {
            return this.notFound(("Page not found!").getBytes());
        }

        byte[] result = null;

        try {
            result = Files.readAllBytes(Paths.get(pagePath));
        } catch (IOException e) {
            return this.internalServerError(("Something went wrong!").getBytes());
        }

        this.httpResponse.addHeader("Content-Type", this.getMimeType(file));

        return this.ok(result);
    }

    private byte[] processGetRequest() {
        if(this.httpRequest.getRequestUrl().equals("/")) {
            //INDEX

            try {
                return this.redirect(Files.readAllBytes(Paths.get(WebConstants.INDEX_PAGE_PATH)), "/html/index.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (this.httpRequest.getRequestUrl().equals("/login")) {
            //LOGIN
            HttpSession session = new HttpSessionImpl();
            session.addAttribute("username", "Pesho");

            this.sessionStorage.addSession(session);

            this.httpResponse.addCookie("Javache", session.getId());
            return this.processPageRequest(this.httpRequest.getRequestUrl());
        } else if (this.httpRequest.getRequestUrl().equals("/logout")) {
            //LOGOUT

            if(!this.httpRequest.getCookies().containsKey("Javache")) {
                return this.redirect(("You must login to access this route!").getBytes()
                        , "/");
            }

            String sessionId = this.httpRequest.getCookies().get("Javache").getValue();
            this.sessionStorage.getById(sessionId).invalidate();

            this.httpResponse.addCookie("Javache", "deleted; expires=Thu, 01 Jan 1970 00:00:00 GMT;");

            return this.ok(("Successfully expired").getBytes());
        } else if (this.httpRequest.getRequestUrl().equals("/forbidden")) {
            //FORBIDDEN

            if(!this.httpRequest.getCookies().containsKey("Javache")) {
                return this.redirect(("You must login to access this route!").getBytes()
                        , "/");
            }

            String sessionId = this.httpRequest.getCookies().get("Javache").getValue();
            HttpSession session = this.sessionStorage.getById(sessionId);
            String username = session.getAttributes().get("username").toString();

            return this.ok(("HELLO " + username + "!!!").getBytes());
        } else if (this.httpRequest.getRequestUrl().equals("/users/profile")) {
            if (!this.httpRequest.getCookies().containsKey("Javache")) {
                return this.ok(("You must login to access this route!").getBytes());
            }

            String sessionId = this.httpRequest.getCookies().get("Javache").getValue();
            HttpSession session = this.sessionStorage.getById(sessionId);
            String email = session.getAttributes().get("user").toString();

            File profile = new File(WebConstants.PROFILE_PAGE_PATH);
            HtmlOptions htmlOptions = null;
            try {
                htmlOptions = HtmlOptions.create(new FileInputStream(profile));
            } catch (IOException e) {
                e.printStackTrace();
            }


//            htmlOptions.getParameters().setProperty("email", email);
            try {
                return this.ok(Files.readAllBytes(Paths.get(WebConstants.PROFILE_PAGE_PATH)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this.processResourceRequest();
    }

    private byte[] processPostRequest() {
        if (this.httpRequest.getRequestUrl().equals("/html/register.html")) {
            String email = this.httpRequest.getBodyParameters().get("email");
            String password = this.httpRequest.getBodyParameters().get("password");
            String passwordConfirm = this.httpRequest.getBodyParameters().get("passwordConfirm");

            if (!password.equals(passwordConfirm)) {
                return this.badRequest(("Error 400: Malformed Request...").getBytes());
            } else if (this.userRepository.isUserEmailPresentInDb(email)) {
                return this.badRequest(("Error 400: User with this email already exists...").getBytes());
            }

            User registerUser = new User(email, password);

            this.userRepository.addUser(registerUser);
            try {
                return this.redirect(Files.readAllBytes(Paths.get(WebConstants.LOGIN_PAGE_PATH)), "/html/login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (this.httpRequest.getRequestUrl().equals("/html/login.html")) {
            String email = this.httpRequest.getBodyParameters().get("email");
            String password = this.httpRequest.getBodyParameters().get("password");

            if (!this.userRepository.isUserEmailPresentInDb(email)) {
                return this.badRequest(("Error 400: User doesn't exist").getBytes());
            } else if (!this.userRepository.getUserByEmail(email).getPassword().equals(password)) {
                return this.badRequest(("Error 400: Incorrect user password").getBytes());
            }

            HttpSession session = new HttpSessionImpl();
            session.addAttribute("user", email);

            this.sessionStorage.addSession(session);
            this.httpResponse.addCookie("Javache", session.getId() + "; Path=/");

            try {
                return this.redirect(Files.readAllBytes(Paths.get(WebConstants.PROFILE_PAGE_PATH)), "/users/profile");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        return null;
    }
}
