package edu.hitsz.strategy;

import java.util.List;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.BulletFactory;

public abstract class ShootStrategy {
    protected int direction;
    public ShootStrategy(int direction){
        this.direction=direction;
    }
    public abstract List<BaseBullet> shoot(int locationX, int locationY, BulletFactory bulletFactory);
}
