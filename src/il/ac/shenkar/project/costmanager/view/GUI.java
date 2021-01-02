package il.ac.shenkar.project.costmanager.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI implements IView {
    private JFrame frame;
    private JButton expense, income, report;
    private JPanel panelBottom,panelCenter, panelTop;

    public GUI() {
        frame = new JFrame("Cost Manager");
        expense = new JButton("Expenses");
        income = new JButton("Incomes");
        report = new JButton("Reports");
        panelBottom = new JPanel();
        panelCenter = new JPanel();
        panelTop = new JPanel();

        //getting frame content pane
        Container container = frame.getContentPane();

        //assigning background colors for each one of the panels
        panelBottom.setBackground(Color.gray);
        panelCenter.setBackground(Color.pink);
        panelTop.setBackground(Color.lightGray);

        //assigning layout managers for each one of the containers
        panelBottom.setLayout(new FlowLayout());
        panelTop.setLayout(new FlowLayout());
        container.setLayout(new BorderLayout());

        //placing the components above the containers
        panelBottom.add(btOK);
        panelBottom.add(btClear);
        panelTop.add(tf);
        panelCenter.add("West", panelCenterLeft);
        panelCenter.add("East", panelCenterRight);
        container.add("North",panelTop);
        container.add("Center",panelCenter);
        container.add("South",panelBottom);



        //handling frame closing event
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                frame.setVisible(false);
                frame.dispose();
                System.exit(0);
            }
        });
    }

    public void start() {
        frame.setSize(800,700);
        frame.setVisible(true);
    }
}
