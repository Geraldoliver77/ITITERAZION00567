package local.hal.st31.android.itarticlecollection00567;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * ST31 評定課題 ITARTICLECOLLECTION
 *
 *  ディアログクラス
 *
 * @author Gerald Oliver
 */
public class ItDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String msg = "";
        Bundle extras = getArguments();
        if(extras != null) {
            msg = extras.getString("message");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.btn_dialog_positive, new
                DialogButtonClickListener());
        AlertDialog dialog = builder.create();
        return dialog;
    }


    private class DialogButtonClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

}
