package chess.GameView;

import chess.ChessBoard.ChessBoard;
import chess.ChessBoard.Chessman;
import chess.ChessBoard.Coordinate;
import chess.ChessGame.ChessGameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class GameView {
    private static final int VIEW_WIDTH = 1024, VIEW_HEIGHT = 768;
    private static final int X_INTERVAL =86,Y_INTERVAL =76;
    private static final int CHESS_WIDTH = 70, CHESS_HEIGHT = 70;

    private JFrame frame;
    private ChessGameEngine gameEngine;
    private ChessBoard board;
    private JLayeredPane pane;
    private HashMap<String, JLabel> chessmenMap = new HashMap<String, JLabel>();
    private String presentSelectedKey;
    private JLabel selectedIcon;
    private JLabel preSelectedIcon;
    public GameView(ChessBoard board, ChessGameEngine gameEngine){
        this.gameEngine = gameEngine;
        this.board = board;
    }

    public Coordinate view2Coordinate(int x, int y){
        if(x < 768 && y < 768)
            return new Coordinate((x - 3) / X_INTERVAL,(y - 3) / Y_INTERVAL);
        return null;

    }
    public Point coordinate2View(Coordinate p){
        return new Point(p.x * X_INTERVAL + 6, p.y * Y_INTERVAL + 6);
    }
    public void init(){
        frame = new JFrame("Chinese Chess - By Xynnn_");
        frame.setIconImage(new ImageIcon("res/picture/icon.png").getImage());

        frame.setSize(VIEW_WIDTH, VIEW_HEIGHT + 40);
        frame.setLocation(0,0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        pane = new JLayeredPane();
        pane.setSize(768,768);
        frame.add(pane);

        preSelectedIcon = new JLabel(new ImageIcon("res/picture/preSelect.png"));
        preSelectedIcon.setSize(73,73);
        preSelectedIcon.setVisible(false);
        selectedIcon = new JLabel(new ImageIcon("res/picture/select.png"));
        selectedIcon.setSize(73,73);
        selectedIcon.setVisible(false);

        pane.add(preSelectedIcon,1);
        pane.add(selectedIcon,1);


        JLabel backgroundPicture = new JLabel(new ImageIcon("res/picture/background.jpg"));
        backgroundPicture.setLocation(0,0);
        backgroundPicture.setSize(VIEW_WIDTH, VIEW_HEIGHT);

        pane.add(backgroundPicture,2);
        ChessBoardListener paneListener = new ChessBoardListener();
        pane.addMouseListener(paneListener);
        pane.addMouseMotionListener(paneListener);

        Map<String, Chessman> map = board.chessmen;
        for(Map.Entry<String, Chessman> it: map.entrySet()){
            ImageIcon img = new ImageIcon("res/picture/chessman/" +
                    it.getKey().substring(0, it.getKey().length() - 1)
                    + ".png");
            JLabel lab = new JLabel(img);
            lab.setSize(CHESS_WIDTH, CHESS_HEIGHT);
            lab.setLocation(coordinate2View(it.getValue().position));
            lab.addMouseListener(new ChessmanLabelListener(it.getKey()));
            chessmenMap.put(it.getKey(), lab);
            pane.add(lab,0);
            lab.setVisible(true);
        }
        frame.setVisible(true);
    }

    public void move1Chessman(String key, Coordinate pos){
        chessmenMap.get(key).setLocation(coordinate2View(pos));
    }

    public void delete1Chessman(String key){
        chessmenMap.get(key).setVisible(false);
        chessmenMap.remove(key);
    }

    class ChessmanLabelListener extends java.awt.event.MouseAdapter{
        private String key;

        public ChessmanLabelListener(String key){
            this.key = key;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            if(presentSelectedKey == null) {
                presentSelectedKey = this.key;
                selectedIcon.setLocation(chessmenMap.get(presentSelectedKey).getLocation());
                selectedIcon.setVisible(true);
            }
            else{
                selectedIcon.setVisible(false);
                gameEngine.operation(board.chessmen.get(presentSelectedKey), board.chessmen.get(this.key).position);
                chessmenMap.get(presentSelectedKey).setLocation(coordinate2View(board.chessmen.get(presentSelectedKey).position));

                presentSelectedKey = null;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e){
            preSelectedIcon.setLocation(chessmenMap.get(this.key).getLocation());
        }

    }

    class ChessBoardListener extends java.awt.event.MouseAdapter{

        @Override
        public void mouseMoved(MouseEvent e) {
            Coordinate p = view2Coordinate(e.getX(),e.getY());
            if(p != null) {
                preSelectedIcon.setLocation(coordinate2View(p));
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            preSelectedIcon.setVisible(true);
        }

        @Override
        public void mouseExited(MouseEvent e){
            //preSelectedIcon.setVisible(false);
        }

        @Override
        public void mouseClicked(MouseEvent e){
            if(e.getX() > 768)
                return;
            if(presentSelectedKey != null){
                gameEngine.operation(board.chessmen.get(presentSelectedKey), view2Coordinate(e.getX(),e.getY()));
                chessmenMap.get(presentSelectedKey).setLocation(coordinate2View(board.chessmen.get(presentSelectedKey).position));
                selectedIcon.setVisible(false);
                presentSelectedKey = null;
            }
        }
    }
}



