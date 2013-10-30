package com.example.CustomDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

class CustomDialog {

	private AlertDialog ad;
	private Context context;
	private Window window;

	private TextView titleTextView;
	private TextView messageTextView;
	private Button postiveButton;
	private Button negativeButton;
	private View title_line;

	private ImageView loadingImageView;
	private TextView loadingDesc;

	private RelativeLayout relativeLayout;
	private boolean isLoading = false;
	private AnimationDrawable animationDrawable = null;

	private CustomDialog(Context context) {
		super();
		this.context = context;
		ad = new AlertDialog.Builder(context).create();
		ad.show();
		window = ad.getWindow();
		View customView = LayoutInflater.from(context).inflate(
				R.layout.customdialog, null);

		relativeLayout = (RelativeLayout) customView
				.findViewById(R.id.root_view);

		titleTextView = (TextView) customView.findViewById(R.id.dialog_title);
		title_line = customView.findViewById(R.id.title_line);
		messageTextView = (TextView) customView
				.findViewById(R.id.dialog_message);

		postiveButton = (Button) customView.findViewById(R.id.positive_Button);
		negativeButton = (Button) customView.findViewById(R.id.negative_Button);

		loadingImageView = (ImageView) customView
				.findViewById(R.id.loading_imageview);
		loadingDesc = (TextView) customView.findViewById(R.id.loading_desc);

		// customView.setLayoutParams(new LinearLayout.LayoutParams(width,
		// height));
		window.setContentView(customView);
	}

	private void showLoadingView() {
		isLoading = true;

		// 去掉Dialog的变暗效果
		WindowManager.LayoutParams layoutParams = ad.getWindow()
				.getAttributes();
		layoutParams.dimAmount = 0;// 0，不变暗，1全暗
		ad.getWindow().setAttributes(layoutParams);
		ad.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

		// 设置背景透明
		relativeLayout.setBackgroundColor(Color.TRANSPARENT);
		loadingImageView.setVisibility(View.VISIBLE);
		loadingDesc.setVisibility(View.VISIBLE);
		loadingImageView.setBackgroundResource(R.drawable.animation_loading);

		animationDrawable = (AnimationDrawable) loadingImageView
				.getBackground();
		startLoadingAnimation();
	}

	public void stopLoadingAnimation() {
		if (isLoading) {
			if (animationDrawable != null) {
				if (animationDrawable.isRunning())
					animationDrawable.stop();
			}
		}
	}

	public void startLoadingAnimation() {
		if (isLoading) {
			if (animationDrawable != null) {
				if (animationDrawable.isRunning()) {
					stopLoadingAnimation();
				}
				animationDrawable.start();
			}
		}
	}

	public void showTitleLine() {
		title_line.setVisibility(View.VISIBLE);
	}

	/**
	 * 按键监听
	 * 
	 * @param listener
	 */
	public void setOnKeyListener(OnKeyListener listener) {
		ad.setOnKeyListener(listener);
	}

	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		try {
			stopLoadingAnimation();
			ad.dismiss();
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 弹出对话框的时候 点击Back健是否取消对话框
	 */
	public void setCancelable(boolean flag) {
		ad.setCancelable(flag);
	}

	/**
	 * 如果你触摸屏幕其它区域,消失Dialog
	 * 
	 * @param flag
	 */
	public void setCanceledOnTouchOutside(boolean flag) {
		ad.setCanceledOnTouchOutside(flag);
	}

	/**
	 * 判断Ｄｉａｌｏｇ是否在显示
	 * 
	 * @return
	 */
	public boolean isShowing() {
		return ad.isShowing();
	}

	public void setDialogMessage(CharSequence message) {
		messageTextView.setVisibility(View.VISIBLE);
		messageTextView.setText(message);
	}

	public void setTitleTextViewText(CharSequence title) {
		titleTextView.setVisibility(View.VISIBLE);
		titleTextView.setText(title);
	}

	public void setPostiveButton(CharSequence title,
			View.OnClickListener listener) {
		postiveButton.setVisibility(View.VISIBLE);
		postiveButton.setText(title);
		postiveButton.setOnClickListener(listener);
	}

	public void setNegativeButton(CharSequence title,
			View.OnClickListener listener) {
		negativeButton.setVisibility(View.VISIBLE);
		negativeButton.setText(title);
		negativeButton.setOnClickListener(listener);
	}

	public static class Builder {
		private AlertParams params;

		public Builder(Context context) {
			params = new AlertParams(context);
		}

		public Builder setTitle(CharSequence title) {
			params.mTitle = title;
			return this;
		}

		public Builder setMessage(CharSequence message) {
			params.mMessage = message;
			return this;
		}

		public Builder setPositiveButton(CharSequence title,
				View.OnClickListener listener) {
			params.mPositiveButtonText = title;
			params.mPositiveButtonListener = listener;
			return this;
		}

		public Builder setNegativeButton(CharSequence title,
				View.OnClickListener listener) {
			params.mNegativeButtonText = title;
			params.mNegativeButtonListener = listener;
			return this;
		}

		public Builder setLoadingText(CharSequence text) {
			params.mLoadingText = text;
			return this;
		}

		public CustomDialog show() {
			CustomDialog dialog = new CustomDialog(params.context);
			if (params.mLoadingText == null
					|| params.mLoadingText.toString().equalsIgnoreCase("")) {
				dialog.showTitleLine();
				dialog.setTitleTextViewText(params.mTitle);
				dialog.setDialogMessage(params.mMessage);
				dialog.setPostiveButton(params.mPositiveButtonText,
						params.mPositiveButtonListener);
				dialog.setNegativeButton(params.mNegativeButtonText,
						params.mNegativeButtonListener);
			} else {
				dialog.showLoadingView();
				dialog.setCancelable(false);
				dialog.setCanceledOnTouchOutside(false);
			}

			return dialog;
		}
	}

	public static class AlertParams {
		public Context context;

		public CharSequence mTitle;
		public CharSequence mMessage;
		public CharSequence mPositiveButtonText;
		public CharSequence mNegativeButtonText;
		public View.OnClickListener mPositiveButtonListener;
		public View.OnClickListener mNegativeButtonListener;
		public CharSequence mLoadingText;

		public AlertParams(Context context) {
			this.context = context;
		}
	}
}
