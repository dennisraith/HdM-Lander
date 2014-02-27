
package de.hdm.spe.lander.graphics;

import android.graphics.RectF;

import de.hdm.spe.lander.graphics.SpriteFont.CharacterInfo;
import de.hdm.spe.lander.graphics.VertexElement.VertexSemantic;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;


public class TextBuffer {

    private final SpriteFont spriteFont;
    private Mesh             mesh;
    private int              bufferSize = 0;

    TextBuffer(GraphicsDevice graphicsDevice, SpriteFont spriteFont, int capacity) {
        this.init(capacity);
        this.spriteFont = spriteFont;
    }

    private void init(int capacity) {
        VertexElement[] elements = new VertexElement[] {
                new VertexElement(0, 16, GL10.GL_FLOAT, 2, VertexSemantic.VERTEX_ELEMENT_POSITION),
                new VertexElement(8, 16, GL10.GL_FLOAT, 2, VertexSemantic.VERTEX_ELEMENT_TEXCOORD)
        };
        this.bufferSize = 6 * 16 * capacity;
        ByteBuffer data = ByteBuffer.allocateDirect(this.bufferSize);
        data.order(ByteOrder.nativeOrder());

        VertexBuffer vertexBuffer = new VertexBuffer();
        vertexBuffer.setElements(elements);
        vertexBuffer.setBuffer(data);
        vertexBuffer.setNumVertices(0);

        this.mesh = new Mesh(vertexBuffer, GL10.GL_TRIANGLES);
    }

    public SpriteFont getSpriteFont() {
        return this.spriteFont;
    }

    public Mesh getMesh() {
        return this.mesh;
    }

    private boolean checkSize(String text) {
        return text.getBytes().length < this.bufferSize;

    }

    public void setText(String text) {
        if (!this.checkSize(text)) {
            this.init(this.bufferSize * 2);
        }
        Map<Character, SpriteFont.CharacterInfo> characterInfos = this.spriteFont.getCharacterInfos();
        Texture texture = this.spriteFont.getMaterial().getTexture();
        ByteBuffer data = this.mesh.getVertexBuffer().getBuffer();

        data.position(0);

        float x = 0;
        float y = 0;
        for (int index = 0; index < text.length(); ++index) {
            char c = text.charAt(index);

            CharacterInfo info = characterInfos.get(c);
            float posLeft = x + info.offset.x;
            float posRight = x + info.offset.x + info.bounds.width();
            float posTop = y - info.offset.y;
            float posBottom = y - (info.offset.y + info.bounds.height());
            float texLeft = (float) info.bounds.left / (float) texture.getWidth();
            float texRight = (float) info.bounds.right / (float) texture.getWidth();
            float texTop = 1.0f - (float) info.bounds.top / (float) texture.getHeight();
            float texBottom = 1.0f - (float) info.bounds.bottom / (float) texture.getHeight();

            // Dreieck 1
            data.putFloat(posLeft);
            data.putFloat(posTop);
            data.putFloat(texLeft);
            data.putFloat(texTop);
            data.putFloat(posLeft);
            data.putFloat(posBottom);
            data.putFloat(texLeft);
            data.putFloat(texBottom);
            data.putFloat(posRight);
            data.putFloat(posTop);
            data.putFloat(texRight);
            data.putFloat(texTop);

            // Dreieck 2
            data.putFloat(posRight);
            data.putFloat(posTop);
            data.putFloat(texRight);
            data.putFloat(texTop);
            data.putFloat(posLeft);
            data.putFloat(posBottom);
            data.putFloat(texLeft);
            data.putFloat(texBottom);
            data.putFloat(posRight);
            data.putFloat(posBottom);
            data.putFloat(texRight);
            data.putFloat(texBottom);

            x += info.width;
            if (index == text.length() - 1) {
                this.mesh.bounds = new RectF(posLeft, posTop, posRight, posBottom);
                //                Log.d(this.getClass().getName(), "Text size: height: " + this.mesh.bounds.height() + " width: " + this.mesh.bounds.width());

            }

        }
        data.position(0);
        this.mesh.getVertexBuffer().setNumVertices(6 * text.length());
    }
}
