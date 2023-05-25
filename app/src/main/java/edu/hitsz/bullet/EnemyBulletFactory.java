package edu.hitsz.bullet;

public class EnemyBulletFactory implements BulletFactory{
    @Override
    public BaseBullet createBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        return new EnemyBullet(locationX,locationY,speedX,speedY,power);
    }
}
