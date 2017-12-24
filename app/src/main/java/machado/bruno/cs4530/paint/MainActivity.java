package machado.bruno.cs4530.paint;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends Activity implements PaletteView.OnChangeColorListener, KnobColorSelectorView.OnColorAddListener, KnobColorSelectorView.OnColorRemoveListener{
    PaintView _paintView = null;
    KnobColorSelectorView _knobColorSelectorView = null;
    PaletteView _paletteView = null;
    ArrayList<PaintSplotchView> _newColors = new ArrayList<PaintSplotchView>();

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        _paintView = new PaintView(this);
        rootLayout.addView(_paintView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3));

        _knobColorSelectorView = new KnobColorSelectorView(this);
        _knobColorSelectorView.setOnColorAddListener(this);
        _knobColorSelectorView.setOnColorRemoveListener(this);
        rootLayout.addView(_knobColorSelectorView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        _paletteView = new PaletteView(this);
        _paletteView.setOnChangeColorListener(this);
        _paletteView.addColor(this, Color.RED);
        _paletteView.addColor(this, Color.GREEN);
        _paletteView.addColor(this, Color.BLUE);
        _paletteView.addColor(this, Color.YELLOW);
        _paletteView.addColor(this, Color.parseColor("#551a8b"));
        LinearLayout.LayoutParams paletteViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3);
        paletteViewParams.gravity = Gravity.CENTER_HORIZONTAL;
        rootLayout.addView(_paletteView, paletteViewParams);

        setContentView(rootLayout);

    }

    @Override
    public void onChangeColor(int color) {
        _paintView.setCoordinateColor(color);
    }

    @Override
    public void onColorAdd(int color) {
        _newColors.add(_paletteView.addColor(this, color));
    }

    @Override
    public void onColorRemove() {
        if(_newColors.isEmpty())
            return;
        PaintSplotchView lastView = _newColors.get(_newColors.size() - 1);
        _paletteView.removeColor(lastView);
        _newColors.remove(lastView);
    }
}
