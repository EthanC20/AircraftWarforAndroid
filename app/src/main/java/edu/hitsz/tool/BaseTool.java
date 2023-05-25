package edu.hitsz.tool;

import java.util.List;

import edu.hitsz.activity.ModeChooseActivity;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.Observer;
import edu.hitsz.basic.AbstractFlyingObject;

public abstract class BaseTool extends AbstractFlyingObject {
    public BaseTool(int locationX,int locationY,int speedX,int speedY){
        super(locationX,locationY,speedX,speedY);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 x 轴出界
        if (locationX <= 0 || locationX >= ModeChooseActivity.screenWidth) {
            vanish();
        }

        // 判定 y 轴出界
        if (speedY > 0 && locationY >= ModeChooseActivity.screenHeight ) {
            // 向下飞行出界
            vanish();
        }else if (locationY <= 0){
            // 向上飞行出界
            vanish();
        }
    }

    public abstract void effect(HeroAircraft heroAircraft, List<? extends Observer> enemyList, List<? extends Observer> bulletList);
}