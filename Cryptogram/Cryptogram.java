package Cryptogram;

 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;

public class Cryptogram extends Window {
  public static final int dCode = 15, dGuess = 30, xM = 50, yM = 50, lineGap = 10, W = 15, H = 45;
  public static G.V SPACE = new G.V(W, 0), START = new G.V(xM, yM), NL = new G.V(0, lineGap + H);
  public static Cell.List cells = new Cell.List();
  public static Font font = new Font("Verdana", Font.PLAIN, 20);

  public Cryptogram() {
    super("Cryptogram", 1000, 800);
    // Cell c = new Cell(Pair.alpha[0]);
    // Cell.selected = c;
    // c.p.guess = "B";
    // new Cell(Pair.alpha[3]);
    // Cell.newLine();
    // new Cell(Pair.alpha[5]);
    loadQuote("NOW IS THE TIME FOR ALL GOOD MAN");
  }

  public void loadQuote(String str){
    // str = validate(str) 
    cells.clear();
    Pair.shuffle();// no guess
    for(int i = 0; i < str.length(); i++){
      char c = str.charAt(i);
      int iAlpha = c - 'A';
      if(c >= 'A' && c <= 'Z'){
        new Cell(Pair.alpha[iAlpha]);
      }else{
        Cell.space();
      }
    }

  }

  public void paintComponent(Graphics g) {
    G.whiteBackground(g);
    g.setFont(font);
    cells.show(g);
  }

  @Override
  public void mouseClicked(MouseEvent me) {
    int x = me.getX(), y = me.getY();
    Cell.selected = cells.hit(x, y);
    repaint();
  }

  @Override
  public void keyTyped(KeyEvent ke) {
    char c = ke.getKeyChar();
    if (c >= 'a' && c <= 'z') {
      c = (char) (c - 'a' + 'A');// convert uppercase
    }
    if (Cell.selected != null) {
      Cell.selected.p.guess = (c >= 'A' && c <= 'Z') ? "" + c : "";
      repaint();
    }
  }

  @Override
  public void keyPressed(KeyEvent ke) {
    int vk = ke.getKeyCode();
    if (Cell.selected != null) {
      if (vk == KeyEvent.VK_LEFT) {
        Cell.selected.left();
      }
      if (vk == KeyEvent.VK_RIGHT) {
        Cell.selected.right();
      }
      repaint();
    }

  }

  public static void main(String[] args) {
    Window.PANEL = new Cryptogram();
    Window.launch();
  }

  // --------------Pair----------------------
  public static class Pair {
    public char actual, code;
    public String guess;// can be null
    public static Pair[] alpha = new Pair[26];
    static {
      for (int i = 0; i < 26; i++) {
        alpha[i] = new Pair((char) ('A' + i));
      }
    }

    public Pair(char c) {
      actual = c;
      code = c;
      guess = "";
    }
  }

  // ------------Cell-------------------------
  public static class Cell {

    public Pair p;
    public int ndx;
    public G.V loc = new G.V();
    public static G.V nextLoc = new G.V(START);
    public static G.V nextLine = new G.V(START);
    public static G.VS vs = new G.VS(0, 0, W, H);
    public static Cell selected = null;

    public Cell(Pair pair) {
      p = pair;
      loc.set(nextLoc);
      space();
      ndx = cells.size();
      cells.add(this);
    }

    public static void space(){
      nextLoc.add(SPACE);
    }

    public void show(Graphics g) {
      if (this == Cell.selected) {
        vs.loc.set(loc);
        vs.draw(g, Color.ORANGE);
      }

      g.setColor(Color.BLACK);
      g.drawString("" + p.code, loc.x, loc.y + dCode);
      g.drawString(p.guess, loc.x, loc.y + dGuess);
    }

    public static void newLine() {
      nextLine.add(NL);
      nextLoc.set(nextLine);
    }

    public boolean hit(int x, int y) {
      vs.loc.set(loc);
      return vs.hit(x, y);
    }

    public void left() {
      if (ndx > 0) {
        Cell.selected = cells.get(ndx - 1);
      }
    }

    public void right() {
      if (ndx < cells.size() - 1) {
        Cell.selected = cells.get(ndx + 1);
      }
    }

    // ---------------CellList----------------
    public static class List extends ArrayList<Cell> {

      public void show(Graphics g) {
        for (Cell c : this) {
          c.show(g);
        }
      }

      public Cell hit(int x, int y) {
        for (Cell c : this) {
          if (c.hit(x, y)) {
            return c;
          }
        }
        return null;
      }
    }

  }

}
