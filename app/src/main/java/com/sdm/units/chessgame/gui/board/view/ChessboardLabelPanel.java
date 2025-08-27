package com.sdm.units.chessgame.gui.board.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public abstract class ChessboardLabelPanel<T extends Enum<T>> extends JPanel {

    private static final int LABEL_RATIO = 4;

    private final boolean vertical;
    private final int panelWidth;
    private final int panelHeight;

    protected ChessboardLabelPanel(T[] values, boolean vertical, int squareWidth, int squareHeight, Function<T, String> labelProvider) {
        this.vertical = vertical;
        this.panelWidth = vertical ? squareHeight / LABEL_RATIO : values.length * squareWidth;
        this.panelHeight = vertical ? values.length * squareHeight : squareHeight / LABEL_RATIO;

        setPreferredSize(new Dimension(panelWidth, panelHeight));

        setLayout(vertical 
            ? new GridLayout(values.length, 1) 
            : new GridLayout(1, values.length)
        );

        addLabels(values, labelProvider);
    }

    protected void addPadding() {
        if (vertical) {
            setBorder(BorderFactory.createEmptyBorder(panelWidth, 0, panelWidth, 0));
        } else {
            setBorder(BorderFactory.createEmptyBorder(0, panelHeight, 0, panelHeight));
        }
    }

    protected void addLabels(T[] values, Function<T, String> labelProvider) {
        removeAll();

        T[] orderedValues = vertical ? Arrays.copyOf(values, values.length) : values;
        
        if (vertical) {
            Collections.reverse(Arrays.asList(orderedValues));
        }
        
        for (T value : orderedValues) {
            add(new JLabel(labelProvider.apply(value), SwingConstants.CENTER));
        }
    }
}