package co.edu.unal.tictactoeonline.model;

public class Game {

    private String owner;
    private String status;
    private String competitor;
    private String turn;
    private String b1;
    private String b2;
    private String b3;
    private String b4;
    private String b5;
    private String b6;
    private String b7;
    private String b8;
    private String b9;
    private String key;
    private String result;
    private int numTies;
    private int numHuman;
    private int numAndroid;
    private int numGamesPlayed;

    public Game() {

    }

    public Game(String owner, String status, String competitor, String turn) {
        this.owner = owner;
        this.status = status;
        this.competitor = competitor;
        this.turn = turn;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompetitor() {
        return competitor;
    }

    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getB1() {
        return b1;
    }

    public void setB1(String b1) {
        this.b1 = b1;
    }

    public String getB2() {
        return b2;
    }

    public void setB2(String b2) {
        this.b2 = b2;
    }

    public String getB3() {
        return b3;
    }

    public void setB3(String b3) {
        this.b3 = b3;
    }

    public String getB4() {
        return b4;
    }

    public void setB4(String b4) {
        this.b4 = b4;
    }

    public String getB5() {
        return b5;
    }

    public void setB5(String b5) {
        this.b5 = b5;
    }

    public String getB6() {
        return b6;
    }

    public void setB6(String b6) {
        this.b6 = b6;
    }

    public String getB7() {
        return b7;
    }

    public void setB7(String b7) {
        this.b7 = b7;
    }

    public String getB8() {
        return b8;
    }

    public void setB8(String b8) {
        this.b8 = b8;
    }

    public String getB9() {
        return b9;
    }

    public void setB9(String b9) {
        this.b9 = b9;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getNumTies() {
        return numTies;
    }

    public void setNumTies(int numTies) {
        this.numTies = numTies;
    }

    public int getNumHuman() {
        return numHuman;
    }

    public void setNumHuman(int numHuman) {
        this.numHuman = numHuman;
    }

    public int getNumAndroid() {
        return numAndroid;
    }

    public void setNumAndroid(int numAndroid) {
        this.numAndroid = numAndroid;
    }

    public int getNumGamesPlayed() {
        return numGamesPlayed;
    }

    public void setNumGamesPlayed(int numGamesPlayed) {
        this.numGamesPlayed = numGamesPlayed;
    }

}
