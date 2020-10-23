package com.coder_zt.xh_androidanimation.utils;

import com.coder_zt.xh_androidanimation.App;

public class UIHelper {
    /**
     * 将dp值转换为px值
     */
    public static int dp2px( float dpValue) {
        final float scale = App.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将px值转换为dp值
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue){
        final float scale = App.getContext().getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    public static float sp2px(float titleTextSize) {
        float scale = App.getContext().getResources().getDisplayMetrics().scaledDensity;
        return titleTextSize * scale + 0.5f;
    }
}
