
package de.hdm.spe.lander.graphics;

import javax.microedition.khronos.opengles.GL10;


public class Material {

    public enum TextureFilterMIN {
        NEAREST(GL10.GL_NEAREST),
        LINEAR(GL10.GL_LINEAR),
        NEAREST_MIPMAP_NEAREST(GL10.GL_NEAREST_MIPMAP_NEAREST),
        NEAREST_MIPMAP_LINEAR(GL10.GL_NEAREST_MIPMAP_LINEAR),
        LINEAR_MIPMAP_NEAREST(GL10.GL_LINEAR_MIPMAP_NEAREST),
        LINEAR_MIPMAP_LINEAR(GL10.GL_LINEAR_MIPMAP_LINEAR);

        private final int value;

        TextureFilterMIN(int ogl) {
            this.value = ogl;
        }

        public int getGLConstant() {
            return this.value;
        }

    }

    public enum TextureFilterMAG {
        NEAREST(GL10.GL_NEAREST),
        LINEAR(GL10.GL_LINEAR);

        private final int value;

        TextureFilterMAG(int ogl) {
            this.value = ogl;
        }

        public int getGLConstant() {
            return this.value;
        }

    }

    public enum TextureWrapMode {
        CLAMP(GL10.GL_CLAMP_TO_EDGE),
        REPEAT(GL10.GL_REPEAT);

        private final int value;

        TextureWrapMode(int value) {
            this.value = value;
        }

        public int getGLConstant() {
            return this.value;
        }
    }

    public enum TextureBlendMode {
        MODULATE(GL10.GL_MODULATE);

        private final int value;

        TextureBlendMode(int value) {
            this.value = value;
        }

        public int getGLConstant() {
            return this.value;
        }

    }

    enum CullSide {
        NONE(0),
        BACK(GL10.GL_BACK);

        private final int value;

        CullSide(int value) {
            this.value = value;
        }

        public int getGLConstant() {
            return this.value;
        }
    }

    public enum BlendFactor {
        ONE(GL10.GL_ONE),
        ZERO(GL10.GL_ZERO),
        SRC_COLOR(GL10.GL_SRC_COLOR),
        ONE_MINUS_SRC_COLOR(GL10.GL_ONE_MINUS_SRC_COLOR),
        DST_COLOR(GL10.GL_DST_COLOR),
        ONE_MINUS_DST_COLOR(GL10.GL_ONE_MINUS_DST_COLOR),
        SRC_ALPHA(GL10.GL_SRC_ALPHA),
        ONE_MINUS_SRC_ALPHA(GL10.GL_ONE_MINUS_SRC_ALPHA),
        DST_ALPHA(GL10.GL_DST_ALPHA),
        ONE_MINUS_DST_ALPHA(GL10.GL_ONE_MINUS_DST_ALPHA);

        private final int value;

        BlendFactor(int value) {
            this.value = value;
        }

        public int getGLConstant() {
            return this.value;
        }
    }

    public enum CompareFunction {
        LESS(GL10.GL_LESS),
        ALWAYS(GL10.GL_ALWAYS);

        private final int value;

        CompareFunction(int value) {
            this.value = value;
        }

        public int getGLConstant() {
            return this.value;
        }
    }

    private Texture          mTexture;
    private boolean          mDepthWrite              = true;
    private TextureFilterMIN mTexFilterMin            = TextureFilterMIN.LINEAR;
    private TextureFilterMAG mTexFilterMag            = TextureFilterMAG.LINEAR;
    private TextureBlendMode mBlendMode               = TextureBlendMode.MODULATE;
    private BlendFactor      mBlendFactorSrc          = BlendFactor.ONE;
    private BlendFactor      mBlendFactorDest         = BlendFactor.ZERO;

    private float[]          mTextureEnvironmentColor = new float[] {0, 0, 0, 0};
    private float[]          mMaterialColor           = new float[] {1, 1, 1, 1};
    private CullSide         mCullFace                = CullSide.NONE;
    private CompareFunction  mCompareValueAlpha       = CompareFunction.ALWAYS;
    private CompareFunction  mCompareValueDepth       = CompareFunction.LESS;
    private float            mAlphaTest               = 0;
    private TextureWrapMode  mWrapModeV               = TextureWrapMode.REPEAT;
    private TextureWrapMode  mWrapModeU               = TextureWrapMode.REPEAT;

    public Texture getTexture() {
        return this.mTexture;
    }

    public Material(Texture t) {
        this.mTexture = t;
    }

    public Material() {

    }

    public void setTexture(Texture texture) {
        this.mTexture = texture;
    }

    public boolean isDepthWriteActive() {
        return this.mDepthWrite;
    }

    public void setDepthWriteActive(boolean mDepthWrite) {
        this.mDepthWrite = mDepthWrite;
    }

    public void setTextureFilters(TextureFilterMIN minify, TextureFilterMAG magnify) {
        this.mTexFilterMin = minify;
        this.mTexFilterMag = magnify;
    }

    public TextureFilterMIN getTextureFilterMin() {
        return this.mTexFilterMin;
    }

    public TextureFilterMAG getTextureFilterMag() {
        return this.mTexFilterMag;
    }

    public TextureWrapMode getWrapModeU() {
        return this.mWrapModeU;
    }

    public TextureWrapMode getWrapModeV() {
        return this.mWrapModeV;
    }

    public void setTextureWrapModes(TextureWrapMode u, TextureWrapMode v) {
        this.mWrapModeU = u;
        this.mWrapModeV = v;
    }

    public TextureBlendMode getTextureBlendMode() {
        return this.mBlendMode;
    }

    public void setTextureBlendMode(TextureBlendMode blendMode) {
        this.mBlendMode = blendMode;
    }

    public float[] getTextureEnvironmentColor() {
        return this.mTextureEnvironmentColor;
    }

    public void setTextureEnvironmentColor(float[] mTextureEnvironmentColor) {
        this.mTextureEnvironmentColor = mTextureEnvironmentColor;
    }

    public void setTextureEnvironmentColor(float r, float g, float b, float a) {
        this.mTextureEnvironmentColor = new float[4];
        this.mTextureEnvironmentColor[0] = r;
        this.mTextureEnvironmentColor[1] = g;
        this.mTextureEnvironmentColor[2] = b;
        this.mTextureEnvironmentColor[3] = a;
    }

    public float[] getMaterialColor() {
        return this.mMaterialColor;
    }

    public void setMaterialColor(float[] mMaterialColor) {
        this.mMaterialColor = mMaterialColor;
    }

    public void setMaterialColor(float r, float g, float b, float a) {
        this.mMaterialColor = new float[4];
        this.mMaterialColor[0] = r;
        this.mMaterialColor[1] = g;
        this.mMaterialColor[2] = b;
        this.mMaterialColor[3] = a;
    }

    public CullSide getCullSide() {
        return this.mCullFace;
    }

    public void setCullSide(CullSide side) {
        this.mCullFace = side;
    }

    public CompareFunction getCompareValue() {
        return this.mCompareValueAlpha;
    }

    public void setCompareValue(CompareFunction mCompareValue) {
        this.mCompareValueAlpha = mCompareValue;
    }

    public float getAlphaTestReference() {
        return this.mAlphaTest;
    }

    public void setAlphaTest(CompareFunction value, float referenceValue) {
        this.mCompareValueAlpha = value;
        this.mAlphaTest = referenceValue;
    }

    public void setDepthTest(CompareFunction function) {
        this.mCompareValueDepth = function;
    }

    public CompareFunction getDepthTest() {
        return this.mCompareValueDepth;
    }

    public void setBlendFactors(BlendFactor src, BlendFactor dest) {
        this.mBlendFactorSrc = src;
        this.mBlendFactorDest = dest;
    }

    public BlendFactor getSourceBlendFactor() {
        return this.mBlendFactorSrc;
    }

    public BlendFactor getDestinationBlendFactor() {
        return this.mBlendFactorDest;
    }

}
