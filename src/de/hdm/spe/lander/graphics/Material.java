
package de.hdm.spe.lander.graphics;

import java.security.InvalidParameterException;


public class Material {

    private Texture          texture;

    private TextureFilter    texFilterMin, texFilterMag;
    private TextureWrapMode  texWrapU, texWrapV;
    private TextureBlendMode texBlendMode;
    private float[]          texBlendColor;

    private float[]          colorMaterial;
    private BlendFactor      blendSrcFactor, blendDstFactor;

    private Side             cullSide;
    private CompareFunction  depthTestFunction;
    private boolean          depthWrite;
    private CompareFunction  alphaTestFunction;
    private float            alphaTestValue;

    public Material() {
        this.texture = null;

        this.texFilterMin = TextureFilter.LINEAR;
        this.texFilterMag = TextureFilter.LINEAR;
        this.texWrapU = TextureWrapMode.REPEAT;
        this.texWrapV = TextureWrapMode.REPEAT;
        this.texBlendMode = TextureBlendMode.MODULATE;
        this.texBlendColor = new float[] {0.0f, 0.0f, 0.0f, 0.0f};

        this.colorMaterial = new float[] {1.0f, 1.0f, 1.0f, 1.0f};
        this.blendSrcFactor = BlendFactor.ONE;
        this.blendDstFactor = BlendFactor.ZERO;

        this.cullSide = Side.NONE;
        this.depthTestFunction = CompareFunction.LESS;
        this.depthWrite = true;
        this.alphaTestFunction = CompareFunction.ALWAYS;
        this.alphaTestValue = 0.0f;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public TextureFilter getTextureFilterMin() {
        return this.texFilterMin;
    }

    public TextureFilter getTextureFilterMag() {
        return this.texFilterMag;
    }

    public TextureWrapMode getTextureWrapModeU() {
        return this.texWrapU;
    }

    public TextureWrapMode getTextureWrapModeV() {
        return this.texWrapV;
    }

    public TextureBlendMode getTextureBlendMode() {
        return this.texBlendMode;
    }

    public float[] getTextureBlendColor() {
        return this.texBlendColor;
    }

    public float[] getMaterialColor() {
        return this.colorMaterial;
    }

    public BlendFactor getBlendSourceFactor() {
        return this.blendSrcFactor;
    }

    public BlendFactor getBlendDestFactor() {
        return this.blendDstFactor;
    }

    public Side getCullSide() {
        return this.cullSide;
    }

    public CompareFunction getDepthTestFunction() {
        return this.depthTestFunction;
    }

    public boolean getDepthWrite() {
        return this.depthWrite;
    }

    public CompareFunction getAlphaTestFunction() {
        return this.alphaTestFunction;
    }

    public float getAlphaTestValue() {
        return this.alphaTestValue;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setTextureFilter(TextureFilter filterMin, TextureFilter filterMag) {
        if (filterMag != TextureFilter.NEAREST && filterMag != TextureFilter.LINEAR)
            throw new InvalidParameterException("Magnification filter must be either NEAREST or LINEAR");

        this.texFilterMin = filterMin;
        this.texFilterMag = filterMag;
    }

    public void setTextureWrap(TextureWrapMode texWrapU, TextureWrapMode texWrapV) {
        this.texWrapU = texWrapU;
        this.texWrapV = texWrapV;
    }

    public void setTextureBlendMode(TextureBlendMode texBlendMode) {
        this.texBlendMode = texBlendMode;
    }

    public void setTextureBlendColor(float[] color) {
        if (this.colorMaterial.length != 4)
            throw new InvalidParameterException("Color must contain 4 elements (RGBA).");

        this.texBlendColor = color.clone();
    }

    public void setColorMaterial(float[] colorMaterial) {
        if (colorMaterial.length != 4)
            throw new InvalidParameterException("Color must contain 4 elements (RGBA).");

        this.colorMaterial = colorMaterial;
    }

    public void setBlendFactors(BlendFactor srcFactor, BlendFactor dstFactor) {
        if (srcFactor == BlendFactor.SRC_COLOR || srcFactor == BlendFactor.ONE_MINUS_SRC_COLOR)
            throw new InvalidParameterException("Invalid source factor.");
        if (dstFactor == BlendFactor.DST_COLOR || dstFactor == BlendFactor.ONE_MINUS_DST_COLOR)
            throw new InvalidParameterException("Invalid destination factor.");

        this.blendSrcFactor = srcFactor;
        this.blendDstFactor = dstFactor;
    }

    public void setCullSide(Side cullSide) {
        this.cullSide = cullSide;
    }

    public void setDepthTestFunction(CompareFunction depthTestFunction) {
        this.depthTestFunction = depthTestFunction;
    }

    public void setDepthWrite(boolean depthWrite) {
        this.depthWrite = depthWrite;
    }

    public void setAlphaTestFunction(CompareFunction alphaTestFunction) {
        this.alphaTestFunction = alphaTestFunction;
    }

    public void setAlphaTestValue(float ref) {
        if (ref < 0.0f || ref > 1.0f)
            throw new InvalidParameterException("Alpha test reference value must lie in the range between 0 and 1");

        this.alphaTestValue = ref;
    }

}
