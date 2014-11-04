/*
 Copyright (C) 2014 Newton Bujiku

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
package com.gottibujiku.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gottibujiku.whatsapptracker.R;

/**
 * Dialog displaying information about the application
 *
 * @author Newton Bujiku
 * @since August 2014
 */
public class AboutDialog extends DialogFragment {

    private Button button;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.setCancelable(false);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_about, null);
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
