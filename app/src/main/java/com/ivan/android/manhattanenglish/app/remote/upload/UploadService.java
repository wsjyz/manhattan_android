package com.ivan.android.manhattanenglish.app.remote.upload;

import android.content.Context;

import java.io.File;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-24
 * Time: PM8:42
 */
public interface UploadService {

    String upload(File file);

    String uploadImage(Context context, File imageFile);
}
