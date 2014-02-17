
package de.hdm.spe.lander.engine;

import android.util.Log;

import de.hdm.spe.lander.engine.VertexElement.VertexSemantic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;


public class Mesh {

    private VertexBuffer mBuffer;
    private int          mode;

    public VertexBuffer getBuffer() {
        return this.mBuffer;
    }

    public int getMode() {
        return this.mode;
    }

    public static Mesh loadFromObj(InputStream in) throws IOException, UnsupportedEncodingException {
        Mesh mesh = new Mesh();
        BufferedReader read = new BufferedReader(new InputStreamReader(in));
        Vector<float[]> positions = null;
        Vector<float[]> textures = null;
        Vector<short[]> indices = null;
        boolean hasTextures = false;

        String data;
        while ((data = read.readLine()) != null) {
            if (data == null || data.startsWith("#")) {
                continue;
            }
            StringTokenizer token = new StringTokenizer(data);

            if (token.countTokens() <= 0) {
                continue;
            }
            String command = token.nextToken();
            if (command.startsWith("vt")) {
                if (textures == null) {
                    textures = new Vector<float[]>();
                    hasTextures = true;
                }
                mesh.parseVT(token, textures);
            }
            else if (command.startsWith("v")) {
                if (positions == null) {
                    positions = new Vector<float[]>();
                }
                mesh.parseV(token, positions);
            }
            else if (command.startsWith("f")) {
                if (indices == null) {
                    indices = new Vector<short[]>();
                }
                mesh.parseF(token, indices);
            }

        }
        VertexElement[] elements = null;
        int vertexSize;
        if (!hasTextures)
        {
            elements = new VertexElement[1];
            VertexElement pos = new VertexElement(0, 12, GL10.GL_FLOAT, 3, VertexSemantic.VERTEX_ELEMENT_POSITION);
            vertexSize = pos.getElementSize();
            elements[0] = pos;
        }
        else {
            elements = new VertexElement[2];
            VertexElement pos = new VertexElement(0, 20, GL10.GL_FLOAT, 3, VertexSemantic.VERTEX_ELEMENT_POSITION);
            vertexSize = pos.getElementSize();

            VertexElement texts = new VertexElement(vertexSize, 20, GL10.GL_FLOAT, 2, VertexSemantic.VERTEX_ELEMENT_TEXCOORD);
            vertexSize = vertexSize + texts.getElementSize();
            elements[0] = pos;
            elements[1] = texts;
        }

        ByteBuffer buffer = ByteBuffer.allocateDirect(vertexSize * indices.size());
        buffer.order(ByteOrder.nativeOrder());

        //extract indice data
        for (int i = 0; i < indices.size(); i++) {
            short[] indice = indices.get(i);
            float[] pos;
            float[] text;
            switch (indice.length) {
                case 0:
                    continue;
                case 1:
                    pos = positions.get(indice[0] - 1);
                    Mesh.arrayToBuffer(buffer, pos);
                    break;
                case 2:
                    pos = positions.get(indice[0] - 1);
                    text = textures.get(indice[1] - 1);
                    Mesh.arrayToBuffer(buffer, pos);
                    Mesh.arrayToBuffer(buffer, text);
                    break;
                case 3:
                    Log.e(Mesh.class.getSimpleName(), "normals not supported!");
                    break;
            }

        }

        buffer.position(0);

        VertexBuffer vBuffer = new VertexBuffer(elements, buffer);
        vBuffer.setVertexCount(indices.size());

        mesh.mBuffer = vBuffer;
        mesh.mode = GL10.GL_TRIANGLES;
        return mesh;
    }

    private static void arrayToBuffer(ByteBuffer buffer, float[] array) {
        for (int i = 0; i < array.length; i++) {
            buffer.putFloat(array[i]);
        }
    }

    private void parseV(StringTokenizer t, Vector<float[]> positions) throws UnsupportedEncodingException {
        if (!this.checkSize(t, 3)) {
            throw new UnsupportedEncodingException();
        }
        float[] array = new float[3];

        array[0] = Float.parseFloat(t.nextToken());
        array[1] = Float.parseFloat(t.nextToken());
        array[2] = Float.parseFloat(t.nextToken());
        this.logArray("Position: ", array);
        positions.add(array);

    }

    private void parseVT(StringTokenizer t, Vector<float[]> textures) throws UnsupportedEncodingException {
        if (!this.checkSize(t, 2)) {
            throw new UnsupportedEncodingException("Invalid texture arguments (wrong number)");
        }
        float[] array = new float[2];

        array[0] = Float.parseFloat(t.nextToken());
        array[1] = Float.parseFloat(t.nextToken());
        this.logArray("Texture: ", array);
        textures.add(array);
    }

    private void parseF(StringTokenizer t, Vector<short[]> indices) throws UnsupportedEncodingException {
        if (!this.checkSize(t, 3)) {
            throw new UnsupportedEncodingException("Invalid indice dimension (3 maximum)");
        }

        String data;
        while (t.hasMoreTokens())
        {
            data = t.nextToken();
            //0 - positions
            //1 - textures
            //2 - normal

            String[] dimension = data.split("/");
            short[] array;
            switch (dimension.length) {
                case 1:
                    array = new short[1];
                    array[0] = Short.parseShort(dimension[0]);
                    break;
                case 2:
                    array = new short[2];
                    array[0] = Short.parseShort(dimension[0]);
                    array[1] = Short.parseShort(dimension[1]);
                    break;
                case 3:
                    array = new short[3];
                    array[0] = Short.parseShort(dimension[0]);
                    array[1] = Short.parseShort(dimension[1]);
                    array[2] = Short.parseShort(dimension[2]);
                    break;
                default:
                    return;
            }
            this.logArray("Indice: ", array);
            indices.add(array);
        }
    }

    private void logArray(String what, Object x) {
        String array = new String();

        for (int i = 0; i < Array.getLength(x); i++) {
            array += " " + Array.get(x, i) + " - ";
        }

        //        Log.d(this.getClass().getSimpleName(), what + array);
    }

    private boolean checkSize(StringTokenizer t, int size) {
        if (t.countTokens() != size) {
            Log.d(this.getClass().getName(), "unrecognized token " + t.nextToken() + " with size " + t.countTokens());
            return false;
        }
        return true;
    }
}
