
package de.hdm.spe.lander.graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLUtils;
import android.util.Log;

import de.hdm.spe.lander.engine.VertexBuffer;
import de.hdm.spe.lander.engine.VertexElement;
import de.hdm.spe.lander.graphics.Material.BlendFactor;
import de.hdm.spe.lander.graphics.Material.CompareFunction;
import de.hdm.spe.lander.graphics.Material.CullSide;
import de.hdm.spe.lander.graphics.Material.TextureBlendMode;
import de.hdm.spe.lander.graphics.Material.TextureFilterMAG;
import de.hdm.spe.lander.graphics.Material.TextureFilterMIN;
import de.hdm.spe.lander.graphics.Material.TextureWrapMode;
import de.hdm.spe.lander.math.Matrix4x4;

import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;


public class GraphicsDevice {

    private GL10           glContext;
    private final Material mMaterial;

    public GraphicsDevice() {
        this.mMaterial = new Material();
    }

    public void onSurfaceCreated(GL10 gl) {
        this.glContext = gl;
        this.glContext.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    public void clear(float red, float green, float blue, float alpha) {
        this.glContext.glClearColor(red, green, blue, alpha);
        this.glContext.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }

    public void clear(float red, float green, float blue, float alpha, float depth) {
        this.clear(red, green, blue, alpha);
        this.clearDepth(depth);
    }

    private void clearDepth(float depth) {
        this.glContext.glClearDepthf(depth);
        this.glContext.glClear(GL10.GL_DEPTH_BUFFER_BIT);
    }

    public void clear(float red, float green, float blue) {
        this.clear(red, green, blue, 1.0f);
    }

    public void setWorldMatrix(Matrix4x4 matrix) {
        this.glContext.glMatrixMode(GL10.GL_MODELVIEW);
        this.glContext.glLoadMatrixf(matrix.data, 0);
    }

    public void setViewPort(int x, int y, int width, int height) {
        this.glContext.glViewport(x, y, width, height);
    }

    public void setCamera(Camera camera) {
        Matrix4x4 m = Matrix4x4.multiply(camera.getProjectionMatrix(), camera.getViewMatrix());
        this.glContext.glMatrixMode(GL10.GL_PROJECTION);
        this.glContext.glLoadMatrixf(m.data, 0);
        this.glContext.glMatrixMode(GL10.GL_MODELVIEW);
    }

    public void bindTexture(Texture tex) {
        if (tex != null) {
            this.glContext.glEnable(GL10.GL_TEXTURE_2D);
            this.glContext.glBindTexture(GL10.GL_TEXTURE_2D, tex.getHandle());
        }
        else {
            Log.e(this.getClass().getName(), "ERROR - Texture Object is null");
        }
    }

    public void unbindTexture() {
        this.glContext.glDisable(GL10.GL_TEXTURE_2D);
        this.glContext.glBindTexture(GL10.GL_TEXTURE_2D, 0);
    }

    public void bindVertexBuffer(VertexBuffer buffer) {
        for (int i = 0; i < buffer.getVertArray().length; i++) {
            VertexElement elem = buffer.getVertArray()[i];
            buffer.getBuffer().position(elem.getOffset());

            switch (elem.getSemantic()) {
                case VERTEX_ELEMENT_NONE:
                    break;
                case VERTEX_ELEMENT_COLOR:
                    this.glContext.glEnableClientState(GL10.GL_COLOR_ARRAY);
                    this.glContext.glColorPointer(3, elem.getType(), elem.getStride(), buffer.getBuffer());
                    break;

                case VERTEX_ELEMENT_POSITION:
                    this.glContext.glEnableClientState(GL10.GL_VERTEX_ARRAY);
                    this.glContext.glVertexPointer(3, elem.getType(), elem.getStride(), buffer.getBuffer());
                    break;
                case VERTEX_ELEMENT_TEXCOORD:
                    this.glContext.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
                    this.glContext.glTexCoordPointer(3, elem.getType(), elem.getStride(), buffer.getBuffer());
                    break;
                default:
                    break;

            }
        }

    }

    public void unbindVertexBuffer(VertexBuffer buffer) {
        for (int i = 0; i < buffer.getVertArray().length; i++) {
            VertexElement elem = buffer.getVertArray()[i];
            switch (elem.getSemantic()) {
                case VERTEX_ELEMENT_NONE:
                    break;
                case VERTEX_ELEMENT_COLOR:
                    this.glContext.glDisableClientState(GL10.GL_COLOR_ARRAY);
                    break;
                case VERTEX_ELEMENT_POSITION:
                    this.glContext.glDisableClientState(GL10.GL_VERTEX_ARRAY);
                    break;
                case VERTEX_ELEMENT_TEXCOORD:
                    this.glContext.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
                    break;
                default:
                    break;

            }
        }

    }

    public void draw(int mode, int first, int count) {
        this.glContext.glDrawArrays(mode, first, count);
    }

    public Texture createTexture(InputStream in) {

        return this.createTexture(BitmapFactory.decodeStream(in));

    }

    public Texture createTexture(Bitmap bmp) {
        int[] textureHandle = new int[1];
        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);
        Bitmap invBmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);

        this.initTextures(textureHandle);

        this.glContext.glBindTexture(GL10.GL_TEXTURE_2D, textureHandle[0]);
        this.initTextureConfig();
        int level = this.createScaledBitmap(invBmp);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, invBmp, 0);
        return new Texture(invBmp.getWidth(), invBmp.getHeight(), textureHandle[0]);
    }

    private int createScaledBitmap(Bitmap bmp) {
        int level = 0;
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        while (height <= 1 || width <= 1) {
            if (height > 1) {
                height = height / 2;
            }
            if (width > 1) {
                width = width / 2;
            }

            bmp = Bitmap.createScaledBitmap(bmp, width, height, false);
            level += 1;
            height = bmp.getHeight();
            width = bmp.getWidth();
            Log.d(this.getClass().getSimpleName(), "Scaling Bitmap Texture: width: " + width + " height: " + height);
        }
        return level;
    }

    private void initTextures(int[] handles) {

        this.glContext.glGenTextures(1, handles, 0);
    }

    private void initTextureConfig() {
        this.glContext.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        this.glContext.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        this.glContext.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        this.glContext.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

    }

    public void setAlphaTest(CompareFunction func, float referenceValue) {
        this.glContext.glAlphaFunc(func.getGLConstant(), referenceValue);

    };

    public void setBlendFactors(BlendFactor srcFactor, BlendFactor dstFactor) {
        //        this.glContext.glBlendFunc(srcFactor.getGLConstant(), dstFactor.getGLConstant());
        if (srcFactor == BlendFactor.ONE && dstFactor == BlendFactor.ZERO) {
            this.glContext.glDisable(GL10.GL_BLEND);
        } else {
            this.glContext.glEnable(GL10.GL_BLEND);
            this.glContext.glBlendFunc(srcFactor.getGLConstant(), dstFactor.getGLConstant());
        }
    };

    public void setCullSide(CullSide side) {
        this.glContext.glCullFace(side.getGLConstant());
    };

    public void setDepthRead(CompareFunction func) {
        if (func != CompareFunction.ALWAYS) {

            this.glContext.glEnable(GL10.GL_DEPTH_TEST);
            this.glContext.glDepthFunc(func.getGLConstant());
        }
        else {
            this.glContext.glDisable(GL10.GL_DEPTH_TEST);
        }

    };

    public void setDepthWrite(boolean enabled) {
        this.glContext.glDepthMask(enabled);
    };

    public void setMaterialColor(float[] color) {
        this.setMaterialColor(color[0], color[1], color[2], color[3]);
    }

    public void setMaterialColor(float r, float g, float b, float a) {
        this.glContext.glColor4f(r, g, b, a);
    }

    public void setTextureEnvironmentColor(float r, float g, float b, float a) {
        this.glContext.glTexEnvfv(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_ENV_COLOR, new float[] {r, g, b, a}, 0);
    };

    public void setTextureEnvironmentColor(float[] color) {
        this.setTextureEnvironmentColor(color[0], color[1], color[2], color[3]);
    };

    public void setTextureBlendMode(TextureBlendMode blendMode) {
        this.glContext.glTexEnvf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_ENV_MODE, blendMode.getGLConstant());

    };

    public void setTextureFilters(TextureFilterMIN min, TextureFilterMAG mag) {
        this.glContext.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, min.getGLConstant());
        this.glContext.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, mag.getGLConstant());

    };

    public void setTextureWrapMode(TextureWrapMode u, TextureWrapMode v) {
        this.glContext.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, u.getGLConstant());
        this.glContext.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, v.getGLConstant());
    };
}
