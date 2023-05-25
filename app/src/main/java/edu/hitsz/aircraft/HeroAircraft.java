package edu.hitsz.aircraft;

import edu.hitsz.ImageManager;
import edu.hitsz.activity.ModeChooseActivity;
import edu.hitsz.bullet.BulletFactory;
import edu.hitsz.bullet.HeroBulletFactory;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.strategy.StraightShootStrategy;

/**
 * 英雄飞机，游戏玩家操控，遵循单例模式（singleton)
 * 【单例模式】
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {
    private int direction=-1;
    public static int fireSupplyCount;
/*
        volatile 修饰，
        singleton = new Singleton() 可以拆解为3步：
        1、分配对象内存(给singleton分配内存)
        2、调用构造器方法，执行初始化（调用 Singleton 的构造函数来初始化成员变量）。
        3、将对象引用赋值给变量(执行完这步 singleton 就为非 null 了)。
        若发生重排序，假设 A 线程执行了 1 和 3 ，还没有执行 2，B 线程来到判断 NULL，B 线程就会直接返回还没初始化的 instance 了。
        volatile 可以避免重排序。
    */
    /** 英雄机对象单例 */
    private volatile static HeroAircraft heroAircraft;

    /**
     * 单例模式：私有化构造方法
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp,ShootStrategy shootStrategy,BulletFactory bulletFactory,int fireSupplyCount) {
        super(locationX, locationY, speedX, speedY, hp,shootStrategy,bulletFactory);
        HeroAircraft.fireSupplyCount =fireSupplyCount;
    }



    /**
     * 通过单例模式获得初始化英雄机
     * 【单例模式：双重校验锁方法】
     * @return 英雄机单例
     */
    public static HeroAircraft getHeroAircraft(){
        if (heroAircraft==null||heroAircraft.hp<=0){
            synchronized (HeroAircraft.class){
                if (heroAircraft==null||heroAircraft.hp<=0){
                    ShootStrategy heroAircraftShootStrategy =new StraightShootStrategy(-1);
                    BulletFactory heroBulletFactory=new HeroBulletFactory();
                    heroAircraft=new HeroAircraft(ModeChooseActivity.screenWidth / 2,
                            ModeChooseActivity.screenHeight - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 1000,heroAircraftShootStrategy,heroBulletFactory,0);
                }
            }
        }
        return heroAircraft;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }
    public void increaseHp(int increase){
        hp+=increase;
        if(hp>maxHp){
            hp=maxHp;
        }
    }
}
