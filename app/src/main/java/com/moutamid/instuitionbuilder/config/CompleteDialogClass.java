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

public class CompleteDialogClass extends Dialog  {
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
        move_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c.startActivity(new Intent(c, WalkThroughActivity.class));
                c.finish();
            dismiss();}
        });


    }


}