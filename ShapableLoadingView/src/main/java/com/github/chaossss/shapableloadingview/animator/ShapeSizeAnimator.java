package com.github.chaossss.shapableloadingview.animator;

import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.github.chaossss.shapableloadingview.factory.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaossss on 2016/2/5.
 */
public class ShapeSizeAnimator extends AbstractShapeAnimator {
    private int duration;
    private float minShapeSize;
    private float maxShapeSize;
    private List<ValueAnimator> animators;

    public ShapeSizeAnimator(int duration, float minShapeSize, float maxShapeSize) {
        this.duration = duration;
        this.minShapeSize = minShapeSize;
        this.maxShapeSize = maxShapeSize;
        init();
    }

    private void init() {
        animators = new ArrayList<>();
    }

    @Override
    public void start() {
        animators.clear();
        for (int i = 0; i < shapes.size(); i++) {
            createShapeAnimatorAndStart(shapes.get(i), 300 * i);
        }
    }

    @Override
    public void stop() {
        for (ValueAnimator animator : animators) {
            animator.cancel();
        }
    }

    @Override
    public void restart() {
        stop();
        start();
    }

    private void createShapeAnimatorAndStart(Shape shape, int startDelay) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ShapeUpdateListener(shape));
        valueAnimator.setFloatValues(minShapeSize, maxShapeSize);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setStartDelay(startDelay);
        valueAnimator.setDuration(duration);
        animators.add(valueAnimator);
        valueAnimator.start();
    }

    private class ShapeUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        private Shape shape;

        public ShapeUpdateListener(Shape shape) {
            this.shape = shape;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (float) animation.getAnimatedValue();
            shape.setSize((int) value);
        }
    }
}
