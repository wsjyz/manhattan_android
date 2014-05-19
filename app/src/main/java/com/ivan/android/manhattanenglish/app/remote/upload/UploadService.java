package com.ivan.android.manhattanenglish.app.remote.upload;

import android.net.Uri;

import java.io.File;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-15
 * Time: PM5:43
 */
public interface UploadService {

    String upload(Uri uri);

    String upload(File file);

}
