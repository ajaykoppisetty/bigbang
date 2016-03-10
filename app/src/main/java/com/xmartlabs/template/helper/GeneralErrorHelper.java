package com.xmartlabs.template.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.annimon.stream.Objects;
import com.crashlytics.android.Crashlytics;
import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.controller.SessionController;
import com.xmartlabs.template.ui.Henson;

import java.io.IOException;

import javax.inject.Inject;

import lombok.Getter;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by santiago on 12/10/15.
 */
public class GeneralErrorHelper {
  private static final String CRASHLYTICS_KEY_RESPONSE_BODY = "response_body";
  private static final String CRASHLYTICS_KEY_RESPONSE_HEADERS = "response_headers";
  private static final String CRASHLYTICS_KEY_STATUS_CODE = "status_code";
  public static final String CRASHLYTICS_KEY_URL = "url";

  @Inject
  Context applicationContext;
  @Inject
  SharedPreferences sharedPreferences;
  @Inject
  SessionController sessionController;

  public GeneralErrorHelper() {
    BaseProjectApplication.getContext().inject(this);
  }

  @Getter
  final Action1<Throwable> generalErrorAction = t -> {
    if (t instanceof HttpException) {
      HttpException httpException = (HttpException) t;
      Response<?> response = httpException.response();

      if (Objects.equals(response.code(), 401)) {
        logOut();
      } else {
        Crashlytics.setString(CRASHLYTICS_KEY_URL, response.raw().request().url().toString());
        Crashlytics.setInt(CRASHLYTICS_KEY_STATUS_CODE, response.code());
        Crashlytics.setString(CRASHLYTICS_KEY_RESPONSE_HEADERS, response.headers().toString());

        try {
          Crashlytics.setString(CRASHLYTICS_KEY_RESPONSE_BODY, response.errorBody().string());
        } catch (IOException e) {
          Timber.e(e, "Couldn't read error body");
        }

        Timber.e(t, null);
      }
    } else {
      Timber.e(t, null);
    }
  };

  private void logOut() {
    finishLogOut();

    Intent intent = Henson.with(applicationContext).gotoWelcomeActivity().build();
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    applicationContext.startActivity(intent);
  }

  /**
   * Triggers the actions that need to be done after logging out.
   *
   * If this is not done after log out, the log out sessionInterceptor could not catch the session token.
   */
  public void finishLogOut() {
    dismissNotifications();
    // TODO: remove data from database too
  }

  public void dismissNotifications() {

  }
}
