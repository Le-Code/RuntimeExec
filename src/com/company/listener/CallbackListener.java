package com.company.listener;

import java.util.List;

public interface CallbackListener {

    void onStart(String msg);

    void onCopy(List<String> callback);

    void onDelete(List<String> callback);

}
