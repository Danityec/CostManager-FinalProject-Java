/**
 * Class Income inheritor from Model.
 * Class Income inheritor 5 methods of CRUD
 * The goal of this class do CRUD on Income of database
 *
 */
package il.ac.shenkar.project.costmanager.model;
import java.sql.ResultSet;

public class Income extends Model {
    /*
     * initialization db in method Income
     */
    public Income(DB db) {
        super();
        this.db = db;
    }
    /*
     * function getAll get all data from table Income
     */
    public ResultSet getAll() {
        String sql = "SELECT * FROM Income";
        ResultSet rs = db.get(sql);
        return rs;
    };
    /*
     * function getByID get data from table Income by id specific
     */
    public ResultSet getByID(int id) {
        String sql = "SELECT * FROM Income WHERE id = " + id;
        ResultSet rs = db.get(sql);
        return rs;
    };
    /*
     * function add, adding to table Income a new income
     */
    public boolean add(String arr[]) {
        String sql = "INSERT INTO Income (description, \"sum\", date) VALUES ('" + arr[0] + "', " + Double.parseDouble(arr[1]) + ", '" + arr[2] + "')";
        if (db.set(sql)) {
            System.out.println("The income information has add to table");
            return true;
        }
        else return false;
    };
    /*
     * function delete, deleted row of data from table Income with id specific.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM Income WHERE id = " + id  ;
        if (db.set(sql)) {
            System.out.println("The income has been delete");
            return true;
        }
        else return false;
    };
    /*
     * function update, doing updates on data from table Income with id specific
     */
    public boolean update(int id, String arr[]) {
        String sql = "UPDATE Income SET description = '" + arr[0] + "', \"sum\" = "+ Double.parseDouble(arr[1]) + ", date = '"+ arr[2] +"' WHERE id = " + id ;
        if (db.set(sql)) {
            System.out.println("The category information has update in table");
            return true;
        }
        else return false;
    };

    public ResultSet getCount(){
        String sql = "SELECT COUNT(*) FROM Income as count";
        ResultSet rs = db.get(sql);
        return rs;
    };
}
