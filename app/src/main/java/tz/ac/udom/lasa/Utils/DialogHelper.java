package tz.ac.udom.lasa.Utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import tz.ac.udom.lasa.R;

public class DialogHelper {

    public static AlertDialog buildProgressCancelableDialog(Activity activity, String message, boolean isCancelable){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.layout_progress_cancelable_dialog, null);
        dialogBuilder.setView(view).setCancelable(isCancelable);

        ((TextView)view.findViewById(R.id.textDialogMessage)).setText(message);

        return dialogBuilder.create();
    }
}
