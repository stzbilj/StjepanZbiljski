package hr.math.web.ponovljenikolokvij;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static DBAdapter db;

    //Elements for output
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.printLayout);

        db = new DBAdapter(this);

        display();
        bestPaid();
    }

    private void bestPaid() {
        Cursor c = db.getWorker();
        EditText editText = (EditText)findViewById(R.id.salary);
        if( c.moveToFirst() ){
            editText.setText(c.getString(1));
        }
        else {
            editText.setText(R.string.error_text);
        }
    }

    public void startService(View view) {
        startService(new Intent(getBaseContext(), MyService.class));
    }

    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
    }

    private void display() {
        if(((LinearLayout) linearLayout).getChildCount() > 0)
            ((LinearLayout) linearLayout).removeAllViews();
        TextView tx1 = new TextView(this);
        tx1.setText(R.string.tx1);
        linearLayout.addView(tx1);
        db.open();
        Cursor c1 = db.getAllWorkers();
        if (c1.moveToFirst())
        {
            do {
                DisplayWorker(c1);
            } while (c1.moveToNext());
        }
        TextView tx2 = new TextView(this);
        tx2.setText(R.string.tx2);
        linearLayout.addView(tx2);
        Cursor c2 = db.getAllWorkStations();
        if (c2.moveToFirst())
        {
            do {
                DisplayWorkStation(c2);
            } while (c2.moveToNext());
        }
    }

    private void DisplayWorker(Cursor c) {
        TextView tx = new TextView(this);
        tx.setText("ID: " + c.getString(0) + "\n" +
                "Ime: " + c.getString(1) + " Mail: " + c.getString(2)+ "\n" +
                "Plaća:  " + c.getString(3)
        );
        linearLayout.addView(tx);
    }

    private void DisplayWorkStation(Cursor c) {
        TextView tx = new TextView(this);
        tx.setText("ID_mjesta: " + c.getString(0) + "\n" +
                "Nivo odgovornosti: " + c.getString(1) + "\n" +
                "Minimalna plaća:  " + c.getString(2)
        );
        linearLayout.addView(tx);

    }

    public void deleteWorkStation(View view) {
        db.open();
        EditText mEdit   = (EditText)findViewById(R.id.delete_id);
        long id = Long.parseLong(mEdit.getText().toString());
        db.deleteWorkStation(id);
        db.close();

        display();
    }

    public void deleteWorker(View view) {
        db.open();
        EditText mEdit   = (EditText)findViewById(R.id.delete_id);
        long id = Long.parseLong(mEdit.getText().toString());
        db.deleteWorker(id);
        db.close();

        display();
        bestPaid();
    }
}
