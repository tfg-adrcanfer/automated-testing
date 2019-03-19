package esadrcanfer.us.mynotes;

import java.util.ArrayList;
import java.util.List;

public class NoteStore {
    private static List<String> notes;

    public static List<String> getNotes(){
        if(notes == null){
            notes = new ArrayList<>();
            notes.add("My first note");
        }
        return notes;
    }

    public static void addNotes(String note){
        if(notes == null){
            notes = new ArrayList<>();
            notes.add("My first note");
        }
        notes.add(note);
    }
}
