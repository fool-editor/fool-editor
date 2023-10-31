package com.ooqn.assist.modules.jme;


import com.jme3.post.SceneProcessor;
import com.jme3.texture.FrameBuffer;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * The implementation of the {@link SceneProcessor} for transferring content between jME and ImageView.
 */
public class ImageViewFrameTransferSceneProcessor extends AbstractFrameTransferSceneProcessor<ImageView> {

    @Override
    protected int getDestinationHeight() {
        return (int) getDestination().getFitHeight();
    }

    @Override
    protected int getDestinationWidth() {
        return (int) getDestination().getFitWidth();
    }

    @Override
    protected boolean isPreserveRatio() {
        return getDestination().isPreserveRatio();
    }

    @Override
    protected void bindDestination(
            SimpleJfxApplication application,
            ImageView destination,
            Node inputNode
    ) {
        super.bindDestination(application, destination, inputNode);
        destination.setScaleY(-1.0);
    }

    @Override
    protected void bindListeners() {
        ImageView destination = getDestination();
        destination.fitWidthProperty().addListener(widthListener);
        destination.fitHeightProperty().addListener(heightListener);
        destination.preserveRatioProperty().addListener(rationListener);
        super.bindListeners();
    }

    @Override
    protected void unbindDestination() {
        ImageView destination = getDestination();
        destination.fitWidthProperty().removeListener(widthListener);
        destination.fitHeightProperty().removeListener(heightListener);
        destination.preserveRatioProperty().removeListener(rationListener);
        super.unbindDestination();
    }

    @Override
    protected FrameTransfer createFrameTransfer(FrameBuffer frameBuffer, int width, int height) {
        return new ImageFrameTransfer(getDestination(), getTransferMode(), isMain() ? null : frameBuffer, width, height);
    }
}