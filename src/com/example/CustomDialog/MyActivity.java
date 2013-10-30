package com.example.CustomDialog;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyActivity extends Activity {

    private Button showDialog;
    private Button showProgressDialog;
    private CustomDialog customDialog = null;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.showDialog:
                    System.out.println(" 显示Dialog ");

                    DisplayMetrics dm = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dm);

                    System.out.println(" width = " + dm.widthPixels
                            + " height = " + dm.heightPixels);

                    customDialog = new CustomDialog.Builder(MyActivity.this)
                            .setTitle("提示")
                            .setMessage(
                                    "二维码又称QR Code，QR全称Quick Response，"
                                            + "是一个近几年来移动设备上超流行的一种编码方式，它比传统的Bar Code条形码能存更多的信息，"
                                            + "也能表示更多的数据类型：比如：字符，数字，日文，中文等等。这两天学习了一下二维码图片生成的相关细节，"
                                            + "觉得这个玩意就是一个密码算法，在此写一这篇文章 ，揭露一下。供好学的人一同学习之。")
                            .setPositiveButton("确定",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            customDialog.dismiss();
                                            System.out.println(" 我点击了确定 ");
                                        }
                                    })
                            .setNegativeButton("取消",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            customDialog.dismiss();
                                            System.out.println(" 我点击了取消 ");
                                        }
                                    }).show();
                    break;
                case R.id.showProgressDialog:
                    System.out
                            .println(" 显示进度条 + custiomDialog " + customDialog);
                    customDialog = new CustomDialog.Builder(MyActivity.this)
                            .setLoadingText("加载中").show();
                    new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected Void doInBackground(Void... params) {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            customDialog.dismiss();
                            Toast.makeText(MyActivity.this, "加载完成",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }.execute();
                    break;
                default:
                    break;
            }
        }
    };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

        showDialog = (Button)findViewById(R.id.showDialog);
        showProgressDialog = (Button)findViewById(R.id.showProgressDialog);

        showDialog.setOnClickListener(clickListener);
        showProgressDialog.setOnClickListener(clickListener);

	}
}
