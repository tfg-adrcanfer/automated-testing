package esadrcanfer.us.mynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class DetailsActivity extends AppCompatActivity {
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = new Integer(extras.get("noteid").toString());
            EditText edit = (EditText)findViewById(R.id.editText1);
            edit.setText(NoteStore.getNotes().get(id));
        }
    }

    public void save(View view) {
        EditText edit = (EditText)findViewById(R.id.editText1);
        if(edit.getText().length() != 0) {
            NoteStore.updateNote(edit.getText().toString(), id);
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    public void delete(View view) {
        NoteStore.deleteNote(NoteStore.getNotes().get(id));
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    public void cancel(View view) {
        finish();
    }
}
