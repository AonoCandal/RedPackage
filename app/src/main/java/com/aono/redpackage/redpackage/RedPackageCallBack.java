package com.aono.redpackage.redpackage;

import android.animation.ObjectAnimator;

/**
 * Created by Aono on 2018/1/17.
 */

public interface RedPackageCallBack {

	/**
	 * 有延迟打开
	 * @param objectAnimatorScale
	 */
	void clickOpenRedPkg(ObjectAnimator objectAnimatorScale);

	/**
	 * 无延迟打开
	 * @param objectAnimatorScale
	 */
	void clickOpenRedPkgNoDelay(ObjectAnimator objectAnimatorScale);
}
