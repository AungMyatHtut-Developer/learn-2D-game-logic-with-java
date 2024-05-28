//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//
//public class GamePanel extends JPanel implements Runnable {
//
//    private static final int PWIDTH = 500; //size of panel
//    private static final int PHEIGHT = 400;
//    private Thread animator; // for the animation
//    private volatile boolean running = false; //stops the animation
//
//    private static final int NO_DELAYS_PER_YIELD = 16;
//    private static int MAX_FRAME_SKIPS = 5;
//    private volatile boolean isPaused = false;
//
//    private int period = 10;
//
//    // global variables for off-screen rendering
//    private Graphics dbg;
//    private Image dbImage = null;
//
//
//    public GamePanel() {
//        setBackground(Color.white); // white background
//        setPreferredSize(new Dimension(PWIDTH,PHEIGHT));
//
//        setFocusable(true);
//        requestFocus(); // JPanel now receives key events
//        readyForTermination();
//
//        // listen for mouse presses
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                testPress(e.getX(), e.getY());
//            }
//        });
//    }
//
//    private void testPress(int x, int y) {
//        if(!isPaused && !gameOver){
//            //do something
//        }
//    }
//
//    private void readyForTermination() {
//        addKeyListener(new KeyAdapter() {
//            // listen for esc, q, end, ctrl-c
//            public void keyPressed(KeyEvent e) {
//                int keyCode = e.getKeyCode();
//                if((keyCode == KeyEvent.VK_ESCAPE) ||
//                        (keyCode == KeyEvent.VK_Q) ||
//                        (keyCode == KeyEvent.VK_END) ||
//                        (keyCode == KeyEvent.VK_C) && e.isControlDown()){
//                    running = false;
//                }
//            }
//        });
//    }
//
//    public void addNotify() {
//        super.notify(); // creates the peer
//        startGame();
//    }
//
//    private void startGame() {
//        if (animator == null || !running) {
//            animator = new Thread(this);
//            animator.start();
//        }
//    }
//
//    public void pauseGame() {
//        isPaused = true;
//    }
//    public void stopGame() {
//        running = false;
//    }
//
//    @Override
//    public void run() {
//
//        long beforeTime, afterTime, timeDiff, sleepTime;
//        long overSleepTime = 0;
//        int noDelays = 0;
//        long excess = 0;
//
//        beforeTime = System.nanoTime();
//
//        running = true;
//        while (running) {
//
//            try {
//                if (isPaused) {
//                    synchronized (this) {
//                        while (isPaused && running) {
//                            wait();
//                        }
//                    }
//                }
//            } catch (InterruptedException exception) {
//
//            }
//
//            gameUpdate(); //game state is updated
//            gameRender(); // render to a buffer
//            paintScreen(); // draw buffer to screen
//
//            afterTime = System.nanoTime();
//            timeDiff = afterTime -  beforeTime;
//            sleepTime = (period - timeDiff) - overSleepTime;
//
//            if (sleepTime > 0) { // some time left in this cycle
//                try {
//                    Thread.sleep(sleepTime / 1_000_000);
//                } catch (InterruptedException exception) {
//                    overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
//                }
//            }else{
//                excess -= sleepTime; // store excess time value
//                overSleepTime = 0;
//                if (++noDelays >= NO_DELAYS_PER_YIELD) {
//                    Thread.yield(); //give another thread a chance to run
//                    noDelays = 0;
//                }
//            }
//
//            beforeTime = System.nanoTime();
//
//            int skips = 0;
//            while ((excess > period) && (skips < MAX_FRAME_SKIPS)) {
//                excess -= period;
//                gameUpdate(); // update state but don't render
//                skips++;
//            }
//        }
//        System.exit(0); // so enclosing JFrame/JApplet exits
//    }
//
//    private void paintScreen() {
//        Graphics g;
//        try {
//            g = this.getGraphics(); // get the panel's graphic context
//            if ((g != null) && (dbImage != null)) {
//                g.drawImage(dbImage, 0, 0, null);
//            }
//            Toolkit.getDefaultToolkit().sync(); // sync the display on some systems
//            g.dispose();
//        } catch (Exception e) {
//            System.out.println("Graphics context error : "+e);
//        }
//    }
//
//    private void gameRender() {
//        if (dbImage == null) {
//            dbImage = createImage(PWIDTH, PHEIGHT);
//            if (dbImage == null) {
//                System.out.println("dbImage is null");
//                return;
//            }
//        }else{
//            dbg = dbImage.getGraphics();
//        }
//
//        //clear the background
//        dbg.setColor(Color.WHITE);
//        dbg.fillRect(0,0, PWIDTH,PHEIGHT);
//
//        //draw game elements
//
//        if (gameOver) {
//            gameOverMessage(dbg);
//        }
//    }
//
//    private void gameOverMessage(Graphics g) {
//        g.drawString(msg, x,y);
//    }
//
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        if (dbImage != null) {
//            g.drawImage(dbImage, 0, 0, null);
//        }
//    }
//
//    private void gameUpdate() {
//        if (!isPaused && !gameOver) {
//            //update game state
//        }
//    }
//
//    public void resumeGame() {
//        isPaused = false;
//    }
//}
