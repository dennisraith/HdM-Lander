
package de.hdm.spe.lander.engine;

import javax.microedition.khronos.opengles.GL10;


public class VertexElement {

    public enum VertexSemantic {
        VERTEX_ELEMENT_NONE,
        VERTEX_ELEMENT_POSITION,
        VERTEX_ELEMENT_COLOR,
        VERTEX_ELEMENT_TEXCOORD
    }

    private final int            offset;
    private final int            stride;
    private final int            type;
    private final int            count;
    private final VertexSemantic semantic;

    public VertexElement(int offset, int stride, int type, int count, VertexSemantic semantic) {
        this.offset = offset;
        this.stride = stride;
        this.type = type;
        this.count = count;
        this.semantic = semantic;

    }

    public int getElementSize() {
        switch (this.type) {
            case GL10.GL_FLOAT:
                return this.count * 4;

            case GL10.GL_SHORT:
                return this.count * 2;
            default:
                throw new UnknownError("Unknown Datatype");

        }
    }

    public int getCount() {
        return this.count;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getStride() {
        return this.stride;
    }

    public int getType() {
        return this.type;
    }

    public VertexSemantic getSemantic() {
        return this.semantic;
    }
}
