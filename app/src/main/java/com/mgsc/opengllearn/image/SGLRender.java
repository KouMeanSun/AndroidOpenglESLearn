package com.mgsc.opengllearn.image;

import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.print.PrinterId;
import android.view.View;

import com.mgsc.opengllearn.image.filter.AFilter;
import com.mgsc.opengllearn.image.filter.ColorFilter;
import com.mgsc.opengllearn.image.filter.ContrastColorFilter;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SGLRender implements GLSurfaceView.Renderer{

    private AFilter mFilter;
    private Bitmap bitmap;
    private int width,height;
    private boolean refreshFlag = false;
    private EGLConfig config;

    public SGLRender(View mView) {
        mFilter = new ContrastColorFilter(mView.getContext(), ColorFilter.Filter.NONE);
    }

    public void setFilter(AFilter filter){
        refreshFlag = true;
        mFilter = filter;
        if(bitmap != null){
            mFilter.setBitMap(bitmap);
        }
    }

    public void setImageBuffer(int[] buffer,int width,int height){
        bitmap= Bitmap.createBitmap(buffer,width,height, Bitmap.Config.RGB_565);
        mFilter.setBitMap(bitmap);
    }
    public void refresh() { refreshFlag = true;}

    public AFilter getFilter() { return mFilter;}

    public void setImage(Bitmap bitmap){
        this.bitmap = bitmap;
        mFilter.setBitMap(bitmap);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        this.config = config;
        mFilter.onSurfaceCreated(gl,config);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width=width;
        this.height=height;
        mFilter.onSurfaceChanged(gl, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if(refreshFlag&&width!=0&&height!=0){
            mFilter.onSurfaceCreated(gl, config);
            mFilter.onSurfaceChanged(gl,width,height);
            refreshFlag=false;
        }
        mFilter.onDrawFrame(gl);
    }

}
