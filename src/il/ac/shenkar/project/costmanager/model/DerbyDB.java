/**
 * The DerbyDB class is used to connect and to correspond with the actual DB.
 * This class is implemented using a the syntax of Derby specific language.
 * @param connection is an object of type Connection to be used to establish the connection the the DB
 * @param statement is an object of type Statement to be used for executing requests to the DB
 * @param rs is an object of type ResultSet ti be used for the data returned by the DB from a query
 */

package il.ac.shenkar.project.costmanager.model;
import java.sql.*;

public class DerbyDB implements DB {
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet rs = null;

    /*
     * The constructor calls the openConnection method so that the connection may be established.
     */
    public DerbyDB() {
        this.openConnection();
    }

    /*
     * openConnection method creates the DB and initializes the connection.
     */
    public void openConnection() {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        String protocol = "jdbc:derby:CostManager;create=true";
        try {
            Class.forName(driver);
            try {
                connection = DriverManager.getConnection(protocol);
            } catch (Exception e) {
                System.out.println("couldn't create DB");
                e.printStackTrace();
            }
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("statement failed");
            e.printStackTrace();
        }
        System.out.println("DB is connected!");

        this.createCategoryTable();
        this.createExpenseTable();
        this.createIncomeTable();
    }
    /*
     * closeConnection method closes the connection safely.
     */
    public void closeConnection() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException se) {
            if ("XJ015".equals(se.getSQLState())) {
                System.out.println("shutdown successful");
            } else {
                se.printStackTrace();
                System.out.println("shutdown error");
            }
        }
        if (statement != null)
            try {
                statement.close();
            } catch (Exception se) {
                se.printStackTrace();
                System.out.println("statement couldn't close");
            }

        if (connection != null)
            try {
                connection.close();
            } catch (Exception se) {
                se.printStackTrace();
                System.out.println("connection couldn't close");
            }

        if (rs != null)
            try {
                rs.close();
            } catch (Exception se) {
                se.printStackTrace();
                System.out.println("rs couldn't close");
            }
    }

    /*
     * createCategoryTable method creates the Category table.
     */
    public void createCategoryTable() {
        try {
            statement.execute("create table Category(" +
                    "id INT NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "name VARCHAR(50) UNIQUE)");
        } catch (SQLException se) {
            if ("X0Y32".equals(se.getSQLState())) {
                System.out.println("Category table exists already");
            } else {
                se.printStackTrace();
                System.out.println("Problem with creating Category table");
            }
        }
    }
    /*
     * createExpenseTable method creates the Expense table.
     */
    public void createExpenseTable() {
        try {
            statement.execute("create table Expense(" +
                    "id INT NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "description VARCHAR(200), " +
                    "\"sum\" FLOAT, " +
                    "date DATE, " +
                    "category VARCHAR(50), " +
                    "FOREIGN KEY (category) REFERENCES Category(name))");
        } catch (SQLException se) {
            if ("X0Y32".equals(se.getSQLState())) {
                System.out.println("Expense table exists already");
            } else {
                se.printStackTrace();
                System.out.println("Problem with creating Expense table");
            }
        }
    }
    /*
     * createIncomeTable method creates the Income table.
     */
    public void createIncomeTable() {
        try {
            statement.execute("create table Income(" +
                    "id INT NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "description VARCHAR(200), " +
                    "\"sum\" FLOAT, " +
                    "date DATE)");
        } catch (SQLException se) {
            if ("X0Y32".equals(se.getSQLState())) {
                System.out.println("Income table exists already");
            } else {
                se.printStackTrace();
                System.out.println("Problem with creating Income table");
            }
        }
    }

    /*
     * The get method executes the query it receives as a parameter
     * and returns the corresponding result set.
     */
    public ResultSet get(String query) {
        try {
            rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException se) {
                se.printStackTrace();
                return null;
        }
    }
    /*
     * The set method executes the query it receives as a parameter
     * and returns an indication if the action was successful or not.
     */
    public boolean set(String query) {
        try {
            statement.execute(query);
            return true;
        } catch(SQLException se) {
                if ("23505".equals(se.getSQLState())) {
                    System.out.println("This category exists already");
                    return true;
                } else {
                    se.printStackTrace();
                    return false;
                }
        }
    }
}