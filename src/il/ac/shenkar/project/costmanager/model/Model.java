/**

 * The class Model is abstract class, including DB interface class and Reports class.
 * Model testator function to classes: Category, Expense and Income.
 * The class Model getting request for action from Commander include in controller.
 * @param db is instance to class DB
 * @param reports is instance to class reports
 */

package il.ac.shenkar.project.costmanager.model;
import java.sql.ResultSet;


public abstract class Model {
    public DB db;
    private Reports reports;
    /*

     * function getAll get all data from database
     * function getByID get data from database by ID specific, approach to particular category or update expenses or delete income.
     * function add, adding to data a new category, new expense or new income
     * function delete, deleted row of data from database with id specific.
     * function update, doing updates on data from database with id specific
     */

    public abstract ResultSet getAll() throws CostManagerException;
    public abstract ResultSet getByID(int id)throws CostManagerException;
    public abstract boolean add(String arr[])throws CostManagerException ;
    public abstract boolean delete(int id)throws CostManagerException;
    public abstract boolean update(int id, String arr[])throws CostManagerException;
}
