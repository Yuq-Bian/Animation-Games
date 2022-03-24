package Cryptogram;

import java.awt.Color;
import java.util.Random;
import java.awt.*;

public class G {
  public static Random RANDOM = new Random();

  public static int rnd(int k) {
    return RANDOM.nextInt(k);
  }

  public static void whiteBackground(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, 5000, 5000);
  }

  // ----------------------V-----------------------------
  public static class V {
    public int x = 0, y = 0;

    public V(V v){
      x = v.x;
      y = v.y;
    }

    public V(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public V(){
      x = 0;
      y = 0;
    }

    public void set(V v){x = v.x; y = v.y;}
    public void add(V v){x += v.x; y += v.y;}

  }

  // -----------------------VS---------------------------
  public static class VS {
    public V loc, size;

    public VS(int x, int y, int w, int h) {
      loc = new V(x, y);
      size = new V(w, h);
    }

    public void fill(Graphics g, Color c) {
      g.setColor(c);
      g.fillRect(loc.x, loc.y, size.x, size.y);
    }

    public void draw(Graphics g, Color c) {
      g.setColor(c);
      g.drawRect(loc.x, loc.y, size.x, size.y);
    }

    public boolean hit(int x, int y) {
      return x > loc.x && y > loc.y && x < loc.x + size.x && y < loc.y + size.y;
    }

  }

}
