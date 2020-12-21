package il.ac.shenkar.project.costmanager.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

class IncomeTest {
    private DerbyDB dataBase;
    private ResultSet rs;

    @BeforeEach
    void setUp() {
        dataBase = new DerbyDB();
        rs = null;
    }

    @AfterEach
    void tearDown() {
        dataBase = null;
        rs = null;
    }

    @Test
    void getAll() {
    }

    @Test
    void getByID() {
    }

    @Test
    void add() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }
}