package com.comp5216.todolist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;


public class AddItemDialog extends DialogFragment {

    private EditText title;
    AddItemDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_add_item, null);

        builder.setMessage(R.string.add_item)
                .setView(dialogView)
                .setPositiveButton(R.string.save_text, new DialogInterface.OnClickListener() {
                    @SuppressLint("StaticFieldLeak")
                    public void onClick(DialogInterface dialog, int id) {
                        title = ((AlertDialog) dialog).findViewById(R.id.EditText_add_item);
                        listener.sendTitle(title.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel_text, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddItemDialog.this.getDialog().cancel();
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
            listener = (AddItemDialogListener) context;

        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement AddItemDialogListener");
        }
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface AddItemDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);

        void onDialogNegativeClick(DialogFragment dialog);

        void sendTitle(String title);
    }
}
