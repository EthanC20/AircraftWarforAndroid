package edu.hitsz.tool;

public class FireSupplyFactory implements ToolFactory{
    @Override
    public BaseTool createTool(int locationX, int locationY, int speedX, int speedY) {
        return new FireSupply(locationX,locationY,speedX,speedY);
    }
}
