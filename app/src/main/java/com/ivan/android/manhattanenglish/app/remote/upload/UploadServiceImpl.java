package com.ivan.android.manhattanenglish.app.remote.upload;

import android.net.Uri;

import com.ivan.android.manhattanenglish.app.remote.AbstractService;

import org.springframework.core.io.UrlResource;

import java.io.File;
import java.net.MalformedURLException;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-15
 * Time: PM5:44
 */
public class UploadServiceImpl extends AbstractService implements UploadService {

    @Override
    public String upload(Uri uri) {
        return upload(new File(uri.getPath()));
    }

    @Override
    public String upload(File file) {
        try {
            UrlResource resource = new UrlResource(file.toURI());
            multiPartPost("url", resource, null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
