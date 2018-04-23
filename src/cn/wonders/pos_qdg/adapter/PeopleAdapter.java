package cn.wonders.pos_qdg.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.bean.User;
import cn.wonders.pos_qdg.db.UserDao;
import cn.wonders.pos_qdg.util.StringUtils;
import cn.wonders.pos_qdg.util.ToastUtil;

/**
 * 人员维护列表
 * 
 * @author n_n
 * 
 */
public class PeopleAdapter extends BaseAdapter<User> {

	private UserDao dao;

	public PeopleAdapter(Context context, ArrayList<User> users) {
		super(context);
		dao = new UserDao(mContext);
		this.data = users;
	}

	@Override
	public void setData(ArrayList<User> list) {
		// TODO Auto-generated method stub
		this.data = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = mInfalter.inflate(R.layout.lv_pepole_item, null);
		TextView number = (TextView) item
				.findViewById(R.id.tv_people_item_number);
		ImageView edit = (ImageView) item
				.findViewById(R.id.iv_people_item_edit);
		ImageView delete = (ImageView) item
				.findViewById(R.id.iv_people_item_delete);
		if (position / 2 == 0) {
			item.setBackgroundColor(Color.rgb(80, 89, 121));
		}
		final User user = data.get(position);

		if ("999999".equals(user.getAccount())) {
			number.setText("操作员账号：" + user.getAccount());
			edit.setVisibility(View.INVISIBLE);
			delete.setVisibility(View.INVISIBLE);
		} else {
			number.setText(user.getAccount());
		}

		edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ("999999".equals(user.getAccount())) {
					ToastUtil.showShort(mContext, "管理员账户不可编辑！");
				} else
					setParamDialog(1, user);
			}
		});
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if ("999999".equals(user.getAccount())) {
					ToastUtil.showShort(mContext, "管理员账户不可编辑！");
				} else {
					setParamDialog(0, user);
				}

			}
		});
		return item;
	}

	/**
	 * 操作员编辑
	 * 
	 * @param index
	 *            1：编辑 0：删除
	 * @param user
	 */
	private void setParamDialog(final int index, final User user) {

		final Dialog mDialog = new Dialog(mContext, R.style.windowDilog);
		mDialog.setContentView(R.layout.dialog_people_manage);
		ImageView sure = (ImageView) mDialog
				.findViewById(R.id.iv_dialog_people_ok);
		ImageView back = (ImageView) mDialog
				.findViewById(R.id.iv_dialog_people_cancel);
		EditText param_account = (EditText) mDialog
				.findViewById(R.id.edt_dialog_people_account);
		final EditText param_password = (EditText) mDialog
				.findViewById(R.id.edt_dialog_people_password);
		TextView title = (TextView) mDialog
				.findViewById(R.id.tv_dialog_people_title);
		if (index == 1)
			title.setText("编辑操作人员");
		else if (index == 0)
			title.setText("删除操作人员");

		param_account.setText(user.getAccount());
		param_password.setText(user.getPassword());
		mDialog.setCanceledOnTouchOutside(false);

		sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (index == 1) {
					String newPassword = param_password.getText().toString()
							.trim();
					if (StringUtils.isBlank(newPassword)) {
						ToastUtil.showShort(mContext, "请输入正确的密码!");
					} else {
						user.setPassword(newPassword);
						int updateUser = dao.updateUser(user);
						if (updateUser == 1) {
							ArrayList<User> list = (ArrayList<User>) dao
									.getAllUser();
							setData(list);
							notifyDataSetChanged();
							ToastUtil.showShort(mContext, "操作员修改成功!");
						} else {
							ToastUtil.showShort(mContext, "操作员修改失败!");
						}

						if (mDialog.isShowing())
							mDialog.dismiss();
					}
				} else if (index == 0) {
					int deleteUser = dao.deleteUser(user);
					if (deleteUser == 1) {
						ArrayList<User> list = (ArrayList<User>) dao
								.getAllUser();
						setData(list);
						notifyDataSetChanged();
					} else {
						ToastUtil.showShort(mContext, "操作员删除失败!");
					}
				}
			}
		});
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (mDialog.isShowing())
					mDialog.dismiss();
			}
		});
		mDialog.show();
	}

}
