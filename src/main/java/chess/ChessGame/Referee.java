package chess.ChessGame;

import chess.ChessBoard.*;

import java.util.HashSet;



public class Referee {
    public enum judgementResult{eat, move, nop}
    private ChessBoard board;
    private HashSet<Coordinate> bx, rx, bshi, rshi ,bshuai, rshuai;
    
    private judgementResult ju(Chessman ju, Coordinate pos) {
        if (pos.x == ju.position.x) {
            int i = Math.min(ju.position.y, pos.y) + 1, end = Math.max(ju.position.y, pos.y);
            while (i < end) {
                if (board.getChessman(pos.x, i) != null) {
                    return judgementResult.nop;
                }
                i++;
            }
            if (board.getChessman(pos) == null) {
                return judgementResult.move;
            }
            if (board.getChessman(pos).key.charAt(0) == ju.key.charAt(0)) {
                return judgementResult.nop;
            } else {
                return judgementResult.eat;
            }
        } else if (pos.y == ju.position.y) {
            int i = Math.min(ju.position.x, pos.x) + 1, end = Math.max(ju.position.x, pos.x);
            while (i < end) {
                if (board.getChessman(i, pos.y) != null) {
                    return judgementResult.nop;
                }
                i++;
            }
            if (board.getChessman(pos) == null) {
                return judgementResult.move;
            }
            if (board.getChessman(pos).key.charAt(0) == ju.key.charAt(0)) {
                return judgementResult.nop;
            } else {
                return judgementResult.eat;
            }
        }
        else return judgementResult.nop;
    }
    private judgementResult ma(Chessman ma, Coordinate pos){
        if(board.getChessman(pos) != null && board.getChessman(pos).key.charAt(0) == ma.key.charAt(0))
            return judgementResult.nop;
        boolean bie = true;
        if(ma.position.x - pos.x == 2 && Math.abs(ma.position.y - pos.y) == 1)
            bie = board.getChessman(ma.position.x - 1,ma.position.y) != null;
        else if(pos.x - ma.position.x == 2 && Math.abs(ma.position.y - pos.y) == 1)
            bie = board.getChessman(ma.position.x + 1,ma.position.y) != null;
        else if(ma.position.y - pos.y == 2 && Math.abs(ma.position.x - pos.x) == 1)
            bie = board.getChessman(ma.position.x,ma.position.y - 1) != null;
        else if(pos.y - ma.position.y == 2 && Math.abs(ma.position.x - pos.x) == 1)
            bie = board.getChessman(ma.position.x,ma.position.y + 1) != null;
        if(bie)
            return judgementResult.nop;
        if(board.getChessman(pos) == null)
            return judgementResult.move;
        else
            return judgementResult.eat;
    }
    private judgementResult xiang(Chessman xiang, Coordinate pos){
        if(board.getChessman(pos) != null && board.getChessman(pos).key.charAt(0) == xiang.key.charAt(0))
            return judgementResult.nop;
        if(Math.abs(pos.x - xiang.position.x) != Math.abs(pos.y - xiang.position.y))
            return judgementResult.nop;
        switch(xiang.key.charAt(0)){
            case 'r':
                if(!rx.contains(pos))
                    return judgementResult.nop;
                break;
            case 'b':
                if(!bx.contains(pos))
                    return judgementResult.nop;
        }
        if(board.getChessman((pos.x + xiang.position.x)/2, (pos.y + xiang.position.y)/2) != null)
            return judgementResult.nop;
        if(board.getChessman(pos) == null)
            return judgementResult.move;
        return judgementResult.eat;
    }
    private judgementResult shi(Chessman shi, Coordinate pos){
        if(board.getChessman(pos) != null && board.getChessman(pos).key.charAt(0) == shi.key.charAt(0))
            return judgementResult.nop;
        if(Math.abs(shi.position.x - pos.x)!= 1 || Math.abs(shi.position.y - pos.y) != 1)
            return judgementResult.nop;
        switch(shi.key.charAt(0)){
            case 'r':
                if(!rshi.contains(pos))
                    return judgementResult.nop;
                break;
            case 'b':
                if(!bshi.contains(pos))
                    return judgementResult.nop;
        }
        if(board.getChessman(pos) == null)
            return judgementResult.move;
        return judgementResult.eat;
    }
    private judgementResult shuai(Chessman shuai, Coordinate pos){
        if(board.getChessman(pos) != null && board.getChessman(pos).key.charAt(0) == shuai.key.charAt(0))
            return judgementResult.nop;
        switch(shuai.key.charAt(0)){
            case 'r':
                if(pos == board.chessmen.get("bz1").position){
                     if(shuai.position.x == board.chessmen.get("bz1").position.x){
                         for(int i = board.chessmen.get("bz1").position.y + 1; i < shuai.position.y; i++){
                             if(board.getChessman(shuai.position.x,i) != null)
                                 return judgementResult.nop;
                         }
                         return judgementResult.eat;
                     }
                     return judgementResult.nop;
                }
                if(Math.abs(shuai.position.x - pos.x) + Math.abs(shuai.position.y - pos.y) != 1)
                    return judgementResult.nop;
                if(!rshuai.contains(pos))
                    return judgementResult.nop;
            break;
            case 'b':
                if(pos == board.chessmen.get("rz1").position){
                    if(shuai.position.x == board.chessmen.get("rz1").position.x){
                        for(int i = shuai.position.y + 1; i < board.chessmen.get("rz1").position.y; i++){
                            if(board.getChessman(shuai.position.x,i) != null)
                                return judgementResult.nop;
                        }
                        return judgementResult.eat;
                    }
                    return judgementResult.nop;
                }
                if(Math.abs(shuai.position.x - pos.x) + Math.abs(shuai.position.y - pos.y) != 1)
                    return judgementResult.nop;
                if(!bshuai.contains(pos))
                    return judgementResult.nop;
        }
        if(board.getChessman(pos) == null)
            return judgementResult.move;
        return judgementResult.eat;
    }
    private judgementResult pao(Chessman pao, Coordinate pos){
        if (pos.x == pao.position.x) {
            int i = Math.min(pao.position.y, pos.y) + 1, end = Math.max(pao.position.y, pos.y);
            int mid = 0;
            while (i < end) {
                if (board.getChessman(pos.x, i) != null) {
                    mid++;
                }
                i++;
            }

            if (board.getChessman(pos) == null && mid == 0) {
                return judgementResult.move;
            }
            if (mid == 1 && board.getChessman(pos) != null && board.getChessman(pos).key.charAt(0) != pao.key.charAt(0)) {
                return judgementResult.eat;
            } else {
                return judgementResult.nop;
            }
        } else if (pos.y == pao.position.y) {
            int i = Math.min(pao.position.x, pos.x) + 1, end = Math.max(pao.position.x, pos.x);
            int mid = 0;
            while (i < end) {
                if (board.getChessman(i, pos.y) != null) {
                    mid++;
                }
                i++;
            }
            if (board.getChessman(pos) == null && mid == 0) {
                return judgementResult.move;
            }
            if (mid == 1 && board.getChessman(pos) != null && board.getChessman(pos).key.charAt(0) != pao.key.charAt(0)) {
                return judgementResult.eat;
            } else {
                return judgementResult.nop;
            }
        }
        return judgementResult.nop;
    }
    private judgementResult bing(Chessman bing, Coordinate pos){
        if(board.getChessman(pos) != null && board.getChessman(pos).key.charAt(0) == bing.key.charAt(0))
            return judgementResult.nop;
        switch (bing.key.charAt(0)){
            case 'r':
                if(bing.position.y > 4 && bing.position.y - pos.y == 1 && bing.position.x == pos.x){
                    if(board.getChessman(pos) == null)
                        return judgementResult.move;
                    else
                        return judgementResult.eat;
                }
                if(bing.position.y <= 4 && bing.position.y - pos.y >= 0 && bing.position.y - pos.y + Math.abs(bing.position.x - pos.x) == 1){
                    if(board.getChessman(pos) == null)
                        return judgementResult.move;
                    else
                        return judgementResult.eat;
                }
                return judgementResult.nop;
            case 'b':
                if(bing.position.y <= 4 && pos.y - bing.position.y == 1 && bing.position.x == pos.x){
                    if(board.getChessman(pos) == null)
                        return judgementResult.move;
                    else
                        return judgementResult.eat;
                }
                if(bing.position.y > 4 && pos.y - bing.position.y >= 0 && pos.y - bing.position.y + Math.abs(bing.position.x - pos.x) == 1){
                    if(board.getChessman(pos) == null)
                        return judgementResult.move;
                    else
                        return judgementResult.eat;
                }
                return judgementResult.nop;
            default:
                return judgementResult.nop;
        }
    }


    public Referee(ChessBoard board){
        this.board = board;
    }
    public void init(){
        bx = new HashSet<Coordinate>();
        bx.add(new Coordinate(2,0));
        bx.add(new Coordinate(0,2));
        bx.add(new Coordinate(2,4));
        bx.add(new Coordinate(4,2));
        bx.add(new Coordinate(6,0));
        bx.add(new Coordinate(6,4));
        bx.add(new Coordinate(8,2));

        rx = new HashSet<Coordinate>();
        rx.add(new Coordinate(2,5));
        rx.add(new Coordinate(0,7));
        rx.add(new Coordinate(2,9));
        rx.add(new Coordinate(4,7));
        rx.add(new Coordinate(6,5));
        rx.add(new Coordinate(6,9));
        rx.add(new Coordinate(8,7));

        bshuai = new HashSet<Coordinate>();
        bshuai.add(new Coordinate(3,0));
        bshuai.add(new Coordinate(4,0));
        bshuai.add(new Coordinate(5,0));
        bshuai.add(new Coordinate(3,1));
        bshuai.add(new Coordinate(4,1));
        bshuai.add(new Coordinate(5,1));
        bshuai.add(new Coordinate(3,2));
        bshuai.add(new Coordinate(4,2));
        bshuai.add(new Coordinate(5,2));

        rshuai = new HashSet<Coordinate>();
        rshuai.add(new Coordinate(3,9));
        rshuai.add(new Coordinate(4,9));
        rshuai.add(new Coordinate(5,9));
        rshuai.add(new Coordinate(3,8));
        rshuai.add(new Coordinate(4,8));
        rshuai.add(new Coordinate(5,8));
        rshuai.add(new Coordinate(3,7));
        rshuai.add(new Coordinate(4,7));
        rshuai.add(new Coordinate(5,7));

        bshi = new HashSet<Coordinate>();
        bshi.add(new Coordinate(3,0));
        bshi.add(new Coordinate(5,0));
        bshi.add(new Coordinate(4,1));
        bshi.add(new Coordinate(3,2));
        bshi.add(new Coordinate(5,2));

        rshi = new HashSet<Coordinate>();
        rshi.add(new Coordinate(3,9));
        rshi.add(new Coordinate(5,9));
        rshi.add(new Coordinate(4,8));
        rshi.add(new Coordinate(3,7));
        rshi.add(new Coordinate(5,7));
    }
    public judgementResult judge(Chessman chessman, Coordinate pos){
        switch (chessman.type){
            case ju:return ju(chessman, pos);
            case ma:return ma(chessman, pos);
            case xiang: return xiang(chessman, pos);
            case shi: return shi(chessman, pos);
            case shuai: return shuai(chessman, pos);
            case pao:return pao(chessman, pos);
            case bing:return bing(chessman, pos);
            default:return judgementResult.nop;
        }
    }


}
