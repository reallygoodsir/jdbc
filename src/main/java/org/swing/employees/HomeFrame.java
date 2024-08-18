package org.swing.employees;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

public class HomeFrame {
    private JFrame frame;
    private JPanel panel;
    private JButton addButton;
    private JButton changeButton;
    private JButton deleteButton;
    private JTable listEmployeeTable;
    private Integer tableSelectedRow = 0;
    private Long id;

    public void start() throws SQLException {
        frame = new JFrame("JFrame Example");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setBackground(Color.gray);

        panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        addButton = new JButton();
        addButton.setBounds(0, 495, 65, 60);
        addButton.setText("ADD");
        addButton.addActionListener(ae -> {
            try {
                new AddMiniFrame(this);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        changeButton = new JButton();
        changeButton.setBounds(67, 495, 65, 60);
        changeButton.setText("EDIT");
        deleteButton = new JButton();
        deleteButton.setBounds(134, 495, 80, 60);
        deleteButton.setText("DELETE");
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(changeButton);
        updateEmployeeTable();
    }

    public void updateEmployeeTable() throws SQLException {
        System.out.println("Update employee table method call start");
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> employees = employeeDAO.fetchAll();
        System.out.println("Employees size " + employees.size());
        String rowData[][] = new String[employees.size()][2];
        String columnNames[] = {"ID", "NAME"};
        if (listEmployeeTable != null) {
            panel.remove(listEmployeeTable);
        }

        listEmployeeTable = new JTable(rowData, columnNames);
        listEmployeeTable.setBounds(0, 0, 300, 300);
        if (!employees.isEmpty()) {
            listEmployeeTable.setRowSelectionInterval(tableSelectedRow, tableSelectedRow);
        }
        panel.add(listEmployeeTable);

        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            listEmployeeTable.setValueAt(employee.getId().toString(), i, 0);
            listEmployeeTable.setValueAt(employee.getName(), i, 1);
        }

        Employee employeeSelected = employees.get(tableSelectedRow);
        System.out.println("Employee selected row " + employeeSelected);
        JTextField employeeId = new JTextField();
        panel.add(employeeId);
        employeeId.setBounds(300, 0, 150, 75);
        employeeId.setText(employeeSelected.getId().toString());
        employeeId.setEditable(false);
        employeeId.setVisible(true);

        JTextField employeeName = new JTextField();
        panel.add(employeeName);
        employeeName.setBounds(300, 75, 150, 75);
        employeeName.setText(employeeSelected.getName());
        employeeName.setVisible(true);
        employeeName.setFocusable(true);

        JTextField employeeBirthDate = new JTextField();
        panel.add(employeeBirthDate);
        employeeBirthDate.setBounds(300, 150, 150, 75);
        employeeBirthDate.setText(employeeSelected.getBirthDate());
        employeeBirthDate.setVisible(true);

        JTextField employeeEmail = new JTextField();
        panel.add(employeeEmail);
        employeeEmail.setBounds(300, 225, 150, 75);
        employeeEmail.setText(employeeSelected.getEmail());
        employeeEmail.setVisible(true);
        employeeEmail.setEnabled(true);


        listEmployeeTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = listEmployeeTable.getSelectedRow();
                System.out.println("Selected row " + selectedRow);
                Employee employee = employees.get(selectedRow);
                Long id = employee.getId();
                String name = employee.getName();
                String birthDate = employee.getBirthDate();
                String email = employee.getEmail();
                employeeId.setText(id.toString());
                employeeName.setText(name);
                employeeBirthDate.setText(birthDate);
                employeeEmail.setText(email);
                employeeId.setVisible(true);
                employeeName.setVisible(true);
                employeeBirthDate.setVisible(true);
                employeeEmail.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        changeButton.addActionListener(ae -> {
            try {
                EmployeeDAO employeeDAO1 = new EmployeeDAO();
                System.out.println("==== " + employeeId.getText() + employeeName.getText() + employeeBirthDate.getText() + employeeEmail.getText());
                id = Long.valueOf(employeeId.getText());
                String name = employeeName.getText();
                String birthDate = employeeBirthDate.getText();
                String email = employeeEmail.getText();
                Employee employee = new Employee(id, name, birthDate, email);
                System.out.println("VVVV " + listEmployeeTable.getSelectedRow());
                employeeDAO1.editEmployee(employee);
                tableSelectedRow = listEmployeeTable.getSelectedRow();

                updateEmployeeTable();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EmployeeDAO employeeDAO1 = new EmployeeDAO();
                    id = Long.valueOf(employeeId.getText());
                    employeeDAO1.deleteEmployee(id);
                    tableSelectedRow = 0;
                    updateEmployeeTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        System.out.println("Update employee table method call end");
    }
}
