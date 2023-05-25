package edu.hitsz.tool;

import java.util.List;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.Observer;

public class HpSupply extends BaseTool{
    private int hpAddNum=100;
    public HpSupply(int locationX,int locationY,int speedX,int speedY){
        super(locationX,locationY,speedX,speedY);
    }

    @Override
    public void effect(HeroAircraft heroAircraft, List<? extends Observer> enemyList, List<? extends Observer> bulletList) {
        heroAircraft.increaseHp(hpAddNum);
    }
}

