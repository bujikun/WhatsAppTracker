package com.gottibujiku.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gottibujiku.whatsapptracker.R;

/**Dialog displaying information about the application
 *
 * @author Newton Bujiku
 * @since August 2014 */
public class AboutDialog extends DialogFragment {

    private Button button;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.setCancelable(false);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_about,null);
        button = (Button) view.findViewById(R.id.dialog_button_ok);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutDialog.this.dismiss();
            }
        });
        return builder.create();
    }
}
