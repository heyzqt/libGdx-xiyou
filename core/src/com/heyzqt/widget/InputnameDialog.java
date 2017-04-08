package com.heyzqt.widget;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.heyzqt.handle.Constant;
import com.heyzqt.handle.DataUtils;
import com.heyzqt.state.Start;
import com.heyzqt.util.Utils;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/4/4.
 *
 * 输入姓名对话框
 */
public class InputnameDialog extends BaseDialog {

	public TextField mNameField;

	private int TEXT_FIELD_WEIGHT;
	private int TEXT_FIELD_HEIGHT;

	public InputnameDialog(float x, float y) {
		super(x, y);
		init();
	}

	private void init() {
		mAtlas = MyGdxGame.assetManager.getTextureAtlas(Constant.RANGE_WIDGET);
		mBackground = MyGdxGame.assetManager.getTexture(Constant.INPUT_NAME_BG);

		TEXT_FIELD_WEIGHT = mBackground.getWidth();
		TEXT_FIELD_HEIGHT = mBackground.getHeight();

		//初始化输入框
		TextField.TextFieldStyle style = new TextField.TextFieldStyle();
		style.font = MyGdxGame.assetManager.getFont();
		style.fontColor = Constant.FONT_COLOR;
		style.background = new TextureRegionDrawable(new TextureRegion(mAtlas.findRegion("inputname")));
		Texture cursor = createCursorTexture();
		style.cursor = new TextureRegionDrawable(new TextureRegion(cursor));

		mNameField = new TextField("", style);
		mNameField.setPosition(x + 60, y - 20);

		//初始化背景
		mWindow = new Image(mBackground);
		mWindow.setPosition(x - 30, y - mWindow.getHeight() / 2);

		//初始化确认按钮
		mConfirmBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("sureBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("sureBtnDown")));
		mConfirmBtn.setPosition(x + 40, y - 120);

		//初始化取消按钮
		mBackBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("cancelBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("cancelBtnDown")));
		mBackBtn.setPosition(x + 200, y - 120);

		mConfirmBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				MyGdxGame.assetManager.getSound(Constant.BTN_COMMON_SOUND).play();
				//确认姓名是否符合规范
				String name = mNameField.getText();
				int result = Utils.isNameFormatted(name);
				//用户名合法
				if (result == 0) {
					Start.isShowInputNameDialog = false;
					//保存用户数据
					MyGdxGame.user.name = name;
					DataUtils.getInstance().saveDataToEncode(Constant.PREFERENCES_USERNAME, name);
					//跳转到选关界面
					Start.isChangeToSelect = true;
				} else {
					//用户名非法
					mNameField.setText("");
				}
				return true;
			}
		});

		mBackBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				MyGdxGame.assetManager.getSound(Constant.BTN_COMMON_SOUND).play();
				Start.isShowInputNameDialog = false;
				return true;
			}
		});

	}

	/**
	 * 创建文本框中的光标纹理
	 */
	private Texture createCursorTexture() {
		Pixmap pixmap = new Pixmap(2, TEXT_FIELD_HEIGHT - 8, Pixmap.Format.RGBA8888);
		pixmap.setColor(0.047f, 0.047f, 0.047f, 1);
		pixmap.fill();
		Texture texture = new Texture(pixmap);
		pixmap.dispose();
		return texture;
	}
}
