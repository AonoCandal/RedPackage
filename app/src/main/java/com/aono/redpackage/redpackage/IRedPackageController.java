package com.aono.redpackage.redpackage;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by Aono on 2018/1/16.
 */

public interface IRedPackageController {

	/**
	 * 初始化红包
	 * @param context
	 * @param group
	 */
	void initRedPkg(Context context, ViewGroup group);

	/**
	 * 显示红包
	 */
	void showRedPkg();

	/**
	 * 隐藏红包
	 */
	void hideRedPkg();

	/**
	 * 拆红包的动态
	 */
	void openRedPkg();
}
