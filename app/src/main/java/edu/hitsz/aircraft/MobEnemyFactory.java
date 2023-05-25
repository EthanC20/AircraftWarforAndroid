package edu.hitsz.aircraft;

import edu.hitsz.ImageManager;
import edu.hitsz.activity.ModeChooseActivity;
import edu.hitsz.bullet.BulletFactory;
import edu.hitsz.bullet.EnemyBulletFactory;
import edu.hitsz.strategy.NoShootShootShootStrategy;
import edu.hitsz.strategy.ShootStrategy;

public class MobEnemyFactory implements EnemyFactory{
    private int hp=30;
    private final int speedX=0;
    private  int speedY=5;
    private final ShootStrategy shootStrategy=new NoShootShootShootStrategy(0);
    private final BulletFactory bulletFactory=new EnemyBulletFactory();
    @Override
    public EnemyAircraft createEnemy(double hpIncreaseRate,double speedIncreaseRate) {
        int locationX=(int) (Math.random() * (ModeChooseActivity.screenWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth()));
        int locationY=(int) (Math.random() * ModeChooseActivity.screenHeight * 0.05);
        return new MobEnemy(locationX,locationY,this.speedX,(int)(this.speedY*(1+speedIncreaseRate)),(int)(this.hp*(1+hpIncreaseRate)),this.shootStrategy,this.bulletFactory);
    }
}