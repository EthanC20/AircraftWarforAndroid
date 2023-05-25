package edu.hitsz.bullet;

public interface BulletFactory {
    BaseBullet createBullet(int locationX,int locationY,int speedX,int speedY,int power);

}
