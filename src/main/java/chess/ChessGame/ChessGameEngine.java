package chess.ChessGame;

import chess.ChessBoard.*;
import chess.GameSound.BackgroundMusicPlay;
import chess.GameSound.SoundWavePlay;
import chess.GameView.GameView;

import javax.swing.*;


public class ChessGameEngine {

    private ChessBoard board;
    private Referee referee;
    private GameView view;
    private BackgroundMusicPlay music;
    private String turn = "r";

    public void init(){
        board = new ChessBoard();
        board.init();

        view = new GameView(board, this);
        view.init();

        referee = new Referee(board);
        referee.init();

        music = new BackgroundMusicPlay();
        music.start();
    }
    public void operation(Chessman chessman, Coordinate pos){
        if(chessman.key.charAt(0) != turn.charAt(0)){return;}
        System.out.println(chessman.type + ":" + chessman.position.x + "," + chessman.position.y
                + " ---> " + pos.x + "," + pos.y + " " + referee.judge(chessman, pos));

        switch (referee.judge(chessman, pos)){
            case eat:
                view.delete1Chessman(board.getChessman(pos).key);
                board.deleteChessman(pos);
                view.move1Chessman(chessman.key, pos);
                board.moveChessman(chessman, pos);
                if(turn.equals("r")){
                    turn = "b";
                }else{
                    turn = "r";
                }
                new SoundWavePlay("res/sound/eat.wav").start();
                break;
            case move:
                view.move1Chessman(chessman.key, pos);
                board.moveChessman(chessman, pos);
                if(turn.equals("r")){
                    turn = "b";
                }else{
                    turn = "r";
                }
                new SoundWavePlay("res/sound/move.wav").start();
                break;
            case nop:
                new SoundWavePlay("res/sound/nop.wav").start();
                break;
            default:
        }

        switch (endGame()){
            case "x": return;
            case "r":
                new SoundWavePlay("res/sound/complete.wav").start();
                JOptionPane.showMessageDialog(null, "The red won!",
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);

                return;
            case "b":
                new SoundWavePlay("res/sound/complete.wav").start();
                JOptionPane.showMessageDialog(null, "The black won!",
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);

                return;
            default:
        }
    }
    public String endGame(){
        if(board.chessmen.get("rz1") == null)
            return "b";
        if(board.chessmen.get("bz1") == null)
            return "r";
        else
            return "x";
    }
    public String getTurn(){return turn;}

}
