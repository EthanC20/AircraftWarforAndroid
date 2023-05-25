package edu.hitsz.aircraft;

import edu.hitsz.ImageManager;
import edu.hitsz.activity.ModeChooseActivity;
import edu.hitsz.bullet.BulletFactory;
import edu.hitsz.bullet.EnemyBulletFactory;
import edu.hitsz.strategy.ScatteringShootStrategy;
import edu.hitsz.strategy.ShootStrategy;

public class BossEnemyFactory implements EnemyFactory{
    private int hp=400;
    private int speedX=2;
    private final int speedY=0;
    private ShootStrategy shootStrategy=new ScatteringShootStrategy(1);
    private final BulletFactory bulletFactory=new EnemyBulletFactory();
    @Override
    public EnemyAircraft createEnemy(double hpIncreaseRate,double speedIncreaseRate) {
        int locationX=(int) (Math.random() * (ModeChooseActivity.screenWidth - ImageManager.BOSS_ENEMY_IMAGE.getWidth()));
        int locationY=(int) (Math.random() * ModeChooseActivity.screenHeight * 0.05);
        return new BossEnemy(locationX,locationY,(int)(this.speedX*(1+speedIncreaseRate)),this.speedY,(int)(this.hp*(1+hpIncreaseRate)),this.shootStrategy,this.bulletFactory);
    }
}
