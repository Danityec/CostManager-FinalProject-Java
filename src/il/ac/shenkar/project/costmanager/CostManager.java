package il.ac.shenkar.project.costmanager;

import il.ac.shenkar.project.costmanager.model.DB;
import il.ac.shenkar.project.costmanager.model.DerbyDB;
import il.ac.shenkar.project.costmanager.view.View;
import il.ac.shenkar.project.costmanager.viewmodel.ViewModel;

import javax.swing.*;

public class CostManager {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            ViewModel viewModel = new ViewModel();
            DB db = null;
            try {
                db = new DerbyDB();
            } catch (Exception e) {
                System.out.println("error");
                e.printStackTrace();
            }
            viewModel.setModel(db);

            View view = new View(viewModel);
            viewModel.setView(view);
            viewModel.start();
        };
        SwingUtilities.invokeLater(runnable);
    }
}
