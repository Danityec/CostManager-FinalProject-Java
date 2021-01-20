package il.ac.shenkar.project.costmanager.viewmodel;

import il.ac.shenkar.project.costmanager.model.*;
import il.ac.shenkar.project.costmanager.view.IView;
import il.ac.shenkar.project.costmanager.view.View;

import java.sql.ResultSet;
import java.util.Map;

public class ViewModel {
    private IView view;

    private Expense expense;
    private Income income;
    private Category category;
    private Reports reports;

    public void setView(IView v){
        view = v;
    }
    public void setModel(DB db){
        expense = new Expense(db);
        income = new Income(db);
        category = new Category(db);
        reports = new Reports(category, expense, income);
    }
    public void start(){
        view.start();
    }

    public void addCategory(String arr[]){
        try {
            category.add(arr);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }
    public void editCategory(int id, String arr[]){
        try {
            category.update(id, arr);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }
    public void deleteCategory(int id){
        try {
            category.delete(id);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getCategories(){
        try {
            return category.getAll();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getCategory(int id){
        try {
            return category.getByID(id);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addIncome(String arr[]){
        try {
            income.add(arr);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }
    public void editIncome(int id, String arr[]){
        try {
            income.update(id, arr);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }
    public void deleteIncome(int id){
        try {
            income.delete(id);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getIncomeCount(){
        try {
            return income.getCount();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getIncomes(){
        try {
            return income.getAll();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getIncome(int id){
        try {
            return income.getByID(id);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addExpense(String arr[]){
        try {
            expense.add(arr);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }
    public void editExpense(int id, String arr[]){
        try {
            expense.update(id, arr);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }
    public void deleteExpense(int id){
        try {
            expense.delete(id);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getExpenseCount(){
        try {
            return expense.getCount();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getExpenseBetweenDates(String date1, String date2){
        try {
            return expense.getBetweenDates(date1, date2);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getExpenses(){
        try {
            return expense.getAll();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getExpense(int id){
        try {
            return expense.getByID(id);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Double> getReport(String date1, String date2){
        try {
            return reports.report(date1, date2);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        return null;
    }
    public double getBalance(){
        try {
            return reports.balance();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
