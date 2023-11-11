package com.ooqn.modules.jme.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.profile.AppProfiler;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.texture.FrameBuffer;
import com.jme3.util.BufferUtils;
import com.ooqn.modules.jme.JmeOffscreenSurfaceContext;
import com.ooqn.modules.jme.post.DefSceneProcessor;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * 视图传输
 */
public class JmeViewAppState extends AbstractAppState {

    private SceneProcessor sceneProcessor;
    private ViewPort viewPort;
    private Application application;

    private Canvas canvas;
    private PixelFormat<ByteBuffer> pixelFormat;

    public JmeViewAppState() {
        canvas = new Canvas();
        pixelFormat = PixelFormat.getByteBgraInstance();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.application = app;
        setViewPort();
        JmeOffscreenSurfaceContext context = (JmeOffscreenSurfaceContext) application.getContext();
        context.getMouseInput().bind(canvas);
        context.getKeyInput().bind(canvas);
        SimpleApplication simpleApplication = (SimpleApplication) app;
        canvas.heightProperty().addListener((observable, oldValue, newValue) -> {
            app.enqueue(() -> {
                simpleApplication.reshape(canvas.widthProperty().intValue(), newValue.intValue());
            });
        });
        canvas.widthProperty().addListener((observable, oldValue, newValue) -> {
            app.enqueue(() -> {
                simpleApplication.reshape(newValue.intValue(), canvas.heightProperty().intValue());
            });
        });
        simpleApplication.reshape(canvas.widthProperty().intValue(), canvas.heightProperty().intValue());
    }

    @Override
    public void update(float tpf) {
        setViewPort();
    }

    private void setViewPort() {
        List<ViewPort> postViews = application.getRenderManager().getPostViews();
        ViewPort lastViewPort = postViews.get(postViews.size() - 1);
        if (sceneProcessor == null) {
            this.viewPort = lastViewPort;
            this.sceneProcessor = new SceneProcessor();
            this.viewPort.addProcessor(this.sceneProcessor);
        } else {
            if (lastViewPort == this.viewPort) {
                return;
            }
            viewPort.removeProcessor(sceneProcessor);
            this.viewPort = lastViewPort;
            this.sceneProcessor = new SceneProcessor();
            this.viewPort.addProcessor(this.sceneProcessor);
        }
    }

    private class SceneProcessor implements DefSceneProcessor {
        private ByteBuffer outBuf;
        private int width, height;

        /**
         * 同步间隔时间
         * 1s 同步 30次
         * 60fps
         */
        private long time = 1000 / 60;
        /**
         * 上一次同步时间
         */
        private long lastSyncTime = System.currentTimeMillis();
        private boolean initialized;

        private RenderManager renderManager;
        private Renderer renderer;

        @Override
        public void initialize(RenderManager rm, ViewPort vp) {
            reshape(vp, vp.getCamera().getWidth(), vp.getCamera().getHeight());
            initialized = true;
            this.renderManager = rm;
            this.renderer = rm.getRenderer();
        }

        @Override
        public boolean isInitialized() {
            return initialized;
        }

        @Override
        public void reshape(ViewPort vp, int w, int h) {
            synchronized (this) {
                outBuf = BufferUtils.createByteBuffer(w * h * 4);
                width = w;
                height = h;
                JmeOffscreenSurfaceContext context = (JmeOffscreenSurfaceContext) application.getContext();
                context.setHeight(height);
                context.setWidth(width);
            }
        }

        @Override
        public void postFrame(FrameBuffer out) {
            long now = System.currentTimeMillis();
            if (now - lastSyncTime > time) {
                synchronized (this) {
                    lastSyncTime = now;
                    try {
                        int width = this.width;
                        int height = this.height;
                        Camera curCamera = renderManager.getCurrentCamera();
                        int viewX = (int) (curCamera.getViewPortLeft() * curCamera.getWidth());
                        int viewY = (int) (curCamera.getViewPortBottom() * curCamera.getHeight());
                        int viewWidth = (int) ((curCamera.getViewPortRight() - curCamera.getViewPortLeft()) * curCamera.getWidth());
                        int viewHeight = (int) ((curCamera.getViewPortTop() - curCamera.getViewPortBottom()) * curCamera.getHeight());

                        renderer.setViewPort(0, 0, width, height);
                        renderer.readFrameBuffer(out, outBuf);
                        renderer.setViewPort(viewX, viewY, viewWidth, viewHeight);

                        byte[] imageByteBuffer = new byte[width * height * 4];
                        for (int y = 0; y < height; y++) {
                            for (int x = 0; x < width * 4; x += 4) {
                                int i = (height - 1 - y) * width * 4 + x;

                                byte r = outBuf.get(i);
                                byte g = outBuf.get(i + 1);
                                byte b = outBuf.get(i + 2);
                                byte a = outBuf.get(i + 3);

                                imageByteBuffer[y * width * 4 + x] = b;
                                imageByteBuffer[y * width * 4 + x + 1] = g;
                                imageByteBuffer[y * width * 4 + x + 2] = r;
                                imageByteBuffer[y * width * 4 + x + 3] = a;
                            }
                        }
//                        saveImage(imageByteBuffer, width, height);
//                    for (int i = 0, length = width * height * 4; i < length; i += 4) {
//                        byte r = outBuf.get(i);
//                        byte g = outBuf.get(i + 1);
//                        byte b = outBuf.get(i + 2);
//                        byte a = outBuf.get(i + 3);
//
//                        imageByteBuffer[i] = b;
//                        imageByteBuffer[i + 1] = g;
//                        imageByteBuffer[i + 2] = r;
//                        imageByteBuffer[i + 3] = a;
//                    }
                        Platform.runLater(() -> {
                            GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
                            PixelWriter pixelWriter = graphicsContext2D.getPixelWriter();
                            pixelWriter.setPixels(0, 0, width, height, pixelFormat, imageByteBuffer, 0, width * 4);
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }


        @Override
        public void cleanup() {
        }

        @Override
        public void setProfiler(AppProfiler profiler) {
        }

        @Override
        public void preFrame(float tpf) {
        }

        @Override
        public void postQueue(RenderQueue rq) {
        }

        private static void saveImage(byte[] imageByteBuffer, int width, int height) {
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

            // Copy the byte array data to the BufferedImage
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width * 4; x += 4) {
                    int i = y * width * 4 + x;

                    int b = imageByteBuffer[i] & 0xFF;
                    int g = imageByteBuffer[i + 1] & 0xFF;
                    int r = imageByteBuffer[i + 2] & 0xFF;
                    int a = imageByteBuffer[i + 3] & 0xFF;

                    int argb = (a << 24) | (r << 16) | (g << 8) | b;
                    bufferedImage.setRGB(x / 4, y, argb);
                }
            }

            // Save the BufferedImage to a file
            try {
                File outputfile = new File("data/file.png");
                ImageIO.write(bufferedImage, "png", outputfile);
                System.out.println("Image saved successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
