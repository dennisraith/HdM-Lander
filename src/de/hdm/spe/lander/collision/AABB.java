
package de.hdm.spe.lander.collision;

import android.util.Log;

import de.hdm.spe.lander.math.MathHelper;
import de.hdm.spe.lander.math.Vector2;


public class AABB implements Shape2D {

    protected Vector2 BOTTOM_LEFT;
    protected Vector2 TOP_RIGHT;

    public AABB() {
        this.BOTTOM_LEFT = new Vector2();
        this.TOP_RIGHT = new Vector2();
    }

    public AABB(Vector2 min, Vector2 max) {
        this.BOTTOM_LEFT = new Vector2(Math.min(min.v[0], max.v[0]), Math.min(min.v[1], max.v[1]));
        this.TOP_RIGHT = new Vector2(Math.max(min.v[0], max.v[0]), Math.max(min.v[1], max.v[1]));
    }

    public AABB(Vector2 position, float width, float height) {
        this.BOTTOM_LEFT = new Vector2(position.v[0] - 0.5f * width, position.v[1] - 0.5f * height);
        this.TOP_RIGHT = new Vector2(position.v[0] + 0.5f * width, position.v[1] + 0.5f * height);
    }

    public AABB(float x, float y, float width, float height) {
        this.BOTTOM_LEFT = new Vector2(x, y);
        this.TOP_RIGHT = new Vector2(x + width, y + height);
    }

    @Override
    public boolean intersects(Shape2D shape) {
        return shape.intersects(this);
    }

    @Override
    public boolean intersects(Point point) {
        Vector2 position = point.getPosition();
        if (position.getX() < this.getMin().getX() || position.getX() > this.getMax().getX())
            return false;
        if (position.getY() < this.getMin().getY() || position.getY() > this.getMax().getY())
            return false;
        return true;
    }

    @Override
    public boolean intersects(Circle circle) {
        Vector2 center = circle.getCenter();

        if (center.getX() >= this.getMin().getX() && center.getX() <= this.getMax().getX())
            return true;
        if (center.getY() >= this.getMin().getY() && center.getY() <= this.getMax().getY())
            return true;

        Vector2 nearestPosition = new Vector2(
                MathHelper.clamp(center.getX(), this.getMin().getX(), this.getMax().getX()),
                MathHelper.clamp(center.getY(), this.getMin().getY(), this.getMax().getY()));

        float radius = circle.getRadius();
        return nearestPosition.getLengthSqr() < radius * radius;
    }

    void log(String name, float diff) {
        Log.d(this.getClass().getName(), "intersection: " + name + " " + diff);
    }

    void log() {
        this.log("minX", this.getMin().getX());
        this.log("maxX", this.getMax().getX());
        this.log("minY", this.getMin().getY());
        this.log("maxY", this.getMax().getY());
    }

    @Override
    public boolean intersects(AABB box) {
        //        this.log();
        //        box.log();
        float diffMaxX = box.getMax().getX() - this.getMax().getX();
        float diffMinX = box.getMin().getX() - this.getMin().getX();
        float diffMaxY = box.getMax().getY() - this.getMax().getY();
        float diffMinY = box.getMin().getY() - this.getMin().getY();

        //        this.log("diffmaxY", diffMaxX);
        //        this.log("diffMinX", diffMinX);
        //        this.log("diffMaxY", diffMaxY);
        //        this.log("diffMinY", diffMinY);

        if (this.getMin().getX() >= box.getMax().getX() || this.getMax().getX() <= box.getMin().getX())
            return false;
        if (this.getMin().getY() >= box.getMax().getY() || this.getMax().getY() <= box.getMin().getY())
            return false;
        return true;
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(
                0.5f * (this.getMin().v[0] + this.getMax().v[0]),
                0.5f * (this.getMin().v[1] + this.getMax().v[1]));
    }

    @Override
    public void setPosition(Vector2 position) {
        Vector2 size = this.getSize();
        this.getMin().v[0] = position.v[0] - 0.5f * size.v[0];
        this.getMin().v[1] = position.v[1] - 0.5f * size.v[1];
        this.getMax().v[0] = position.v[0] + 0.5f * size.v[0];
        this.getMax().v[1] = position.v[1] + 0.5f * size.v[1];
    }

    public Vector2 getMin() {
        return this.BOTTOM_LEFT;
    }

    public void setMin(Vector2 min) {
        this.getMin().v[0] = Math.min(min.v[0], this.TOP_RIGHT.v[0]);
        this.getMin().v[1] = Math.min(min.v[1], this.TOP_RIGHT.v[1]);
        this.getMax().v[0] = Math.max(min.v[0], this.TOP_RIGHT.v[0]);
        this.getMax().v[1] = Math.max(min.v[1], this.TOP_RIGHT.v[1]);
    }

    public Vector2 getMax() {
        return this.TOP_RIGHT;
    }

    public void setMax(Vector2 max) {
        this.TOP_RIGHT.v[0] = Math.max(max.v[0], this.BOTTOM_LEFT.v[0]);
        this.TOP_RIGHT.v[1] = Math.max(max.v[1], this.BOTTOM_LEFT.v[1]);
        this.BOTTOM_LEFT.v[0] = Math.min(max.v[0], this.BOTTOM_LEFT.v[0]);
        this.BOTTOM_LEFT.v[1] = Math.min(max.v[1], this.BOTTOM_LEFT.v[1]);
    }

    public Vector2 getSize() {
        return Vector2.subtract(this.TOP_RIGHT, this.BOTTOM_LEFT);
    }

    public void setSize(Vector2 size) {
        Vector2 position = this.getPosition();
        this.BOTTOM_LEFT.v[0] = position.v[0] - 0.5f * size.v[0];
        this.BOTTOM_LEFT.v[1] = position.v[1] - 0.5f * size.v[1];
        this.TOP_RIGHT.v[0] = position.v[0] + 0.5f * size.v[0];
        this.TOP_RIGHT.v[1] = position.v[1] + 0.5f * size.v[1];
    }

}
