package game.object.fixed;

import java.awt.Graphics;

import game.object.FixedObj;

public class Block extends FixedObj {

  public Block(int positionX, int positionY) {
    super(positionX, positionY);
    height = 30;
    width = 30;
  }

  @Override
  public void draw(Graphics g) {}
}
