package com.santiagonoailles.matsprofesionales.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.santiagonoailles.matsprofesionales.helper.FirebaseHelper;
import com.santiagonoailles.matsprofesionales.helper.MatsSettings;

/**
 * Created by nahuelul on 21/7/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseHelper.setToken(MatsSettings.getProfessionalId(this),refreshedToken);
        MatsSettings.saveToken(this, refreshedToken);
    }
}
