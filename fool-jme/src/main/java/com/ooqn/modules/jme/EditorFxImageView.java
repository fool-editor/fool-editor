package com.ooqn.modules.jme;

import javafx.scene.image.ImageView;

/**
 * The resizable image view.
 *
 * @author JavaSaBr
 * @author jayfella
 */
public class EditorFxImageView extends ImageView {

    private long resizeDelay = 50;


    public EditorFxImageView() {
    }

    @Override
    public double minHeight(double width) {
        return 64;
    }

    @Override
    public double maxHeight(double width) {
        return 1000;
    }

    @Override
    public double prefHeight(double width) {
        return minHeight(width);
    }

    @Override
    public double minWidth(double height) {
        return 64;
    }

    @Override
    public double maxWidth(double height) {
        return 10000;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        super.setFitWidth(width);
        super.setFitHeight(height);
    }

    /**
     * Returns the delay time of resizing the canvas.
     *
     * @return the delay time of resizing the canvas.
     */
    public long getResizeDelay() {
        return resizeDelay;
    }

    /**
     * Delays resizing the JME viewport and thus reducing the continual resizes when a window is resized.
     *
     * @param resizeDelay the time in ms to delay a window redraw.
     */
    public void setResizeDelay(long resizeDelay) {
        this.resizeDelay = resizeDelay;
    }

}
