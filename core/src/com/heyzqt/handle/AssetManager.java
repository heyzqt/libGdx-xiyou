package com.heyzqt.handle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

/**
 * Created by heyzqt on 2017/2/14.
 *
 * 资源管理器
 */
public class AssetManager {

	private HashMap<String, Texture> textures;
	private HashMap<String, TextureAtlas> atlas;
	private BitmapFont mFont;
	private HashMap<String, Music> musics;
	private HashMap<String, Sound> sounds;
	private BitmapFont mNumFont;

	public AssetManager() {
		textures = new HashMap<String, Texture>();
		atlas = new HashMap<String, TextureAtlas>();
		musics = new HashMap<String, Music>();
		sounds = new HashMap<String, Sound>();
	}

	//加载字体
	public void loadFont() {
		mFont = new BitmapFont(Gdx.files.internal("font/text48.fnt"));
	}

	//释放字体
	public void removeFont() {
		if (mFont != null) {
			mFont.dispose();
		}
	}

	//获取数字字体资源
	public BitmapFont getNumFont() {
		return mNumFont;
	}

	//加载数字字体
	public void loadNumFont() {
		mNumFont = new BitmapFont(Gdx.files.internal("font/font_1_m.fnt"));
	}

	//释放数字字体
	public void removeNumFont() {
		if (mNumFont != null) {
			mNumFont.dispose();
		}
	}

	//获取字体资源
	public BitmapFont getFont() {
		return mFont;
	}

	//加载图片
	public void loadTexture(String path, String key) {
		Texture tex = new Texture(path);
		textures.put(key, tex);
	}

	//卸载图片
	public void removeTexture(String key) {
		Texture tex = textures.get(key);
		if (tex != null) {
			tex.dispose();
		}
		textures.remove(tex);
	}

	//获取图片
	public Texture getTexture(String key) {
		return textures.get(key);
	}

	//加载图片集合
	public void loadTextureAtlas(String path, String key) {
		TextureAtlas atla = new TextureAtlas(path);
		atlas.put(key, atla);
	}

	//卸载图片集合
	public void removeTextureAtals(String key) {
		TextureAtlas a = atlas.get(key);
		if (a != null) {
			a.dispose();
		}
		atlas.remove(key);
	}

	//获取图片集合
	public TextureAtlas getTextureAtlas(String key) {
		return atlas.get(key);
	}

	//加载Music
	public void loadMusic(String path, String key) {
		Music music = Gdx.audio.newMusic(Gdx.files.internal(path));
		musics.put(key, music);
	}

	//卸载music
	public void removeMusic(String key) {
		Music m = musics.get(key);
		if (m != null) {
			m.dispose();
		}
		musics.remove(key);
	}

	//获取Music
	public Music getMusic(String key) {
		return musics.get(key);
	}

	//加载Sound
	public void loadSound(String path, String key) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
		sounds.put(key, sound);
	}

	//卸载Sound
	public void removeSound(String key) {
		Sound sound = sounds.get(key);
		if (sound != null) {
			sound.dispose();
		}
		sounds.remove(key);
	}

	//获取Sound
	public Sound getSound(String key) {
		return sounds.get(key);
	}

	public void removeAll() {
		//清理字体
		removeFont();

		//清理数字字体
		removeNumFont();

		//清理所有图片
		for (Object object : textures.values()) {
			Texture tex = (Texture) object;
			tex.dispose();
		}
		textures.clear();

		//清理所有图片集合
		for (Object object : atlas.values()) {
			TextureAtlas at = (TextureAtlas) object;
			at.dispose();
		}
		atlas.clear();

		//清理所有Music
		for (Object object : musics.values()) {
			Music m = (Music) object;
			m.dispose();
		}
		musics.clear();

		//清理所有Sound
		for (Object object : sounds.values()) {
			Sound s = (Sound) object;
			s.dispose();
		}
		sounds.clear();
	}
}
