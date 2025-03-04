import java.awt.*;
import java.awt.event.*;
import  javax.swing.* ;
import java.util.Random;;
public class GamePanel extends JPanel implements ActionListener {
    //var
    static final int screenHeight = 600 ;
    static final int screenWidth = 600 ;
    static final int unitSize = 25 ;
    static final int gameUnits = (screenHeight *screenWidth) / unitSize ;
    static final int delay = 75 ;
        //snake var 
    final int x[] = new int[gameUnits] ;
    final int y[] = new int[gameUnits] ;
    int bodyParts = 6 ;
        //Apple var
    int applesEaten ;
    int appleX;
    int appleY;

    char direction ='R'; // U for Up 
    boolean running = false ;
    Timer timer ;
    Random random ;
    //constructor
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
        for(int i = 0; i < bodyParts; i++) {
        x[i] = 75 - i * 25; // Start with the snake positioned horizontally
        y[i] = 75; // Start with the snake positioned vertically at 75 pixels
    }

    }
    //methods :
    public void startGame(){
        newApple();
        running = true ;
        timer = new Timer(delay,this );
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free" , Font.BOLD , 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score : " + applesEaten, (screenWidth-metrics.stringWidth("Score : " + applesEaten))/2, g.getFont().getSize());
       if (running){
         g.setColor(Color.red);
        g.fillOval(appleX, appleY, unitSize, unitSize);
        for (int i = 0 ; i < bodyParts ; i++){
            g.setColor(Color.green);
            g.fillRect(x[i], y[i], unitSize, unitSize);
        }}else{
           gameOver(g);
       }
    }
    public void move(){
        for (int i = bodyParts ; i > 0 ; i--){
            x[i] =x[i-1];
            y[i] =y[i-1];
        }
        switch (direction) {
            case 'R':
                x[0] = x[0] +unitSize ;
                break;
            case 'L':
                x[0] = x[0] - unitSize ;
                break;
            case 'U':
                y[0] = y[0] - unitSize ;
                break;
            case 'D':
                y[0] =y[0] + unitSize ;
                break;
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(screenWidth/unitSize))*unitSize;
        appleY = random.nextInt((int)(screenHeight/unitSize))*unitSize;
    }
    public void checkApple(){
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
    for(int i = bodyParts ; i > 0 ; i--){
        if (x[0]== x[i] && y[0] == y[i]){
            running = false ;
        }
    }
    if (x[0] < 0) {
        running = false ;
    }
    if (x[0] > screenWidth){
        running = false ;
    }
    if (y[0] < 0){
        running = false ;
    }
    if (y[0] > screenHeight){
        running = false ;
    }
    if (!running) {
        timer.stop();
    }
    }
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free" , Font.BOLD , 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score : " + applesEaten, (screenWidth-metrics.stringWidth("Score : " + applesEaten))/2, g.getFont().getSize());
       g.setColor(Color.red);
        g.setFont(new Font("Ink Free" , Font.BOLD , 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString(("Game Over !!"), (screenWidth-metrics2.stringWidth("Game Over !!"))/2, screenHeight/2);
    }
    
    @Override
        public void actionPerformed(ActionEvent e) {
    if(running){
        move();
        checkApple();
        checkCollisions();
        repaint(); 
    }
}


    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                    direction = 'L';
                 }
                 break;
                 case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                    direction = 'R';
                 }
                 break;
                 case KeyEvent.VK_UP:
                    if (direction != 'D'){
                    direction = 'U';
                 }
                 break;
                 case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                    direction = 'D';
                 }
                 break;
            }
        }
    }
}
