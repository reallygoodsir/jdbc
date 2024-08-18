package org.game.app;

import org.game.dao.GameDAO;
import org.game.reader.Reader;
import org.game.model.Game;
import org.game.utils.Resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) throws URISyntaxException, FileNotFoundException,
            SQLException, ClassNotFoundException {
        Resources resources = new Resources();
        File file = resources.read("games.txt");
        Reader reader = new Reader();
        List<Game> games = reader.readGames(file);
        System.out.println(games);
        GameDAO gameDAO = new GameDAO();
        int createdGames = gameDAO.saveGames(games);
        System.out.println("createdGames " + createdGames);
    }
}
