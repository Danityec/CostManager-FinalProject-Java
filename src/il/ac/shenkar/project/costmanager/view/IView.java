package il.ac.shenkar.project.costmanager.view;
import il.ac.shenkar.project.costmanager.viewmodel.*;

import java.util.Map;

public interface IView {
    public void setViewModel(ViewModel vm);
    public void start();

    public void homePage();

    public void expensePage();
    public void expenseViewPage(int id);
    public void addExpense();
    public void updateExpense(int id);

    public void incomePage();
    public void incomeViewPage(int id);
    public void addIncome();
    public void updateIncome(int id);

    public void addCategory();

    public void reportsPage();
    public void listReportPage(Map<String, Double> reportMap);
    public void pieChartReportPage(Map<String, Double> reportMap);
}
