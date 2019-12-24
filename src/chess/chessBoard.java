package chess;

import java.awt.*;

public class chessBoard {

    private boolean[][] position = new boolean[9][10];

    public void setPosition(Point p ){
        position[p.x][p.y] = true;
    }
    public void clearPosition(Point p ){
        position[p.x][p.y] = false;
    }
    public void initial(){
        for(int i = 0 ; i < 9 ; i++){
            position[i][0] = true;
            position[i][9] = true;
        }
        for(int i = 0 ; i < 5 ; i++){
            position[2*i][3] = true;
            position[2*i][6] = true;
        }
        position[1][2] = true;
        position[7][2] = true;
        position[1][7] = true;
        position[7][7] = true;
    }
    public boolean getPosition(int x, int y){return position[x][y];}
    public boolean inlineWithNoBar(Point p1, Point p2){
        if(p1.x == p2.x){
            for(int i = Math.min(p1.y,p2.y);i <= Math.max(p1.y,p2.y);i++){
                if(position[p1.x][i] && i != p1.y && i != p2.y)
                    return false;
            }
            return true;
        }
        if(p1.y == p2.y){
            for(int i = Math.min(p1.x,p2.x);i <= Math.max(p1.x,p2.x);i++){
                if(position[i][p1.y] && i != p1.x && i != p2.x)
                    return false;
            }
            return true;
        }
        return false;
    }
    public boolean inlineWithOneBar(Point p1, Point p2){
        if(p1.x == p2.x){
            int bars = 0;
            for(int i = Math.min(p1.y,p2.y);i <= Math.max(p1.y,p2.y);i++){
                if(position[p1.x][i] && i != p1.y && i != p2.y)
                    bars++;
            }
            return bars == 1;
        }
        if(p1.y == p2.y){
            int bars = 0;
            for(int i = Math.min(p1.x,p2.x);i <= Math.max(p1.x,p2.x);i++){
                if(position[i][p1.y] && i != p1.x && i != p2.x)
                    bars++;
            }
            return bars == 1;
        }
        return false;
    }
    public boolean horseWithNoBar(Point src, Point dst){
        if(dst.x - src.x == 2 && !position[src.x+1][src.y] )return true;
        if(src.x - dst.x == 2 && !position[src.x-1][src.y] )return true;
        if(dst.y - src.y == 2 && !position[src.x][src.y+1] )return true;
        return src.y - dst.y == 2 && !position[src.x][src.y - 1];
    }
    public boolean elephantWithNoBar(Point src, Point dst){
        return !position[(src.x + dst.x) / 2][(src.y + dst.y) / 2];
    }
}
