package com.ivan.android.manhattanenglish.app.remote.upload;

import com.ivan.android.manhattanenglish.app.remote.AbstractService;

import org.springframework.core.io.UrlResource;

import java.io.File;
import java.net.MalformedURLException;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-24
 * Time: PM8:53
 */
public class UploadServiceImpl extends AbstractService implements UploadService {

    @Override
    public String upload(File file) {
        String action = "/upload/uploadAnnex";
        try {
            UrlResource resource = new UrlResource(file.toURI());
            return multiPartPost(getUrl(action), resource, null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
