package com.sdm.units.chessgame.gui.board.view;

import java.awt.BorderLayout;
import java.util.EnumMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClockView;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEvent;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEventListener;

public class ChessGameFrame extends JFrame implements ChessGameView, ChessGameEventListener {

    private final EnumMap<ChessPieceColor, ChessClockView> clocks;
    private final ChessboardView chessboardView;
    private final MoveHistoryView moveHistoryArea;

    public ChessGameFrame(String name, ChessboardView chessboardView, EnumMap<ChessPieceColor, ChessClockView> clocks, MoveHistoryView moveHistoryArea, GameToolbarView toolBar) {
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        this.chessboardView = chessboardView;
        this.clocks = clocks;
        this.moveHistoryArea = moveHistoryArea;

        setContentPane(buildMainPanel(toolBar));
    }

    private JPanel buildMainPanel(GameToolbarView toolBar) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(toolBar.asComponent(), BorderLayout.NORTH);
        mainPanel.add(buildVerticalSplit(), BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(moveHistoryArea.asComponent()), BorderLayout.SOUTH);
        return mainPanel;
    }

    private JSplitPane buildVerticalSplit() {
        JSplitPane verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            new ClocksPanel(clocks),
            new ChessboardWithCoordinatesPanel(chessboardView)
        );
        verticalSplit.setOneTouchExpandable(false);
        return verticalSplit;
    }

    public String getMoveHistoryText() {
        return moveHistoryArea.getText();
    }

    @Override
    public void onChessGameEvent(ChessGameEvent event) {
        event.handleOn(this);
    }

    @Override
    public void display() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void setChessboard(Chessboard board) {
        chessboardView.renderChessboardState(board);
        this.pack();
    }

    @Override
    public void displayMessage(String messageToDisplay) {
        JOptionPane.showMessageDialog(this, messageToDisplay);
    }

    @Override
    public void recordMessage(String messageToRecord) {
        moveHistoryArea.append(messageToRecord);
    }

    @Override
    public void startClock(ChessPieceColor color) {
        clocks.get(color).getClock().start();
    }

    @Override
    public void stopClock(ChessPieceColor color) {
        clocks.get(color).getClock().stop();
    }

    @Override
    public void close() {
        this.dispose();
    }

    @Override
    public void reset() {
        moveHistoryArea.clear();
        clocks.values().forEach(ChessClockView::reset);
    }
}