package il.ac.shenkar.project.costmanager.model;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    private DerbyDB dataBase;
    private ResultSet rs;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        dataBase = new DerbyDB();
        rs = null;
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        dataBase = null;
        rs = null;
    }

    @org.junit.jupiter.api.Test
    void getAll() {
    }

    @org.junit.jupiter.api.Test
    void getByID() {
    }

    @org.junit.jupiter.api.Test
    void add() {
    }

    @org.junit.jupiter.api.Test
    void delete() {
    }

    @org.junit.jupiter.api.Test
    void update() {
    }
}