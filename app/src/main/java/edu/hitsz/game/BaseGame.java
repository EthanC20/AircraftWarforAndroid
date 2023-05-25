package edu.hitsz.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.hitsz.ImageManager;
import edu.hitsz.R;
import edu.hitsz.activity.ModeChooseActivity;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.BossEnemyFactory;
import edu.hitsz.aircraft.EliteEnemyFactory;
import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.EnemyFactory;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemyFactory;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.basic.RandomNumGenerator;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.music.Bgm;
import edu.hitsz.music.MusicController;
import edu.hitsz.tool.BaseTool;
import edu.hitsz.tool.BombSupply;

/**
 * 游戏逻辑抽象基类，遵循模板模式，action() 为模板方法
 * 包括：游戏主面板绘制逻辑，游戏执行逻辑。
 * 子类需实现抽象方法，实现相应逻辑
 * @author hitsz
 */
public abstract class BaseGame extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    public static final String TAG = "BaseGame";
    boolean mbLoop = false; //控制绘画线程的标志位
    private SurfaceHolder mSurfaceHolder;
    private Canvas canvas;  //绘图的画布
    private Paint mPaint;
    private Handler handler;
    private Context context;

    //点击屏幕位置
    float clickX = 0, clickY=0;

    private int backGroundTop = 0;

    /**
     * 背景图片缓存，可随难度改变
     */
    protected Bitmap backGround;



    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private final int timeInterval = 16;

    private final HeroAircraft heroAircraft;

    protected final List<EnemyAircraft> enemyAircraft;

    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<BaseTool> toolList;

    protected int enemyMaxNumber = 5;

    private boolean gameOverFlag = false;

    private int score = 0;
    private int bossGeneratorScore=0;
    private final int createBossScore=300;

    protected Thread difficultyController;

    private int time = 0;

    private int bossNum=0;
    private int cycle=0;
    /**
     * 周期（ms)
     * 控制英雄机射击周期，默认值设为简单模式
     */
    private int enemyCycleDuration = 600;
    private int heroCycleDuration=500;
    private int enemyCycleTime = 0;
    private int heroCycleTime=0;
    private int mobRate=70;
    private double enemyIncreaseRate=0;
    private int bossMaxNum=1;
    private int cycleNum=3;
    private boolean haveMusic;
    private final MusicController musicController;


    public BaseGame(Context context, Handler handler, boolean haveMusic){
        super(context);
        this.haveMusic=haveMusic;
        this.handler = handler;
        mbLoop = true;
        mPaint = new Paint();  //设置画笔
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        this.setFocusable(true);
        ImageManager.initImage(context);
        this.context=context;

        //音效
        musicController=new MusicController(haveMusic,context);

        musicController.bgmDisplay();

        // 初始化英雄机
        heroAircraft = HeroAircraft.getHeroAircraft();
        heroAircraft.setHp(1000);

        enemyAircraft = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        toolList=new LinkedList<>();

        heroController();

        if (increaseDifficulty()){
            Runnable increaseDifficulty=()->{
                while (!gameOverFlag){
                    try {
                        Thread.sleep(getSleepTime());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    this.enemyIncreaseRate+=getEnemyIncreaseRate();
                    this.enemyCycleDuration*=0.98;
                    this.heroCycleDuration*=0.98;
                    this.mobRate-=1;
                    this.mobRate=Math.max(20,mobRate);
                    System.out.println("提高难度！精英机概率："+(100-mobRate)+"%,敌机属性提升倍率："+(enemyIncreaseRate+1)+",敌机发射子弹频率上升5%");
                }
            };
            difficultyController=new Thread(increaseDifficulty);
            difficultyController.start();
        }
    }
    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {
        EnemyFactory mobEnemyFactory=new MobEnemyFactory();
        EnemyFactory eliteEnemyFactory=new EliteEnemyFactory();
        EnemyFactory bossEnemyFactory=new BossEnemyFactory();
        Runnable task = () -> {

                time += timeInterval;

            // 周期性执行（控制频率）
            if (enemyTimeCountAndNewCycleJudge()) {
                bossNum=0;
                for (EnemyAircraft enemy: enemyAircraft){
                    if (enemy instanceof BossEnemy){
                        bossNum++;
                    }
                }
                // 新敌机产生
                if (enemyAircraft.size() < enemyMaxNumber) {
                    if(bossGeneratorScore>createBossScore){
                        bossGeneratorScore-=createBossScore;
                        while (bossNum<bossMaxNum&&haveBoss()){
                            EnemyAircraft boss;
                            //困难模式boss敌机属性随时间上升
                            if (bossIncrease()){
                                boss=bossEnemyFactory.createEnemy(cycle*getBossHpIncreaseRate(),enemyIncreaseRate);
                                System.out.println("产生BOSS敌机");
                                System.out.println("Boss敌机血量倍率"+(1+cycle*getBossHpIncreaseRate()));
                            }
                            else {
                                boss=bossEnemyFactory.createEnemy(0,0);
                            }
                            cycle++;
                            if (cycle%cycleNum==0&&bossIncrease()){
                                enemyMaxNumber++;
                                System.out.println("提高难度！屏幕中敌机的最大数目增加，为："+enemyMaxNumber);
                                bossMaxNum++;
                                System.out.println("提高难度！屏幕中Boss敌机的最大数目增加，为："+bossMaxNum);
                                cycleNum*=2;
                            }
                            MediaPlayer bossBgmPlayer=MediaPlayer.create(context,R.raw.bgm_boss);
                            boss.setMediaPlayer(bossBgmPlayer,haveMusic);
                            enemyAircraft.add(boss);
                            bossNum++;
                        }
                    }else {
                        int decision= RandomNumGenerator.getRandom();
                        if(decision<=mobRate){
                            EnemyAircraft mobEnemy=mobEnemyFactory.createEnemy(enemyIncreaseRate,enemyIncreaseRate);
                            enemyAircraft.add(mobEnemy);
                        }else {
                            EnemyAircraft eliteEnemy=eliteEnemyFactory.createEnemy(enemyIncreaseRate,enemyIncreaseRate);
                            enemyAircraft.add(eliteEnemy);
                        }
                    }
                }
                enemyShootAction();
            }
            if(heroTimeCountAndNewCycleJudge()){
                heroShootAction();
            }
            // 子弹移动
            bulletsMoveAction();
            // 飞机移动
            aircraftMoveAction();

            // 撞击检测
            try {
                crashCheckAction();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 后处理
            postProcessAction();
            if (heroAircraft.getHp() <= 0) {
                gameOver();
            }

            try {
                Thread.sleep(timeInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
    }

    public void heroController(){
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                clickX = motionEvent.getX();
                clickY = motionEvent.getY();
                heroAircraft.setLocation(clickX, clickY);

                if ( clickX<0 || clickX> ModeChooseActivity.screenWidth || clickY<0 || clickY> ModeChooseActivity.screenHeight){
                    // 防止超出边界
                    return false;
                }
                return true;
            }
        });
    }


    private void heroShootAction() {
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }
    private void enemyShootAction() {
        for (AbstractAircraft enemy : enemyAircraft) {
            enemyBullets.addAll(enemy.shoot());
        }
    }
    private boolean enemyTimeCountAndNewCycleJudge() {
        enemyCycleTime += timeInterval;
        if (enemyCycleTime >= enemyCycleDuration && enemyCycleTime - timeInterval < enemyCycleTime) {
            // 跨越到新的周期
            enemyCycleTime %= enemyCycleDuration;
            return true;
        } else {
            return false;
        }
    }
    private boolean heroTimeCountAndNewCycleJudge() {
        heroCycleTime += timeInterval;
        if (heroCycleTime >= heroCycleDuration && heroCycleTime - timeInterval < heroCycleTime) {
            // 跨越到新的周期
            heroCycleTime %= heroCycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircraft) {
            enemyAircraft.forward();
        }
        for (BaseTool tool:toolList){
            tool.forward();
        }
    }


    /**
     * 碰撞检测：
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() throws InterruptedException {
        // 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet==null||bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet==null||bullet.notValid()) {
                continue;
            }
            for (EnemyAircraft enemyAircraft : enemyAircraft) {
                if (enemyAircraft==null||enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    musicController.bulletHitBgmDisplay();
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }
        // 我方获得补给
        for (BaseTool gameTool:toolList){
            if(gameTool==null||gameTool.notValid()){
                continue;
            }
            if(heroAircraft.crash(gameTool)){
                gameTool.effect(heroAircraft,enemyAircraft,enemyBullets);
                gameTool.vanish();
                musicController.getSupplyBgmDisplay();
                if (gameTool instanceof BombSupply){
                    musicController.bombBgmDisplay();
                }
            }
        }
        for (EnemyAircraft enemyAircraft:enemyAircraft){
            if (enemyAircraft.notValid()) {
                List<BaseTool> newTools=enemyAircraft.drop();
                if (newTools!=null){
                    toolList.addAll(newTools);
                }
                score += enemyAircraft.getScore();
                bossGeneratorScore+=enemyAircraft.getScore();
            }
        }
    }
    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircraft.removeIf(AbstractFlyingObject::notValid);
        toolList.removeIf(AbstractFlyingObject::notValid);

    }

    public void draw() {
        canvas = mSurfaceHolder.lockCanvas();
        if(mSurfaceHolder == null || canvas == null){
            return;
        }

        //绘制背景，图片滚动
        canvas.drawBitmap(backGround,0,this.backGroundTop-backGround.getHeight(),mPaint);
        canvas.drawBitmap(backGround,0,this.backGroundTop,mPaint);
        backGroundTop +=1;
        if (backGroundTop == ModeChooseActivity.screenHeight)
            this.backGroundTop = 0;

        //先绘制子弹，后绘制飞机
        paintImageWithPositionRevised(enemyBullets); //敌机子弹


        paintImageWithPositionRevised(heroBullets);  //英雄机子弹


        paintImageWithPositionRevised(enemyAircraft);//敌机

        paintImageWithPositionRevised(toolList);//道具

        canvas.drawBitmap(ImageManager.HERO_IMAGE,
                heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY()- ImageManager.HERO_IMAGE.getHeight() / 2,
                mPaint);

        //画生命值
        paintScoreAndLife();

        mSurfaceHolder.unlockCanvasAndPost(canvas);

    }

    private void paintImageWithPositionRevised(List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            Bitmap image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            canvas.drawBitmap(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, mPaint);
        }
    }

    private void paintScoreAndLife() {
        int x = 10;
        int y = 40;

        mPaint.setColor(Color.RED);
        mPaint.setTextSize(50);

        canvas.drawText("SCORE:" + this.score, x, y, mPaint);
        y = y + 60;
        canvas.drawText("LIFE:" + this.heroAircraft.getHp(), x, y, mPaint);
    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        new Thread(this).start();
        Log.i(TAG, "start surface view thread");
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        ModeChooseActivity.screenWidth = i1;
        ModeChooseActivity.screenHeight = i2;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        mbLoop = false;
    }

    @Override
    public void run() {

        while (mbLoop){   //游戏结束停止绘制
            synchronized (mSurfaceHolder){
                action();
                draw();
            }
        }
        Message message = Message.obtain();
        message.what = 1 ;
        message.arg1=score;
        handler.sendMessage(message);
    }

    public int getScore() {
        return score;
    }

    private void gameOver(){
        // 游戏结束
        for (EnemyAircraft enemy:enemyAircraft){
            enemy.vanish();
        }
        mbLoop = false;
        gameOverFlag = true;
        musicController.bgmStop();
        musicController.gameOverBgmDisplay();
        System.out.println("Game Over!");
    }

    public abstract boolean haveBoss();
    public abstract boolean increaseDifficulty();
    public abstract int getSleepTime();
    public abstract boolean bossIncrease();
    public abstract double getBossHpIncreaseRate();
    public abstract double getEnemyIncreaseRate();
}
