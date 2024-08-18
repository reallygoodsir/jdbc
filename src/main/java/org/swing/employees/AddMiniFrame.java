package org.swing.employees;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddMiniFrame extends App {
    private JFrame miniFrame = new JFrame("Second");
    private JPanel panel = new JPanel(new FlowLayout());
    private JButton execute = new JButton();

    private HomeFrame homeFrame;

    public AddMiniFrame(HomeFrame homeFrame) throws SQLException {
        this.homeFrame = homeFrame;
        execute.setText("save");
        miniFrame.setSize(250, 400);
        miniFrame.setLocationRelativeTo(null);
        miniFrame.setBackground(Color.gray);
        miniFrame.setVisible(true);

        JTextArea idText = new JTextArea("ID:");
        idText.setLocation(20, 50);
        idText.setEditable(false);
        JTextArea nameText = new JTextArea("NAME:");
        nameText.setLocation(20, 50);
        nameText.setEditable(false);
        JTextArea birthDateText = new JTextArea("BIRTH DATE:");
        birthDateText.setLocation(20, 50);
        birthDateText.setEditable(false);
        JTextArea emailText = new JTextArea("EMAIL:");
        emailText.setLocation(20, 50);
        emailText.setEditable(false);

        JTextField idTextField = new JTextField(19);
        idTextField.setMargin(new Insets(1, 10, 10, 10));
        JTextField nameTextField = new JTextField(19);
        nameTextField.setMargin(new Insets(1, 10, 10, 10));
        JTextField birthTextField = new JTextField(19);
        birthTextField.setMargin(new Insets(1, 10, 10, 10));
        JTextField emailTextField = new JTextField(19);
        emailTextField.setMargin(new Insets(1, 10, 10, 10));

        panel.add(idText);
        panel.add(idTextField);

        panel.add(nameText);
        panel.add(nameTextField);

        panel.add(birthDateText);
        panel.add(birthTextField);

        panel.add(emailText);
        panel.add(emailTextField);

        execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("press save button");
                    EmployeeDAO employeeDAO = new EmployeeDAO();

                    Long id = Long.valueOf(idTextField.getText());
                    String name = nameTextField.getText();
                    String birthDate = birthTextField.getText();
                    String email = emailTextField.getText();
                    idTextField.setText("");
                    nameTextField.setText("");
                    birthTextField.setText("");
                    emailTextField.setText("");
                    System.out.println(id + " " + name + " " + birthDate + " " + email);

                    Employee employee = new Employee(id, name, birthDate, email);
                    employeeDAO.createEmployee(employee);
                    miniFrame.dispose();
                    homeFrame.updateEmployeeTable();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(execute);

        miniFrame.add(panel);
    }
}

