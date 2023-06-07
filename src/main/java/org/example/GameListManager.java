package org.example;

import java.util.ArrayList;
import java.util.Random;

public class GameListManager {
    private static ArrayList<Game> gamesList;

    private DataBase dataBase = new DataBase();

    public GameListManager() {

        gamesList = new ArrayList<>();
    }

    public void addGame(Game game) {

        gamesList.add(game);
    }

    public void removeGame(Game game) {

        gamesList.remove(game);
    }

    public Game findGameById(int id) {
        for (Game game : gamesList) {
            if (game.getId() == id) {
                return game;
            }
        }
        return null;
    }

    public int generateRandomId() {
        Random random = new Random();
        return 1001 + random.nextInt(9000);
    }

    public String getGamesListResponse() {
        StringBuilder response = new StringBuilder();
        response.append("games_list ");
        for (Game game : gamesList) {
            response.append(game.getId());
            response.append(" ");
            response.append(game.getPlayer1());
            response.append(" ");
            response.append(game.getPlayer2());
            response.append(" ");
        }
        return response.toString();
    }

    public String createGame(String username) {
        int gameId = generateRandomId();
        Game newGame = new Game(gameId, username, "-");
        addGame(newGame);
        dataBase.changeStatus("WaitingRoom", username);
        return "game_created " + gameId;
    }

    public String deleteGame(int gameId) {
        Game game = findGameById(gameId);
        if (game != null) {
            removeGame(game);
            dataBase.changeStatus("Online", game.getPlayer1());
            dataBase.changeStatus("Online", game.getPlayer2());
            return "game_deleted";
        } else {
            return "game_not_found";
        }
    }

    public String joinGame(int gameId, String username) {
        Game game = findGameById(gameId);
        if (game != null) {
            game.setPlayer2(username);
            dataBase.changeStatus("WaitingRoom", username);
            return "game_joined " + gameId;
        } else {
            return "game_not_found";
        }
    }

    public String leaveGame(int gameId) {
        Game game = findGameById(gameId);
        if (game != null) {
            dataBase.changeStatus("Online", game.getPlayer1());
            game.setPlayer2("-");
            return "game_left " + gameId;
        } else {
            return "game_not_found";
        }
    }
}

