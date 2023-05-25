package edu.hitsz.strategy;

import java.util.LinkedList;
import java.util.List;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.BulletFactory;

public class StraightShootStrategy extends ShootStrategy{
    private final int speedX=0;
    private int speedY=12;
    private int power=30;
    public StraightShootStrategy(int direction){
        super(direction);
    }
    public void increaseSpeed(){
        speedY++;
    }
    public void increasePower(){
        power+=2;
    }

    @Override
    public List<BaseBullet> shoot(int locationX, int locationY,BulletFactory bulletFactory) {
        List<BaseBullet> bulletList=new LinkedList<>();
        bulletList.add(bulletFactory.createBullet(locationX,locationY+2*direction,speedX,speedY*direction,power));
        return bulletList;
    }
}
