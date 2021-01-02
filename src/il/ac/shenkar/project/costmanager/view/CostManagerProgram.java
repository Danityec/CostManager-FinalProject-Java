package il.ac.shenkar.project.costmanager.view;

import javax.swing.*;

public class CostManagerProgram {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();
                gui.start();
            }
        };
        SwingUtilities.invokeLater(runnable);
    }
}
