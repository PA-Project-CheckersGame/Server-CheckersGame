package org.example;

import java.util.ArrayList;
import java.util.Random;

public class GameListManager {

    private static ArrayList<Game> gamesList;
    private static ArrayList<ActiveGame> activeGamesList;
    private DataBase dataBase = new DataBase();

    public GameListManager() {

        gamesList = new ArrayList<>();
        activeGamesList = new ArrayList<>();

    }

    public void addGame(Game game) {

        gamesList.add(game);

    }

    public void removeGame(Game game) {

        gamesList.remove(game);

    }

    public void addActiveGame(ActiveGame activeGame) {

        activeGamesList.add(activeGame);

    }

    public void removeActiveGame(ActiveGame activeGame) {

        activeGamesList.remove(activeGame);

    }

    public Game findGameById(int id) {

        for (Game game : gamesList) {

            if (game.getId() == id) {

                return game;

            }
        }

        return null;
    }

    public ActiveGame findActiveGameById(int gameId) {

        for (ActiveGame activeGame : activeGamesList) {

            if (activeGame.getGameId() == gameId) {

                return activeGame;

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

    public String startGame(int gameId) {

        Game game = findGameById(gameId);

        if (game != null) {

            dataBase.changeStatus("InGame", game.getPlayer1());
            dataBase.changeStatus("InGame", game.getPlayer2());
            ActiveGame activeGame = new ActiveGame(gameId, game.getPlayer1(), game.getPlayer2());
            activeGamesList.add(activeGame);
            return "game_started " + gameId + " " + activeGame.getPlayer1() + " " + activeGame.getPlayer2() + " " + activeGame.getTurn();

        } else {

            return "game_not_found";

        }
    }

    public String getWaitingRoomUpdate(int gameId) {

        String response;
        ActiveGame activeGame = findActiveGameById(gameId);

        if (activeGame != null) {

            response =  "waiting_room_update game_started " + gameId + " " + activeGame.getPlayer1() + " " + activeGame.getPlayer2() + " " + activeGame.getTurn();

        } else {

            response =  "waiting_room_update game_not_started";

        }

        Game game = findGameById(gameId);

        if(game == null) {

            response = "waiting_room_update game_deleted";

        }

        return response;
    }

    public String setGameMove(int gameId, String turn, String gameBoard) {

        ActiveGame activeGame = findActiveGameById(gameId);

        if (activeGame != null) {

            activeGame.setTurn(turn);
            activeGame.setGameBoard(gameBoard);
            return "set_game_move ok";

        } else {

            return "game_not_found";

        }
    }

    public String setGameStatus(int gameId, String status) {

        ActiveGame activeGame = findActiveGameById(gameId);

        if (activeGame != null) {

            activeGame.setStatus(status);
            return "set_game_status_ok";

        } else {

            return "game_not_found";

        }
    }

    public String getGameUpdate(int gameId) {

        ActiveGame activeGame = findActiveGameById(gameId);

        if (activeGame != null) {

            return "game_update " + activeGame.getTurn() + " " + activeGame.getGameBoard();

        } else {

            return "game_not_found";

        }
    }

    public String getGameStatus(int gameId) {

        ActiveGame activeGame = findActiveGameById(gameId);

        if (activeGame != null) {

            return "game_status " + activeGame.getStatus();

        } else {

            return "game_not_found";

        }
    }

    public String deleteActiveGame(int gameId) {

        ActiveGame activeGame = findActiveGameById(gameId);

        if (activeGame != null) {

            removeActiveGame(activeGame);
            Game game = findGameById(gameId);
            removeGame(game);

            if (game != null) {

                dataBase.changeStatus("Online", game.getPlayer1());
                dataBase.changeStatus("Online", game.getPlayer2());

            }

            return "active_game_deleted";

        } else {

            return "active_game_not_found";

        }
    }
}

