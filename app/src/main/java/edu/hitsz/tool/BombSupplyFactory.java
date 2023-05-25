package edu.hitsz.tool;

public class BombSupplyFactory implements ToolFactory{
    @Override
    public BaseTool createTool(int locationX, int locationY, int speedX, int speedY) {
        return new BombSupply(locationX,locationY,speedX,speedY);
    }
}
