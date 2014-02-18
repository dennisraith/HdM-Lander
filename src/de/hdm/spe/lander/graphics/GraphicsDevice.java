
package de.hdm.spe.lander.graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.opengl.GLUtils;

import de.hdm.spe.lander.math.Matrix4x4;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.InvalidParameterException;

import javax.microedition.khronos.opengles.GL10;


public class GraphicsDevice {

    private GL10 gl;

    public void bindTexture(Texture texture) {
        this.gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.getHandle());
        this.gl.glEnable(GL10.GL_TEXTURE_2D);
    }

    public void unbindTexture() {
        this.gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
        this.gl.glDisable(GL10.GL_TEXTURE_2D);
    }

    public void bindVertexBuffer(VertexBuffer vertexBuffer) {
        ByteBuffer buffer = vertexBuffer.getBuffer();

        for (VertexElement element : vertexBuffer.getElements()) {
            int offset = element.getOffset();
            int stride = element.getStride();
            int type = element.getType();
            int count = element.getCount();

            buffer.position(offset);

            switch (element.getSemantic()) {
                case VERTEX_ELEMENT_POSITION:
                    this.gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
                    this.gl.glVertexPointer(count, type, stride, buffer);
                    break;

                case VERTEX_ELEMENT_COLOR:
                    this.gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
                    this.gl.glColorPointer(count, type, stride, buffer);
                    break;

                case VERTEX_ELEMENT_TEXCOORD:
                    this.gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
                    this.gl.glTexCoordPointer(count, type, stride, buffer);
                    break;
            }
        }
    }

    public void unbindVertexBuffer(VertexBuffer vertexBuffer) {
        for (VertexElement element : vertexBuffer.getElements()) {
            switch (element.getSemantic()) {
                case VERTEX_ELEMENT_POSITION:
                    this.gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
                    break;

                case VERTEX_ELEMENT_COLOR:
                    this.gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
                    break;

                case VERTEX_ELEMENT_TEXCOORD:
                    this.gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
                    break;
            }
        }
    }

    public SpriteFont createSpriteFont(Typeface typeface, float size) {
        return new SpriteFont(this, typeface, size);
    }

    public TextBuffer createTextBuffer(SpriteFont spriteFont, int capacity) {
        return new TextBuffer(this, spriteFont, capacity);
    }

    public Texture createTexture(InputStream stream) {
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        if (bitmap == null)
            return null;

        return this.createTexture(bitmap);
    }

    public Texture createTexture(Bitmap bitmap) {
        int level = 0;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Texture Handle erstellen
        int[] handles = new int[1];
        this.gl.glGenTextures(1, handles, 0);

        // Texture binden
        int handle = handles[0];
        this.gl.glBindTexture(GL10.GL_TEXTURE_2D, handle);

        Texture texture = new Texture(handle, width, height);

        // Bitmap an der Y-Achse spiegeln 
        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);

        // MipMaps erzeugen und laden
        while (width >= 1 && height >= 1) {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, bitmap, 0);

            if (height == 1 || width == 1)
                break;

            level++;
            height /= 2;
            width /= 2;

            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        }

        return texture;
    }

    public void clear(float red, float green, float blue, float alpha, float depth)
    {
        this.gl.glClearColor(red, green, blue, alpha);
        this.gl.glClearDepthf(depth);
        this.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    }

    public void clear(float red, float green, float blue, float alpha) {
        this.gl.glClearColor(red, green, blue, alpha);
        this.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }

    public void clear(float red, float green, float blue) {
        this.gl.glClearColor(red, green, blue, 1.0f);
        this.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }

    public void draw(int mode, int first, int count) {
        this.gl.glDrawArrays(mode, first, count);
    }

    public void resize(int width, int height) {
        this.gl.glViewport(0, 0, width, height);
    }

    public void setAlphaTest(CompareFunction func, float referenceValue) {
        if (func == CompareFunction.ALWAYS) {
            this.gl.glDisable(GL10.GL_ALPHA_TEST);
        } else {
            this.gl.glEnable(GL10.GL_ALPHA_TEST);
            this.gl.glAlphaFunc(GraphicsDevice.getGLConstant(func), referenceValue);
        }
    }

    public void setBlendFactors(BlendFactor srcFactor, BlendFactor dstFactor) {
        if (srcFactor == BlendFactor.ONE && dstFactor == BlendFactor.ZERO) {
            this.gl.glDisable(GL10.GL_BLEND);
        } else {
            this.gl.glEnable(GL10.GL_BLEND);
            this.gl.glBlendFunc(GraphicsDevice.getGLConstant(srcFactor), GraphicsDevice.getGLConstant(dstFactor));
        }
    }

    public void setCamera(Camera camera) {
        Matrix4x4 viewProjection = Matrix4x4.multiply(camera.getProjection(), camera.getView());

        this.gl.glMatrixMode(GL10.GL_PROJECTION);
        this.gl.glLoadMatrixf(viewProjection.m, 0);
        this.gl.glMatrixMode(GL10.GL_MODELVIEW);
    }

    public void setCullSide(Side side) {
        if (side == Side.NONE) {
            this.gl.glDisable(GL10.GL_CULL_FACE);
        } else {
            this.gl.glEnable(GL10.GL_CULL_FACE);
            this.gl.glCullFace(GraphicsDevice.getGLConstant(side));
        }
    }

    public void setDepthTest(CompareFunction func) {
        if (func == CompareFunction.ALWAYS) {
            this.gl.glDisable(GL10.GL_DEPTH_TEST);
        } else {
            this.gl.glEnable(GL10.GL_DEPTH_TEST);
            this.gl.glDepthFunc(GraphicsDevice.getGLConstant(func));
        }
    }

    public void setDepthWrite(boolean enabled) {
        this.gl.glDepthMask(enabled);
    }

    public void setMaterialColor(float[] color) {
        this.setMaterialColor(color[0], color[1], color[2], color[3]);
    }

    public void setMaterialColor(float red, float green, float blue, float alpha) {
        this.gl.glColor4f(red, green, blue, alpha);
    }

    public void setTextureBlendColor(float[] color) {
        this.gl.glTexEnvfv(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_COLOR, color, 0);
    }

    public void setTextureBlendColor(float red, float green, float blue, float alpha) {
        this.setTextureBlendColor(new float[] {red, green, blue, alpha});
    }

    public void setTextureBlendMode(TextureBlendMode blendMode) {
        this.gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GraphicsDevice.getGLConstant(blendMode));
    }

    public void setTextureFilters(TextureFilter filterMin, TextureFilter filterMag) {
        this.gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GraphicsDevice.getGLConstant(filterMin));
        this.gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GraphicsDevice.getGLConstant(filterMag));
    }

    public void setTextureWrapMode(TextureWrapMode wrapU, TextureWrapMode wrapV) {
        this.gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GraphicsDevice.getGLConstant(wrapU));
        this.gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GraphicsDevice.getGLConstant(wrapV));
    }

    public void setWorldMatrix(Matrix4x4 world) {
        this.gl.glLoadMatrixf(world.m, 0);
    }

    public void onSurfaceCreated(GL10 gl) {
        this.gl = gl;

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    private static int getGLConstant(BlendFactor blendFactor) {
        switch (blendFactor) {
            case ZERO:
                return GL10.GL_ZERO;
            case ONE:
                return GL10.GL_ONE;
            case SRC_COLOR:
                return GL10.GL_SRC_COLOR;
            case ONE_MINUS_SRC_COLOR:
                return GL10.GL_ONE_MINUS_SRC_COLOR;
            case DST_COLOR:
                return GL10.GL_DST_COLOR;
            case ONE_MINUS_DST_COLOR:
                return GL10.GL_ONE_MINUS_DST_COLOR;
            case SRC_ALPHA:
                return GL10.GL_SRC_ALPHA;
            case ONE_MINUS_SRC_ALPHA:
                return GL10.GL_ONE_MINUS_SRC_ALPHA;
            case DST_ALPHA:
                return GL10.GL_DST_ALPHA;
            case ONE_MINUS_DST_ALPHA:
                return GL10.GL_ONE_MINUS_DST_ALPHA;
            default:
                throw new InvalidParameterException("Unknown value.");
        }
    }

    private static int getGLConstant(CompareFunction func) {
        switch (func) {
            case NEVER:
                return GL10.GL_NEVER;
            case ALWAYS:
                return GL10.GL_ALWAYS;
            case LESS:
                return GL10.GL_LESS;
            case LESS_OR_EQUAL:
                return GL10.GL_LEQUAL;
            case EQUAL:
                return GL10.GL_EQUAL;
            case GREATER_OR_EQUAL:
                return GL10.GL_GEQUAL;
            case GREATER:
                return GL10.GL_GREATER;
            case NOT_EQUAL:
                return GL10.GL_NOTEQUAL;
            default:
                throw new InvalidParameterException("Unknown value.");
        }
    }

    private static int getGLConstant(Side side) {
        switch (side) {
            case NONE:
                return 0;
            case FRONT:
                return GL10.GL_FRONT;
            case BACK:
                return GL10.GL_BACK;
            case FRONT_AND_BACK:
                return GL10.GL_FRONT_AND_BACK;
            default:
                throw new InvalidParameterException("Unknown value.");
        }
    }

    private static int getGLConstant(TextureBlendMode blendMode) {
        switch (blendMode) {
            case REPLACE:
                return GL10.GL_REPLACE;
            case MODULATE:
                return GL10.GL_MODULATE;
            case DECAL:
                return GL10.GL_DECAL;
            case BLEND:
                return GL10.GL_BLEND;
            case ADD:
                return GL10.GL_ADD;
            default:
                throw new InvalidParameterException("Unknown value.");
        }
    }

    private static int getGLConstant(TextureFilter filter) {
        switch (filter) {
            case NEAREST:
                return GL10.GL_NEAREST;
            case NEAREST_MIPMAP_NEAREST:
                return GL10.GL_NEAREST_MIPMAP_NEAREST;
            case NEAREST_MIPMAP_LINEAR:
                return GL10.GL_NEAREST_MIPMAP_LINEAR;
            case LINEAR:
                return GL10.GL_LINEAR;
            case LINEAR_MIPMAP_NEAREST:
                return GL10.GL_LINEAR_MIPMAP_NEAREST;
            case LINEAR_MIPMAP_LINEAR:
                return GL10.GL_LINEAR_MIPMAP_LINEAR;
            default:
                throw new InvalidParameterException("Unknown value.");
        }
    }

    private static int getGLConstant(TextureWrapMode wrapMode) {
        switch (wrapMode) {
            case CLAMP:
                return GL10.GL_CLAMP_TO_EDGE;
            case REPEAT:
                return GL10.GL_REPEAT;
            default:
                throw new InvalidParameterException("Unknown value.");
        }
    }
}
