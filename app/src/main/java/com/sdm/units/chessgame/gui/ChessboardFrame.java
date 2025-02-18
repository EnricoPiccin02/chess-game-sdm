package com.sdm.units.chessgame.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardRank;
import com.sdm.units.chessgame.pieces.ChessPiece;
import com.sdm.units.chessgame.pieces.Pawn;

public class ChessboardFrame extends JFrame {

    static final String gapList[] = {"0", "10", "15", "20"};
    final static int maxGap = 20;
    JComboBox horGapComboBox;
    JComboBox verGapComboBox;
    JButton applyButton = new JButton("Click me!");
    GridLayout chessboardGridLayout = new GridLayout(0,8);
     
    public ChessboardFrame(String name) {
        super(name);
        setResizable(false);
    }
     
    public void initGaps() {
        horGapComboBox = new JComboBox(gapList);
        verGapComboBox = new JComboBox(gapList);
    }
     
    public void addComponentsToPane(final Container pane) {
        initGaps();
        final JPanel compsToExperiment = new JPanel() {
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                for(int r = 0; r < 8; r++){
                    for(int c = 0; c < 8; c++){
                        g.setColor((r + c) % 2 != 0 ? Color.BLACK : Color.WHITE);
                        g.fillRect(r * 64, c * 64, 64, 64);
                    }
                }
            }
        };

        compsToExperiment.setLayout(chessboardGridLayout);
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(8,8));
         
        compsToExperiment.setPreferredSize(new Dimension(8 * 64, 8 * 64));

        chessboardGridLayout.setHgap(10);
        chessboardGridLayout.setVgap(10);

        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                ChessPiece cp = new Pawn((r + c) % 2 == 0 ? ChessPieceColor.BLACK : ChessPieceColor.WHITE);
                compsToExperiment.add(cp.drawChessPiece());
            }
        }

        compsToExperiment.repaint();

        chessboardGridLayout.layoutContainer(compsToExperiment);

        //Add controls to set up horizontal and vertical gaps
        controls.add(new JLabel("Horizontal gap:"));
        controls.add(new JLabel("Vertical gap:"));
        controls.add(new JLabel(" "));
        controls.add(horGapComboBox);
        controls.add(verGapComboBox);
        controls.add(applyButton);
         
        //Process the Apply gaps button press
        applyButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Get the horizontal gap value
                String horGap = (String)horGapComboBox.getSelectedItem();
                //Get the vertical gap value
                String verGap = (String)verGapComboBox.getSelectedItem();
                //Set up the horizontal gap value
                chessboardGridLayout.setHgap(Integer.parseInt(horGap));
                //Set up the vertical gap value
                chessboardGridLayout.setVgap(Integer.parseInt(verGap));
                //Set up the layout of the buttons
                chessboardGridLayout.layoutContainer(compsToExperiment);
            }
        });
        pane.add(compsToExperiment, BorderLayout.NORTH);
        pane.add(new JSeparator(), BorderLayout.CENTER);
        pane.add(controls, BorderLayout.SOUTH);
    }
     
    /**
     * Create the GUI and show it.  For thread safety,
     * this method is invoked from the
     * event dispatch thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        ChessboardFrame frame = new ChessboardFrame("Chessboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}