package il.ac.shenkar.project.costmanager.view;

import il.ac.shenkar.project.costmanager.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        buttonSection.setLayout(new GridLayout(count+1,1,0,0));

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
        buttonSection.setLayout(new GridLayout(count+1,1,0,0));

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
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
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
                JPanel updateForm = new JPanel();
                contentPanel.add("South", updateForm);

                JLabel nameLbl = new JLabel("Name");
                JLabel categoryLbl = new JLabel("Category");
                JLabel dateLbl = new JLabel("Date");
                JLabel sumLbl = new JLabel("Sum");
                JTextField nameTxt = new JTextField();
                JTextField categoryTxt = new JTextField();
                JTextField dateTxt = new JTextField();
                JTextField sumTxt = new JTextField();
                JButton submit = new JButton("Submit");

                updateForm.add(nameLbl);
                updateForm.add(nameTxt);
                updateForm.add(categoryLbl);
                updateForm.add(categoryTxt);
                updateForm.add(dateLbl);
                updateForm.add(dateTxt);
                updateForm.add(sumLbl);
                updateForm.add(sumTxt);
                updateForm.add(submit);

                String[] newInfo = {"hhh"};
                try {
                    expense.update(id, newInfo);
                } catch (Exception e) {
                }
            }
        };
        editItem.addActionListener(editAction);

        ActionListener deleteAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    expense.delete(id);
                } catch (Exception e) {}

            }
        };
        deleteItem.addActionListener(deleteAction);
    }           // EDIT

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
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
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
                String[] newInfo = {"hhh"};
                try {
                    income.update(id, newInfo);






























                } catch (Exception e) {}
            }
        };
        editItem.addActionListener(editAction);

        ActionListener deleteAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    income.delete(id);
                } catch (Exception e) {}
            }
        };
        deleteItem.addActionListener(deleteAction);
    }            // EDIT

    public void addExpense() {
        title.setText("New expense");
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        contentPanel.removeAll();
        frame.setSize(500, 250);
        JPanel formArea = new JPanel();
        formArea.setLayout(new GridLayout(3,2,0,0));
        

        JPanel firstDateArea = new JPanel();
        firstDateArea.setLayout(new GridLayout(2,1,0,0));
        JPanel secondDataArea = new JPanel();
        secondDataArea.setLayout(new GridLayout(2,1,0,0));
        JPanel thirdDataArea = new JPanel();
        thirdDataArea.setLayout(new GridLayout(2,1,0,0));
        JPanel fourDataArea = new JPanel();
        fourDataArea.setLayout(new GridLayout(2,1,0,0));
        JPanel fiveDataArea = new JPanel();
        fiveDataArea.setLayout(new GridLayout(2,1,0,0));

        JPanel buttonsArea = new JPanel();
        buttonsArea.setBounds(30, 40, 200, 300);
        buttonsArea.setLayout(new GridLayout(1,1,0,0));
        JButton button = new JButton("Adding Expense");
        button.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));



        JLabel DescriptionLbl = new JLabel("Description");
        JTextField Description = new JTextField();
        DescriptionLbl.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        firstDateArea.add(DescriptionLbl);
        firstDateArea.add(Description);

        JLabel DateLbl = new JLabel("Date (MM/DD/YYYY)");
        JTextField Date = new JTextField();
        DateLbl.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        secondDataArea.add(DateLbl);
        secondDataArea.add(Date);

        JLabel CostLbl = new JLabel("Cost");
        JTextField Cost = new JTextField();
        CostLbl.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        thirdDataArea.add(CostLbl);
        thirdDataArea.add(Cost);

        JLabel CategoryLbl = new JLabel("Category");
        JTextField Category = new JTextField();
        CategoryLbl.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        firstDateArea.add(CategoryLbl);
        firstDateArea.add(Category);

        formArea.add(firstDateArea);
        formArea.add(secondDataArea);
        formArea.add(thirdDataArea);
        formArea.add(buttonsArea);

        contentPanel.add("South", button);
        contentPanel.add("Center", formArea);


    }

    public void addIncome() {
        title.setText("New income");
        contentPanel.removeAll();
        frame.setSize(500, 250);

        JPanel formArea = new JPanel();
        formArea.setLayout(new GridLayout(3,2,0,0));

        JPanel firstDateArea = new JPanel();
        firstDateArea.setLayout(new GridLayout(2,1,0,0));
        JPanel secondDataArea = new JPanel();
        secondDataArea.setLayout(new GridLayout(2,1,0,0));
        JPanel thirdDataArea = new JPanel();
        thirdDataArea.setLayout(new GridLayout(2,1,0,0));
        JPanel fourDataArea = new JPanel();
        fourDataArea.setLayout(new GridLayout(2,1,0,0));

        JPanel buttonsArea = new JPanel();
        buttonsArea.setBounds(30, 40, 200, 300);
        buttonsArea.setLayout(new GridLayout(1,1,0,0));
        JButton button = new JButton("Adding Income");


        JLabel DescriptionLbl = new JLabel("Description");
        JTextField Description = new JTextField();
        firstDateArea.add(DescriptionLbl);
        firstDateArea.add(Description);

        JLabel DateLbl = new JLabel("Date (MM/DD/YYYY)");
        JTextField Date = new JTextField();
        secondDataArea.add(DateLbl);
        secondDataArea.add(Date);

        JLabel CostLbl = new JLabel("Cost");
        JTextField Cost = new JTextField();
        thirdDataArea.add(CostLbl);
        thirdDataArea.add(Cost);


        formArea.add(firstDateArea);
        formArea.add(secondDataArea);
        formArea.add(thirdDataArea);
        formArea.add(buttonsArea);

        contentPanel.add("South", button);
        contentPanel.add("Center", formArea);


    }

    public void reportsPage() {
        title.setText("Reports");
        contentPanel.removeAll();
        frame.setSize(500, 250);

        JPanel formArea = new JPanel();
        formArea.setLayout(new GridLayout(3,1,0,0));
        JPanel firstDateArea = new JPanel();
        firstDateArea.setLayout(new GridLayout(2,1,0,0));
        JPanel secondDateArea = new JPanel();
        secondDateArea.setLayout(new GridLayout(2,1,0,0));
        JPanel buttonsArea = new JPanel();

        JLabel firstDateLbl = new JLabel("First Date (MM/DD/YYYY)");
        JTextField firstDate = new JTextField();
        firstDateArea.add(firstDateLbl);
        firstDateArea.add(firstDate);
        JLabel secondDateLbl = new JLabel("Second Date (MM/DD/YYYY)");
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

        ActionListener listAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    String date1 = firstDate.getText();
                    String date2 = secondDate.getText();
                    Map<String, Double> reportMap = reports.report(date1, date2);
                    listReportPage(reportMap);
                } catch (Exception e) {}
            }
        };
        listReport.addActionListener(listAction);

        ActionListener pieChartAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    String date1 = firstDate.getText();
                    String date2 = secondDate.getText();
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
