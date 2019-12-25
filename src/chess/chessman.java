package chess;

import java.awt.*;

import static chess.player.UP;

enum chessmanType{CAR, HORSE, ELEPHANT, GUARD, BOSS, GUN, SOLDIER}
enum player{UP, DOWN}

public class chessman {

    private chessmanType type;
    private player owner;
    private Point position;
    private Image img;

    public chessmanType getType(){return type;}

    //所属方

    //构造函数
    public chessman(chessmanType type, player owner, Point pos, Image im){
        this.owner = owner;
        this.type = type;
        this.position = pos;
        this.img = im;
    }

    public Image getImg() {
        return img;
    }

    //设置存活状态
    public void setAlived() {
    }
    public Point getPosition(){
        return this.position;
    }
    public void setPosition(Point p){
        this.position = p;
    }
    private boolean inUpBossArea(Point p){
        return p.x >= 3 && p.x <= 5 && p.y >= 0 && p.y <= 2;
    }
    private boolean inDownBossArea(Point p){
        return p.x >= 3 && p.x <= 5 && p.y >= 7 && p.y <= 9;
    }
    private boolean inUpArea(Point p){
        return p.x >= 0 && p.x <= 8 && p.y <= 4 && p.y >= 0;
    }
    private boolean inDownArea(Point p){
        return p.x >= 0 && p.x <= 8 && p.y >= 5 && p.y <= 9;
    }
    private boolean inArea(Point p){
        return p.x >= 0 && p.x <= 8 && p.y >= 0 && p.y <= 9;
    }
    private boolean inlinex(Point p){
        return this.position.x == p.x;
    }
    private boolean inliney(Point p){
        return this.position.y == p.y;
    }
    private boolean horseMove(Point p){
        return (Math.abs(p.x - this.position.x) == 1 && Math.abs(p.y - this.position.y) == 2 )||( Math.abs(p.x - this.position.x) == 2 && Math.abs(p.y - this.position.y) == 1);
    }
    //设置位置
    //需要一个检查能否移动的函数
    //棋盘大小是10*9的
    //这里只检查走的规则符不符合
    public boolean ableToMove(Point pos){
        if(pos == this.position)
            return false;
        if(!inArea(pos))
            return false;
        switch (this.type){
            case CAR:
            case GUN:
                return this.inlinex(pos) || this.inliney(pos);
            case HORSE:
                return horseMove(pos);
            case ELEPHANT:
                if(Math.abs(pos.x - this.position.x)==2 && Math.abs(pos.y-this.position.y)==2)
                    {
                        if(this.owner==UP){
                            return this.inUpArea(pos);
                        }
                        else{
                            return this.inDownArea(pos);
                        }
                    }
                else
                    {return false;}
            case GUARD:
                if(Math.abs(pos.x-this.position.x)==1 && Math.abs(pos.y-this.position.y)==1)
                    {
                        switch (this.owner){
                            case UP:
                                return inUpBossArea(pos);
                            case DOWN:
                                return inDownBossArea(pos);
                        }
                    }
                else
                    {return false;}
            case BOSS:
                if(Math.abs(pos.x-this.position.x)+Math.abs(pos.y-this.position.y)==1)
                    {
                        switch (this.owner) {
                            case UP:
                                return this.inUpBossArea(pos);
                            case DOWN:
                                return this.inDownBossArea(pos);
                        }
                    }
                else
                    {return false;}
            case SOLDIER:
                if((Math.abs(pos.x-this.position.x)+Math.abs(pos.y-this.position.y))==1) {
                    switch (this.owner) {
                        case UP:
                            if (this.inUpArea(this.position) && pos.y > this.position.y && pos.x==this.position.x) return true;
                            return this.inDownArea(this.position) && pos.y >= this.position.y;
                        case DOWN:
                            if (this.inDownArea(this.position) && pos.y < this.position.y && pos.x==this.position.x) return true;
                            return this.inUpArea(this.position) && pos.y <= this.position.y;
                    }
                }
                else{return false;}

        }
        return false;
    }

}
