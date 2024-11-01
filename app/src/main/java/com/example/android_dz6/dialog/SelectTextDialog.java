package com.example.android_dz6.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.android_dz6.MainActivity;
import com.example.android_dz6.R;
import com.example.android_dz6.interfaces.IText;
import com.example.android_dz6.util.AppConstant;
import com.example.android_dz6.util.TestUtils;

public class SelectTextDialog extends DialogFragment {

    private Spinner textSpinner;
    private IText text;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        text = (MainActivity)context;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_title)
                .setView(R.layout.select_text)
                .setPositiveButton(R.string.dialog_ok, (dialog, which) -> {
                    Dialog d = getDialog();
                    if (d != null) {
                        text.setText(textSpinner.getSelectedItem().toString());
                    }
                })
                .setNeutralButton(R.string.dialog_random, (dialog, which) -> {
                    Dialog d = getDialog();
                    if (d != null) {
                        int randInd = TestUtils.getRandomInteger(0, AppConstant.SELECT_TEXTS.size() - 1);
                        text.setText(AppConstant.SELECT_TEXTS.get(randInd));
                    }
                })
                .setNegativeButton(R.string.dialog_no, null)
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();
        setSpinnerValues();
    }


    private void setSpinnerValues() {
        Dialog d = getDialog();
        if (d == null) {
            return;
        }

        textSpinner = d.findViewById(R.id.word_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                AppConstant.SELECT_TEXTS
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textSpinner.setAdapter(adapter);
    }
}