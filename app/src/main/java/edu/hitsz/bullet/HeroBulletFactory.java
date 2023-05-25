package edu.hitsz.bullet;

public class HeroBulletFactory implements BulletFactory{
    @Override
    public BaseBullet createBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        return new HeroBullet(locationX,locationY,speedX,speedY,power);
    }
}
