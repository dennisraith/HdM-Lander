
package de.hdm.spe.lander.collision;

import de.hdm.spe.lander.math.Vector2;


public class Point implements Shape2D {

    private final Vector2 position;

    public Point() {
        this.position = new Vector2();
    }

    public Point(float x, float y) {
        this.position = new Vector2(x, y);
    }

    public Point(Vector2 position) {
        this.position = new Vector2(position.v[0], position.v[1]);
    }

    @Override
    public boolean intersects(Shape2D shape) {
        return shape.intersects(this);
    }

    @Override
    public boolean intersects(Point point) {
        return 0.0000001 > Vector2.subtract(point.position, this.position).getLengthSqr();
    }

    @Override
    public boolean intersects(Circle circle) {
        float r = circle.getRadius();
        float distSqr = Vector2.subtract(circle.getCenter(), this.position).getLengthSqr();
        return distSqr <= r * r;
    }

    @Override
    public boolean intersects(AABB box) {
        if (this.position.getX() < box.getMin().getX() || this.position.getX() > box.getMax().getX())
            return false;
        if (this.position.getY() < box.getMin().getY() || this.position.getY() > box.getMax().getY())
            return false;
        return true;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    public void setPosition(Vector2 position) {
        this.position.v[0] = position.v[0];
        this.position.v[1] = position.v[1];
    }

}
