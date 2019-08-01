package esadrcanfer.us.mynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreateNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
    }

    public void save(View view) {
        EditText edit = (EditText)findViewById(R.id.editText1);
        if(edit.getText().length() != 0) {
            NoteStore.addNotes(edit.getText().toString());
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivityForResult(intent, 0);
            finish();
        }
    }

    public void cancel(View view) {
        finish();
    }
}
