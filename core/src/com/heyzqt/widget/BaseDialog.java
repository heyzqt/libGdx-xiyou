package com.heyzqt.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

/**
 * Created by heyzqt on 2017/4/4.
 *
 * Dialog基类
 */
public class BaseDialog {

	protected float x;
	protected float y;

	//对话框
	public Image mWindow;

	//返回按钮
	public ImageButton mBackBtn;

	//确定按钮
	public ImageButton mConfirmBtn;

	//组件
	protected TextureAtlas mAtlas;

	protected Texture mBackground;

	public BaseDialog(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
