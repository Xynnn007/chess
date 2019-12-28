package chess.ChessBoard;

import com.sun.org.apache.xpath.internal.objects.XNull;

import java.util.HashMap;
import java.util.Map;

import static chess.ChessBoard.Chessman.ChessmanType.*;


public class ChessBoard {
    private Chessman[][] position = new Chessman[9][10];
    public HashMap<String, Chessman> chessmen;
    
    private void initialChessmen(){
        //initial all chessmen
        // z refers to BOSS
        chessmen.put("bj1",new Chessman(0,0,"bj1",ju));
        chessmen.put("bj2",new Chessman(8,0,"bj2",ju));
        chessmen.put("bm1",new Chessman(1,0,"bm1",ma));
        chessmen.put("bm2",new Chessman(7,0,"bm2",ma));
        chessmen.put("bx1",new Chessman(2,0,"bx1",xiang));
        chessmen.put("bx2",new Chessman(6,0,"bx2",xiang));
        chessmen.put("bs1",new Chessman(3,0,"bs1",shi));
        chessmen.put("bs2",new Chessman(5,0,"bs2",shi));
        chessmen.put("bz1",new Chessman(4,0,"bz1",shuai));
        chessmen.put("bp1",new Chessman(1,2,"bp1",pao));
        chessmen.put("bp2",new Chessman(7,2,"bp2",pao));
        chessmen.put("bb1",new Chessman(0,3,"bb1",bing));
        chessmen.put("bb2",new Chessman(2,3,"bb2",bing));
        chessmen.put("bb3",new Chessman(4,3,"bb3",bing));
        chessmen.put("bb4",new Chessman(6,3,"bb4",bing));
        chessmen.put("bb5",new Chessman(8,3,"bb5",bing));

        chessmen.put("rj1",new Chessman(0,9,"rj1",ju));
        chessmen.put("rj2",new Chessman(8,9,"rj2",ju));
        chessmen.put("rm1",new Chessman(1,9,"rm1",ma));
        chessmen.put("rm2",new Chessman(7,9,"rm2",ma));
        chessmen.put("rx1",new Chessman(2,9,"rx1",xiang));
        chessmen.put("rx2",new Chessman(6,9,"rx2",xiang));
        chessmen.put("rs1",new Chessman(3,9,"rs1",shi));
        chessmen.put("rs2",new Chessman(5,9,"rs2",shi));
        chessmen.put("rz1",new Chessman(4,9,"rz1",shuai));
        chessmen.put("rp1",new Chessman(1,7,"rp1",pao));
        chessmen.put("rp2",new Chessman(7,7,"rp2",pao));
        chessmen.put("rb1",new Chessman(0,6,"rb1",bing));
        chessmen.put("rb2",new Chessman(2,6,"rb2",bing));
        chessmen.put("rb3",new Chessman(4,6,"rb3",bing));
        chessmen.put("rb4",new Chessman(6,6,"rb4",bing));
        chessmen.put("rb5",new Chessman(8,6,"rb5",bing));
    }
    private void initialPosition(){
        position[0][0] = chessmen.get("bj1");
        position[1][0] = chessmen.get("bm1");
        position[2][0] = chessmen.get("bx1");
        position[3][0] = chessmen.get("bs1");
        position[4][0] = chessmen.get("bz1");
        position[5][0] = chessmen.get("bs2");
        position[6][0] = chessmen.get("bx2");
        position[7][0] = chessmen.get("bm2");
        position[8][0] = chessmen.get("bj2");
        position[1][2] = chessmen.get("bp1");
        position[7][2] = chessmen.get("bp2");
        position[0][3] = chessmen.get("bb1");
        position[2][3] = chessmen.get("bb2");
        position[4][3] = chessmen.get("bb3");
        position[6][3] = chessmen.get("bb4");
        position[8][3] = chessmen.get("bb5");

        position[0][9] = chessmen.get("rj1");
        position[1][9] = chessmen.get("rm1");
        position[2][9] = chessmen.get("rx1");
        position[3][9] = chessmen.get("rs1");
        position[4][9] = chessmen.get("rz1");
        position[5][9] = chessmen.get("rs2");
        position[6][9] = chessmen.get("rx2");
        position[7][9] = chessmen.get("rm2");
        position[8][9] = chessmen.get("rj2");
        position[1][7] = chessmen.get("rp1");
        position[7][7] = chessmen.get("rp2");
        position[0][6] = chessmen.get("rb1");
        position[2][6] = chessmen.get("rb2");
        position[4][6] = chessmen.get("rb3");
        position[6][6] = chessmen.get("rb4");
        position[8][6] = chessmen.get("rb5");
    }
    public void init(){
        chessmen = new HashMap<String, Chessman>();
        initialChessmen();
        initialPosition();
    }

    public Chessman getChessman(Coordinate p){
        return position[p.x][p.y];
    }
    public Chessman getChessman(int x, int y){
        return position[x][y];
    }
    public void deleteChessman(Coordinate p){
        chessmen.remove(position[p.x][p.y].key);
        position[p.x][p.y] = null;

    }
    public void moveChessman(Chessman chessman, Coordinate p){
        position[chessman.position.x][chessman.position.y] = null;
        chessman.position = p;
        position[p.x][p.y] = chessman;
    }

}

