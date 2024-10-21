import exception.DataAlreadyExistsException;
import out.AuthHandler;

import java.io.IOException;

public class Main {
    private static final AuthHandler authHandler;

    static {
        try {
            authHandler = new AuthHandler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws DataAlreadyExistsException {
        authHandler.authMenu();
    }
}