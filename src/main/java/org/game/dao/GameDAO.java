package org.game.dao;

import org.game.model.Game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class GameDAO {
    static final String DB_URL = "jdbc:mysql://localhost/games_db";
    static final String USER = "root";
    static final String PASS = "root";
    static final String QUERY = "INSERT INTO games VALUES (?, ?, ?, ?, ?)";

    public static int saveGames(List<Game> games) throws ClassNotFoundException, SQLException {
        int result = 0;
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            for (Game game : games) {
                preparedStatement.setInt(1, game.getId());
                preparedStatement.setString(2, game.getName());
                preparedStatement.setString(3, game.getDescription());
                preparedStatement.setDate(4, game.getReleaseDate());
                preparedStatement.setBoolean(5, game.getOnSale());
                preparedStatement.execute();
                result++;
            }
            connection.commit();
            System.out.println("\n");

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return result;
    }
}
