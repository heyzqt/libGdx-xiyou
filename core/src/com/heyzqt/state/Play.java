package com.heyzqt.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.heyzqt.entity.Monkey;
import com.heyzqt.handle.Box2DContactListener;
import com.heyzqt.handle.Constant;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 游戏界面
 */
public class Play extends GameState {

	//声明世界
	private World mWorld;

	//物理世界相机
	private OrthographicCamera mBox2DCamera;

	//物理世界渲染器
	private Box2DDebugRenderer mBox2DRender;

	//声明刚体监听器
	private Box2DContactListener mContactListener;

	//地图
	private TiledMap mMap;

	//瓦片大小
	private float tileSize;

	//地图宽度（瓦片数量）
	private float tileWidth;

	//地图高度（瓦片数量）
	private float tileHeight;

	//地图渲染器
	private OrthoCachedTiledMapRenderer mMapRenderer;
	private OrthogonalTiledMapRenderer mOrthogonalTiledMapRenderer;

	//游戏渲染时间
	private float statetime;

	//刚体信息
	private BodyDef mBodyDef;

	//刚体
	private Body mBody;

	//游戏主角
	private Monkey mMonkey;

	public Play(GameStateManager manager) {
		super(manager);
		init();
	}

	private void init() {

		//初始化世界 初始化重力
		mWorld = new World(new Vector2(0, -9.8f), true);

		//初始化刚体世界相机
		mBox2DCamera = new OrthographicCamera();
		mBox2DCamera.setToOrtho(false, MyGdxGame.VIEW_WIDTH / Constant.RATE,
				MyGdxGame.VIEW_HEIGHT / Constant.RATE);

		//初始化Box2D渲染器
		mBox2DRender = new Box2DDebugRenderer();

		//初始化相机渲染器
		mMapRenderer = new OrthoCachedTiledMapRenderer(mMap);

		//创建地图
		createMap();

		//创建主角
		createActor();

	}

	private void createActor() {
		//初始化刚体属性
		mBodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		mBodyDef.type = BodyDef.BodyType.DynamicBody;
		//position是刚体中心点的位置
		mBodyDef.position.set(100 / Constant.RATE, 400 / Constant.RATE);
		mBody = mWorld.createBody(mBodyDef);
		shape.setAsBox(30 / Constant.RATE, 30 / Constant.RATE);
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constant.PLAYER;
		fixtureDef.filter.maskBits = Constant.BLOCK;
		mBody.createFixture(fixtureDef).setUserData("player");

		//创建传感器 foot
		shape.setAsBox(25 / Constant.RATE, 3 / Constant.RATE, new Vector2(0, -30 / Constant.RATE), 0);
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constant.PLAYER;
		fixtureDef.filter.maskBits = Constant.BLOCK;
		fixtureDef.isSensor = true;
		mBody.createFixture(fixtureDef).setUserData("foot");

		mMonkey = new Monkey(mBody);
	}

	private void createMap() {
		try {
			mMap = new TmxMapLoader().load("map/level_1.tmx");
		} catch (Exception e) {
			e.printStackTrace();
			Gdx.app.exit();
		}

		//赋值地图参数
		tileSize = mMap.getProperties().get("tilewidth", Integer.class);
		tileWidth = mMap.getProperties().get("width", Integer.class);
		tileHeight = mMap.getProperties().get("height", Integer.class);

		//绑定地面图层与刚体
		TiledMapTileLayer layer = (TiledMapTileLayer) mMap.getLayers().get("movable");

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
				//设置恢复力为0
				chainFixtureDef.friction = 0;
				chainFixtureDef.filter.categoryBits = Constant.BLOCK;
				chainFixtureDef.filter.maskBits = Constant.PLAYER;
				//设置传感器
				chainFixtureDef.isSensor = false;

				mWorld.createBody(bodyDef).createFixture(chainFixtureDef).setUserData("block");
			}
		}
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		handleInput();
		mWorld.step(1 / 60f, 1, 1);
		statetime += Gdx.graphics.getDeltaTime();

		mBox2DCamera.update();
		mBox2DRender.render(mWorld, mBox2DCamera.combined);
	}

	@Override
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			mBody.applyForceToCenter(0, 400, true);
		}
	}

	@Override
	public void dispose() {

	}
}
