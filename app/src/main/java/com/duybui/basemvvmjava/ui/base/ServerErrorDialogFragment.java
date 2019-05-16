package com.duybui.basemvvmjava.ui.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.duybui.basemvvmjava.R;
import com.duybui.basemvvmjava.utils.AppConstants;

public class ServerErrorDialogFragment extends DialogFragment {

    public static ServerErrorDialogFragment newInstance(String title, String body) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.TITLE, title);
        bundle.putString(AppConstants.BODY, body);
        ServerErrorDialogFragment serverErrorDialogFragment = new ServerErrorDialogFragment();
        serverErrorDialogFragment.setArguments(bundle);
        return serverErrorDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        String title = getString(R.string.server_error_dialog_title);
        String body = getString(R.string.server_error_dialog_message);

        //get title and body
        if (getArguments() != null) {
            title = getArguments().getString(AppConstants.TITLE);
            body = getArguments().getString(AppConstants.BODY);
        }
        alertDialogBuilder.setTitle(title);

        alertDialogBuilder.setMessage(body);

        alertDialogBuilder.setPositiveButton(
                R.string.server_error_dialog_button_caption,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }
        );

        return alertDialogBuilder.create();
    }
}
