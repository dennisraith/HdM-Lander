
package de.hdm.spe.lander.models;

import android.content.Context;
import android.graphics.RectF;

import de.hdm.spe.lander.collision.AABB;
import de.hdm.spe.lander.collision.Shape2D;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.graphics.VertexBuffer;
import de.hdm.spe.lander.graphics.VertexElement;
import de.hdm.spe.lander.graphics.VertexElement.VertexSemantic;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector2;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;


public class Square extends AABB implements DrawableObject, Shape2D {

    private int                   vertexSize   = 0;
    private final int[]           drawOrder    = {0, 1, 2, 0, 2, 3};
    private VertexElement         vElementPos;
    private Material              material;
    private Mesh                  squareMesh;
    private final VertexElement[] elemnts      = new VertexElement[1];
    private Matrix4x4             mWorld;

    Vector<Vector2>               coords       = new Vector<Vector2>();
    Vector2                       BOTTOM_RIGHT = new Vector2(1f, -1f);
    Vector2                       TOP_LEFT     = new Vector2(-1f, 1f);

    private float                 Z            = 0;

    private Vector2               mPosition;
    private RectF                 mBounds;

    public Square() {
        this.mBounds = new RectF();
    };

    public Square(float x, float y, float width, float height) {
        this.setBounds(new RectF(x - width / 2, y + height / 2, x + width / 2, y - height / 2));
    }

    private void generateMesh() {
        this.coords.add(this.BOTTOM_LEFT); //BL
        this.coords.add(this.BOTTOM_RIGHT); //BR
        this.coords.add(this.TOP_RIGHT); //TR
        this.coords.add(this.TOP_LEFT); //TL

        this.vertexSize = 4 * 3; //3 coords (x,y,Z) ‡ 4 float bytes
        this.vElementPos = new VertexElement(0, this.vertexSize, GL10.GL_FLOAT, 3, VertexSemantic.VERTEX_ELEMENT_POSITION);
        this.mWorld = new Matrix4x4();
        this.material = new Material();

        this.elemnts[0] = this.vElementPos;
        ByteBuffer buffer = ByteBuffer.allocateDirect(this.vertexSize * this.drawOrder.length);
        buffer.order(ByteOrder.nativeOrder());
        buffer.position(0);
        for (int i = 0; i < this.drawOrder.length; i++) {

            Vector2 p = this.coords.get(this.drawOrder[i]);
            buffer.putFloat(p.getX());
            buffer.putFloat(p.getY());
            buffer.putFloat(this.Z);
        }

        VertexBuffer vBuffer = new VertexBuffer();
        vBuffer.setElements(this.elemnts);
        vBuffer.setBuffer(buffer);
        vBuffer.setNumVertices(this.drawOrder.length);
        this.squareMesh = new Mesh(vBuffer, GL10.GL_TRIANGLES);
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        this.generateMesh();
        InputStream stream = context.getAssets().open("space.png");
        this.material.setTexture(device.createTexture(stream));

    }

    public void setZaxis(float z) {
        this.Z = z;
    }

    public static Square getBackgroundSquare() {
        //        Point A = new Point(-100f, -100f);
        //        Point B = new Point(100f, -100f);
        //        Point C = new Point(100f, 100f);
        //        Point D = new Point(-100f, 100f);
        return new Square(0, 0, 0, 0);
    }

    public RectF getBounds() {
        return this.mBounds;
    }

    private void onPositionChanged() {
        float width = this.mBounds.width();
        float height = this.mBounds.height();
        float x = this.getPosition().getX();
        float y = this.getPosition().getY();
        this.mBounds = new RectF(new RectF(x - width / 2, y + height / 2, x + width / 2, y - height / 2));

    }

    public void setBounds(RectF bounds) {
        this.mBounds = bounds;
        this.BOTTOM_LEFT = new Vector2(this.mBounds.left, this.mBounds.bottom);
        this.TOP_RIGHT = new Vector2(this.mBounds.right, this.mBounds.top);
        this.BOTTOM_RIGHT = new Vector2(this.mBounds.right, this.mBounds.bottom);
        this.TOP_LEFT = new Vector2(this.mBounds.left, this.mBounds.top);
        this.mPosition = new Vector2(this.mBounds.centerX(), this.mBounds.centerY());
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

    @Override
    public Vector2 getPosition() {
        return this.mPosition;
    }

    @Override
    public void setPosition(Vector2 position) {
        this.mPosition = position;
        this.onPositionChanged();
    }

}
