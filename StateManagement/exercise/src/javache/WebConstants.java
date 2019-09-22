package javache;

public final class WebConstants {
    public static final Integer DEFAULT_SERVER_PORT = 8000;

    public static final String SERVER_HTTP_VERSION = "HTTP/1.1";

    public static final String RESOURCE_FOLDER_PATH =
            System.getProperty("user.dir")
            + "\\src\\resources\\";

    public static final String ASSETS_FOLDER_PATH =
            RESOURCE_FOLDER_PATH + "assets\\";

    public static final String PAGES_FOLDER_PATH =
            RESOURCE_FOLDER_PATH + "pages\\";

    public static final String LOGIN_PAGE_PATH =
            ASSETS_FOLDER_PATH + "html\\login.html";

    public static final String PROFILE_PAGE_PATH =
            PAGES_FOLDER_PATH + "users\\profile.html";

    public static final String INDEX_PAGE_PATH =
            ASSETS_FOLDER_PATH + "html\\index.html";

    private WebConstants() { }
}
