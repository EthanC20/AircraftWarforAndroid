package edu.hitsz.aircraft;

import edu.hitsz.ImageManager;
import edu.hitsz.activity.ModeChooseActivity;
import edu.hitsz.bullet.BulletFactory;
import edu.hitsz.bullet.EnemyBulletFactory;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.strategy.StraightShootStrategy;

public class EliteEnemyFactory implements EnemyFactory{
    private final int speedX=0;
    private int speedY=5;
    private int hp=60;
    private ShootStrategy straightShootStrategy= new StraightShootStrategy(1);
    private BulletFactory bulletFactory=new EnemyBulletFactory();
    @Override
    public EnemyAircraft createEnemy(double hpIncreaseRate,double speedIncreaseRate) {
        int locationX=(int) (Math.random() * (ModeChooseActivity.screenWidth - ImageManager.ELITE_ENEMY_IMAGE.getWidth()));
        int locationY=(int) (Math.random() * ModeChooseActivity.screenHeight * 0.05);
        return new EliteEnemy(locationX,locationY,this.speedX,(int)(this.speedY*(1+speedIncreaseRate)),(int)(this.hp*(1+hpIncreaseRate)),this.straightShootStrategy,this.bulletFactory);
    }
}