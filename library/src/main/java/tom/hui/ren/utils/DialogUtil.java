package tom.hui.ren.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tom.hui.ren.core.R;

/**
 * @author renhui
 * @date 16-11-21
 * @desc tom.hui.ren.utils
 */

public class DialogUtil {
    public static void showConfirmDialog(Context context,String content, String leftBtn, String rightBtn, final
    AlertDialog.OnClickListener
        listener) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.left_btn) {
                    listener.onClick(alertDialog, AlertDialog.BUTTON_NEGATIVE);
                } else if (view.getId() == R.id.right_btn) {
                    listener.onClick(alertDialog, AlertDialog.BUTTON_POSITIVE);
                }
            }
        };
        alertDialog.setMessage(content);
        alertDialog.getWindow()
                   .setContentView(R.layout.layout_confirm_dialog);
        ((TextView) alertDialog.findViewById(R.id.dialog_content)).setText(content);
        Button leftButton = (Button) alertDialog.findViewById(R.id.left_btn);
        leftButton.setText(leftBtn);
        Button rightButton = (Button) alertDialog.findViewById(R.id.right_btn);
        rightButton.setText(rightBtn);
        leftButton.setOnClickListener(onClickListener);
        rightButton.setOnClickListener(onClickListener);
    }

    public static AlertDialog getAndShowLoadingDialog(Context context,String content){
        AlertDialog loadingDialog = new AlertDialog.Builder(context).create();
        loadingDialog.show();
        loadingDialog.getWindow()
                   .setContentView(R.layout.layout_loading_dialog);
        ((TextView) loadingDialog.findViewById(R.id.loading_dlg_echo_text)).setText(content);
        return loadingDialog;
    }
}
