package org.example;


public class ActiveGame {
        private int gameId;
        private String player1;
        private String player2;
        private String turn;
        private String status = "onGoing";
        private String gameBoard = "0_2_0_2_0_2_0_2_2_0_2_0_2_0_2_0_0_2_0_2_0_2_0_2_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_1_0_1_0_1_0_1_0_0_1_0_1_0_1_0_1_1_0_1_0_1_0_1_0";

    public ActiveGame(int gameId, String player1, String player2) {
        this.gameId = gameId;
        this.player1 = player1;
        this.player2 = player2;
        this.turn = player1;
    }

    public int getGameId() {
            return gameId;
        }

        public String getPlayer1() {
            return player1;
        }

        public String getPlayer2() {
            return player2;
        }

        public String getTurn() {
            return turn;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }

        public void setPlayer1(String player1) {
            this.player1 = player1;
        }

        public void setPlayer2(String player2) {
            this.player2 = player2;
        }

        public void setTurn(String turn) {
                this.turn = turn;
            }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGameBoard() {
            return gameBoard;
        }

        public void setGameBoard(String gameBoard) {
            this.gameBoard = gameBoard;
        }
}


