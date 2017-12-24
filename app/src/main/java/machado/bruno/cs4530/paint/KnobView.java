package machado.bruno.cs4530.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by brunomachado on 24/12/2017.
 */

public class KnobView extends View {
    RectF _knobRect = new RectF();
    float _theta = 0;
    Color _baseColor = Color.RED;
    int _colorGrad = 100;
    OnChangeColorListener _onChangeColorListener = null;

    public KnobView(Context context) {
        super(context);
    }

    enum Color {
        RED,
        GREEN,
        BLUE
    }

    public interface OnChangeColorListener {
        void onChangeColor();
    }

    public void setOnChangeColorListener (OnChangeColorListener onChangeColorListener){
        _onChangeColorListener = onChangeColorListener;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        PointF touchPoint = new PointF();
        touchPoint.x = event.getX() - _knobRect.centerX();
        touchPoint.y = event.getY() - _knobRect.centerY();

        setTheta((float) Math.PI + (float) Math.atan2(touchPoint.y, touchPoint.x));
        float normalizedColorGrad = _theta/(2.0f*(float)Math.PI);
        int colorGrad = (int) (normalizedColorGrad * 255);
        setColorGrad(colorGrad);
        _onChangeColorListener.onChangeColor();
        return true;
    }

    public void setBaseColor(Color color) {
        _baseColor = color;
    }

    public void setColorGrad(int colorGrad) {
        _colorGrad = colorGrad;
        invalidate();
    }

    public int getColorGrad() {
        return _colorGrad;
    }

    public void setTheta(float theta) {
        _theta = theta;
        invalidate();
    }

    private int getColor() {
        switch(_baseColor){
            case RED:
                return android.graphics.Color.rgb(_colorGrad, 0, 0);
            case GREEN:
                return android.graphics.Color.rgb(0, _colorGrad, 0);
            case BLUE:
                return android.graphics.Color.rgb(0,0,_colorGrad);
            default:
                throw new RuntimeException();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        PointF knobCenter = new PointF();
        knobCenter.x = getWidth() * 0.5f;
        knobCenter.y = getHeight() * 0.5f;

        _knobRect.left = getPaddingLeft();
        _knobRect.top = getPaddingTop();
        _knobRect.right = getWidth() - getPaddingRight();
        _knobRect.bottom = _knobRect.width();

        float knobRadius = _knobRect.width() * 0.5f;

        float horizontalDelta = knobCenter.x - knobRadius;
        float verticalDelta = knobCenter.y - knobRadius;
        _knobRect.top += verticalDelta;
        _knobRect.bottom += verticalDelta;

        Paint knobPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        knobPaint.setColor(getColor());

        canvas.drawOval(_knobRect, knobPaint);

        float nibRadius = knobRadius * 0.05f;

        PointF nibCenter = new PointF();
        nibCenter.x = knobCenter.x - (knobRadius * 0.8f) * (float) Math.cos(_theta);
        nibCenter.y = knobCenter.y - (knobRadius * 0.8f) * (float) Math.sin(_theta);

        RectF nibRect = new RectF();
        nibRect.left = nibCenter.x - nibRadius;
        nibRect.top = nibCenter.y - nibRadius;
        nibRect.right = nibCenter.x + nibRadius;
        nibRect.bottom = nibCenter.y + nibRadius;

        Paint nibPaint = new Paint();
        nibPaint.setColor(android.graphics.Color.WHITE);

        canvas.drawOval(nibRect, nibPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float density = getResources().getDisplayMetrics().density;

        int desiredWidth = (int) (0.1f * 166f * density);
        int desiredHeight = (int) (0.1f * 166f * density);

        int width = Math.max(desiredWidth, getSuggestedMinimumWidth());
        int height = Math.max(desiredHeight, getSuggestedMinimumHeight());

        if(width < height)
            width = height;
        else
            height = width;

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }
}
