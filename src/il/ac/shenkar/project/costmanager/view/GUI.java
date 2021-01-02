package il.ac.shenkar.project.costmanager.view;

import il.ac.shenkar.project.costmanager.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GUI implements IView {
    private JFrame frame;
    private JButton expenseBtn, incomeBtn, reportBtn, homeBtn;
    private JLabel title;
    private JPanel panelBottom, panelTop, contentPanel;

    private DerbyDB dataBase;
    private ResultSet rs;
    private Expense expense;
    private Income income;
    private Category category;
    private Reports reports;


    public GUI() {
        dataBase = new DerbyDB();
        rs = null;
        expense = new Expense(dataBase);
        income = new Income(dataBase);
        category = new Category(dataBase);
        reports = new Reports(category, expense, income);


        frame = new JFrame("Cost Manager");

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                frame.setVisible(false);
                frame.dispose();
                System.exit(0);
            }
        });

        expenseBtn = new JButton("Expenses");
        incomeBtn = new JButton("Incomes");
        reportBtn = new JButton("Reports");
        homeBtn = new JButton("Home");
        panelBottom = new JPanel();
        panelTop = new JPanel();
        contentPanel = new JPanel();
        title = new JLabel();

        Container container = frame.getContentPane();

        panelBottom.setBackground(Color.gray);
        panelTop.setBackground(Color.lightGray);
        contentPanel.setBackground(Color.lightGray);

        panelBottom.setLayout(new BorderLayout());
        panelTop.setLayout(new FlowLayout());
        contentPanel.setLayout(new BorderLayout());
        container.setLayout(new BorderLayout());

        panelTop.add(homeBtn);
        panelTop.add(expenseBtn);
        panelTop.add(incomeBtn);
        panelTop.add(reportBtn);
        panelBottom.add("North", title);
        panelBottom.add(contentPanel);
        container.add("North",panelTop);
        container.add("Center",panelBottom);

        ActionListener homeAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                homePage();
            }
        };
        homeBtn.addActionListener(homeAction);


        ActionListener expenseAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                expensePage();
            }
        };
        expenseBtn.addActionListener(expenseAction);

        ActionListener incomeAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                incomePage();
            }
        };
        incomeBtn.addActionListener(incomeAction);

        ActionListener reportAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                reportsPage();
            }
        };
        reportBtn.addActionListener(reportAction);

        homePage();
    }

    public void homePage() {
        title.setText("Cost Manager");
        contentPanel.removeAll();

        JLabel balance = new JLabel();
        contentPanel.add(balance);

        double balanceValue = reports.balance();
        String sign = "+";
        if (balanceValue < 0)
            sign = "-";
        balance.setText("Balance: " + balanceValue + "$" + sign + "\n");
    }

    public void expensePage() {
        title.setText("Expenses");
        contentPanel.removeAll();

        int count = 0;
        rs = expense.getCount();
        try {
            while (rs.next())
                count = rs.getInt(1);
        } catch (Exception e){
        }

        String[][] table = new String[count+1][];
        String[] columnNames = { "NO.", "DESCRIPTION", "CATEGORY", "DATE", "SUM" };
        table[0] = columnNames;
        int index = 1;
        rs = expense.getAll();
        try {
            while(rs.next()) {
                String[] row = new String[5];
                int id = rs.getInt("id");
                row[0] = (String.valueOf(id));

                String description = rs.getString("description");
                row[1] = (description);

                String catName = rs.getString("category");
                row[2] = (catName);

                Date date = rs.getDate("date");
                row[3] = (String.valueOf(date));

                double sum = rs.getDouble("sum");
                row[4] = (String.valueOf(sum));

                table[index] = row;
                index += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JTable expenseList = new JTable(table, columnNames);
        expenseList.setBounds(30, 40, 200, 300);
        contentPanel.add(expenseList);
        expenseList.setBackground(Color.lightGray);

        JButton addExpenseBtn = new JButton("Add New Expense");
        contentPanel.add("South", addExpenseBtn);
    }

    public void incomePage() {
        title.setText("Incomes");
        contentPanel.removeAll();

        int count = 0;
        rs = income.getCount();
        try {
            while (rs.next())
                count = rs.getInt(1);
        } catch (Exception e){
        }

        String[][] table = new String[count+1][];
        String[] columnNames = { "NO.", "DESCRIPTION", "DATE", "SUM" };
        table[0] = columnNames;
        int index = 1;
        rs = income.getAll();
        try {
            while(rs.next()) {
                String[] row = new String[4];
                int id = rs.getInt("id");
                row[0] = (String.valueOf(id));

                String description = rs.getString("description");
                row[1] = (description);

                Date date = rs.getDate("date");
                row[2] = (String.valueOf(date));

                double sum = rs.getDouble("sum");
                row[3] = (String.valueOf(sum));

                table[index] = row;
                index += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JTable incomeList = new JTable(table, columnNames);
        incomeList.setBounds(30, 40, 200, 300);
        contentPanel.add(incomeList);
        incomeList.setBackground(Color.lightGray);

        JButton addIncomeBtn = new JButton("Add New Income");
        contentPanel.add("South", addIncomeBtn);
    }

    public void reportsPage() {
        title.setText("Reports");
        contentPanel.removeAll();


        JPanel formArea = new JPanel();
        formArea.setLayout(new GridLayout(3,1,5,10));
        JPanel firstDateArea = new JPanel();
        firstDateArea.setLayout(new GridLayout(2,1,5,5));
        JPanel secondDateArea = new JPanel();
        secondDateArea.setLayout(new GridLayout(2,1,5,5));
        JPanel buttonsArea = new JPanel();

        JLabel firstDateLbl = new JLabel("First Date");
        JTextField firstDate = new JTextField();
        firstDateArea.add(firstDateLbl);
        firstDateArea.add(firstDate);
        JLabel secondDateLbl = new JLabel("Second Date");
        JTextField secondDate = new JTextField();
        secondDateArea.add(secondDateLbl);
        secondDateArea.add(secondDate);

        JButton listReport = new JButton("List Report");
        JButton pieChartReport = new JButton("Pie Chart Report");
        buttonsArea.add(listReport);
        buttonsArea.add(pieChartReport);

        formArea.setBackground(Color.lightGray);
        formArea.add(firstDateArea);
        formArea.add(secondDateArea);
        formArea.add(buttonsArea);
        contentPanel.add("Center", formArea);

    }

    public void start() {
        frame.setSize(800,700);
        frame.setVisible(true);
    }
}
