package org.transaction.examples;

import java.sql.*;

public class TransactionsExample1 {
    static final String DB_URL = "jdbc:mysql://localhost/personal_db";
    static final String USER = "root";
    static final String PASS = "root";
    static final String QUERY_SELECT_MONEY_COUNT = "select money_count from bank_accounts where id = ? and name = ?";

    static final String QUERY_UPDATE_MONEY_COUNT = "update bank_accounts set money_count = ? where id = ?";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        Integer moneyCount1 = fetchMoneyCount(1, "Billy");
        System.out.println("Billy money count: " + moneyCount1);

        Integer moneyCount2 = fetchMoneyCount(2, "Maria");
        System.out.println("Maria money count: " + moneyCount2);

        updateMoneyCount(1, moneyCount1 - 200);
        updateMoneyCount(2, moneyCount2 + 200);

    }

    private static Integer fetchMoneyCount(int id, String name) throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_MONEY_COUNT);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int moneyCount = resultSet.getInt("money_count");
                return moneyCount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return null;
    }

    private static Integer updateMoneyCount(int id, int moneyAccount) throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE_MONEY_COUNT);
            preparedStatement.setInt(1, moneyAccount);
            preparedStatement.setInt(2, id);
            int updateRows = preparedStatement.executeUpdate();
            return updateRows;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return null;
    }
}
