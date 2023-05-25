package edu.hitsz.tool;

public interface ToolFactory {
    BaseTool createTool(int locationX,int locationY,int speedX,int speedY);

}
