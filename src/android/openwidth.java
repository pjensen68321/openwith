package advice2u.phonegap.plugins.openwith;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * This class echoes a string called from JavaScript.
 */
public class openwidth extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("open")) {
            String message = args.getString(0);
            this.open(message, callbackContext);
            return true;
        }
        return false;
    }

    private void open(String url, CallbackContext callbackContext) {
        // Create URI
        Uri uri = Uri.parse(url);

        Intent intent = null;
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file

        if (url.contains(".doc") || url.contains(".docx")) {
            // Word document
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/msword");
        } else if(url.contains(".pdf")) {
            // PDF file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
        } else if(url.contains(".ppt") || url.contains(".pptx")) {
            // Powerpoint file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if(url.contains(".xls") || url.contains(".xlsx")) {
            // Excel file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if(url.contains(".rtf")) {
            // RTF file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/rtf");
        } else if(url.contains(".wav")) {
            // WAV audio file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "audio/x-wav");
        } else if(url.contains(".gif")) {
            // GIF file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "image/gif");
        } else if(url.contains(".jpg") || url.contains(".jpeg")) {
            // JPG file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "image/jpeg");
        } else if(url.contains(".png")) {
            // PNG file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "image/png");
        } else if(url.contains(".txt")) {
            // Text file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "text/plain");
        } else if(url.contains(".mpg") || url.contains(".mpeg") || url.contains(".mpe") || url.contains(".mp4") || url.contains(".avi")) {
            // Video files
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "video/*");
        }

        //if you want you can also define the intent type for any other file

        //additionally use else clause below, to manage other unknown extensions
        //in this case, Android will show all applications installed on the device
        //so you can choose which application to use


        else { 
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "*/*");
        }

        this.cordova.getActivity().startActivity(intent);
    }
}
