package edu.hitsz.tool;

import java.util.LinkedList;
import java.util.List;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.Observer;

public class BombSupply extends BaseTool{
    private List<Observer> observerList=new LinkedList<>();
    public BombSupply(int locationX,int locationY,int speedX,int speedY){
        super(locationX,locationY,speedX,speedY);
    }
    @Override
    public void effect(HeroAircraft heroAircraft, List<? extends Observer> enemyList,List<? extends Observer> bulletList) {
        observerList.addAll(enemyList);
        observerList.addAll(bulletList);
        notifyObserver();
    }

    public void notifyObserver(){
        for (Observer observer:observerList){
            observer.update();
        }
    }
}
