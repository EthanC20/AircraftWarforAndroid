package edu.hitsz.aircraft;

public interface EnemyFactory {
    EnemyAircraft createEnemy(double hpIncreaseRate,double speedIncreaseRate);
}
