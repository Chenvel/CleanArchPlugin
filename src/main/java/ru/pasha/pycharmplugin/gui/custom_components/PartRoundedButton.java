package ru.pasha.pycharmplugin.gui.custom_components;

import java.awt.*;

public class PartRoundedButton extends RoundedButton {

    private final PartButtonDirections roundedPart;

    public PartRoundedButton(String label, PartButtonDirections roundedPart) {
        super(label);
        this.roundedPart = roundedPart;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(60, 63, 65));
        if (roundedPart.equals(PartButtonDirections.TOP_RIGHT)) {
            g.fillRect(0, 0, getWidth() / 2, getHeight());
            g.fillRect(0, getHeight() / 2, getWidth(), getHeight() / 2);
        } else if (roundedPart.equals(PartButtonDirections.BOTTOM_LEFT)) {
            g.fillRect(0, 0, getWidth(), getHeight() / 2);
            g.fillRect(getWidth() / 2, 0, getWidth() / 2, getHeight());
        } else if (roundedPart.equals(PartButtonDirections.TOP_LEFT)) {
            g.fillRect(getWidth() / 2, 0, getWidth() / 2, getHeight());
            g.fillRect(0, getHeight() / 2, getWidth(), getHeight() / 2);
        } else if (roundedPart.equals(PartButtonDirections.BOTTOM_RIGHT)) {
            g.fillRect(0, 0, getWidth(), getHeight() / 2);
            g.fillRect(0, 0, getWidth() / 2, getHeight());
        }
    }
}
