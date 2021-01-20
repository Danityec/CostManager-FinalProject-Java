package il.ac.shenkar.project.costmanager.viewmodel;

import il.ac.shenkar.project.costmanager.model.DB;
import il.ac.shenkar.project.costmanager.model.Model;
import il.ac.shenkar.project.costmanager.view.IView;

import java.sql.ResultSet;
import java.util.Map;

public interface IViewModel {
    public void setView(IView v);
    public void setModel(DB db);
    public void start();

    public void addCategory(String arr[]);
    public void editCategory(int id, String arr[]);
    public void deleteCategory(int id);
    public ResultSet getCategories();
    public ResultSet getCategory(int id);

    public void addIncome(String arr[]);
    public void editIncome(int id, String arr[]);
    public void deleteIncome(int id);
    public ResultSet getIncomeCount();
    public ResultSet getIncomes();
    public ResultSet getIncome(int id);

    public void addExpense(String arr[]);
    public void editExpense(int id, String arr[]);
    public void deleteExpense(int id);
    public ResultSet getExpenseCount();
    public ResultSet getExpenseBetweenDates(String date1, String date2);
    public ResultSet getExpenses();
    public ResultSet getExpense(int id);

    public Map<String, Double> getReport(String date1, String date2);
    public double getBalance();
}
