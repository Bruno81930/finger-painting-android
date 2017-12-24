package machado.bruno.cs4530.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunomachado on 21/12/2017.
 */

// TODO Do onMeasure
    
public class PaintView extends View {

   class PaintPoint {
       public float x;
       public float y;
       public int color;

       public PaintPoint(float x, float y, int color) {
           this.x = x;
           this.y = y;
           this.color = color;
       }
   }

   ArrayList<PaintPoint> _paintPoints = new ArrayList<PaintPoint>();
   int _currentColor = Color.BLACK;

    public PaintView(Context context) {
        super(context);
    }

    public void setCoordinateColor(int color) {
        _currentColor = color;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PaintPoint paintPoint = new PaintPoint(event.getX(), event.getY(), _currentColor);
        _paintPoints.add(paintPoint);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint coordinatePaint = new Paint();
        coordinatePaint.setStyle(Paint.Style.STROKE);
        coordinatePaint.setStrokeWidth(20);

        if(_paintPoints.size() > 0) {
            Path path = new Path();
            int pastColor = _paintPoints.get(0).color;
            path.moveTo(_paintPoints.get(0).x, _paintPoints.get(0).y);
            for (int index = 1; index < _paintPoints.size(); index++) {
                if(_paintPoints.get(index).color != pastColor){
                    path = new Path();
                    path.moveTo(_paintPoints.get(index).x, _paintPoints.get(index).y);
                }
                else
                    path.lineTo(_paintPoints.get(index).x, _paintPoints.get(index).y);
                coordinatePaint.setColor(_paintPoints.get(index).color);
                canvas.drawPath(path, coordinatePaint);


                pastColor = _paintPoints.get(index).color;
            }

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredWidth = 700;
        int desiredHeight = 700;

        setMeasuredDimension(
                resolveSize(desiredWidth, widthMeasureSpec),
                resolveSize(desiredHeight, heightMeasureSpec));
    }
}
