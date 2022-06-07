package entity;


import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX; 
	public final int screenY;
//	public int hasKey = 0;
	int standCounter = 0;
	boolean moving = false;
	int pixelCounter = 0;
	
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 1;
		solidArea.y = 1;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 46;
		solidArea.height = 46;
		
		
		setDefaultValues();
		getPlayerImage();
		
	}
	
	public void setDefaultValues() {
		
		worldX = 48 * 23; // player's position in the world
		worldY = 48 * 21;
		speed = 4;
		direction = "down"; // any direction is fine
		
		
	}
	public void getPlayerImage() {
		
			up1 = setup("boy_up_1");
			up2 = setup("boy_up_2");
			down1 = setup("boy_down_1");
			down2 = setup("boy_down_2");
			left1 = setup("boy_left_1");
			left2 = setup("boy_left_2");
			right1 = setup("boy_right_1");
			right2 = setup("boy_right_2");
		
		}
	
	
	public BufferedImage setup(String imageName) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage Image = null;
		
		try {
			Image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
			Image = uTool.scaleImage(Image, gp.tileSize, gp.tileSize);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return Image;
	}
	public void update() {
						
		if(moving == false) {
			if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) { 
				
				if(keyH.upPressed == true) {
					direction = "up";
					 
				}
				else if(keyH.downPressed == true) {
					direction = "down";
					
				}
				else if(keyH.leftPressed == true) {
					direction = "left";
					
				}
				else if(keyH.rightPressed == true) {
					direction = "right";
					
				}
				
				moving = true;
				
				// Checks for tile collision
				collisionOn = false;
				gp.cChecker.checkTile(this);
				
				// CHECK OBJECT COLLISION
				int  objIndex = gp.cChecker.checkObject(this, true);
				pickUpObject(objIndex);
				
				}
			else {
				standCounter++;
				
				if(standCounter == 20) {
					spriteNum = 1;
					standCounter = 0;
				}
		}
		}
		if(moving == true) {
			if(collisionOn == false) {
				
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
			spriteCounter++;
			if(spriteCounter > 17) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
			
			pixelCounter += speed;
			
			if(pixelCounter == 48) {
				moving = false;
				pixelCounter = 0;
				
			}
			
			
		}
		
	}
		
	
	public void pickUpObject(int i) {
		
		if(i != 999) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if (spriteNum == 1) {
				image = up1;
				
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
				
			}
			if(spriteNum == 2) {
				image = down2;
				
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
				
			}
			if(spriteNum == 2) {
				image = left2;
				
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
				
			}
			if(spriteNum == 2) {
				image = right2;
				
			}
			break;
	}
		
		int x = screenX;
		int y = screenY;
		
		if(screenX > worldX) {
			x = worldX;
		}
		if(screenY > worldY) {
			y = worldY;
		}
		int rightOffset = gp.screenWidth - screenX;
		if(rightOffset > gp.worldWidth - worldX) {
			x = gp.screenWidth - (gp.worldWidth - worldX);
		}
		int bottomOffset = gp.screenHeight - screenY;
		if(bottomOffset > gp.worldHeight - worldY) {
			y = gp.screenHeight - (gp.worldHeight - worldY);
		}
		
		
		
	g2.drawImage(image, x, y, null);
	// g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
	
}
	}