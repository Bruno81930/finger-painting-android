package machado.bruno.cs4530.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import org.jetbrains.annotations.Nullable;

/**
 * Created by brunomachado on 21/12/2017.
 */

// TODO Do onMeasure

public class PaintSplotchView extends View {
    OnSplotchClickListener _splotchClickListener = null;

    boolean _highlight = false;
    int _color = Color.BLACK;

    public interface OnSplotchClickListener {
        public void onSplotchClick(PaintSplotchView view);
    }

    public void setonSplotchClickListener(OnSplotchClickListener splotchClickListener) {
        _splotchClickListener = splotchClickListener;
    }

    public boolean isHighlight() {
        return _highlight;
    }

    public void setHighlight(boolean highlight) {
        _highlight = highlight;
        invalidate();
    }

    public int getColor() {
        return _color;
    }

    public void setColor(int color) {
        _color = color;
        invalidate();
    }

    public PaintSplotchView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        _splotchClickListener.onSplotchClick(this);
        return super.onTouchEvent(event);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF splotchRect = new RectF();
        splotchRect.left = getPaddingLeft();
        splotchRect.top = getPaddingTop();
        splotchRect.right = getWidth() + getPaddingRight();
        splotchRect.bottom = getHeight() + getPaddingBottom();

        Paint splotchPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        splotchPaint.setColor(_color);

        canvas.drawOval(splotchRect, splotchPaint);

        int highlightColor = Color.YELLOW;
        if(_highlight) {
            if(_color == Color.YELLOW)
                highlightColor = Color.BLUE;
            float highlightSplotchInnerRadius = splotchRect.width() * 0.05f;
            RectF highlightSplotchRect = new RectF();
            highlightSplotchRect.left = splotchRect.left + highlightSplotchInnerRadius * 0.5f;
            highlightSplotchRect.top = splotchRect.top + highlightSplotchInnerRadius * 0.5f;
            highlightSplotchRect.right = splotchRect.right - highlightSplotchInnerRadius * 0.5f;
            highlightSplotchRect.bottom = splotchRect.bottom - highlightSplotchInnerRadius * 0.5f;
            Paint highlightSplotchPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            highlightSplotchPaint.setStrokeWidth(highlightSplotchInnerRadius);
            highlightSplotchPaint.setStyle(Paint.Style.STROKE);
            highlightSplotchPaint.setColor(highlightColor);

            canvas.drawOval(highlightSplotchRect, highlightSplotchPaint);

        }
    }
}
