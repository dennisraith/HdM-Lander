
package de.hdm.spe.lander.models;

import android.content.Context;

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

    Vector<float[]>         coords     = new Vector<float[]>();

    int[]                   drawOrder  = {0, 1, 2, 0, 2, 3};

    VertexElement[]         elemnts    = new VertexElement[1];

    int                     vertexSize = 0;

    VertexElement           pos;
    private Mesh            squareMesh;
    private final Matrix4x4 mWorld;

    public Square() {
        this.coords.add(new float[] {-.5f, .5f, 0f});   //top right
        this.coords.add(new float[] {-.5f, -.5f, 0f}); //bottom left
        this.coords.add(new float[] {.5f, -.5f, 0f});  //bottom right
        this.coords.add(new float[] {.5f, .5f, 0f});//top left

        this.vertexSize = 4 * 3;
        this.pos = new VertexElement(0, this.vertexSize, GL10.GL_FLOAT, 4, VertexSemantic.VERTEX_ELEMENT_POSITION);
        this.mWorld = new Matrix4x4().scale(.000000001f);

    }

    @Override
    public void prepare(Context context) throws IOException {
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
        vBuffer.setNumVertices(this.coords.size());
        this.squareMesh = new Mesh(vBuffer, GL10.GL_TRIANGLES);

    }

    @Override
    public Mesh getMesh() {
        return this.squareMesh;
    }

    @Override
    public Material getMaterial() {
        return new Material();
    }

    @Override
    public Matrix4x4 getWorld() {
        return this.mWorld;
    }

}
