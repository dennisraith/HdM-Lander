
package de.hdm.spe.lander.graphics;

import android.graphics.RectF;
import android.util.Log;

import de.hdm.spe.lander.graphics.VertexElement.VertexSemantic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;


public class Mesh {

    int             mode;
    VertexBuffer    vertexBuffer;
    Vector<float[]> positions = null;
    RectF           bounds    = new RectF();

    public Mesh() {
    }

    public Mesh(VertexBuffer vertexBuffer, int mode) {
        this.vertexBuffer = vertexBuffer;
        this.mode = mode;
    }

    public int getMode() {
        return this.mode;
    }

    public VertexBuffer getVertexBuffer() {
        return this.vertexBuffer;
    }

    private void measure() {
        if (this.positions == null || this.positions.size() == 0) {
            return;
        }
        this.measureHeight();
        this.measureWidth();

    }

    private void measureHeight() {

        float minY = 0;
        float maxY = 0;
        for (float[] vertex : this.positions) {
            float y = vertex[1];
            if (y < minY) {
                minY = y;
            }
            if (y > maxY) {
                maxY = y;
            }
        }
        this.bounds.bottom = minY;
        this.bounds.top = maxY;
    }

    private void measureWidth() {
        float minX = 0;
        float maxX = 0;
        for (float[] vertex : this.positions) {
            float x = vertex[0];
            if (x < minX) {
                minX = x;
            }
            if (x > maxX) {
                maxX = x;
            }
        }
        this.bounds.left = minX;
        this.bounds.right = maxX;
    }

    public RectF getBounds() {
    	if(bounds==null || bounds.isEmpty()){
    		measure();
    	}
        return this.bounds;
    }

    public static Mesh loadFromOBJ(InputStream stream) throws IOException {
        Vector<float[]> positions = null;
        Vector<float[]> texCoords = null;
        Vector<short[]> indexGroups = null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            // Kommentare überspringen
            if (line.startsWith("#"))
                continue;

            // Zeile in Tokens teilen
            StringTokenizer tokenizer = new StringTokenizer(line);
            int numTokens = tokenizer.countTokens();

            // Ist die Zeile leer?
            if (numTokens < 1)
                continue;

            String command = tokenizer.nextToken();

            // Positionsdaten parsen
            if (command.equals("v")) {
                if (numTokens != 4)
                    throw new IOException("Unsupported Line with v command. Only 3 coordinates (x y z) are supported!");

                float[] position = new float[] {
                        Float.parseFloat(tokenizer.nextToken()),
                        Float.parseFloat(tokenizer.nextToken()),
                        Float.parseFloat(tokenizer.nextToken())
                };

                if (positions == null)
                    positions = new Vector<float[]>();

                positions.add(position);
            }

            // Texturkoordinaten parsen
            if (command.equals("vt")) {
                if (numTokens != 3)
                    throw new IOException("Unsupported Line with vt command. Only 2 coordinates (u v) are supported!");

                float[] texCoord = new float[] {
                        Float.parseFloat(tokenizer.nextToken()),
                        Float.parseFloat(tokenizer.nextToken())
                };

                if (texCoords == null)
                    texCoords = new Vector<float[]>();

                texCoords.add(texCoord);
            }

            // Faces parsen
            if (command.equals("f")) {
                if (numTokens != 4)
                    throw new IOException("Unsupported Line with f command. Only triangles are supported!");

                for (int i = 0; i < 3; ++i) {
                    short[] indexGroup = new short[3];

                    String[] indices = tokenizer.nextToken().split("/");
                    if (indices.length > 0)
                        indexGroup[0] = Short.parseShort(indices[0]);
                    if (indices.length > 1)
                        indexGroup[1] = Short.parseShort(indices[1]);
                    if (indices.length > 2)
                        indexGroup[2] = Short.parseShort(indices[2]);

                    if (indexGroups == null)
                        indexGroups = new Vector<short[]>();

                    indexGroups.add(indexGroup);
                }
            }
        }

        int numElements = 0;
        int vertexSize = 0;
        boolean hasPositionData = (positions != null);
        boolean hasTexCoordData = (texCoords != null);

        if (hasPositionData) {
            numElements++;
            vertexSize += 12;
        }
        if (hasTexCoordData) {
            numElements++;
            vertexSize += 8;
        }

        int elementIndex = 0;
        int elementOffset = 0;
        VertexElement[] elements = new VertexElement[numElements];
        if (hasPositionData) {
            elements[elementIndex] = new VertexElement(elementOffset, vertexSize, GL10.GL_FLOAT, 3, VertexSemantic.VERTEX_ELEMENT_POSITION);
            elementOffset += 12;
            elementIndex++;
        }
        if (hasTexCoordData) {
            elements[elementIndex] = new VertexElement(elementOffset, vertexSize, GL10.GL_FLOAT, 2, VertexSemantic.VERTEX_ELEMENT_TEXCOORD);
            elementOffset += 8;
            elementIndex++;
        }

        ByteBuffer buffer = ByteBuffer.allocateDirect(vertexSize * indexGroups.size());
        buffer.order(ByteOrder.nativeOrder());

        for (short[] indexGroup : indexGroups) {
            if (hasPositionData) {
                short vertexIndex = indexGroup[0];
                for (float f : positions.elementAt(vertexIndex - 1)) {
                    buffer.putFloat(f);
                }
            }

            if (hasTexCoordData) {
                short texCoordIndex = indexGroup[1];
                for (float f : texCoords.elementAt(texCoordIndex - 1)) {
                    buffer.putFloat(f);
                }
            }
        }

        buffer.position(0);

        VertexBuffer vertexBuffer = new VertexBuffer();
        vertexBuffer.setElements(elements);
        vertexBuffer.setBuffer(buffer);
        vertexBuffer.setNumVertices(indexGroups.size());

        Mesh mesh = new Mesh();
        mesh.vertexBuffer = vertexBuffer;
        mesh.mode = GL10.GL_TRIANGLES;
        mesh.positions = positions;
        mesh.measure();
        Log.d(Mesh.class.getName(), "Parsed mesh with height " + mesh.bounds.height());
        Log.d(Mesh.class.getName(), "Parsed mesh with width " + mesh.bounds.width());
        return mesh;
    }
}
