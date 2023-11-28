package com.moutamid.instuitionbuilder.config;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.moutamid.instuitionbuilder.Home.WalkThroughActivity;
import com.moutamid.instuitionbuilder.R;

public class CompleteDialogClass extends Dialog implements
        View.OnClickListener {
    public Activity c;
    public Dialog d;

    public CompleteDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.complete);
        TextView move_next = findViewById(R.id.move_next);
        move_next.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_next:
                c.startActivity(new Intent(c, WalkThroughActivity.class));
                c.finish();
                dismiss();
                break;


            default:
                break;
        }
        dismiss();
    }
}