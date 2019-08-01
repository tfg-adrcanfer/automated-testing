package esadrcanfer.us.complexapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void print1(View view) {
        TextView textView = findViewById(R.id.textView4);
        textView.setText("Seleccionado CheckBox 1");
    }

    public void print2(View view) {
        TextView textView = findViewById(R.id.textView4);
        textView.setText("Seleccionado CheckBox 2");
    }

    public void print3(View view) {
        TextView textView = findViewById(R.id.textView4);
        textView.setText("Seleccionado CheckBox 3");
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        intent.putExtra("text", "CheckBox3");
        startActivity(intent);
    }
}

