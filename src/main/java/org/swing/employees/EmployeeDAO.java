package org.swing.employees;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO extends AbstractDAO {

    public List<Employee> fetchAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(QUERY_SELECT_EMPLOYEES);

        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String birthDate = resultSet.getString("birth_date");
            String email = resultSet.getString("email");

            Employee employee = new Employee(id, name, birthDate, email);
            employees.add(employee);
        }
        connection.close();
        System.out.println("Retrieved all employees successfully");
        return employees;
    }


    public void createEmployee(Long id, String name, String birthDate, String email) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_CREATE_EMPLOYEE);
        preparedStatement.setLong(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, birthDate);
        preparedStatement.setString(4, email);
        preparedStatement.execute();
        System.out.println("Employee created successfully");
        connection.close();
    }

    public void createEmployee(Employee employee) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_CREATE_EMPLOYEE);
        preparedStatement.setLong(1, employee.getId());
        preparedStatement.setString(2, employee.getName());
        preparedStatement.setString(3, employee.getBirthDate());
        preparedStatement.setString(4, employee.getEmail());
        preparedStatement.execute();
        System.out.println("Employee created successfully");
        connection.close();
    }

    public void editEmployee(Employee employee) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE_EMPLOYEES);
        preparedStatement.setString(1, employee.getName());
        preparedStatement.setString(2, employee.getBirthDate());
        preparedStatement.setString(3, employee.getEmail());
        preparedStatement.setLong(4, employee.getId());
        preparedStatement.execute();
        System.out.println("Employee updated successfully");
        connection.close();
    }

    public void deleteEmployee(long id) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE_EMPLOYEE);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        connection.close();
    }
}
