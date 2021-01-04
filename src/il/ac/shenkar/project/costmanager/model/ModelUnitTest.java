package il.ac.shenkar.project.costmanager.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class ModelUnitTest {
    public static void main(String[] args) {
        DerbyDB dataBase=null;
        try {
            dataBase = new DerbyDB();
        } catch (Exception e) {
//            System.out.println(e);
            System.exit(2);
        }

        ResultSet rs = null;

        Expense expense = new Expense(dataBase);
        Income income = new Income(dataBase);
        Category category = new Category(dataBase);

        Reports reports = new Reports(category, expense, income);

        String[] cat = new String[1];
        cat[0] = "Shopping";                    // name
        try {
            category.add(cat);
        } catch (Exception e) {
            System.out.println("modelUnitTest: "+e);
        }

        cat[0] = "Biluy";                       // name
        try {
            category.add(cat);
        } catch (Exception e) {
            System.out.println("modelUnitTest: "+e);
        }

        cat[0] = "Gas";                         // name
        try {
            category.add(cat);
        } catch (Exception e) {
            System.out.println("modelUnitTest: "+e);
        }
        cat[0] = "Clothes Shopping";
        try {
            category.update(1, cat);
        } catch (Exception e) {
            System.out.println("modelUnitTest: "+e);
        }


//        category.delete(2);

//        rs = category.getAll();
//        try {
//            while(rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                System.out.println("id: " + id + "; name: " + name);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String d = "2020-12-01";
        String[] inc = new String[3];
        inc[0] = "Salary from work";       // description
        inc[1] = "33500.5";                       // sum
        inc[2] = d;                  // date
        try {
            income.add(inc);
        } catch (Exception e) {
            System.out.println("modelUnitTest: "+e);
        }


        inc[0] = "Salary from hobby";       // description
        inc[1] = "500.5";                       // sum
        inc[2] = "2020-12-12";                  // date
        try {
            income.add(inc);
        } catch (Exception e) {
            System.out.println("modelUnitTest: "+e);
        }

        inc[0] = "Salary from WORK";       // description
        inc[1] = "50.5";                       // sum
        inc[2] = "2020-12-12";                  // date
        try {
            income.update(1, inc);
        } catch (Exception e) {
            System.out.println("modelUnitTest: "+e);
        }

//        income.delete(2);


//        rs = income.getAll();
//        try {
//            while(rs.next()) {
//                int id = rs.getInt("id");
//                String description = rs.getString("description");
//                double sum = rs.getDouble("sum");
//                Date date = rs.getDate("date");
//                System.out.println("id: " + id + "; description: " + description +
//                        "; sum: " + sum + "; date: " + date);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//

        String[] exp = new String[4];
        exp[0] = "Shopping in Shufersal";       // description
        exp[1] = "308.2";                       // sum
        exp[2] = "2020-12-20";                  // date
        exp[3] = "Gas";                           // category id

        try {
            expense.add(exp);
        } catch (Exception e) {
            System.out.println("modelUnitTest: "+e);
        }

        exp[0] = "Shopping in ZARA";       // description
        exp[1] = "808.2";                       // sum
        exp[2] = "2020-12-20";                  // date
        exp[3] = "Clothes Shopping";                           // category id
        try {
            expense.add(exp);
        } catch (Exception e) {
            System.out.println("modelUnitTest: "+e);
        }

        exp[0] = "Shopping in HM";       // description
        exp[1] = "80.2";                       // sum
        exp[2] = "2020-12-19";                  // date
        exp[3] = "Biluy";                           // category id
        try {
            expense.add(exp);
        } catch (Exception e) {
            System.out.println("modelUnitTest: "+e);
        }

        exp[0] = "Shopping in ZARA HOME";       // description
        exp[1] = "8008.2";                       // sum
        exp[2] = "2020-12-20";                  // date
        exp[3] = "Biluy";                           // category id
        try {
            expense.update(1, exp);
        } catch (Exception e) {
            System.out.println("modelUnitTest: "+e);
        }

//        expense.delete(7);

//        rs = expense.getAll();
//        try {
//            while(rs.next()) {
//                int id = rs.getInt("id");
//                String description = rs.getString("description");
//                double sum = rs.getDouble("sum");
//                Date date = rs.getDate("date");
//                String catName = rs.getString("category");
//                System.out.println("id: " + id + "; description: " + description +
//                        "; sum: " + sum + "; date: " + date + "; category: " + catName);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



//        rs = category.getAll();
//        System.out.println("Category table: ");
//        try {
//            while(rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                System.out.println("id: " + id + "; name: " + name);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        rs = income.getAll();
//        System.out.println("Income table: ");
//        try {
//            while(rs.next()) {
//                int id = rs.getInt("id");
//                String description = rs.getString("description");
//                double sum = rs.getDouble("sum");
//                Date date = rs.getDate("date");
//                System.out.println("id: " + id + "; description: " + description +
//                        "; sum: " + sum + "; date: " + date);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        rs = expense.getAll();
//        System.out.println("Expense table: ");
//        try {
//            while(rs.next()) {
//                int id = rs.getInt("id");
//                String description = rs.getString("description");
//                double sum = rs.getDouble("sum");
//                Date date = rs.getDate("date");
//                String catName = rs.getString("category");
//                System.out.println("id: " + id + "; description: " + description +
//                        "; sum: " + sum + "; date: " + date + "; category: " + catName);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        double balance = reports.balance();
//        System.out.println("balance: " +balance);
//
//        String date1 = "2020-12-01";
//        String date2 = "2020-12-20";
//
//        Map<String, Double> report = new HashMap<String, Double>();
//        report = reports.report(date1, date2);
//
//        System.out.println(report.toString());

        try {
            dataBase.closeConnection();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
