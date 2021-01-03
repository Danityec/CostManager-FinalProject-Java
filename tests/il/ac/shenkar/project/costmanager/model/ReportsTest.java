package il.ac.shenkar.project.costmanager.model;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportsTest {
    private DerbyDB dataBase;
    private ResultSet rs;
    private Expense expense;
    private Income income;
    private Category category;
    private Reports reports;

    @BeforeEach
    void setUp() throws CostManagerException {
        dataBase = new DerbyDB();
        rs = null;
        expense = new Expense(dataBase);
        income = new Income(dataBase);
        category = new Category(dataBase);
        reports = new Reports(category, expense, income);
    }

    @AfterEach
    void tearDown() {
        dataBase = null;
        rs = null;
        expense = null;
        income = null;
        category = null;
        reports = null;
    }

    @Test
    void report() {
        String dateA = "2020-12-01";
        String dateB = "2020-12-20";
        Map<String, Double> Expected = new HashMap<String, Double>();
        Expected.put("Biluy", 8088.4);
        Expected.put("Clothes Shopping", 808.2);

        Map<String, Double> Actual = new HashMap<String, Double>();
        try {
            Actual = reports.report(dateA, dateB);
        } catch (Exception e) {}


        assertEquals(Expected, Actual);
    }

    @Test
    void balance() {
        double Expected = 41604.4;
        double Actual = 0;
        try {
            Actual = reports.balance();
        } catch (Exception e) {}


        assertEquals(Expected, Actual, 0.02);
    }
}