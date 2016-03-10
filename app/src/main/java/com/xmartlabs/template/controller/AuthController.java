package com.xmartlabs.template.controller;

import android.support.annotation.NonNull;

import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.model.AuthResponse;
import com.xmartlabs.template.model.LoginRequest;
import com.xmartlabs.template.model.Session;
import com.xmartlabs.template.service.AuthService;

import javax.inject.Inject;

import rx.Single;

/**
 * Created by santiago on 01/03/16.
 */
public class AuthController extends ServiceController {
  @Inject
  AuthService authService;
  @Inject
  SessionController sessionController;

  public Single<AuthResponse> login(@NonNull LoginRequest loginRequest) {
    return authService.login(loginRequest)
        .doOnSuccess(authResponse -> {
          Session session = sessionController.setSession(authResponse);
          setLoginInfo(session);
        });
  }

  public void setLoginInfo(@SuppressWarnings("UnusedParameters") @NonNull Session session) {

  }
}
