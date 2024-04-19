package com.xiaopeng.instrument.animator;
/* loaded from: classes.dex */
public enum AnimatorType {
    SlideInDown(SlideInDownAnimator.class),
    SlideInNormal(SlideInNormalAnimator.class),
    HaloAnimator(HaloAnimator.class),
    TransitionGear(TransitionGearAnimator.class),
    SlideOutScale(SlideOutScaleAnimator.class),
    FrgChangeLeft(FrgChangeLeftAnimator.class),
    FrgChangeRight(FrgChangeRightAnimator.class),
    BreathAnimator(BreathAnimator.class),
    PauseBreathAnimator(PauseBreathAnimator.class);
    
    private Class animatorClazz;

    AnimatorType(Class cls) {
        this.animatorClazz = cls;
    }

    public BaseViewAnimator initAnimator() {
        try {
            return (BaseViewAnimator) this.animatorClazz.newInstance();
        } catch (Exception unused) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
