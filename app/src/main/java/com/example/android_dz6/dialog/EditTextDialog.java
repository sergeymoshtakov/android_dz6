package com.example.android_dz6.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.android_dz6.interfaces.IText;
import com.example.android_dz6.MainActivity;
import com.example.android_dz6.R;

public class EditTextDialog extends DialogFragment {

    private IText text;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        text = (MainActivity) context;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_title)
                .setView(R.layout.my_input_text)
                .setPositiveButton(R.string.dialog_ok, (dialog, which) -> {
                    Dialog d = getDialog();
                    Bundle b = getArguments();
                    if (d != null && b != null) {
                        EditText editText = d.findViewById(R.id.dialog_et);
                        text.setText(editText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.dialog_no, null)
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog d = getDialog();
        Bundle b = getArguments();
        if (d != null && b != null) {
            String str = b.getString("str");
            EditText editText = d.findViewById(R.id.dialog_et);
            editText.setText(str);
        }
    }
}