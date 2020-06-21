package Model;

import java.io.Serializable;

public class MazeCharacter implements Serializable {
    private String characterName;
    private int characterRow;
    private int characterCol;
    private String characterDirection;
    private String url;

    public MazeCharacter() {
        characterName = " Mulan ";
        url = "/Images/MulanI.png";
        characterRow = 0;
        characterCol = 0;
        characterDirection = "front";
    }

    public MazeCharacter(int row, int col) {
        characterRow = row;
        characterCol = col;
    }

    public MazeCharacter(String name, int row, int col) {
        characterName = name;
        characterRow = row;
        characterCol = col;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacter(String characterName, String url) {
        this.characterName = characterName;
        this.url = url;
    }

    public int getCharacterRow() {
        return characterRow;
    }

    public void setCharacterRow(int characterRow) {
        this.characterRow = characterRow;
    }

    public int getCharacterCol() {
        return characterCol;
    }

    public void setCharacterCol(int characterCol) {
        this.characterCol = characterCol;
    }

    public String getCharacterDirection() {
        return characterDirection;
    }

    public void setCharacterDirection(String characterDirection) {
        this.characterDirection = characterDirection;
    }

    public void setCharacterPosition(int row, int col) {
        this.characterRow = row;
        this.characterCol = col;
    }
}