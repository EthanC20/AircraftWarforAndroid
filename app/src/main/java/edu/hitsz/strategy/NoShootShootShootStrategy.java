package edu.hitsz.strategy;

import java.util.LinkedList;
import java.util.List;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.BulletFactory;

public class NoShootShootShootStrategy extends ShootStrategy {
    public NoShootShootShootStrategy(int direction) {
        super(direction);
    }

    @Override
    public List<BaseBullet> shoot(int locationX, int locationY, BulletFactory bulletFactory) {
        return new LinkedList<>();
    }
}
