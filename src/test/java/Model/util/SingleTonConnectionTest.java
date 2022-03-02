package Model.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleTonConnectionTest {
    @Test
    public void testConnection(){
        assertDoesNotThrow(SingleTonConnection::getInstance);
    }
}