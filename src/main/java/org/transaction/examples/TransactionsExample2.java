package org.transaction.examples;

import java.sql.*;

public class TransactionsExample2 {
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

        int moneyAccountSender = moneyCount1 - 10;
        int moneyAccountReceiver = moneyCount2 + 10;
        updateMoneyCount(1, 2, moneyAccountSender, moneyAccountReceiver);

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

    private static Integer updateMoneyCount(int idSender,
                                            int idReceiver,
                                            int moneyAccountSender,
                                            int moneyAccountReceiver) throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setAutoCommit(false);

            PreparedStatement preparedStatementSender = connection.prepareStatement(QUERY_UPDATE_MONEY_COUNT);
            preparedStatementSender.setInt(1, moneyAccountSender);
            preparedStatementSender.setInt(2, idSender);
            int updateRows = preparedStatementSender.executeUpdate();
            System.out.println("Sender updated rows " + updateRows);

            if (true) {
                throw new RuntimeException();
            }
            PreparedStatement preparedStatementReceiver = connection.prepareStatement(QUERY_UPDATE_MONEY_COUNT);
            preparedStatementReceiver.setInt(1, moneyAccountReceiver);
            preparedStatementReceiver.setInt(2, idReceiver);
            int updateRows2 = preparedStatementReceiver.executeUpdate();
            System.out.println("Receiver updated rows " + updateRows2);

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return null;
    }
}
