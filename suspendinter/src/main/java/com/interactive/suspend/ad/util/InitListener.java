package com.interactive.suspend.ad.util;

/**
 * Created by drason on 2018/7/23.
 */

public interface InitListener {
    void onInitSuccess();
    void onInitFailed(String errorMessage);
}
