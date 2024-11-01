package com.example.android_dz6;

import android.os.Bundle;
import android.app.DatePickerDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_dz6.dialog.EditTextDialog;
import com.example.android_dz6.dialog.SelectTextDialog;
import com.example.android_dz6.interfaces.IText;
import com.example.android_dz6.util.AppConstant;
import com.example.android_dz6.util.TestUtils;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements IText {

    private Calendar currentCalendar;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private TextView timeTextView;
    private EditText mainEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemInsets.left, systemInsets.top, systemInsets.right, systemInsets.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        initializeViews();

        NotificationManager.createNotificationChannel(this);

        currentCalendar = Calendar.getInstance();
        onDateSetListener = (DatePicker datePicker, int year, int month, int day) -> {
            currentCalendar.set(Calendar.YEAR, year);
            currentCalendar.set(Calendar.MONTH, month);
            currentCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateDateDisplay();
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        if (!AppConstant.ALL_ACTIONS_MENU.contains(itemId)) {
            return super.onOptionsItemSelected(menuItem);
        }

        if (itemId == R.id.action_edit_text) {
            displayEditTextDialog();
        } else if (itemId == R.id.action_select_text) {
            displaySelectTextDialog();
        } else if (itemId == R.id.action_get_current_time) {
            showCurrentTimeNotification();
        } else if (itemId == R.id.action_change_date) {
            openDatePicker();
        } else if (itemId == R.id.action_show_notification) {
            showInputTextNotification();
        }

        return true;
    }

    @Override
    public void setText(String text) {
        mainEditText.setText(text);
        timeTextView.setText(TestUtils.getCurrentDateTime());
    }

    private void initializeViews() {
        timeTextView = findViewById(R.id.edit_time_text_view);
        mainEditText = findViewById(R.id.edit_text_main);
    }

    private void openDatePicker() {
        new DatePickerDialog(
                MainActivity.this,
                onDateSetListener,
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void updateDateDisplay() {
        timeTextView.setText(TestUtils.getCurrentDateTime(currentCalendar));
    }

    private void displayEditTextDialog() {
        EditTextDialog dialog = new EditTextDialog();
        Bundle args = new Bundle();
        args.putString("str", mainEditText.getText().toString());
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "edit_dialog");
    }

    private void displaySelectTextDialog() {
        SelectTextDialog dialog = new SelectTextDialog();
        dialog.show(getSupportFragmentManager(), "select_dialog");
    }

    private void showCurrentTimeNotification() {
        NotificationManager.showNotification(
                MainActivity.this,
                "Current date and time",
                TestUtils.getCurrentDateTime()
        );
    }

    private void showInputTextNotification() {
        String inputText = mainEditText.getText().toString();
        if (!inputText.isEmpty()) {
            NotificationManager.showNotification(
                    MainActivity.this,
                    "Current input",
                    inputText + " (" + TestUtils.getCurrentDateTime() + ")");
        }
    }
}
