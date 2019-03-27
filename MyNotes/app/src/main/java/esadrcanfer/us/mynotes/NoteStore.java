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
        return new ArrayList<>(notes);
    }

    public static void addNotes(String note){
        if(notes == null){
            notes = new ArrayList<>();
            notes.add("My first note");
        }
        notes.add(note);
    }

    public static void updateNote(String note, Integer pos){
        notes.set(pos, note);
    }

    public static void deleteNote(String note){
        notes.remove(note);
    }
}
