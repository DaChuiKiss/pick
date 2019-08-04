package com.ergou.hailiao.mvp.http.upload;

public interface ProgressListener {
    void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish);
}
