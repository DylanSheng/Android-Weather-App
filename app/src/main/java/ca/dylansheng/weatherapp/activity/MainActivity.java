package ca.dylansheng.weatherapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import ca.dylansheng.weatherapp.R;

import static android.R.attr.onClick;

public class MainActivity extends Activity implements View.OnTouchListener{
    private ImageButton imageButtonMainActivity_1;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        imageButtonMainActivity_1 = (ImageButton) findViewById(R.id.imageButtonMainActivity_1) ;
        imageButtonMainActivity_1.setOnTouchListener(this);

        //intent to getInfoActivity

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.imageButtonMainActivity_1:
                if (gestureDetector.onTouchEvent(event)) {
                    Intent intent = new Intent(MainActivity.this, getInfoActivity.class);
                    startActivity(intent);
                } else {
                    // your code for move and drag
                }

                break;
            default:
                break;
        }
        return false;
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

}