package sg.edu.rp.c346.id22015056.problemstatement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editTextTitle, editTextSingers, editTextYear;
    private RadioGroup radioGroupRating;
    private Button btnInsert, btnGetSongs;
    private ListView listViewSongs;
    private ArrayAdapter<Song> adapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextSingers = findViewById(R.id.editTextSingers);
        editTextYear = findViewById(R.id.editTextYear);
        radioGroupRating = findViewById(R.id.radioGroupRating);
        btnInsert = findViewById(R.id.btnInsert);
        btnGetSongs = findViewById(R.id.btnGetSongs);
        listViewSongs = findViewById(R.id.listViewSongs);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewSongs.setAdapter(adapter);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString().trim();
                String singers = editTextSingers.getText().toString().trim();
                int year = Integer.parseInt(editTextYear.getText().toString().trim());

                int selectedRadioButtonId = radioGroupRating.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                int stars = Integer.parseInt(selectedRadioButton.getText().toString());

                long result = dbHelper.insertSong(title, singers, year, stars);
                if (result != -1) {
                    Toast.makeText(MainActivity.this, "Song inserted successfully", Toast.LENGTH_SHORT).show();
                    editTextTitle.setText("");
                    editTextSingers.setText("");
                    editTextYear.setText("");
                    radioGroupRating.clearCheck();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to insert song", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGetSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Song> songList = dbHelper.getAllSongs();
                adapter.clear();
                adapter.addAll(songList);
            }
        });
    }
}