package com.honglei.toolbox.utils.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Calendar;

import cn.adinnet.jjshipping.utils.LogUtils;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * Created by developer on 2017/4/26.
 */

public class AlertInfoDialog {

    private static final String TAG ="AlertInfoDialog";
    private static OnDialogDatePickedClickListener dialogDataPickedListener;
    private static onDialogClickListener dialogButtonListener;

    private static AlertInfoDialog aDialog;


    public interface onDialogClickListener{}

    //时间 pickerClick
    public  interface OnDialogDatePickedClickListener extends onDialogClickListener {
        public void onDialogClick(String date);
    }



    public interface onDialogPositiveAndNegativeButtonClickListener extends onDialogClickListener{
        public void OnPositive();
        public void OnNegative();
    }

    public interface onDialogPositiveButtonClickListener extends onDialogClickListener{
        public void OnPositive();
    }

    private AlertInfoDialog(){}

    public void setOnDialogDatePickedClickListener(OnDialogDatePickedClickListener dialogClickListener){
        this.dialogDataPickedListener = dialogClickListener;
    }

    public void setOnDialogButtonClickListener (onDialogClickListener dialogButtonListener){
        this.dialogButtonListener = dialogButtonListener;
    }


    public static AlertInfoDialog getInstanceOfDatePicked(Context context){

        initAlertInfoDialog();
//        initAlertDialog(context);


        final  AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();

        DatePicker picker = new DatePicker(context);
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        picker.setDate(year, month);
        picker.setMode(DPMode.SINGLE);

        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                if (dialogDataPickedListener != null)
                  dialogDataPickedListener.onDialogClick(date);
                dialog.dismiss();
            }
        });

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(picker, params);
        dialog.getWindow().setGravity(Gravity.CENTER);


        return aDialog;

    }

    public static AlertInfoDialog getInstanceOfButton(Context context,String title,String message){
        return getInstanceOfButton(context,title,message,null,null);
    }

    public static AlertInfoDialog getInstanceOfButton(Context context,String title,String message,String positiveButtonString) {
        return getInstanceOfButton(context,title,message,positiveButtonString,null);
    }


    public static AlertInfoDialog getInstanceOfButton(final Context context, String title, String message, String positiveButtonString , final String negativeButtonString){

       initAlertInfoDialog();

        AlertDialog.Builder builderSec = new AlertDialog.Builder(context);

        builderSec.setTitle(title);

        builderSec.setMessage(message);

        builderSec.setPositiveButton(positiveButtonString ==null?"知道了":positiveButtonString, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
                if (dialogButtonListener != null) {
                   if(negativeButtonString == null){
                       try {
                            ((onDialogPositiveButtonClickListener)dialogButtonListener).OnPositive();
                        }catch (Exception e){
                           ((onDialogPositiveAndNegativeButtonClickListener)dialogButtonListener).OnPositive();
                       }

                   }else {
                       try {
                           ((onDialogPositiveAndNegativeButtonClickListener)dialogButtonListener).OnPositive();
                       }catch (Exception e){
                           ((onDialogPositiveButtonClickListener)dialogButtonListener).OnPositive();
                       }

                   }

                }
            }
        });


        if (negativeButtonString != null) {
            builderSec.setNegativeButton(negativeButtonString, new DialogInterface.OnClickListener() {

                @Override

                public void onClick(DialogInterface dialog, int which) {
                    if (dialogButtonListener != null){
                        try {
                            ((onDialogPositiveAndNegativeButtonClickListener)dialogButtonListener).OnNegative();
                        }catch (Exception e){
                            Log.e("error","你应该使用 onDialogPositiveAndNegativeButtonClickListener而不是onDialogPositiveButtonClickListener");
                            System.out.print(e.toString());
                            Toast.makeText(context,"error:你应该使用 onDialogPositiveAndNegativeButtonClickListener",Toast.LENGTH_SHORT).show();
                        }

                    }


                }
            });
        }
        AlertDialog dialogSec = builderSec.show();

        return aDialog;
    }


    public static void initAlertInfoDialog(){
        if (aDialog == null){
            aDialog = new AlertInfoDialog();
        }
    }
//    public static void initAlertDialog(Context context){
//        if (dialog != null){
//            dialog.dismiss();
//            dialog = null;
//        }
//
//    }


}
