package org.game.reader;

import org.game.model.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Reader {
    public List<Game> readGames(File file) throws FileNotFoundException {
        ArrayList<Game> games = new ArrayList<>();
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String[] row = myReader.nextLine().split(";");

            Integer id = Integer.parseInt(row[0]);
            String name = row[1];
            String description = row[2];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
            LocalDate convertDate = LocalDate.parse(row[3], formatter);
            Date date = Date.valueOf(convertDate);
            Boolean onSale = Boolean.parseBoolean(row[4]);

            Game game = new Game(id, name, description, date, onSale);

            games.add(game);
        }

        return games;
    }
}
