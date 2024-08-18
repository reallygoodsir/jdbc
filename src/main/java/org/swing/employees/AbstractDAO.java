package org.swing.employees;

public class AbstractDAO {
    public static final String DB_URL = "jdbc:mysql://localhost/employee_db";
    public static final String USER = "root";
    public static final String PASS = "root";
    public static String QUERY_SELECT_EMPLOYEES = "select * from employee_manager";

    public static String QUERY_CREATE_EMPLOYEE = "INSERT INTO employee_manager VALUES (?, ?, ?, ?)";
    public static String QUERY_UPDATE_EMPLOYEES = "update employee_manager set name = ?, birth_date = ?, email = ? where id = ? ";
    public static String QUERY_DELETE_EMPLOYEE = "DELETE FROM employee_manager WHERE id = ?";
}
