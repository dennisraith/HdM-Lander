
package de.hdm.spe.lander.models;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;
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


@Deprecated
public class Square3 implements DrawableObject {

    Vector<float[]>   coords     = new Vector<float[]>();
    int[]             drawOrder  = {0, 1, 2, 0, 2, 3};
    VertexElement[]   elemnts    = new VertexElement[1];
    int               vertexSize = 0;

    float[]           A          = new float[] {-1f, -1f, 0f};
    float[]           B          = new float[] {1f, -1f, 0f};
    float[]           C          = new float[] {1f, 1f, 0f};
    float[]           D          = new float[] {-1f, 1f, 0f};

    VertexElement     pos;
    private Mesh      squareMesh;
    private Matrix4x4 mWorld;
    private Material  material;

    public Square3() {

    }

    public Square3(float[] A, float[] B, float[] C, float[] D) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
    }

    public Square3(float x, float y, float width, float height) {

    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        this.coords.add(this.A); //BL
        this.coords.add(this.B); //BR
        this.coords.add(this.C); //TR
        this.coords.add(this.D); //TL

        this.vertexSize = 4 * 3; //3 coords ‡ 4 float bytes
        this.pos = new VertexElement(0, this.vertexSize, GL10.GL_FLOAT, 3, VertexSemantic.VERTEX_ELEMENT_POSITION);
        this.mWorld = new Matrix4x4();
        this.material = new Material();

        this.elemnts[0] = this.pos;
        ByteBuffer buffer = ByteBuffer.allocateDirect(this.vertexSize * this.drawOrder.length);
        buffer.order(ByteOrder.nativeOrder());
        buffer.position(0);
        for (int i = 0; i < this.drawOrder.length; i++) {

            for (float f : this.coords.get(this.drawOrder[i])) {
                buffer.putFloat(f);
            }
        }

        VertexBuffer vBuffer = new VertexBuffer();
        vBuffer.setElements(this.elemnts);
        vBuffer.setBuffer(buffer);
        vBuffer.setNumVertices(this.drawOrder.length);
        this.squareMesh = new Mesh(vBuffer, GL10.GL_TRIANGLES);

    }

    public static Square3 getBackgroundSquare() {

        float[] A = new float[] {-100f, -100f, -5f};
        float[] B = new float[] {100f, -100f, -5f};
        float[] C = new float[] {100f, 100f, -5f};
        float[] D = new float[] {-100f, 100f, -5f};

        return new Square3(A, B, C, D);
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
