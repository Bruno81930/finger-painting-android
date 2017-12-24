package machado.bruno.cs4530.paint;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by brunomachado on 24/12/2017.
 */

public class KnobColorSelectorView extends LinearLayout implements KnobView.OnChangeColorListener, View.OnClickListener{
    int _newColorSplotch;
    Button _minusButton = null;
    Button _plusButton = null;
    KnobView _knobView1 = null;
    KnobView _knobView2 = null;
    KnobView _knobView3 = null;
    OnColorAddListener _onColorAddListener = null;
    OnColorRemoveListener _onColorRemoveListener = null;

    public KnobColorSelectorView(Context context) {
        super(context);

        setOrientation(HORIZONTAL);

        _minusButton = new Button(context);
        _minusButton.setText("-");
        _minusButton.setOnClickListener(this);
        addView(_minusButton, new LayoutParams(50, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        _plusButton = new Button(context);
        _plusButton.setText("+");
        _plusButton.setOnClickListener(this);
        addView(_plusButton, new LayoutParams(50, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        _knobView1 = new KnobView(context);
        _knobView1.setBaseColor(KnobView.Color.RED);
        _knobView1.setOnChangeColorListener(this);
        addView(_knobView1, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        _knobView2 = new KnobView(context);
        _knobView2.setBaseColor(KnobView.Color.GREEN);
        _knobView2.setOnChangeColorListener(this);
        addView(_knobView2, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        _knobView3 = new KnobView(context);
        _knobView3.setBaseColor(KnobView.Color.BLUE);
        _knobView3.setOnChangeColorListener(this);
        addView(_knobView3, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        calculateColor();

    }

    @Override
    public void onClick(View view) {
        if(view == _plusButton)
            _onColorAddListener.onColorAdd(_newColorSplotch);
        if(view == _minusButton)
            _onColorRemoveListener.onColorRemove();
    }

    public interface OnColorAddListener {
        void onColorAdd(int color);
    }

    public void setOnColorAddListener(OnColorAddListener onColorAddListener) {
        _onColorAddListener = onColorAddListener;
    }

    public interface OnColorRemoveListener {
        void onColorRemove();
    }

    public void setOnColorRemoveListener(OnColorRemoveListener onColorRemoveListener) {
        _onColorRemoveListener = onColorRemoveListener;
    }

    void calculateColor() {
        _newColorSplotch = Color.rgb(_knobView1.getColorGrad(), _knobView2.getColorGrad(), _knobView3.getColorGrad());
    }

    @Override
    public void onChangeColor() {
        calculateColor();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float density = getResources().getDisplayMetrics().density;

        int desiredWidth = (int) (0.5f * 166f * density);
        int desiredHeight = (int) (0.5f * 166f * density);

        int width = Math.max(desiredWidth, getSuggestedMinimumWidth());
        int height = Math.max(desiredHeight, getSuggestedMinimumHeight());

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }
}
