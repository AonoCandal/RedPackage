package com.aono.redpackage;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;

public class FristActivity extends AppCompatActivity {

	private boolean isOpen;
	private ImageView mTvSmallRedPkg;
	private float x;
	private float y;
	private View view;
	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frist);
		view = findViewById(R.id.view_red_pkg);
		mTvSmallRedPkg = (ImageView) findViewById(R.id.tv_small_red_pkg);
		mTvSmallRedPkg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			@Override
			public void onGlobalLayout() {
				mTvSmallRedPkg.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				x = mTvSmallRedPkg.getX() + mTvSmallRedPkg.getWidth() / 2 - (view.getX() + view.getWidth() / 2);
				y = mTvSmallRedPkg.getY() + mTvSmallRedPkg.getHeight() / 2 - (view.getY() + view.getHeight() / 2);
			}
		});
		mTvSmallRedPkg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.show();
				mTvSmallRedPkg.setVisibility(View.INVISIBLE);
				AnimatorSet animatorSet = new AnimatorSet();
				ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.1f, 1f);
				ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.1f, 1f);
				ObjectAnimator animatorTransX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, x, 0f);
				ObjectAnimator animatorTransY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, y, 0f);

				animatorSet.playTogether(animatorScaleX, animatorScaleY, animatorTransX, animatorTransY);
				animatorSet.setDuration(700);
				animatorSet.start();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		super.onResume();
		isOpen = false;
		view = getLayoutInflater().inflate(R.layout.red_package, null, false);
		final ImageButton mIvClickRedPackage = view.findViewById(R.id.iv_click_red_package);
		final ImageView mIvCloseRedPackage = view.findViewById(R.id.iv_close_red_package);

		dialog = new AlertDialog.Builder(this, R.style.DefaultLoadingAlertDialogStyle)
				.setView(view)
				.setCancelable(true)
				.create();
		dialog.show();
		mIvClickRedPackage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				isOpen = true;
				ObjectAnimator objectAnimatorScale = ObjectAnimator.ofFloat(view, "rotationY", 0f, 360f);
				objectAnimatorScale.setDuration(500);
				objectAnimatorScale.setRepeatCount(3);
				objectAnimatorScale.start();
				objectAnimatorScale.addListener(new SimpleAnimatorListener() {
					@Override
					public void onAnimationEnd(Animator animation) {
						mIvClickRedPackage.setVisibility(View.GONE);
					}
				});
			}
		});

		mIvCloseRedPackage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isOpen) {
					view.setVisibility(View.GONE);
					dialog.dismiss();
				} else {
					AnimatorSet animatorSet = new AnimatorSet();
					ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 0.1f);
					ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 0.1f);
					ObjectAnimator animatorTransX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f, x);
					ObjectAnimator animatorTransY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0f, y);

					animatorSet.playTogether(animatorScaleX, animatorScaleY, animatorTransX, animatorTransY);
					animatorSet.setDuration(700);
					animatorSet.start();
					animatorSet.addListener(new SimpleAnimatorListener() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mTvSmallRedPkg.setVisibility(View.VISIBLE);
							dialog.hide();
						}
					});
				}
			}
		});
	}
}
