package il.ac.shenkar.project.costmanager.view;

import il.ac.shenkar.project.costmanager.model.*;
import org.apache.derby.client.am.stmtcache.JDBCStatementCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

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
        try {
            dataBase = new DerbyDB();
        } catch (Exception e) {}
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

    public void start() {
        frame.setVisible(true);
    }

    public void homePage() {
        title.setText("Cost Manager");
        contentPanel.removeAll();
        frame.setSize(500, 150);

        JLabel balance = new JLabel();
        contentPanel.add("North", balance);
        double balanceValue = 0;
        try {
            balanceValue = reports.balance();
        } catch (Exception e) {}

        String sign = "+";
        if (balanceValue < 0)
            sign = "-";
        balance.setText("Balance: " + balanceValue + "$ " + sign + "\n");
    }

    public void expensePage() {
        title.setText("Expenses");
        contentPanel.removeAll();

        int count = 0;
        try {
            rs = expense.getCount();
        } catch (Exception e) {}
        try {
            while (rs.next())
                count = rs.getInt(1);
        } catch (Exception e){
        }
        int height = 130 + count*16;
        frame.setSize(600, height);
        JPanel buttonSection = new JPanel();
        buttonSection.setBounds(30, 40, 200, 300);
        buttonSection.setLayout(new GridLayout(count+1,1,1,1));

        String[][] table = new String[count+1][];
        String[] columnNames = { "NO.", "DESCRIPTION", "CATEGORY", "DATE", "SUM" };
        table[0] = columnNames;
        int index = 1;
        JPanel btnPlaceholder = new JPanel();
        btnPlaceholder.setBackground(Color.lightGray);
        buttonSection.add(btnPlaceholder);
        try {
            rs = expense.getAll();
        } catch (Exception e) {}
        try {
            while(rs.next()) {
                String[] row = new String[5];
                row[0] = (String.valueOf(index));

                String description = rs.getString("description");
                row[1] = (description);

                String catName = rs.getString("category");
                row[2] = (catName);

                Date date = rs.getDate("date");
                row[3] = (String.valueOf(date));

                double sum = rs.getDouble("sum");
                row[4] = (String.valueOf(sum));

                int id = rs.getInt("id");
                JButton viewBtn = new JButton("View");
                buttonSection.add(viewBtn);
                ActionListener viewAction = new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        expenseViewPage(id);
                    }
                };
                viewBtn.addActionListener(viewAction);

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
        contentPanel.add("East", buttonSection);

        JButton addExpenseBtn = new JButton("Add New Expense");
        contentPanel.add("South", addExpenseBtn);

        ActionListener addAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    addExpense();
                } catch (Exception e) {}
            }
        };
        addExpenseBtn.addActionListener(addAction);
    }

    public void incomePage() {
        title.setText("Incomes");
        contentPanel.removeAll();

        int count = 0;
        try {
            rs = income.getCount();
        } catch (Exception e) {}

        try {
            while (rs.next())
                count = rs.getInt(1);
        } catch (Exception e){
        }
        int height = 130 + count*16;
        frame.setSize(500, height);
        JPanel buttonSection = new JPanel();
        buttonSection.setBounds(30, 40, 200, 300);
        buttonSection.setLayout(new GridLayout(count+1,1,1,1));

        String[][] table = new String[count+1][];
        String[] columnNames = { "NO.", "DESCRIPTION", "DATE", "SUM" };
        table[0] = columnNames;

        JPanel btnPlaceholder = new JPanel();
        btnPlaceholder.setBackground(Color.lightGray);
        buttonSection.add(btnPlaceholder);

        int index = 1;
        try {
            rs = income.getAll();
        } catch (Exception e) {}
        try {
            while(rs.next()) {
                String[] row = new String[4];
                row[0] = (String.valueOf(index));

                String description = rs.getString("description");
                row[1] = (description);

                Date date = rs.getDate("date");
                row[2] = (String.valueOf(date));

                double sum = rs.getDouble("sum");
                row[3] = (String.valueOf(sum));

                int id = rs.getInt("id");
                JButton viewBtn = new JButton("View");
                buttonSection.add(viewBtn);
                ActionListener viewAction = new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        incomeViewPage(id);
                    }
                };
                viewBtn.addActionListener(viewAction);


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
        contentPanel.add("East", buttonSection);
        JButton addIncomeBtn = new JButton("Add New Income");
        contentPanel.add("South", addIncomeBtn);

        ActionListener addAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    addIncome();
                } catch (Exception e) {}
            }
        };
        addIncomeBtn.addActionListener(addAction);
    }

    public void expenseViewPage(int id){
        contentPanel.removeAll();
        frame.setSize(600, 180);

        JLabel nameLbl = new JLabel();
        JLabel categoryLbl = new JLabel();
        JLabel dateLbl = new JLabel();
        JLabel sumLbl = new JLabel();

        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new GridLayout(4, 1, 0, 0));

        itemPanel.add(nameLbl);
        itemPanel.add(categoryLbl);
        itemPanel.add(dateLbl);
        itemPanel.add( sumLbl);

        JButton editItem = new JButton("Edit");
        JButton deleteItem = new JButton("Delete");

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(2,1,0,0));

        btnPanel.add(editItem);
        btnPanel.add(deleteItem);

        JPanel infoSection = new JPanel();
        itemPanel.setLayout(new GridLayout(4,2,0,0));

        infoSection.add(itemPanel);
        infoSection.add(btnPanel);

        contentPanel.add(infoSection);

        try {
            rs = expense.getByID(id);
        } catch (Exception e) {}
        try {
            while(rs.next()) {
                String description = rs.getString("description");
                title.setText(description);
                nameLbl.setText(description);

                String catName = rs.getString("category");
                categoryLbl.setText(catName);

                Date date = rs.getDate("date");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = dateFormat.format(date);
                dateLbl.setText(strDate);

                double sum = rs.getDouble("sum");
                String strSum=Double.toString(sum);
                sumLbl.setText(strSum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ActionListener editAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                updateExpense(id);
            }
        };
        editItem.addActionListener(editAction);

        ActionListener deleteAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    expense.delete(id);
                } catch (Exception e) {}
                expensePage();
            }
        };
        deleteItem.addActionListener(deleteAction);
    }

    public void incomeViewPage(int id){
        contentPanel.removeAll();
        frame.setSize(500, 160);

        JLabel nameLbl = new JLabel();
        JLabel dateLbl = new JLabel();
        JLabel sumLbl = new JLabel();

        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new GridLayout(3, 1, 0, 0));

        itemPanel.add(nameLbl);
        itemPanel.add(dateLbl);
        itemPanel.add( sumLbl);

        JButton editItem = new JButton("Edit");
        JButton deleteItem = new JButton("Delete");

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(2,1,0,0));

        btnPanel.add(editItem);
        btnPanel.add(deleteItem);

        JPanel infoSection = new JPanel();
        itemPanel.setLayout(new GridLayout(3,2,0,0));

        infoSection.add(itemPanel);
        infoSection.add(btnPanel);

        contentPanel.add(infoSection);

        try {
            rs = income.getByID(id);
        } catch (Exception e) {}
        try {
            while(rs.next()) {
                String description = rs.getString("description");
                title.setText(description);
                nameLbl.setText(description);

                Date date = rs.getDate("date");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = dateFormat.format(date);
                dateLbl.setText(strDate);

                double sum = rs.getDouble("sum");
                String strSum=Double.toString(sum);
                sumLbl.setText(strSum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ActionListener editAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                updateIncome(id);
            }
        };
        editItem.addActionListener(editAction);

        ActionListener deleteAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    income.delete(id);
                } catch (Exception e) {}
                incomePage();
            }
        };
        deleteItem.addActionListener(deleteAction);
    }

    public void addExpense() {
        title.setText("New Expense");
//        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));

        contentPanel.removeAll();
        frame.setSize(500, 350);

        JPanel formArea = new JPanel();
        formArea.setLayout(new GridLayout(9,1,0,0));

        JLabel descriptionLbl = new JLabel("Description");
        JTextField descriptionTxt = new JTextField();

        ArrayList<String> categorySelectOptions = new ArrayList<String>();
        try {
            rs = category.getAll();
        } catch (Exception e) {}
        try {
            while (rs.next()) {
                categorySelectOptions.add(rs.getString("name"));
            }
        } catch (Exception e) {}

        String[] categorySelections = categorySelectOptions.toArray(new String[categorySelectOptions.size()]);

        JList categoryList = new JList(categorySelections);

        JLabel categoryLbl = new JLabel("Category");
        JPanel categorySection = new JPanel();
        categorySection.setLayout(new GridLayout(1, 2, 0, 0));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(categoryList);
        categorySection.add(scrollPane);
        JButton newCategoryBtn = new JButton("Add new category");
        categorySection.add(newCategoryBtn);

        JLabel dateLbl = new JLabel("Date (YYYY-MM-DD)");
        JTextField dateTxt = new JTextField();

        JLabel sumLbl = new JLabel("Cost");
        JTextField sumTxt = new JTextField();

        JButton submitBtn = new JButton("Add Expense");

        formArea.add(descriptionLbl);
        formArea.add(descriptionTxt);
        formArea.add(categoryLbl);
        formArea.add(categorySection);
        formArea.add(dateLbl);
        formArea.add(dateTxt);
        formArea.add(sumLbl);
        formArea.add(sumTxt);
        formArea.add(submitBtn);

        contentPanel.add("Center", formArea);

        ActionListener addCategoryAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                addCategory();
            }
        };
        newCategoryBtn.addActionListener(addCategoryAction);

        ActionListener addAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String description = descriptionTxt.getText();
                String sum = sumTxt.getText();
                String date = dateTxt.getText();
                String category = (String) categoryList.getSelectedValue();
                String[] newInfo = {description, sum, date, category};
                try {
                    expense.add(newInfo);
                } catch (Exception e) {}

                expensePage();
            }
        };
        submitBtn.addActionListener(addAction);
    }

    public void addIncome() {
        title.setText("New income");
        contentPanel.removeAll();
        frame.setSize(500, 300);

        JPanel formArea = new JPanel();
        formArea.setLayout(new GridLayout(7,1,0,0));

        JLabel descriptionLbl = new JLabel("Description");
        JTextField descriptionTxt = new JTextField();

        JLabel dateLbl = new JLabel("Date (YYYY-MM-DD)");
        JTextField dateTxt = new JTextField();

        JLabel sumLbl = new JLabel("Cost");
        JTextField sumTxt = new JTextField();

        JButton submitBtn = new JButton("Add Income");

        formArea.add(descriptionLbl);
        formArea.add(descriptionTxt);
        formArea.add(dateLbl);
        formArea.add(dateTxt);
        formArea.add(sumLbl);
        formArea.add(sumTxt);
        formArea.add(submitBtn);

        contentPanel.add("Center", formArea);

        ActionListener addAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String description = descriptionTxt.getText();
                String sum = sumTxt.getText();
                String date = dateTxt.getText();
                String[] newInfo = {description, sum, date};
                try {
                    income.add(newInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }

                incomePage();
            }
        };
        submitBtn.addActionListener(addAction);
    }

    public void addCategory() {
        title.setText("New Category");
        contentPanel.removeAll();
        frame.setSize(500, 200);

        JPanel formArea = new JPanel();
        formArea.setLayout(new GridLayout(3,1,0,0));

        JLabel nameLbl = new JLabel("Name");
        JTextField nameTxt = new JTextField();

        JButton submitBtn = new JButton("Add Category");

        formArea.add(nameLbl);
        formArea.add(nameTxt);
        formArea.add(submitBtn);

        contentPanel.add("Center", formArea);

        ActionListener addAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String name = nameTxt.getText();
                String[] newInfo = {name};
                try {
                    category.add(newInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
                addExpense();
            }
        };
        submitBtn.addActionListener(addAction);
    }

    public void updateExpense(int id) {
        title.setText("Update Expense");
        contentPanel.removeAll();
        frame.setSize(500, 350);

        String description = "";
        String categoryStr = "";
        String strDate = "";
        String strSum = "";

        try {
            rs = expense.getByID(id);
        } catch (Exception e) {}
        try {
            while(rs.next()) {
                description = rs.getString("description");

                categoryStr = rs.getString("category");

                Date date = rs.getDate("date");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                strDate = dateFormat.format(date);

                double sum = rs.getDouble("sum");
                strSum=Double.toString(sum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel formArea = new JPanel();
        formArea.setLayout(new GridLayout(9,1,0,0));

        JLabel descriptionLbl = new JLabel("Description");
        JTextField descriptionTxt = new JTextField(description);

        ArrayList<String> categorySelectOptions = new ArrayList<String>();
        try {
            rs = category.getAll();
        } catch (Exception e) {}
        try {
            while (rs.next()) {
                categorySelectOptions.add(rs.getString("name"));
            }
        } catch (Exception e) {}

        String[] categorySelections = categorySelectOptions.toArray(new String[categorySelectOptions.size()]);
        JList categoryList = new JList(categorySelections);
        int index = categorySelectOptions.indexOf(categoryStr);
        categoryList.setSelectedIndex(index);

        JLabel categoryLbl = new JLabel("Category");
//        JTextField categoryTxt = new JTextField(category);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(categoryList);

        JLabel dateLbl = new JLabel("Date (YYYY-MM-DD)");
        JTextField dateTxt = new JTextField(strDate);

        JLabel sumLbl = new JLabel("Cost");
        JTextField sumTxt = new JTextField(strSum);

        JButton submitBtn = new JButton("Update Expense");

        formArea.add(descriptionLbl);
        formArea.add(descriptionTxt);
        formArea.add(categoryLbl);
        formArea.add(scrollPane);
        formArea.add(dateLbl);
        formArea.add(dateTxt);
        formArea.add(sumLbl);
        formArea.add(sumTxt);
        formArea.add(submitBtn);

        contentPanel.add("Center", formArea);

        ActionListener updateAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String description = descriptionTxt.getText();
                String category = (String) categoryList.getSelectedValue();
                String date = dateTxt.getText();
                String sum = sumTxt.getText();
                String[] newInfo = {description, sum, date, category};
                try {
                    expense.update(id, newInfo);
                } catch (Exception e) {}
                expensePage();
            }
        };
        submitBtn.addActionListener(updateAction);
    }

    public void updateIncome(int id) {
        title.setText("New income");
        contentPanel.removeAll();
        frame.setSize(500, 300);

        String description = "";
        String strDate = "";
        String strSum = "";

        try {
            rs = income.getByID(id);
        } catch (Exception e) {}
        try {
            while(rs.next()) {
                description = rs.getString("description");

                Date date = rs.getDate("date");
                System.out.println(date);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                strDate = dateFormat.format(date);

                double sum = rs.getDouble("sum");
                strSum=Double.toString(sum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel formArea = new JPanel();
        formArea.setLayout(new GridLayout(7,1,0,0));

        JLabel descriptionLbl = new JLabel("Description");
        JTextField descriptionTxt = new JTextField(description);

        JLabel dateLbl = new JLabel("Date (YYYY-MM-DD)");
        JTextField dateTxt = new JTextField(strDate);

        JLabel sumLbl = new JLabel("Cost");
        JTextField sumTxt = new JTextField(strSum);

        JButton submitBtn = new JButton("Update Income");

        formArea.add(descriptionLbl);
        formArea.add(descriptionTxt);
        formArea.add(dateLbl);
        formArea.add(dateTxt);
        formArea.add(sumLbl);
        formArea.add(sumTxt);
        formArea.add(submitBtn);

        contentPanel.add("Center", formArea);

        ActionListener updateAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String description = descriptionTxt.getText();
                String date = dateTxt.getText();
                String sum = sumTxt.getText();
                String[] newInfo = {description, sum, date};

                try {
                  boolean test = income.update(id, newInfo);
                  System.out.println(test);
                } catch (Exception e) {
                    System.out.println("updateAction VIEW: "+e);
                }
                incomePage();
            }
        };
        submitBtn.addActionListener(updateAction);
    }

    public void reportsPage() {
        title.setText("Reports");
        contentPanel.removeAll();
        frame.setSize(500, 250);

        JPanel formArea = new JPanel();
        formArea.setLayout(new GridLayout(5,1,0,0));
        formArea.setBackground(Color.lightGray);

        JPanel buttonsArea = new JPanel();
        buttonsArea.setLayout(new GridLayout(1,2,0,0));

        JLabel firstDateLbl = new JLabel("First Date (YYYY-MM-DD)");
        JTextField firstDateTxt = new JTextField();

        JLabel secondDateLbl = new JLabel("Second Date (YYYY-MM-DD)");
        JTextField secondDateTxt = new JTextField();

        JButton listReport = new JButton("List Report");
        JButton pieChartReport = new JButton("Pie Chart Report");
        buttonsArea.add(listReport);
        buttonsArea.add(pieChartReport);

        formArea.add(firstDateLbl);
        formArea.add(firstDateTxt);
        formArea.add(secondDateLbl);
        formArea.add(secondDateTxt);
        formArea.add(buttonsArea);

        contentPanel.add("Center", formArea);

        ActionListener listAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    String date1 = firstDateTxt.getText();
                    String date2 = secondDateTxt.getText();
                    Map<String, Double> reportMap = reports.report(date1, date2);
                    listReportPage(reportMap);
                } catch (Exception e) {}
            }
        };
        listReport.addActionListener(listAction);

        ActionListener pieChartAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    String date1 = firstDateTxt.getText();
                    String date2 = secondDateTxt.getText();
                    Map<String, Double> reportMap = reports.report(date1, date2);
                    pieChartReportPage(reportMap);
                } catch (Exception e) {}

            }
        };
        pieChartReport.addActionListener(pieChartAction);
    }

    public void listReportPage(Map<String, Double> reportMap) {
        title.setText("List Report");
        contentPanel.removeAll();

        int count = reportMap.size();
        int height = 130 + count*16;
        frame.setSize(600, height);

        String[][] table = new String[count+1][];
        String[] columnNames = { "CATEGORY", "SUM" };
        table[0] = columnNames;
        int index = 1;
        int j = 0;
        String[] categories = reportMap.keySet().toArray(new String[0]);
        Double[] sums = reportMap.values().toArray(new Double[0]);

        while(index < count+1) {
            table[index] = new String[2];
            table[index][0] = categories[j];
            table[index][1] = String.valueOf(sums[j]);
            index += 1;
            j += 1;
        }
        JTable reportList = new JTable(table, columnNames);
        reportList.setBounds(30, 40, 200, 300);
        contentPanel.add(reportList);
        reportList.setBackground(Color.lightGray);
    }

    public void pieChartReportPage(Map<String, Double> reportMap) {
        title.setText("Pie Chart Report");
        contentPanel.removeAll();
    }
}