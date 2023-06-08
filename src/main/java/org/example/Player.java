package org.example;

public class Player {

    private int id;
    private String PlayerName;
    private String PlayerPassword;
    private boolean PlayerIsOnline;
    private String PlayerStatus;
    private int PlayerScore;

    public Player(String playerName, String playerPassword, Boolean playerIsOnline, String playerStatus) {

        this.PlayerName = playerName;
        this.PlayerPassword = playerPassword;
        this.PlayerIsOnline = playerIsOnline;
        PlayerStatus = playerStatus;

    }

    public Player(int id, String playerName, String playerPassword, boolean playerIsOnline, String playerStatus, int playerScore) {

        this.id = id;
        PlayerName = playerName;
        PlayerPassword = playerPassword;
        PlayerIsOnline = playerIsOnline;
        PlayerStatus = playerStatus;
        PlayerScore = playerScore;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    public String getPlayerPassword() {
        return PlayerPassword;
    }

    public void setPlayerPassword(String playerPassword) {
        PlayerPassword = playerPassword;
    }

    public boolean isPlayerIsOnline() {
        return PlayerIsOnline;
    }

    public void setPlayerIsOnline(boolean playerIsOnline) {
        PlayerIsOnline = playerIsOnline;
    }

    public String getPlayerStatus() {
        return PlayerStatus;
    }

    public void setPlayerStatus(String playerStatus) {
        PlayerStatus = playerStatus;
    }

    public int getPlayerScore() {
        return PlayerScore;
    }

    public void setPlayerScore(int playerScore) {
        PlayerScore = playerScore;
    }

    @Override
    public String toString() {

        return "Player{" +
                "id=" + id +
                ", PlayerName='" + PlayerName + '\'' +
                ", PlayerPassword='" + PlayerPassword + '\'' +
                ", PlayerIsOnline=" + PlayerIsOnline +
                ", PlayerStatus='" + PlayerStatus + '\'' +
                ", PlayerScore=" + PlayerScore +
                '}';

    }
}
