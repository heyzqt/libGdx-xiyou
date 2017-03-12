package com.heyzqt.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.heyzqt.entity.Background;
import com.heyzqt.entity.Boss;
import com.heyzqt.entity.Enemy;
import com.heyzqt.entity.Monkey;
import com.heyzqt.handle.Box2DContactListener;
import com.heyzqt.handle.Constant;
import com.heyzqt.handle.State;
import com.heyzqt.handle.Utils;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 游戏界面
 */
public class Play extends GameState {

	/**
	 * 刚体世界
	 */
	//声明世界
	private World mWorld;
	//物理世界相机
	private OrthographicCamera mBox2DCamera;
	//物理世界渲染器
	private Box2DDebugRenderer mBox2DRender;
	//声明刚体监听器
	private Box2DContactListener mContactListener;

	/**
	 * 游戏地图
	 */
	//地图
	private TiledMap mMap;
	//瓦片大小
	private float tileSize;
	//地图宽度（瓦片数量）
	private float tileWidth;
	//地图高度（瓦片数量）
	private float tileHeight;
	//地图渲染器
	private OrthogonalTiledMapRenderer mOrthogonalTiledMapRenderer;
	//地图编号
	public static int level = 0;

	/**
	 * 界面控件
	 */
	//孙悟空头像
	private TextureRegion mSunAvatar;
	//血槽
	private TextureRegion mBloodProgress;
	private TextureRegion mBloodProgressBG;
	private float bloodProgress;        //血槽值
	//分数
	private Label mScore;
	private BitmapFont mScoreFont;
	//左按钮
	private ImageButton mLeftBtn;
	//右按钮
	private ImageButton mRightBtn;
	//跳跃按钮
	private ImageButton mJumpBtn;
	//攻击按钮
	private ImageButton mAttackBtn;

	/**
	 * 精灵
	 */
	//游戏背景
	private Background mBackground;
	//游戏主角
	public static Monkey mMonkey;
	//持刀天兵
	private Array<Enemy> mEnemyDaos;
	//Boss
	private Boss mBoss;

	/**
	 * 刚体
	 */
	//刚体信息
	private BodyDef mBodyDef;
	//刚体
	private Body mBody;
	//孙悟空攻击夹具
	private FixtureDef mAttackFixDef;
	//孙悟空形状夹具
	private Fixture mStandFix;
	private FixtureDef mStandFixDef;

	/**
	 * 其他
	 */
	//游戏渲染时间
	private float statetime;

	public Play(GameStateManager manager) {
		super(manager);
		init();
	}

	private void init() {

		//初始化世界 初始化重力
		mWorld = new World(new Vector2(0, -9.8f), true);

		//初始化刚体世界相机
		mBox2DCamera = new OrthographicCamera();
		mBox2DCamera.setToOrtho(false, MyGdxGame.VIEW_WIDTH / Constant.RATE, MyGdxGame.VIEW_HEIGHT / Constant.RATE);

		//初始化Box2D渲染器
		mBox2DRender = new Box2DDebugRenderer();

		//创建地图
		createMap();

		//创建天兵
		createEnemy();

		//创建Boss
		createBoss(level);

		//创建主角
		createActor();

		//初始化界面控件
		TextureAtlas mBloodAtlas = MyGdxGame.mAssetManager.getTextureAtlas(Constant.PLAY_BLOOD);
		//初始化头像
		mSunAvatar = mBloodAtlas.findRegion("sunAvatar");
		//初始化血槽
		mBloodProgressBG = mBloodAtlas.findRegion("blood_border");
		mBloodProgress = mBloodAtlas.findRegion("blood");
		//初始化分数
		mScoreFont = new BitmapFont(Gdx.files.internal("font/text48.fnt"));
		mScoreFont.getData().setScale(0.8f, 1f);
		Label.LabelStyle style = new Label.LabelStyle(MyGdxGame.mAssetManager.getNumFont(), null);
		mScore = new Label("0", style);
		mScore.setPosition(1100, 590);
		//初始化操作杆
		TextureAtlas mAtlas = MyGdxGame.mAssetManager.getTextureAtlas(Constant.PLAY_WIDGET);
		//左行动按钮
		mLeftBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("leftBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("leftBtnDown")));
		mLeftBtn.setPosition(100, 20);
		//右行动按钮
		mRightBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("rightBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("rightBtnDown")));
		mRightBtn.setPosition(260, 20);
		//攻击按钮
		mAttackBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("attackBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("attackBtnDown")));
		mAttackBtn.setPosition(1000, 35);
		//跳跃按钮
		mJumpBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("jumpBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("jumpBtnDown")));
		mJumpBtn.setPosition(1130, 140);

		mStage.addActor(mScore);
		mStage.addActor(mLeftBtn);
		mStage.addActor(mRightBtn);
		mStage.addActor(mAttackBtn);
		mStage.addActor(mJumpBtn);

		//初始化孙悟空所有按钮的监听事件
		initListener();

		//注册碰撞监听事件
		mContactListener = new Box2DContactListener();
		mWorld.setContactListener(mContactListener);
	}

	private void initListener() {
		//左按钮
		mLeftBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				//添加一个水平方向速度
				mBody.setLinearVelocity(-1f, 0);
				Monkey.STATE = State.STATE_LEFT;
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				//添加一个水平方向速度
				mBody.setLinearVelocity(0f, 0);
				Monkey.STATE = State.STATE_IDEL_LEFT;
			}
		});

		//右按钮
		mRightBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Monkey.STATE = State.STATE_RIGHT;
				mBody.setLinearVelocity(1f, 0);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Monkey.STATE = State.STATE_IDEL_RIGHT;
				mBody.setLinearVelocity(0, 0);
			}
		});

		//攻击按钮
		mAttackBtn.addListener(new ClickListener() {
			Fixture mFixture;

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				if (mStandFix != null) {
					mBody.destroyFixture(mStandFix);
				}
				//设置速度为0
				mBody.setLinearVelocity(0, 0);

				//创建攻击传感器 stick
				PolygonShape shape = new PolygonShape();
				if (Monkey.STATE == State.STATE_RIGHT ||
						Monkey.STATE == State.STATE_IDEL_RIGHT || Monkey.STATE == State.STATE_RIGHT_HITED) {
					Monkey.STATE = State.STATE_RIGHT_ATTACK;
					shape.setAsBox(30 / Constant.RATE, 5 / Constant.RATE
							, new Vector2(60 / Constant.RATE, 0), 0);
				} else {
					Monkey.STATE = State.STATE_LEFT_ATTACK;
					shape.setAsBox(30 / Constant.RATE, 5 / Constant.RATE
							, new Vector2(-60 / Constant.RATE, 0), 0);
				}

				mAttackFixDef = new FixtureDef();
				mAttackFixDef.shape = shape;
				mAttackFixDef.filter.categoryBits = Constant.PLAYER;
				mAttackFixDef.filter.maskBits = Constant.ENEMY_DAO;
				mAttackFixDef.isSensor = true;
				mFixture = mBody.createFixture(mAttackFixDef);
				mFixture.setUserData("stick");
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (mFixture != null) {
					mBody.destroyFixture(mFixture);
				}

				//设置孙悟空在攻击完后的状态
				if (mRightBtn.isPressed()) {
					Monkey.STATE = State.STATE_RIGHT;
					mBody.setLinearVelocity(1f, 0);
				} else if (mLeftBtn.isPressed()) {
					Monkey.STATE = State.STATE_LEFT;
					mBody.setLinearVelocity(-1f, 0);
				} else if (Monkey.STATE == State.STATE_RIGHT_ATTACK) {
					Monkey.STATE = State.STATE_IDEL_RIGHT;
				} else if (Monkey.STATE == State.STATE_LEFT_ATTACK) {
					Monkey.STATE = State.STATE_IDEL_LEFT;
				}

				//重新初始化孙悟空的monkey传感器
				mStandFix = mBody.createFixture(mStandFixDef);
				mStandFix.setUserData("monkey");
			}
		});

		//跳跃按钮
		mJumpBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				mBody.applyForceToCenter(0, 250, true);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			}
		});
	}

	private void createEnemy() {
		mEnemyDaos = new Array<Enemy>();

		Array<MapLayer> mapLayers = new Array<MapLayer>();
		//天兵对象层
		mapLayers.add(mMap.getLayers().get("enemy"));
		mapLayers.add(mMap.getLayers().get("enemyFu"));
		mapLayers.add(mMap.getLayers().get("enemyQiang"));

		for (MapLayer mapLayer : mapLayers) {
			if (mapLayer == null) {
				continue;
			}

			//初始化持刀天兵刚体形状
			BodyDef enemyDef = new BodyDef();
			enemyDef.type = BodyDef.BodyType.DynamicBody;
			//多边形形状
			PolygonShape polygonShape = new PolygonShape();
			//设置夹具
			FixtureDef enemyFixDef = new FixtureDef();

			//遍历enemy对象层
			for (MapObject object : mapLayer.getObjects()) {
				//坐标
				float x = 0;
				float y = 0;
				//获取对象坐标
				if (object instanceof EllipseMapObject) {
					EllipseMapObject ellipseMapObject = (EllipseMapObject) object;
					x = ellipseMapObject.getEllipse().x / Constant.RATE;
					y = ellipseMapObject.getEllipse().y / Constant.RATE;
				}

				//持刀天兵夹具
				polygonShape.setAsBox(30 / Constant.RATE, 60 / Constant.RATE);
				enemyFixDef.shape = polygonShape;
				enemyFixDef.isSensor = true;
				enemyFixDef.filter.categoryBits = Constant.ENEMY_DAO;
				enemyFixDef.filter.maskBits = Constant.PLAYER;

				//设置位置
				enemyDef.position.set(x, y);
				Body enemyBody = mWorld.createBody(enemyDef);
				enemyBody.createFixture(enemyFixDef).setUserData("enemy");

				//创建脚传感器 foot
				polygonShape.setAsBox(28 / Constant.RATE, 3 / Constant.RATE, new Vector2(0, -58 / Constant.RATE), 0);
				enemyFixDef.shape = polygonShape;
				enemyFixDef.filter.categoryBits = Constant.ENEMY_DAO;
				enemyFixDef.filter.maskBits = Constant.BLOCK;
				enemyFixDef.isSensor = false;
				enemyBody.createFixture(enemyFixDef).setUserData("enemyFoot");

				Enemy enemy = new Enemy(enemyBody, mapLayer.getName());
				mEnemyDaos.add(enemy);
				enemyBody.setUserData(enemy);

				Thread thread = new Thread(enemy);
				thread.start();
			}
		}
	}

	private void createBoss(int level) {

		MapLayer mapLayer = mMap.getLayers().get("Boss");
		if (mapLayer == null) return;

		//初始化Boss刚体形状
		BodyDef bossDef = new BodyDef();
		bossDef.type = BodyDef.BodyType.DynamicBody;
		//多边形形状
		PolygonShape polygonShape = new PolygonShape();
		//设置夹具
		FixtureDef bossFixDef = new FixtureDef();

		//遍历Boss对象层
		for (MapObject object : mapLayer.getObjects()) {
			//坐标
			float x = 0;
			float y = 0;
			//获取对象坐标
			if (object instanceof EllipseMapObject) {
				EllipseMapObject ellipseMapObject = (EllipseMapObject) object;
				x = ellipseMapObject.getEllipse().x / Constant.RATE;
				y = ellipseMapObject.getEllipse().y / Constant.RATE;
			}

			//boss夹具
			polygonShape.setAsBox(30 / Constant.RATE, 60 / Constant.RATE);
			bossFixDef.shape = polygonShape;
			bossFixDef.isSensor = true;
			bossFixDef.filter.categoryBits = Constant.BOSS;
			bossFixDef.filter.maskBits = Constant.PLAYER;

			//设置位置
			bossDef.position.set(x, y);
			Body bossBody = mWorld.createBody(bossDef);
			bossBody.createFixture(bossFixDef).setUserData("boss");

			//创建脚传感器 foot
			polygonShape.setAsBox(28 / Constant.RATE, 3 / Constant.RATE, new Vector2(0, -58 / Constant.RATE), 0);
			bossFixDef.shape = polygonShape;
			bossFixDef.filter.categoryBits = Constant.BOSS;
			bossFixDef.filter.maskBits = Constant.BLOCK;
			bossFixDef.isSensor = false;
			bossBody.createFixture(bossFixDef).setUserData("bossFoot");

			//设置Boss
			switch (level) {
				case 0:        //第一关
					mBoss = new Boss(bossBody, MyGdxGame.mAssetManager.getTextureAtlas(Constant.BOSS_JULING_ROLE));
					break;
				case 1:        //第二关
					mBoss = new Boss(bossBody, MyGdxGame.mAssetManager.getTextureAtlas(Constant.BOSS_ZENGZHANG_ROLE));
					break;
				case 2:        //第三关
					mBoss = new Boss(bossBody, MyGdxGame.mAssetManager.getTextureAtlas(Constant.BOSS_GUANGMU_ROLE));
					break;
				case 3:        //第四关
					mBoss = new Boss(bossBody, MyGdxGame.mAssetManager.getTextureAtlas(Constant.BOSS_DUOWEN_ROLE));
					break;
				case 4:        //第五关
					mBoss = new Boss(bossBody, MyGdxGame.mAssetManager.getTextureAtlas(Constant.BOSS_ERLANG_ROLE));
					break;
			}
			bossBody.setUserData(mBoss);

			Thread thread = new Thread(mBoss);
			thread.start();
		}
	}

	private void createActor() {
		//初始化刚体属性
		mBodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		//设置孙悟空形状和静态传感器monkey
		mBodyDef.type = BodyDef.BodyType.DynamicBody;
		//position是刚体中心点的位置
		mBodyDef.position.set(100 / Constant.RATE, 400 / Constant.RATE);
		mBody = mWorld.createBody(mBodyDef);
		PolygonShape standShape = new PolygonShape();
		standShape.setAsBox(36 / Constant.RATE, 60 / Constant.RATE);
		mStandFixDef = new FixtureDef();
		mStandFixDef.shape = standShape;
		mStandFixDef.filter.categoryBits = Constant.PLAYER;
		mStandFixDef.filter.maskBits = Constant.ENEMY_DAO;
		mStandFix = mBody.createFixture(mStandFixDef);
		mStandFix.setUserData("monkey");

		//创建传感器 foot
		shape.setAsBox(28 / Constant.RATE, 3 / Constant.RATE, new Vector2(0, -60 / Constant.RATE), 0);
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constant.PLAYER;
		fixtureDef.filter.maskBits = Constant.BLOCK;
		fixtureDef.isSensor = false;
		mBody.createFixture(fixtureDef).setUserData("foot");

		mMonkey = new Monkey(mBody);
		mBody.setUserData(mMonkey);

		//设置敌人总数
		switch (level) {
			case 0:        //第一关
				mMonkey.setAllEnemiesCount(mMonkey.level_1_enemies);
				break;
			case 1:        //第二关
				mMonkey.setAllEnemiesCount(mMonkey.level_2_enemies);
				break;
			case 2:        //第三关
				mMonkey.setAllEnemiesCount(mMonkey.level_3_enemies);
				break;
			case 3:        //第四关
				mMonkey.setAllEnemiesCount(mMonkey.level_4_enemies);
				break;
			case 4:        //第五关
				mMonkey.setAllEnemiesCount(mMonkey.level_5_enemies);
				break;
		}
	}

	private void createMap() {
		try {
			mMap = new TmxMapLoader().load("map/level_" + level + ".tmx");
			Music music;
			switch (level) {
				case 0:        //第一关
					//初始化背景
					mBackground = new Background(Constant.FIRST_GAME_BG);
					//初始化音乐
					music = MyGdxGame.mAssetManager.getMusic(Constant.LEVEL_0_BGM);
					music.setLooping(true);
					music.play();
					break;
				case 1:        //第二关
					//初始化背景
					mBackground = new Background(Constant.SECOND_GAME_BG);
					//初始化音乐
					music = MyGdxGame.mAssetManager.getMusic(Constant.LEVEL_0_BGM);
					music.setLooping(true);
					music.play();
					break;
				case 2:        //第三关
					music = MyGdxGame.mAssetManager.getMusic(Constant.LEVEL_0_BGM);
					music.setLooping(true);
					music.play();
					break;
				case 3:        //第四关背景音乐
					music = MyGdxGame.mAssetManager.getMusic(Constant.LEVEL_0_BGM);
					music.setLooping(true);
					music.play();
					break;
				case 4:        //第五关背景音乐
					music = MyGdxGame.mAssetManager.getMusic(Constant.LEVEL_0_BGM);
					music.setLooping(true);
					music.play();
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Gdx.app.exit();
		}

		//初始化相机渲染器
		mOrthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(mMap);

		//赋值地图参数
		tileSize = mMap.getProperties().get("tilewidth", Integer.class);
		tileWidth = mMap.getProperties().get("width", Integer.class);
		tileHeight = mMap.getProperties().get("height", Integer.class);

		//绑定地面图层与刚体
		TiledMapTileLayer layer = (TiledMapTileLayer) mMap.getLayers().get("ground");

		//遍历所有单元格
		BodyDef bodyDef = new BodyDef();
		FixtureDef chainFixtureDef = new FixtureDef();
		for (int row = 0; row < layer.getHeight(); row++) {
			for (int col = 0; col < layer.getWidth(); col++) {
				TiledMapTileLayer.Cell cell = layer.getCell(col, row);
				if (cell == null || cell.getTile() == null) {
					continue;
				}

				bodyDef.type = BodyDef.BodyType.StaticBody;
				bodyDef.position.set(
						(col + 0.5f) * tileSize / Constant.RATE,
						(row + 0.5f) * tileSize / Constant.RATE);

				//设置地面是链式形状
				ChainShape chainShape = new ChainShape();
				Vector2[] vector2 = new Vector2[3];
				vector2[0] = new Vector2(-tileSize / 2 / Constant.RATE, -tileSize / 2 / Constant.RATE);
				vector2[1] = new Vector2(-tileSize / 2 / Constant.RATE, tileSize / 2 / Constant.RATE);
				vector2[2] = new Vector2(tileSize / 2 / Constant.RATE, tileSize / 2 / Constant.RATE);
				chainShape.createChain(vector2);
				//绑定夹具与链式图形
				chainFixtureDef.shape = chainShape;
				//设置摩擦系数为0
				chainFixtureDef.friction = 0;
				chainFixtureDef.filter.categoryBits = Constant.BLOCK;
				chainFixtureDef.filter.maskBits = Constant.PLAYER | Constant.ENEMY_DAO | Constant.BOSS;
				//设置传感器
				chainFixtureDef.isSensor = false;

				mWorld.createBody(bodyDef).createFixture(chainFixtureDef).setUserData("block");
			}
		}
	}

	@Override
	public void update(float delta) {

		mWorld.step(1 / 60f, 1, 1);

		/**
		 * 处理主角与天兵战斗逻辑
		 * 获得已经被移除对象 并进行清理
		 */
		Array<Body> removeBodies = mContactListener.getRemoveEnemies();
		for (Body removeBody : removeBodies) {
			//设置天兵状态为死亡
			Enemy enemy = (Enemy) removeBody.getUserData();
			enemy.setLive(false);
			//移除天兵
			mEnemyDaos.removeValue(enemy, true);
			//销毁刚体
			mWorld.destroyBody(removeBody);
			//孙悟空打败敌人数目加一
			mMonkey.beatEnemy();
		}
		removeBodies.clear();

		//移除已经死亡的boss
		Body bossBody = mContactListener.getRemoveBoss();
		if (bossBody != null) {
			Boss boss = (Boss) bossBody.getUserData();
			boss.setLive(false);
			mBoss = null;
			mWorld.destroyBody(bossBody);
			mMonkey.beatEnemy();
		}
		bossBody = null;
		mContactListener.setRemoveBoss(null);


		//设置主角当前血量
		bloodProgress = (Monkey.BLOOD - mMonkey.attacks) * 26;

		//设置分数
		mScore.setText(mMonkey.getEnemyCount() + "");

		/**
		 * 主角死亡 方式一：掉落到屏幕之外 方式二：敌人攻击主角致死
		 */
		if (mMonkey.getBody().getPosition().y < 0) {
			mGameStateManager.setState(GameStateManager.FAILURE);
		} else if (mMonkey.attacks == Monkey.BLOOD) {
			mGameStateManager.setState(GameStateManager.FAILURE);
		}

		/**
		 * 主角通关 并判断星级
		 */
		if (mMonkey.getBody().getPosition().x * Constant.RATE > tileWidth * tileSize) {
			mGameStateManager.setState(GameStateManager.SUCCESS);
			Success.winGrades = (Monkey.BLOOD - mMonkey.attacks);
			//血量判断
			if ((Monkey.BLOOD - mMonkey.attacks) >= Monkey.BLOOD / 2) {
				Success.winGrades = 1;
			} else {
				Success.winGrades = 0;
			}

			//时间判断
			if (statetime <= 60) {
				Success.winGrades += 2;
			} else {
				Success.winGrades += 1;
			}

			//分数判断
			if (mMonkey.getEnemyCount() == mMonkey.getEnemiesCount()) {
				Success.winGrades += 2;
			} else if (mMonkey.getEnemyCount() == 0) {
				Success.winGrades += 0;
			} else {
				Success.winGrades += 1;
			}
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		statetime += Gdx.graphics.getDeltaTime();
		Utils.setTime(statetime);

		update(statetime);

		/**
		 * 绘图世界
		 */
		//设置相机投影矩阵锚点位置
		mCamera.position.set(mMonkey.getPosition().x * Constant.RATE + MyGdxGame.VIEW_WIDTH / 4,
				MyGdxGame.VIEW_HEIGHT / 2, 0);
		//调整游戏相机
		adjustCamera();
		mCamera.update();

		//设置绘图矩阵
		mBatch.setProjectionMatrix(mUICamera.combined);
		//画背景
		if (mBackground != null) {
			mBackground.render(mBatch);
		}

		//画地图
		mOrthogonalTiledMapRenderer.setView(mCamera);
		mOrthogonalTiledMapRenderer.render();

		mBatch.begin();
		//画血槽
		mBatch.draw(mBloodProgressBG, 130, mScore.getY() - 55);
		if (bloodProgress >= 0) {
			mBatch.draw(mBloodProgress, 133, mScore.getY() - 53, bloodProgress, 26);
		}
		//画孙悟空头像
		mBatch.draw(mSunAvatar, 80, mScore.getY() - 80, 70, 80);
		//画分数
		mScoreFont.draw(mBatch, "分数:", 725, mScore.getY() - 20);
		mBatch.end();

		mBatch.setProjectionMatrix(mCamera.combined);
		//画持刀天兵
		for (Enemy enemy : mEnemyDaos) {
			enemy.render(mBatch, statetime);
		}
		//画Boss
		if (mBoss != null) {
			mBoss.render(mBatch, statetime);
		}

		//画孙悟空
		mMonkey.render(mBatch, statetime);

		/**
		 * 画舞台
		 */
		mStage.act();
		mStage.draw();

		/**
		 * 物理世界
		 */
		mBox2DCamera.position.set(mMonkey.getPosition().x + MyGdxGame.VIEW_WIDTH / 4 / Constant.RATE,
				MyGdxGame.VIEW_HEIGHT / 2 / Constant.RATE, 0);
		//调整2D相机
		adjustBox2DCamera();
		mBox2DCamera.update();
		//渲染物理世界
		mBox2DRender.render(mWorld, mBox2DCamera.combined);
	}

	/**
	 * 调整游戏相机
	 */
	private void adjustCamera() {
		//当相机锚点x坐标小于相机视距一半时，不再移动相机
		if (mCamera.position.x < mCamera.viewportWidth / 2) {
			mCamera.position.x = mCamera.viewportWidth / 2;
		}

		//当相机锚点x坐标大于地图宽度时，不再移动相机
		if (mCamera.position.x > (tileWidth * tileSize - mCamera.viewportWidth / 2)) {
			mCamera.position.x = tileWidth * tileSize - mCamera.viewportWidth / 2;
		}
	}


	/**
	 * 调整物理世界渲染相机
	 */
	private void adjustBox2DCamera() {
		//最小情况 当物理世界相机锚点x坐标小于相机视距一半时，不再移动相机
		if (mBox2DCamera.position.x < mBox2DCamera.viewportWidth / 2) {
			mBox2DCamera.position.x = mBox2DCamera.viewportWidth / 2;
		}

		//最大情况 当物理相机锚点x坐标大于地图宽度时，不再移动相机
		if (mBox2DCamera.position.x > (tileWidth / Constant.RATE * tileSize - mBox2DCamera.viewportWidth / 2)) {
			mBox2DCamera.position.x = tileWidth / Constant.RATE * tileSize - mBox2DCamera.viewportWidth / 2;
		}
	}

	@Override
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			mBody.applyForceToCenter(0, 300, true);
		}
	}

	@Override
	public void dispose() {
		//关闭背景音乐
		switch (level) {
			case 0:        //第一关背景音乐
				MyGdxGame.mAssetManager.getMusic(Constant.LEVEL_0_BGM).stop();
				break;
			case 1:        //第二关背景音乐
				MyGdxGame.mAssetManager.getMusic(Constant.LEVEL_0_BGM).stop();
				break;
			case 2:        //第三关背景音乐
				MyGdxGame.mAssetManager.getMusic(Constant.LEVEL_0_BGM).stop();
				break;
			case 3:        //第四关背景音乐
				MyGdxGame.mAssetManager.getMusic(Constant.LEVEL_0_BGM).stop();
				break;
			case 4:        //第五关背景音乐
				MyGdxGame.mAssetManager.getMusic(Constant.LEVEL_0_BGM).stop();
				break;
		}

		//清空演员
		mStage.getActors().clear();
		//清空舞台
		mStage.clear();
	}
}
