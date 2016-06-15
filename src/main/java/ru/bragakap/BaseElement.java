package ru.bragakap;

import java.io.Serializable;
import java.util.Objects;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

/**
 * Created by Kapitonenko on 15.01.16.
 */
public abstract class BaseElement implements Serializable {
    private int x, y;
    private int width, height;
    private int speed;
    private Color color;


    public void paintMe(GC gc) {
        gc.setBackground(color);
        gc.fillOval(x, y, width, height);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }



    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    private int vx = 0;

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    private int vy = 0;
    private boolean exist = true;
    private String image;

    public String getImage() {
        return image;
    }

    public boolean isExist() {
        return exist;
    }

    public void die() {
        exist = false;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isInside(BaseElement el) {
//        if(this instanceof ru.bragakap.Food)
//            System.out.print(el.getWidth() + " " + el.getHeight() + " " + this.x + " " + this.y + "\n");
        if(el.x >= this.x && el.x <= this.x + this.width && el.y >= this.y && el.y <= this.y + this.height)
            return true;
        if(el.x + el.width >= this.x && el.x + el.width <= this.x + this.width &&
                el.y >= this.y && el.y <= this.y + this.height)
            return true;
        if(el.x >= this.x && el.x <= this.x + this.width && el.y + el.height >= this.y &&
                el.y + el.height <= this.y + this.height)
            return true;
        if(el.x + el.width >= this.x && el.x + el.width <= this.x + this.width &&
                el.y + el.height >= this.y && el.y + el.height <= this.y + this.height)
            return true;
        if(this.x >= el.x && this.x <= el.x + el.width && this.y >= el.y && this.y <= el.y + el.height)
            return true;
        if(this.x + this.width >= el.x && this.x + this.width <= el.x + el.width &&
                this.y >= el.y && this.y <= el.y + el.height)
            return true;
        if(this.x >= el.x && this.x <= el.x + el.width && this.y + this.height >= el.y &&
                this.y + this.height <= el.y + el.height)
            return true;
        return this.x + this.width >= el.x && this.x + this.width <= el.x + el.width &&
                this.y + this.height >= el.y && this.y + this.height <= el.y + el.height;
    }

    abstract public void intersection(Pacman player);
    public void move() {
        x += vx;
        y += vy;
    }

    public void paint() {
        //singleton.paint(image, x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseElement that = (BaseElement) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y) &&
                Objects.equals(width, that.width) &&
                Objects.equals(height, that.height) &&
                Objects.equals(speed, that.speed) &&
                Objects.equals(vx, that.vx) &&
                Objects.equals(vy, that.vy) &&
                Objects.equals(exist, that.exist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height, speed, vx, vy, exist);
    }
}