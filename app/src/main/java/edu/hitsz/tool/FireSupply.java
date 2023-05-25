package edu.hitsz.tool;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.Observer;
import edu.hitsz.strategy.ScatteringShootStrategy;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.strategy.StraightShootStrategy;

import java.util.List;


public class FireSupply extends BaseTool{
    public FireSupply(int locationX,int locationY,int speedX,int speedY){
        super(locationX,locationY,speedX,speedY);
    }

    @Override
    public void effect(HeroAircraft heroAircraft, List<? extends Observer> enemyList, List<? extends Observer> bulletList) {
        HeroAircraft.fireSupplyCount++;
        Runnable fireSupplyActivate=()->{
            ShootStrategy scatteringShootStrategy =new ScatteringShootStrategy(-1);
            heroAircraft.setShootStrategy(scatteringShootStrategy);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            HeroAircraft.fireSupplyCount--;
            if (HeroAircraft.fireSupplyCount ==0){
                ShootStrategy straightShootStrategy=new StraightShootStrategy(-1);
                heroAircraft.setShootStrategy(straightShootStrategy);
            }
        };
        new Thread(fireSupplyActivate).start();
    }

}
