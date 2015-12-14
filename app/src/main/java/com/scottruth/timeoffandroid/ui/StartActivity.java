package com.scottruth.timeoffandroid.ui;

import android.content.Intent;
import android.os.Bundle;

import com.scottruth.timeoffandroid.controller.demo.DemoController;
import com.scottruth.timeoffandroid.controller.SessionController;
import com.scottruth.timeoffandroid.model.Session;

import javax.inject.Inject;

/**
 * Created by santiago on 31/08/15.
 */
public class StartActivity extends BaseActivity {
    @Inject
    SessionController sessionController;
    @Inject
    DemoController demoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Session session = sessionController.getSession();

        Intent intent;
//  TODO: check session, if session is null then go to welcome activity
//        if (session == null) {
//            intent = Henson.with(getContext()).gotoWelcomeActivity().build();
//        } else {
            intent = Henson.with(getContext()).gotoMainActivity().build();
//            authController.setLoginInfo(session);
//        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getContext().startActivity(intent);
    }
}