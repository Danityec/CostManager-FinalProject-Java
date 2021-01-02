/**
 * Class Category inheritor from Model.
 * Class Category inheritor 5 methods of CRUD
 * The goal of this class do CRUD on Category of database
 *
 */

package il.ac.shenkar.project.costmanager.model;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Category extends Model {
    /*
     * initialization db in method Category
     */
    public Category(DB db) {
        super();
        this.db = db;
    }

    /*
     * function getAll get all data from table Category
     */
    public ResultSet getAll() throws CostManagerException
    {
            String sql = "SELECT * FROM Category";
            ResultSet rs = db.get(sql);
            return rs;
    };

    /*
     * function getByID get data from table Category by id specific
     */
    public ResultSet getByID(int id) throws CostManagerException
    {
        String sql = "SELECT * FROM Category WHERE id = " + id;
        ResultSet rs = db.get(sql);
        return rs;
    };

    /*
     * function add, adding to table Category a new Category
     */
    public boolean add(String arr[])throws CostManagerException
    {
        String sql = "INSERT INTO Category (name) " + "VALUES ('" + arr[0] + "')";
        if (db.set(sql)) {
            System.out.println("The new category was added to table");
            return true;
        } else {

            return false;
        }
    };
    /*
     * function delete, deleted row of data from table Category with id specific.
     */
    public boolean delete(int id) throws CostManagerException
    {
        String sql = "DELETE FROM Category WHERE id=" + id  ;
        if (db.set(sql)) {
            System.out.println("The category has been delete");
            return true;
        }
        else return false;
    };
    /*
     * function update, doing updates on data from table Category with id specific
     */
    public boolean update(int id, String arr[]) throws CostManagerException
    {
        String sql = "UPDATE Category SET name = '" + arr[0] + "' WHERE id = " + id ;
        if (db.set(sql)) {
            System.out.println("The category information was updated in table");
            return true;
        }
        else return false;
    };
}
