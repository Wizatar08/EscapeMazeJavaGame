package com.wizatar08.escapemaze.visuals;

import com.wizatar08.escapemaze.helpers.Timer;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import static com.wizatar08.escapemaze.visuals.Drawer.LoadPNG;
import static com.wizatar08.escapemaze.visuals.Drawer.drawQuadTex;

public class AnimatedTex extends Tex {
    private final int imageHeight, totalFrames;
    private int frame;
    private boolean fade, playAnim;
    private float secondsBetweenFrames;
    private final Timer timer;
    private Texture texture;
    private String texturePath;
    private AnimationSettings animationManager;

    /**
     * Creates an animated texture with default values
     * @param textureName
     * @param imageHeight
     */
    public AnimatedTex(String textureName, int imageHeight) {
        this(textureName, imageHeight, 1);
    }

    /**
     * Creates a sudden transition animated texture where you can specify seconds between frames
     * @param textureName
     * @param imageHeight
     * @param secondsBetweenFrames
     */
    public AnimatedTex(String textureName, int imageHeight, float secondsBetweenFrames) {
        this(textureName, imageHeight, secondsBetweenFrames, false);
    }

    /**
     * Creates an animated texture with all customization options
     * @param textureName
     * @param imageHeight
     * @param secondsBetweenFrames
     * @param fade
     */
    public AnimatedTex(String textureName, int imageHeight, float secondsBetweenFrames, boolean fade) {
        this(textureName, imageHeight, secondsBetweenFrames, fade, AnimationSettings.LOOP_FOREVER);
    }

    public AnimatedTex(String textureName, int imageHeight, float secondsBetweenFrames, boolean fade, AnimationSettings animationSettings) {
        super(textureName);
        this.texture = LoadPNG(textureName);
        this.texturePath = textureName;
        this.animationManager = animationSettings;
        this.imageHeight = imageHeight;
        this.secondsBetweenFrames = secondsBetweenFrames;
        this.fade = fade;
        this.timer = new Timer(Timer.TimerModes.COUNT_DOWN, secondsBetweenFrames);
        this.totalFrames = (int) Math.floor(texture.getImageHeight() / imageHeight);
        if (!this.animationManager.backwards) {
            this.frame = 0;
        } else {
            this.frame = totalFrames - 1;
        }
        this.timer.unpause();
        this.playAnim = true;
    }

    public enum AnimationSettings {
        PLAY_ONCE(false, false),
        LOOP_FOREVER(true, false),
        PLAY_BACKWARDS_ONCE(false, true),
        PLAY_BACKWARDS_FOREVER(true, true);

        private boolean runAgain, backwards;

        AnimationSettings(boolean runAgain, boolean backwards) {
            this.runAgain = runAgain;
            this.backwards = backwards;
        }

        public boolean keepsRunning() {
            return runAgain;
        }
        public boolean runsBackwards() {
            return backwards;
        }
    }

    private int getNextFrameNum() {
        int f = frame;
        f++;
        if (f >= totalFrames) {
            f = 0;
        }
        return f;
    }

    private int getBackwardsFrameNum() {
        int f = frame;
        f--;
        if (f < 0) {
            f = totalFrames - 1;
        }
        return f;
    }

    public void goToFrame(int frameNumber) {
        if (frameNumber > 0 && frameNumber <= totalFrames) {
            frame = frameNumber - 1;
        }
    }

    public void startPlayingAnimation() {
        playAnim = true;
    }
    public void stopPlayingAnimation() {
        playAnim = false;
    }

    public void setAnimationSettings(AnimationSettings settings) {
        playAnim = true;
        animationManager = settings;
        animationManager.backwards = settings.backwards;
        animationManager.runAgain = settings.runAgain;
    }

    @Override
    public void draw(float x, float y, float customWidth, float leftX, float rightX, float topY, float bottomY, float angle, Color color) {
        float calculatedTopY = topY * (1.0f / totalFrames);
        float calculatedBottomY = bottomY * (1.0f / totalFrames);
        if (playAnim) {
            timer.update();
        }
        if (timer.getTotalSeconds() <= 0 || timer.getTotalSeconds() > 256) {
            timer.setTime(timer.getStartingSeconds());
            if (!animationManager.backwards) {
                frame = getNextFrameNum();
            } else {
                frame = getBackwardsFrameNum();
            }
        }
        drawQuadTex(texture, x, y, customWidth, imageHeight * (bottomY - topY), angle, calculatedTopY + ((1.0f / totalFrames) * frame - 1), calculatedBottomY + ((1.0f / totalFrames) * frame - 1), leftX, rightX, color.r, color.g, color.b, color.a);
        if (fade) {
            if (!animationManager.backwards) {
                drawQuadTex(texture, x, y, customWidth, imageHeight * (bottomY - topY), angle, calculatedTopY + ((1.0f / totalFrames) * getNextFrameNum()), calculatedBottomY + ((1.0f / totalFrames) * getNextFrameNum()), leftX, rightX, color.r, color.g, color.b, color.a * ((timer.getStartingSeconds() - timer.getTotalSeconds()) / timer.getStartingSeconds()));
            } else {
                drawQuadTex(texture, x, y, customWidth, imageHeight * (bottomY - topY), angle, calculatedTopY + ((1.0f / totalFrames) * getBackwardsFrameNum()), calculatedBottomY + ((1.0f / totalFrames) * getBackwardsFrameNum()), leftX, rightX, color.r, color.g, color.b, color.a * ((timer.getStartingSeconds() - timer.getTotalSeconds()) / timer.getStartingSeconds()));
            }
        }
        if (!animationManager.runAgain) {
            if ((frame + 1) >= totalFrames && !animationManager.backwards) {
                stopPlayingAnimation();
            } else if ((frame + 1) <= 1 && animationManager.backwards) {
                stopPlayingAnimation();
            }
            System.out.println(((frame + 1) <= 1 && animationManager.backwards));
        }
        //System.out.println(frame + ", " + animationManager + ", " + animationManager.runAgain + ", " + animationManager.backwards);
    }

    public int getImageHeight() {
        return imageHeight;
    }
    public float getSecondsBetweenFrames() {
        return secondsBetweenFrames;
    }
    public boolean isFading() {
        return fade;
    }
    public int getFrame() {
        return frame + 1;
    }
    public int getTotalFrames() {
        return totalFrames;
    }
    public boolean isPlaying() {
        return playAnim;
    }

    public AnimationSettings getSettings() {
        return animationManager;
    }
}
