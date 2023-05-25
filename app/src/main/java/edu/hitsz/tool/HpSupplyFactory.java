package edu.hitsz.tool;

public class HpSupplyFactory implements ToolFactory{
    @Override
    public BaseTool createTool(int locationX, int locationY, int speedX, int speedY) {
        return new HpSupply(locationX,locationY,speedX,speedY);
    }
}
