
package de.hdm.spe.lander.collision;

import de.hdm.spe.lander.math.MathHelper;
import de.hdm.spe.lander.math.Vector2;


public class Circle implements Shape2D {

    private Vector2 center;
    private float   radius;

    public Circle() {
        this.center = new Vector2();
        this.radius = 0.0f;
    }

    public Circle(Vector2 center, float radius) {
        this.center = new Vector2(center.v[0], center.v[1]);
        this.radius = radius;
    }

    public Circle(float x, float y, float radius) {
        this.center = new Vector2(x, y);
        this.radius = radius;
    }

    @Override
    public boolean intersects(Shape2D shape) {
        return shape.intersects(this);
    }

    @Override
    public boolean intersects(Point point) {
        float distSqr = Vector2.subtract(point.getPosition(), this.getCenter()).getLengthSqr();
        return distSqr <= this.radius * this.radius;
    }

    @Override
    public boolean intersects(Circle circle) {
        float distSqr = Vector2.subtract(circle.center, this.center).getLengthSqr();
        return distSqr <= (this.radius + circle.radius) * (this.radius + circle.radius);
    }

    @Override
    public boolean intersects(AABB box) {
        Vector2 min = box.getMin();
        Vector2 max = box.getMax();

        //        if (this.center.getX() >= min.getX() && this.center.getX() <= max.getX() && this.center.getY() >= min.getY() && this.center.getY() <= max.getY())
        //        {
        //            return true;
        //        }

        Vector2 nearestPosition = new Vector2(
                MathHelper.clamp(this.center.getX(), min.getX(), max.getX()),
                MathHelper.clamp(this.center.getY(), min.getY(), max.getY()));
        Vector2 distance = nearestPosition.subtract(this.getCenter());
        return distance.getLength() < this.radius;
    }

    @Override
    public Vector2 getPosition() {
        return this.center;
    }

    @Override
    public void setPosition(Vector2 position) {
        this.center.v[0] = position.v[0];
        this.center.v[1] = position.v[1];
    }

    public Vector2 getCenter() {
        return this.center;
    }

    public void setCenter(Vector2 center) {
        this.center = center;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

}
