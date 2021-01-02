/**
 * Class Expense inheritor from Model.
 * Class Expense inheritor 5 methods of CRUD
 * The goal of this class do CRUD on Expense of database
 *
 */
package il.ac.shenkar.project.costmanager.model;
import java.sql.ResultSet;
import java.util.*;

public class Expense extends Model {
    /*
     * initialization db in method Expense.
     */
    public Expense(DB db) {
        super();
        this.db = db;
    }
    /*
     * function getAll get all data from table Expense.
     */
    public ResultSet getAll(){
        String sql = "SELECT * FROM Expense";
        ResultSet rs = db.get(sql);
        return rs;
    };
    /*
     * function getByID get data from table Expense by id specific.
     */
    public ResultSet getByID(int id){
        String sql = "SELECT * FROM Expense WHERE id = " + id;
        ResultSet rs = db.get(sql);
        return rs;
    };
    /*
     * function add, adding to table Expense a new expense.
     */
    public boolean add(String arr[]){
        String sql = "INSERT INTO Expense (description, \"sum\", date, category) VALUES ('" + arr[0] + "', " + Double.parseDouble(arr[1]) + ", '" + arr[2] + "', '" + arr[3] + "')" ;
        if (db.set(sql)) {
            System.out.println("The expense information has been added to table");
            return true;
        }
        else return false;
    };
    /*
     * function delete, deleted row of data from table Expense with id specific.
     */
    public boolean delete(int id){
        String sql = "DELETE FROM Expense WHERE id=" + id  ;
        if (db.set(sql)) {
            System.out.println("The expense has been deleted");
            return true;
        }
        else return false;
    };
    /*
     * function update, doing updates on data from table Expense with id specific.
     */
    public boolean update(int id, String arr[]){
        String sql = "UPDATE Expense SET description = '" + arr[0] + "', \"sum\" = "+ Double.parseDouble(arr[1]) + ", date = '" + arr[2] + "', category = '" + arr[3] +"' WHERE id= "+id ;

        if (db.set(sql)) {
            System.out.println("The category information was updated in table");
            return true;
        }
        else return false;
    };
    /*
     * function getBetweenDates, get data from table Expense between 2 dates.
     */
    public ResultSet getBetweenDates(String date1, String date2){
        String sql = "SELECT * FROM Expense WHERE date BETWEEN '" + date1 + "' AND '" + date2 + "'";
        ResultSet rs = db.get(sql);
        return rs;
    };

    public ResultSet getCount(){
        String sql = "SELECT COUNT(*) FROM Expense as count";
        ResultSet rs = db.get(sql);
        return rs;
    };
}
