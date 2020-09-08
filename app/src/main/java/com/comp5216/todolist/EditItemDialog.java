package com.comp5216.todolist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.Date;


public class EditItemDialog extends DialogFragment {

    private EditText title;
    private Button date;
    private Button time;
    private String currentTitle; // current title of the to do item
    private Date currentDate; // current date of the to do item
    EditItemDialogListener listener;

    public EditItemDialog(Date date) {
        this.currentDate = date;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_edit_item, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

//        dialogView.findViewById(R.id.Button_edit_date).setOnClickListener(new View.OnClickListener() {
//            @Override
//                public void onClick(View view) {
//                    DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.DatePicker_edit_date);
//                    Calendar calendar = new GregorianCalendar();
//                    calendar.setTime(currentDate);
//                    int year = calendar.get(Calendar.YEAR);
//                    int month = calendar.get(Calendar.MONTH) + 1;
//                    int day = calendar.get(Calendar.DAY_OF_MONTH);
//                    datePicker.updateDate(year, month, day);
//                    alertDialog.setView(dialogView);
//                    alertDialog.show();
//            }
//        });

        builder.setMessage(R.string.add_item)
                .setView(dialogView)
                .setPositiveButton(R.string.save_text, new DialogInterface.OnClickListener() {
                    @SuppressLint("StaticFieldLeak")
                    public void onClick(DialogInterface dialog, int id) {
                        title = ((AlertDialog) dialog).findViewById(R.id.EditText_edit_item);
//                        date = ((AlertDialog) dialog).findViewById(R.id.Button_edit_date);
//                        time = ((AlertDialog) dialog).findViewById(R.id.Button_edit_time);
                    }
                })
                .setNegativeButton(R.string.cancel_text, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditItemDialog.this.getDialog().cancel();
                    }
                });
        // Create the AlertDialog object and return it

        return builder.create();
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the AddItemDialogListener so we can send events to the host
            listener = (EditItemDialogListener) context;

        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement EditItemDialogListener");
        }
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface EditItemDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);

        void sendTitleAndDate(String title);
    }
}
