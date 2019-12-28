package chess.ChessBoard;

public class Coordinate {
    public int x, y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coordinate(int[] p){
        this.x = p[0];
        this.y = p[1];
    }

    @Override
    public int hashCode(){
        return x * 100 +  y;
    }

    @Override
    public boolean equals(Object obj){
        if(! (obj instanceof Coordinate))
            return false;
        return ((Coordinate) obj).x == x && ((Coordinate) obj).y == y;
    }
}
