package il.ac.shenkar.project.costmanager.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class ModelUnitTest {
    public static void main(String[] args) {
        DerbyDB dataBase = new DerbyDB();
        ResultSet rs = null;

        Expense expense = new Expense(dataBase);
        Income income = new Income(dataBase);
        Category category = new Category(dataBase);

        Reports reports = new Reports(category, expense, income);

//        String[] cat = new String[1];
//        cat[0] = "Shopping";                    // name
//        category.add(cat);
//
//        cat[0] = "Biluy";                       // name
//        category.add(cat);
//
//        cat[0] = "Gas";                         // name
//        category.add(cat);
//
//        cat[0] = "Clothes Shopping";
//        category.update(1, cat);
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

//        String[] inc = new String[3];
//        inc[0] = "Salary from work";       // description
//        inc[1] = "8500.5";                       // sum
//        inc[2] = "2020-12-01";                  // date
//        income.add(inc);
//
//        inc[0] = "Salary from hobby";       // description
//        inc[1] = "500.5";                       // sum
//        inc[2] = "2020-12-12";                  // date
//        income.add(inc);
//
//        inc[0] = "Salary from WORK";       // description
//        inc[1] = "50000.5";                       // sum
//        inc[2] = "2020-12-12";                  // date
//        income.update(1, inc);
////        income.delete(2);
//
//
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
//
//        String[] exp = new String[4];
//        exp[0] = "Shopping in Shufersal";       // description
//        exp[1] = "308.2";                       // sum
//        exp[2] = "2020-12-20";                  // date
//        exp[3] = "Gas";                           // category id
//        expense.add(exp);
//
//        exp[0] = "Shopping in ZARA";       // description
//        exp[1] = "808.2";                       // sum
//        exp[2] = "2020-12-20";                  // date
//        exp[3] = "Clothes Shopping";                           // category id
//        expense.add(exp);
//
//        exp[0] = "Shopping in HM";       // description
//        exp[1] = "80.2";                       // sum
//        exp[2] = "2020-12-19";                  // date
//        exp[3] = "Biluy";                           // category id
//        expense.add(exp);
//
//        exp[0] = "Shopping in ZARA HOME";       // description
//        exp[1] = "8008.2";                       // sum
//        exp[2] = "2020-12-20";                  // date
//        exp[3] = "Biluy";                           // category id
//        expense.update(1, exp);
////        expense.delete(7);
//
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



        rs = category.getAll();
        System.out.println("Category table: ");
        try {
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println("id: " + id + "; name: " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        rs = income.getAll();
        System.out.println("Income table: ");
        try {
            while(rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                double sum = rs.getDouble("sum");
                Date date = rs.getDate("date");
                System.out.println("id: " + id + "; description: " + description +
                        "; sum: " + sum + "; date: " + date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        rs = expense.getAll();
        System.out.println("Expense table: ");
        try {
            while(rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                double sum = rs.getDouble("sum");
                Date date = rs.getDate("date");
                String catName = rs.getString("category");
                System.out.println("id: " + id + "; description: " + description +
                        "; sum: " + sum + "; date: " + date + "; category: " + catName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        double balance = reports.balance();
        System.out.println("balance: " +balance);

        String date1 = "2020-12-01";
        String date2 = "2020-12-20";

        Map<String, Double> report = new HashMap<String, Double>();
        report = reports.report(date1, date2);

        System.out.println(report.toString());


        dataBase.closeConnection();
    }
}
