package com.bang.transpor1.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.FrameMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.bang.transpor1.LoginActivity;
import com.bang.transpor1.R;

public class ExitLogonFragment extends Fragment{

    private View view;
    private Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_exit_logon, container, false);
        exitLogin();
        return view;
    }

    private void exitLogin() {
//        Dialog.
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_exitlogon,null);
        if (activity == null){
            return;
        }else {
            if (view.getParent() != null) {
                ViewGroup viewGroup = (ViewGroup) view.getParent();
                viewGroup.removeView(view);
            }
            final AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .setTitle("退出登录")
                    .setNegativeButton("取消退出",null)
                    .setPositiveButton("退出登录",null)
                    .show();
            alertDialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

}
