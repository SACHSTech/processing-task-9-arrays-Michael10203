import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {

  //Spawns in all the images 
  PImage ImgSnowyBackground;
  PImage ImgSnowflake;
  PImage ImgFabRules;

  //Check True of false if the keys are pressed 
  boolean PressedW = false;
  boolean PressedA = false;
  boolean PressedS = false;
  boolean PressedD = false;
  
  //Regular location for Mr fabora 
  float fltFabRulesX = 500;
  float fltFabRulesY = 700; 

  // Spawns the snowflakes at the top 
  float fltSnowflakeX[] = new float[20];
  float fltSnowflakeY[] = new float[20];

  //true or false for the snowflakes 
  boolean isSnowflakeVisible[] = new boolean[20];

  // Starts the Lives at 3
  int playerLives = 3;

  // Game on or off
  boolean gameOver = false;

  public void settings() {
    size(1000, 1000);
  }

  public void setup() {
    background(210, 255, 173);
    ImgSnowflake = loadImage("ImgSnowflakey.png");
    ImgSnowyBackground = loadImage("SnowyBackground.png");
    ImgFabRules = loadImage("FabRules.png");
    
    resizeImage(ImgFabRules, 200, 200);
    resizeImage(ImgSnowflake, 50, 50);

    for (int i = 0; i < fltSnowflakeY.length; i++) {
      fltSnowflakeY[i] = random(height);
      fltSnowflakeX[i] = random(width);
      isSnowflakeVisible[i] = true; // Initially, all snowflakes are visible
    }  
  }
    
  /*
    * Controls the Images Sizes
    * @author Michael liang
    * @param: None
    * @return: Void 
    */
  private void resizeImage(PImage img, int newWidth, int newHeight) {
    img.resize(newWidth, newHeight);
  }

  public void draw() {

    /*
    * When the game is over everything would freeze
    * @author Michael liang
    * @param: Check the lives, if under 3
    * @return: Frozen
    */
    if (!gameOver) {
      image(ImgSnowyBackground, 0, 0);

      // Draw lives
      for (int i = 0; i < playerLives; i++) {
        fill(255, 0, 0);
        rect(width - 30 * (i + 1), 10, 25, 25);
      }

      // Draw snowflakes
      for (int i = 0; i < fltSnowflakeY.length; i++) {
        // Check if the snowflake is visible before drawing
        if (isSnowflakeVisible[i]) {
          image(ImgSnowflake, fltSnowflakeX[i], fltSnowflakeY[i], 100, 100);

          // Collision detection with Fab Rules
          if (dist(fltFabRulesX, fltFabRulesY, fltSnowflakeX[i], fltSnowflakeY[i]) < 50) {
           
           // Subtract players lives
           playerLives--;

            // See if all lives are done
            if (playerLives == 0) {
              gameOver = true;
            }
          
            // Snowflake is not visible after collision
            isSnowflakeVisible[i] = false; 
          }

          // Snowflake movement
          fltSnowflakeY[i] += 2;

          // Reset snowflake position if it goes off the screen
          if (fltSnowflakeY[i] > height) {
            fltSnowflakeY[i] = 0;
            fltSnowflakeX[i] = random(width);
            isSnowflakeVisible[i] = true;
          }
        }
      }

      // Draw Fab Rules
      image(ImgFabRules, fltFabRulesX, fltFabRulesY, 200, 200);

      // Fab Rules movement
      if (PressedW && fltFabRulesY > 0) {
        fltFabRulesY -= 2;
      }
      if (PressedS && fltFabRulesY < height - 100) {
        fltFabRulesY += 2;
      }
      if (PressedA && fltFabRulesX > 0) {
        fltFabRulesX -= 2;
      }
      if (PressedD && fltFabRulesX < width - 100) {
        fltFabRulesX += 2;
      }
    }
  }
 /*
    * KeyPressed
    * @author Michael liang
    * @param: None
    * @return: Void 
    */
  public void keyPressed() {
    if (key == 'w') {
      PressedW = true;
    } else if (key == 's') {
      PressedS = true;
    } else if (key == 'a') {
      PressedA = true;
    } else if (key == 'd') {
      PressedD = true;
    }
  }
 /*
    * Key Released
    * @author Michael liang
    * @param: None
    * @return: Void 
    */
  public void keyReleased() {
    if (key == 'w') {
      PressedW = false;
    } else if (key == 's') {
      PressedS = false;
    } else if (key == 'a') {
      PressedA = false;
    } else if (key == 'd') {
      PressedD = false;
    }
  }
 /*
    * Checks if the mouse is pressed 
    * @author Michael liang
    * @param: None
    * @return: Void 
    */
  public void mousePressed() {
    // Check mouse click on snowflakes and make them disappear
    for (int i = 0; i < fltSnowflakeY.length; i++) {
      if (dist(mouseX, mouseY, fltSnowflakeX[i], fltSnowflakeY[i]) < 50) {
        isSnowflakeVisible[i] = false;
      }
    }
  }
}
