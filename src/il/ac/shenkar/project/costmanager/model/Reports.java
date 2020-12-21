/**
 * The Reports class is used to analyze the data in the DB.
 * @param category is an object of the Category table in the DB
 * @param expense is an object of the Expense table in the DB
 * @param income is an object of the Income table in the DB
 */

package il.ac.shenkar.project.costmanager.model;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Reports {
    private Category category;
    private Expense expense;
    private Income income;

    /*
     * The constructor initializes the private table objects.
     */
    public Reports(Category category, Expense expense, Income income) {
        this.category = category;
        this.expense = expense;
        this.income = income;
    }

    /*
     * The report method gets the data about expenses between two dates the method receives.
     * The method returns a map with keys that are the categories of the expenses and
     * the values are the sum of the expense in this category.
     * This method will be used for both "list report" and for "pie chart report"
     */
    public Map<String, Double> report(String date1, String date2) {
        Map<String, Double> report = new HashMap<String, Double>();

        ResultSet rs = expense.getBetweenDates(date1, date2);
        try {
            while(rs.next()){
                double expenseSum = rs.getDouble("sum");
                String categoryName = rs.getString("category");

                if (report.get(categoryName) != null) {
                    expenseSum += report.get(categoryName);
                }
                expenseSum = Double.parseDouble(new DecimalFormat("#.##").format(expenseSum));
                report.put(categoryName, expenseSum);
            }
        } catch (Exception e) {
            System.out.println("error with rs - expense");
            e.printStackTrace();
        }
        return report;
    }

    /*
     * The balance method gets the data about expenses and incomes.
     * The method returns the value of the sum of all incomes, minus the sum of all expenses.
     */
    public double balance() {
        ResultSet rs = expense.getAll();
        double expenseSum = 0;

        try {
            while(rs.next()){
                expenseSum += rs.getDouble("sum");
            }
        } catch (Exception e) {
            System.out.println("error with expenseRS");
        }

        rs = income.getAll();
        double incomeSum = 0;

        try {
            while(rs.next()){
                incomeSum += rs.getDouble("sum");
            }
        } catch (Exception e) {
            System.out.println("error with incomeRS");
        }
        double balance = (incomeSum - expenseSum);
        balance = Double.parseDouble(new DecimalFormat("#.##").format(balance));

        return balance;
    }
}

