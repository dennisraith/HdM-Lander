
package de.hdm.spe.lander.models;

import android.content.Context;

import de.hdm.spe.lander.collision.Point;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.graphics.VertexBuffer;
import de.hdm.spe.lander.graphics.VertexElement;
import de.hdm.spe.lander.graphics.VertexElement.VertexSemantic;
import de.hdm.spe.lander.math.Matrix4x4;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;


public class Square implements DrawableObject {

    Vector<Point>     coords     = new Vector<Point>();
    int[]             drawOrder  = {0, 1, 2, 0, 2, 3};
    VertexElement[]   elemnts    = new VertexElement[1];
    int               vertexSize = 0;

    Point             A          = new Point(-1f, -1f);
    Point             B          = new Point(1f, -1f);
    Point             C          = new Point(1f, 1f);
    Point             D          = new Point(-1f, 1f);

    private float     Z          = 0;

    VertexElement     pos;
    private Mesh      squareMesh;
    private Matrix4x4 mWorld;
    private Material  material;

    public Square() {

    }

    public Square(Point A, Point B, Point C, Point D) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
    }

    public Square(float x, float y, float width, float height) {

        this.A = new Point(x - width / 2, y - height / 2);
        this.B = new Point(x + width / 2, y - height / 2);
        this.C = new Point(x + width / 2, y + height / 2);
        this.D = new Point(x - width / 2, y + height / 2);

    }

    @Override
    public void prepare(Context context) throws IOException {
        this.coords.add(this.A); //BL
        this.coords.add(this.B); //BR
        this.coords.add(this.C); //TR
        this.coords.add(this.D); //TL

        this.vertexSize = 4 * 3; //3 coords (x,y,Z) � 4 float bytes
        this.pos = new VertexElement(0, this.vertexSize, GL10.GL_FLOAT, 3, VertexSemantic.VERTEX_ELEMENT_POSITION);
        this.mWorld = new Matrix4x4();
        this.material = new Material();

        this.elemnts[0] = this.pos;
        ByteBuffer buffer = ByteBuffer.allocateDirect(this.vertexSize * this.drawOrder.length);
        buffer.order(ByteOrder.nativeOrder());
        buffer.position(0);
        for (int i = 0; i < this.drawOrder.length; i++) {

            Point p = this.coords.get(this.drawOrder[i]);
            buffer.putFloat(p.getPosition().getX());
            buffer.putFloat(p.getPosition().getY());
            buffer.putFloat(this.Z);
        }

        VertexBuffer vBuffer = new VertexBuffer();
        vBuffer.setElements(this.elemnts);
        vBuffer.setBuffer(buffer);
        vBuffer.setNumVertices(this.drawOrder.length);
        this.squareMesh = new Mesh(vBuffer, GL10.GL_TRIANGLES);

    }

    public void setZaxis(float z) {
        this.Z = z;
    }

    public static Square getBackgroundSquare() {
        Point A = new Point(-100f, -100f);
        Point B = new Point(100f, -100f);
        Point C = new Point(100f, 100f);
        Point D = new Point(-100f, 100f);
        return new Square(A, B, C, D);
    }

    @Override
    public Mesh getMesh() {
        return this.squareMesh;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public Matrix4x4 getWorld() {
        return this.mWorld;
    }

}
