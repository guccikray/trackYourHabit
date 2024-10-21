package adminEntity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AdminTest {

    @Test
    void testCheckIfAdmin_ReturnsTrue() {
        Admin admin = Admin.getInstance();

        assertTrue(admin.checkIfAdmin("admin", "admin@gmail.com", "root"));

    }

    @Test
    void testCheckIfAdmin_ReturnsFalse() {
        Admin admin = Admin.getInstance();

        assertThrows(IllegalArgumentException.class, () -> {admin.checkIfAdmin("admin",
                "admin@gmail.com", "reet");
        });
    }

}
