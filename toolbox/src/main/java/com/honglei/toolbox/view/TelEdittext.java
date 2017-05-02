package com.honglei.toolbox.view;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;



public class TelEdittext extends EditText {
    public boolean isTel = true;
    private String addString=" ";
    private boolean isRun=false;

    private int maxLength = 11;

    public TelEdittext(Context context) {
        this(context,null);
    }

    public TelEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("tag", "onTextChanged()之前");
                if (isRun) {//这几句要加，不然每输入一个值都会执行两次onTextChanged()，导致堆栈溢出，原因不明：因为下面的setText会回调这里，循环了！！
                    isRun = false;
                    return;
                }
                isRun = true;
                Log.i("tag", "onTextChanged()");
                if (isTel) {
                    String finalString = "";
                    int index = 0;
                    String telString = s.toString().replace(" ", "");
                    if ((index + (maxLength == 11 ? 3 : 4)) < telString.length()) {
                        finalString += (telString.substring(index, index + (maxLength == 11 ? 3 : 4)) + addString);
                        index += (maxLength == 11 ? 3 : 4);
                    }
                    while (index + (maxLength == 11 || maxLength == 8 ? 4 : 6) < telString.length()) {
                        finalString += (telString.substring(index, index + (maxLength == 11 || maxLength == 8 ? 4 : 6)) + addString);
                        index += maxLength == 11 || maxLength == 8 ? 4 : 6;
                    }
                    finalString += telString.substring(index, telString.length());
                    TelEdittext.this.setText(finalString);
                    //此语句不可少，否则输入的光标会出现在最左边，不会随输入的值往右移动
                    if (finalString.length() > TelEdittext.this.getMaxLength())
                        TelEdittext.this.setSelection(TelEdittext.this.getMaxLength());
                    else TelEdittext.this.setSelection(finalString.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        this.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(maxLength == 11 ? (maxLength + 2) : (maxLength + 1))});
//        this.setText(this.getText().toString().replace(" ", "")); // 每次切换区域刷新格式,此处可按位数保留输入
        this.setText("");   // 每次切换区域清空输入
    }

    public int getMaxLength() {
        return maxLength == 11 ? (maxLength + 2) : (maxLength + 1);
    }
}