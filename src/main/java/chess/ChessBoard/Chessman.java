package chess.ChessBoard;

import chess.ChessGame.*;


public class Chessman {
    public enum ChessmanType{ju, ma, xiang, shi ,shuai, pao, bing}
    public String key;
    public ChessmanType type;
    public Coordinate position;
    public Chessman(int x, int y, String key, ChessmanType type){
        this.position = new Coordinate(x,y);
        this.type = type;
        this.key = key;
    }
}
