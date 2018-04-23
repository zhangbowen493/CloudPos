package cn.wonders.pos_qdg.adapter;



import cn.wonders.pos_qdg.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************************
 * [Summary] TODO 请在此处简要描述此类所实现的功能。因为这项注释主要是为了在IDE环境中生成tip帮助，务必简明扼要 . [Remarks]
 * TODO 请在此处详细描述类的功能、调用方法、注意事项、以及与其它类的关系.
 **********************************************************************************/

public class CustomProgressDialog extends Dialog {
	private static CustomProgressDialog customProgressDialog = null;

	public CustomProgressDialog(Context context) {
		super(context);
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static CustomProgressDialog createDialog(Context context) {
		customProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.view_progress_dialog);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		customProgressDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失  
		return customProgressDialog;
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		if (customProgressDialog == null) {
			return;
		}
		ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loading_img);
		AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
		animationDrawable.start();
	}

	/**
	 * 
	 * [Summary] setTitile 标题
	 * 
	 * @param strTitle
	 * @return
	 * 
	 */
	public CustomProgressDialog setTitile(String strTitle) {
		return customProgressDialog;
	}

	/**
	 * [Summary] setMessage 提示内容
	 * 
	 * @param strMessage
	 * @return
	 * 
	 */
	public CustomProgressDialog setMessage(String strMessage) {
		if(customProgressDialog!=null){
			TextView tvMsg = (TextView) customProgressDialog.findViewById(R.id.tv_loadingmsg);
			if (tvMsg != null) {
				tvMsg.setText(strMessage);
			}
		}
		return customProgressDialog;
	}

	// 显示滚动进度
	public static void showProgressDialog(Context context, String strMessage) {
		if (customProgressDialog == null) {
			customProgressDialog = createDialog(context);
			customProgressDialog.setMessage(strMessage);
		}
		customProgressDialog.show();
	}
	// 显示滚动进度
	public static void showProgressDialog(Context context, String strMessage,boolean isOutsideClick) {
		if (customProgressDialog == null) {
			customProgressDialog = createDialog(context);
			customProgressDialog.setMessage(strMessage);
		}
		customProgressDialog.show();
		customProgressDialog.setCanceledOnTouchOutside(isOutsideClick);
		customProgressDialog.setOnDismissListener(new  OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				
			}
		});
	}
	// 显示滚动进度
	public static void showProgressDialog(Context context, String strMessage,final AsyncTask task) {
		if (customProgressDialog == null) {
			customProgressDialog = createDialog(context);
			customProgressDialog.setMessage(strMessage);
		}
//		customProgressDialog.setOnDismissListener(new  OnDismissListener() {
//			@Override
//			public void onDismiss(DialogInterface dialog) {
//				if(task != null) task.cancel(true);
//			}
//		});
		customProgressDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					if(customProgressDialog!= null && customProgressDialog.isShowing())
						customProgressDialog.hideProgressDialog();
					if(task != null) task.cancel(true);
					return true;
				}
				return false;
			}
		});
		customProgressDialog.show();
	}
	// 显示滚动进度
	public static void showProgressDialog(Context context, String strMessage,boolean isOutsideClick,final AsyncTask task) {
		if (customProgressDialog == null) {
			customProgressDialog = createDialog(context);
			customProgressDialog.setMessage(strMessage);
		}
		customProgressDialog.show();
		customProgressDialog.setCanceledOnTouchOutside(isOutsideClick);
		customProgressDialog.setOnDismissListener(new  OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				if(task != null) task.cancel(true);
			}
		});
	}

	// 隐藏滚动进度
	public static void hideProgressDialog() {
		if (customProgressDialog != null) {
			customProgressDialog.dismiss();
			customProgressDialog = null;
		}
	}

	@Override
	public void onBackPressed() {
		if (customProgressDialog != null) {
			customProgressDialog.dismiss();
			customProgressDialog = null;
		}
	}

}
