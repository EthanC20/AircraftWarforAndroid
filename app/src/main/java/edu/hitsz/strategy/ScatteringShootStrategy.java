package edu.hitsz.strategy;

import java.util.LinkedList;
import java.util.List;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.BulletFactory;

public class ScatteringShootStrategy extends ShootStrategy{
    protected final int shootNum=3;
    protected int speed=12;
    protected int power=30;
    public ScatteringShootStrategy(int direction) {
        super(direction);
    }

    @Override
    public List<BaseBullet> shoot(int locationX, int locationY, BulletFactory bulletFactory) {
        List<BaseBullet> bulletList=new LinkedList<>();
        BaseBullet bullet;
        double deltaAngle=Math.PI/6;
        int speedX;
        int speedY;
        for (int i=0;i<shootNum;i++){
            speedX=(int)(speed*Math.cos(Math.PI/3+i*deltaAngle));
            speedY=(int)(speed*Math.sin(Math.PI/3+i*deltaAngle))*direction;
            bullet=bulletFactory.createBullet(locationX,
                    locationY+6*direction, speedX,speedY,power);
            bulletList.add(bullet);
        }
        return bulletList;
    }


}
