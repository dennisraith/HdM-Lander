
package de.hdm.spe.lander.engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class VertexBuffer {

    private VertexElement[] mVertArray;
    private ByteBuffer      mBuffer;
    private int             vertexCount;

    public VertexBuffer(VertexElement[] elem, ByteBuffer buffer) {
        this.mVertArray = elem;
        this.mBuffer = ByteBuffer.allocateDirect(buffer.capacity());
        this.mBuffer.order(ByteOrder.nativeOrder());
        this.mBuffer.put(buffer);
    }

    public VertexElement[] getVertArray() {
        return this.mVertArray;
    }

    public void setVertArray(VertexElement[] mVertArray) {
        this.mVertArray = mVertArray;
    }

    public ByteBuffer getBuffer() {
        return this.mBuffer;
    }

    public void setBuffer(ByteBuffer mBuffer) {
        this.mBuffer = mBuffer;
    }

    public int getVertexCount() {
        return this.vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

}
