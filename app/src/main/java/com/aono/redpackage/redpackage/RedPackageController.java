package com.aono.redpackage.redpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.aono.redpackage.R;
import com.aono.redpackage.SimpleAnimatorListener;

/**
 * Created by Aono on 2018/1/16.
 * 红包控制
 */

public class RedPackageController implements IRedPackageController {

	private final RedPackageCallBack callback;
	private boolean isOpen;
	private ImageView mTvSmallRedPkg;
	private float x;
	private float y;
	private View redPackage;
	private View mBgShadow;
	private View mRedPackageShadow;

	private static final int SCALE_TIME = 500;
	private static final int ROTATION_TIME = 500;
	private static final int TRANS_TIME = 500;
	private ImageButton mIvClickRedPackage;
	private ImageView mIvCloseRedPackage;


	public RedPackageController(RedPackageCallBack callBack) {
		this.callback = callBack;
	}

	@Override
	public void initRedPkg(Context context, ViewGroup group) {
		isOpen = false;
		group.addView(LayoutInflater.from(context).inflate(R.layout.content_red_package, group, false));
		redPackage = group.findViewById(R.id.view_red_pkg);
		mTvSmallRedPkg = group.findViewById(R.id.tv_small_red_pkg);
		mBgShadow = group.findViewById(R.id.bg_shadow);
		mRedPackageShadow = group.findViewById(R.id.red_package_shadow);
		mIvClickRedPackage = redPackage.findViewById(R.id.iv_click_red_package);
		mIvCloseRedPackage = redPackage.findViewById(R.id.iv_close_red_package);



		//计算位移距离
		mTvSmallRedPkg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			@Override
			public void onGlobalLayout() {
				mTvSmallRedPkg.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				x = mTvSmallRedPkg.getX() + mTvSmallRedPkg.getWidth() / 2 - (redPackage.getX() + redPackage.getWidth() / 2);
				y = mTvSmallRedPkg.getY() + mTvSmallRedPkg.getHeight() / 2 - (redPackage.getY() + redPackage.getHeight() / 2);
			}
		});

		//点击小红包
		mTvSmallRedPkg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showRedPkg();
				mTvSmallRedPkg.setVisibility(View.INVISIBLE);
				AnimatorSet animatorSet = new AnimatorSet();
				ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(redPackage, View.SCALE_X, 0.1f, 1f);
				ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(redPackage, View.SCALE_Y, 0.1f, 1f);
				ObjectAnimator animatorTransX = ObjectAnimator.ofFloat(redPackage, View.TRANSLATION_X, x, 0f);
				ObjectAnimator animatorTransY = ObjectAnimator.ofFloat(redPackage, View.TRANSLATION_Y, y, 0f);

				animatorSet.playTogether(animatorScaleX, animatorScaleY, animatorTransX, animatorTransY);
				animatorSet.setDuration(SCALE_TIME);
				animatorSet.start();
			}
		});

		//点击拆红包
		mIvClickRedPackage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (Build.MANUFACTURER.equals("HUAWEI")){
					ObjectAnimator objectAnimatorScale = ObjectAnimator.ofFloat(view, View.SCALE_X,
							1f, 0.9f, 0.8f, 0.7f, 0.6f, 0.5f, 0.4f, 0.3f, 0.2f, 0.1f, 0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1f);
					objectAnimatorScale.setDuration(ROTATION_TIME);
					objectAnimatorScale.setRepeatCount(ValueAnimator.INFINITE);
					objectAnimatorScale.start();
					objectAnimatorScale.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							openRedPkg();
						}
					});
					callback.clickOpenRedPkg(objectAnimatorScale);
				}else {
					ObjectAnimator objectAnimatorScale = ObjectAnimator.ofFloat(view, View.ROTATION_Y, 0f, 360f);
					objectAnimatorScale.setDuration(ROTATION_TIME);
					objectAnimatorScale.setRepeatCount(ValueAnimator.INFINITE);
					objectAnimatorScale.start();
					objectAnimatorScale.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							openRedPkg();
						}
					});
					callback.clickOpenRedPkg(objectAnimatorScale);
				}
			}
		});

		//点击关闭红包
		mIvCloseRedPackage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isOpen) {
					hideRedPkg();
				} else {
					AnimatorSet animatorSet = new AnimatorSet();
					ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(redPackage, View.SCALE_X, 1f, 0.1f);
					ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(redPackage, View.SCALE_Y, 1f, 0.1f);
					ObjectAnimator animatorTransX = ObjectAnimator.ofFloat(redPackage, View.TRANSLATION_X, 0f, x);
					ObjectAnimator animatorTransY = ObjectAnimator.ofFloat(redPackage, View.TRANSLATION_Y, 0f, y);

					animatorSet.playTogether(animatorScaleX, animatorScaleY, animatorTransX, animatorTransY);
					animatorSet.setDuration(SCALE_TIME);
					animatorSet.start();
					animatorSet.addListener(new SimpleAnimatorListener() {
						@Override
						public void onAnimationEnd(Animator animation) {
							hideRedPkg();
							mTvSmallRedPkg.setVisibility(View.VISIBLE);
							RotateAnimation objectAnimatorRotation = new RotateAnimation(10, -10, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
							objectAnimatorRotation.setDuration(100);
							objectAnimatorRotation.setRepeatMode(Animation.REVERSE);
							objectAnimatorRotation.setRepeatCount(3);
							mTvSmallRedPkg.startAnimation(objectAnimatorRotation);
						}
					});
				}
			}
		});
	}

	@Override
	public void showRedPkg() {
		redPackage.setVisibility(View.VISIBLE);
		mBgShadow.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideRedPkg() {
		redPackage.setVisibility(View.GONE);
		mBgShadow.setVisibility(View.GONE);
	}

	@Override
	public void openRedPkg() {
		isOpen = true;
		AnimatorSet animatorSet = new AnimatorSet();
		ObjectAnimator shadowTransX = ObjectAnimator.ofFloat(mRedPackageShadow, View.TRANSLATION_Y, 0f, -1000);
		ObjectAnimator openTransY = ObjectAnimator.ofFloat(mIvClickRedPackage, View.TRANSLATION_Y, 0f, 1000);
		animatorSet.playTogether(shadowTransX, openTransY);
		animatorSet.setDuration(TRANS_TIME);
		animatorSet.start();
		animatorSet.addListener(new SimpleAnimatorListener(){
			@Override
			public void onAnimationEnd(Animator animation) {
				mRedPackageShadow.setVisibility(View.GONE);
				mIvClickRedPackage.setVisibility(View.GONE);
			}
		});
	}
}
