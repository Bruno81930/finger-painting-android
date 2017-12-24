package machado.bruno.cs4530.paint;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.icu.util.Measure;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by brunomachado on 22/12/2017.
 */

// TODO Do onMeasure

public class PaletteView extends ViewGroup implements PaintSplotchView.OnSplotchClickListener{

    PaintSplotchView highlightedView = null;
    OnChangeColorListener _onChangeColorListener = null;

    public PaletteView(Context context) {
        super(context);
    }

    public interface OnChangeColorListener {
        void onChangeColor(int Color);
    }

    public void setOnChangeColorListener(OnChangeColorListener onChangeColorListener) {
        _onChangeColorListener = onChangeColorListener;
    }

    public PaintSplotchView addColor(Context context, int color) {
       PaintSplotchView splotchView = new PaintSplotchView(context);
       splotchView.setColor(color);
       splotchView.setonSplotchClickListener(this);
       addView(splotchView);
       invalidate();
       return splotchView;
    }

    public void removeColor(View view) {
        removeView(view);
        invalidate();
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        float circleRadius = getWidth() * 0.5f;

        float density = getResources().getDisplayMetrics().density ;
        PointF childSize = new PointF();
        childSize.x = density * 160f * 0.25f;
        childSize.y = density * 160f * 0.25f;

        PointF scaleBias = new PointF();
        scaleBias.x = getWidth() * 0.5f;
        scaleBias.y = getHeight() * 0.5f;

        for(int childIndex = 0; childIndex < getChildCount(); childIndex++) {

            View child = getChildAt(childIndex);

            float childTheta = (float) (Math.PI * childIndex * 2) / getChildCount();
            PointF childCenter = new PointF();
            childCenter.x = scaleBias.x + (circleRadius - childSize.x * 0.5f) * (float) Math.cos(childTheta);
            childCenter.y = scaleBias.y + (circleRadius - childSize.y * 0.5f) * (float) Math.sin(childTheta);

            Rect childRect = new Rect();
            childRect.left = (int) (childCenter.x - childSize.x * 0.5f);
            childRect.top = (int) (childCenter.y - childSize.y * 0.5f);
            childRect.right = (int) (childCenter.x + childSize.x * 0.5f);
            childRect.bottom = (int) (childCenter.y + childSize.y * 0.5f);


            child.layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
        }
    }

    @Override
    public void onSplotchClick(PaintSplotchView view) {
        if(highlightedView != null)
            highlightedView.setHighlight(false);

        highlightedView = view;
        highlightedView.setHighlight(true);

        _onChangeColorListener.onChangeColor(view.getColor());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredWidth = 800;
        int desiredHeight = 800;

        int width = resolveSize(desiredWidth, widthMeasureSpec);
        int height = resolveSize(desiredHeight, heightMeasureSpec);

        setMeasuredDimension(
                width,
                height);
    }
}
