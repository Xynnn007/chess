package chess;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class chessGame extends JFrame{
    private ImageIcon background, select;
    private GamePanel listener = null;
    private Container ct;

    private void createGamePanel(){
        background = new ImageIcon("src/chess/picture/background.jpg");// 背景图片
        select = new ImageIcon("src/chess/picture/select.png");// 选择图片
        ct = this.getContentPane();
        this.setLayout(null);
        this.listener = new GamePanel(background.getImage(), select.getImage());
        listener.setBounds(0,0,1024,768);

        ct.add(listener);

        this.setSize(1024,799);
        this.setLocation(0,0);

        // 注册监听
        this.addMouseListener(listener);
        // this.addActionListener(listener);

        // 按钮用这个，this.addActionListener(listener);

        //this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("中国象棋-by Xynnn_");
        this.setResizable(false);
        this.setVisible(true);
    }
    public chessGame(){
        this.createGamePanel();
    }

}

class chessGameEngine{
    //该上方走了
    enum operationStatus{SELECTED, UNSELECTED}
    private operationStatus status = operationStatus.UNSELECTED;
    private chessman operationChessman = null;
    private boolean upTurn;
    private boolean endGame;
    private boolean upWin;

    private Vector<chessman> upChessman = new Vector<chessman>();
    private Vector<chessman> downChessman = new Vector<chessman>();
    private chessBoard chessBoard = new chessBoard();
    //某坐标是否有棋子
    private boolean checkAnyChessmanOnPoint(Point p){
        return chessBoard.getPosition(p.x,p.y);
    }
    //
    //判断別腿
    private boolean moveWithNoBar(chessman chessman, Point dst){
        if(!chessman.ableToMove(dst))
            return false;
        Point src = chessman.getPosition();
        switch (chessman.getType()){
            case HORSE:
                return chessBoard.horseWithNoBar(src,dst);
            case CAR:
                return chessBoard.inlineWithNoBar(src, dst);
            case GUN:
                return checkAnyChessmanOnPoint(dst) && chessBoard.inlineWithOneBar(src, dst) ||
                        !checkAnyChessmanOnPoint(dst) && chessBoard.inlineWithNoBar(src, dst);
            case ELEPHANT:
                return chessBoard.elephantWithNoBar(src, dst);
            default:
                return true;
        }
    }
    //判断某个位置有没有自己的棋子,并且返回对应的下标
    private int checkOwnChessmanOnPoint(player owner, Point p){
        switch (owner){
            case UP:
                for(int i = 0; i < upChessman.size(); i++){
                    if(upChessman.elementAt(i).getPosition().x == p.x && upChessman.elementAt(i).getPosition().y == p.y)
                        return i;
                }
                return -1;
            case DOWN:
                for(int i = 0; i < downChessman.size(); i++){
                    if(downChessman.elementAt(i).getPosition().x == p.x && downChessman.elementAt(i).getPosition().y == p.y)
                        return i;
                }
                return -1;
            default:
                return -1;
        }
    }
    private void initial_game() {
        //上方先走
        this.upTurn = false;

        //游戏结束
        this.endGame = false;

        //初始化双方棋子
        //上方
        this.upChessman.clear();
        this.upChessman.add(new chessman(chessmanType.CAR, player.UP, new Point(0,0),
                new ImageIcon("src/chess/picture/chessman/black_che.png").getImage()));
        this.upChessman.add(new chessman(chessmanType.CAR, player.UP, new Point(8,0),
                new ImageIcon("src/chess/picture/chessman/black_che.png").getImage()));

        this.upChessman.add(new chessman(chessmanType.HORSE, player.UP, new Point(1,0),
                new ImageIcon("src/chess/picture/chessman/black_ma.png").getImage()));
        this.upChessman.add(new chessman(chessmanType.HORSE, player.UP, new Point(7,0),
                new ImageIcon("src/chess/picture/chessman/black_ma.png").getImage()));
        this.upChessman.add(new chessman(chessmanType.ELEPHANT, player.UP,new Point(2,0),
                new ImageIcon("src/chess/picture/chessman/black_xiang.png").getImage()));
        this.upChessman.add(new chessman(chessmanType.ELEPHANT, player.UP,new Point(6,0),
                new ImageIcon("src/chess/picture/chessman/black_xiang.png").getImage()));
        this.upChessman.add(new chessman(chessmanType.GUARD, player.UP,new Point(3,0),
                new ImageIcon("src/chess/picture/chessman/black_shi.png").getImage()));
        this.upChessman.add(new chessman(chessmanType.GUARD, player.UP,new Point(5,0),
                new ImageIcon("src/chess/picture/chessman/black_shi.png").getImage()));
        this.upChessman.add(new chessman(chessmanType.BOSS, player.UP,new Point(4,0),
                new ImageIcon("src/chess/picture/chessman/black_shuai.png").getImage()));
        this.upChessman.add(new chessman(chessmanType.GUN, player.UP,new Point(1,2),
                new ImageIcon("src/chess/picture/chessman/black_pao.png").getImage()));
        this.upChessman.add(new chessman(chessmanType.GUN, player.UP, new Point(7,2),
                new ImageIcon("src/chess/picture/chessman/black_pao.png").getImage()));
        this.upChessman.add(new chessman(chessmanType.SOLDIER, player.UP, new Point(0,3),
                new ImageIcon("src/chess/picture/chessman/black_bing.png").getImage()));
        this.upChessman.add(new chessman(chessmanType.SOLDIER, player.UP, new Point(2,3),
                new ImageIcon("src/chess/picture/chessman/black_bing.png").getImage()));
        this.upChessman.add(new chessman(chessmanType.SOLDIER, player.UP, new Point(4,3),
                new ImageIcon("src/chess/picture/chessman/black_bing.png").getImage()));
        this.upChessman.add(new chessman(chessmanType.SOLDIER, player.UP,new Point(6,3),
                new ImageIcon("src/chess/picture/chessman/black_bing.png").getImage()));
        this.upChessman.add(new chessman(chessmanType.SOLDIER, player.UP, new Point(8,3),
                new ImageIcon("src/chess/picture/chessman/black_bing.png").getImage()));
        //下方
        this.downChessman.clear();
        this.downChessman.add(new chessman(chessmanType.CAR, player.UP,new Point(0,9),
                new ImageIcon("src/chess/picture/chessman/red_che.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.CAR, player.UP,new Point(8,9),
                new ImageIcon("src/chess/picture/chessman/red_che.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.HORSE, player.DOWN,new Point(1,9),
                new ImageIcon("src/chess/picture/chessman/red_ma.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.HORSE, player.DOWN,new Point(7,9),
                new ImageIcon("src/chess/picture/chessman/red_ma.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.ELEPHANT, player.DOWN,new Point(2,9),
                new ImageIcon("src/chess/picture/chessman/red_xiang.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.ELEPHANT, player.DOWN,new Point(6,9),
                new ImageIcon("src/chess/picture/chessman/red_xiang.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.GUARD, player.DOWN,new Point(3,9),
                new ImageIcon("src/chess/picture/chessman/red_shi.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.GUARD, player.DOWN,new Point(5,9),
                new ImageIcon("src/chess/picture/chessman/red_shi.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.BOSS, player.DOWN,new Point(4,9),
                new ImageIcon("src/chess/picture/chessman/red_shuai.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.GUN, player.DOWN,new Point(1,7),
                new ImageIcon("src/chess/picture/chessman/red_pao.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.GUN, player.DOWN,new Point(7,7),
                new ImageIcon("src/chess/picture/chessman/red_pao.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.SOLDIER, player.DOWN,new Point(0,6),
                new ImageIcon("src/chess/picture/chessman/red_bing.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.SOLDIER, player.DOWN,new Point(2,6),
                new ImageIcon("src/chess/picture/chessman/red_bing.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.SOLDIER, player.DOWN,new Point(4,6),
                new ImageIcon("src/chess/picture/chessman/red_bing.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.SOLDIER, player.DOWN,new Point(6,6),
                new ImageIcon("src/chess/picture/chessman/red_bing.png").getImage()));
        this.downChessman.add(new chessman(chessmanType.SOLDIER, player.DOWN,new Point(8,6),
                new ImageIcon("src/chess/picture/chessman/red_bing.png").getImage()));

        //初始化棋盘统计
        this.chessBoard.initial();
    }
    private void gameStart(){
        System.out.println("游戏开始！");
    }
    private boolean moveOnce(chessman chessman, Point dst){
        if(!moveWithNoBar(chessman,dst))return false;
        if(upTurn){
            if(checkOwnChessmanOnPoint(player.UP,dst)>=0)
                return false;
            int enemyChess = checkOwnChessmanOnPoint(player.DOWN, dst);
            if(enemyChess >= 0){
                if(downChessman.elementAt(enemyChess).getType() == chessmanType.BOSS){
                    endGame = true;
                    upWin = true;
                }
                downChessman.elementAt(enemyChess).setAlived();
                downChessman.remove(enemyChess);
            }
            chessBoard.clearPosition(chessman.getPosition());
            chessBoard.setPosition(dst);
            chessman.setPosition(dst);
            return true;
        }
        else{
            if(checkOwnChessmanOnPoint(player.DOWN,dst)>=0)
                return false;
            int enemyChess = checkOwnChessmanOnPoint(player.UP, dst);
            if(enemyChess >= 0){
                if(upChessman.elementAt(enemyChess).getType() == chessmanType.BOSS){
                    endGame = true;
                    upWin = false;
                }
                upChessman.elementAt(enemyChess).setAlived();
                upChessman.remove(enemyChess);
            }
            chessBoard.clearPosition(chessman.getPosition());
            chessBoard.setPosition(dst);
            chessman.setPosition(dst);
            return true;
        }
    }
    //初始化棋局
    private player getCurrentPlayer(){
        if(this.upTurn){
            return player.UP;
        }
        else{
            return player.DOWN;
        }
    }
    public chessGameEngine(){
        this.initial_game();
        this.gameStart();

    }
    public void operation(Point p){

        switch (status){
            case SELECTED:
                if(operationChessman.getPosition().x == p.x && operationChessman.getPosition().y == p.y){
                    operationChessman = null;
                    status = operationStatus.UNSELECTED;
                }
                else if(moveOnce(operationChessman,p)) {
                    System.out.println("move:"+operationChessman.getType() +
                            " to " + p);
                    status = operationStatus.UNSELECTED;
                    upTurn = !upTurn;
                    operationChessman = null;
                }
                break;
            case UNSELECTED:
                int selectedChess = checkOwnChessmanOnPoint(getCurrentPlayer(),p);
                System.out.println(selectedChess);
                if(selectedChess >= 0 ){
                    if(upTurn){
                        operationChessman = upChessman.elementAt(selectedChess);

                    }
                    else{
                        operationChessman = downChessman.elementAt(selectedChess);
                    }
                    status = operationStatus.SELECTED;
                    System.out.println("selected:"+operationChessman.getType()+" at "+operationChessman.getPosition());
                }
                break;
            default:
        }
    }
    public boolean isEndGame(){
        return this.endGame;
    }
    public boolean isUpWin(){
        return this.upWin;
    }
    public Vector<chessman> getUpChessman() {
        return upChessman;
    }
    public Vector<chessman> getDownChessman() {
        return downChessman;
    }
    public operationStatus getStatus(){
        return this.status;
    }
    public chessman getOperationChessman() {
        return operationChessman;
    }
}

class GamePanel extends JPanel implements java.awt.event.MouseListener {
    private chessGameEngine game = new chessGameEngine();
    private Image backgroundIm,selectIcon;
    private Vector<chessman> up = game.getUpChessman();
    private Vector<chessman> down = game.getDownChessman();
    public void paint(Graphics g) {
        super.paint(g);
        for(int i = 0 ; i < up.size(); i++){
            chessman tmp = up.elementAt(i);
            g.drawImage(tmp.getImg(),tmp.getPosition().x * 86 + 3,tmp.getPosition().y * 76 + 2,this);
        }
        for(int i = 0 ; i < down.size(); i++){
            chessman tmp = down.elementAt(i);
            g.drawImage(tmp.getImg(),tmp.getPosition().x * 86 + 3,tmp.getPosition().y * 76 + 2,this);
        }
        System.out.println(game.getStatus());
        if(game.getStatus() == chessGameEngine.operationStatus.SELECTED){
            chessman now = game.getOperationChessman();
            g.drawImage(selectIcon,now.getPosition().x * 86,now.getPosition().y * 76,76,76,this);
        }
    }
    public GamePanel(Image backgroundIm, Image selectIcon){
        this.backgroundIm = backgroundIm;
        this.selectIcon = selectIcon;
        this.setOpaque(true);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundIm, 0, 0, this.getWidth(), this.getHeight(), this);


    }
    @Override
    // 1.鼠标点击
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        Point p = new Point();
        int x = e.getX(),y = e.getY();
        if(x < 768 && y < 768) {
            p.x = (e.getX() - 3) / 85;
            p.y = (e.getY() - 3) / 76;

            game.operation(p);
            this.repaint();
            if (game.isEndGame()) {
                if (game.isUpWin()) {
                    JOptionPane.showMessageDialog(null, "黑方获胜！", "游戏结束", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "红方获胜！", "游戏结束", JOptionPane.INFORMATION_MESSAGE);
                }
            }


            //System.out.println(p.x + "," + p.y);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //do nothing
    }

}
