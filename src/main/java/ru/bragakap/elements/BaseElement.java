package ru.bragakap.elements;

import java.io.Serializable;
import java.nio.channels.SelectionKey;
import java.util.List;
import java.util.Objects;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import ru.bragakap.util.Settings;

/**
 * Created by Kapitonenko on 15.01.16.
 */
public abstract class BaseElement implements Serializable {
    private int x, y;
    private int width, height;
    private int speed;

    private int colorR;
    private int colorG;
    private int colorB;

    protected String nameOfObject;

    public BaseElement(int x, int y, String nameOfObject) {
        this.x = x;
        this.y = y;

        width = Integer.parseInt(Settings.getProperty(String.format("%s.width", nameOfObject)));
        height = Integer.parseInt(Settings.getProperty(String.format("%s.height", nameOfObject)));
        System.out.println(String.format("%s: %s %s", nameOfObject, width, height));
    }

    public void paintMe(GC gc) {
        Device device = Display.getCurrent();
        gc.setBackground(new Color (device, colorR, colorG, colorB));
        gc.fillOval(x, y, width, height);
    }

    public void setColor(int r, int g, int b) {
        colorB = b;
        colorG = g;
        colorR = r;
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

    public void ressurect() {exist = true;}

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
//        if(this instanceof ru.bragakap.elements.Food)
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

    public void turn(List<BaseElement> elements) {

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