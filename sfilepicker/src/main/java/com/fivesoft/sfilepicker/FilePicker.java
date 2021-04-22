package com.fivesoft.sfilepicker;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * The simplest way to pick files from user's device.
 */

public class FilePicker {

    private static final int PICKFILE_REQUEST_CODE = 4546;
    private static OnFilesPickedListener listener;
    private static String[] fileTypes = new String[]{"file/*"};
    private static String title = "Select file(s)";
    private static boolean allowMultipleFiles = false;

    private final Activity activity;

    public static final String IMAGE = "image/*";
    public static final String AUDIO = "audio/*";
    public static final String VIDEO = "video/*";
    public static final String TEXT = "text/*";

    private FilePicker(Activity activity){
        this.activity = activity;
    }

    /**
     * Creates new FilePicker instance
     * @return - new FilePicker instance.
     */

    public static FilePicker from(Activity activity){
        return new FilePicker(activity);
    }

    /**
     * @param title - Text which will be displayed
     *              on a file picker window.
     *              DEFAULT: "Select file(s)"
     * @return current FilePicker instance.
     */

    public FilePicker setTitle(String title){
        FilePicker.title = title;
        return this;
    }

    /**
     * @param fileTypes - Choose which types of files user can choose.
     */

    public FilePicker setFileTypes(String... fileTypes){
        FilePicker.fileTypes = fileTypes;
        return this;
    }

    /**
     * @param allowMultipleFiles - Set to true if you want
     *                           to allow user to select more than
     *                           one file. DEFAULT: false
     * @return current FilePicker instance.
     */

    public FilePicker setAllowMultipleFiles(boolean allowMultipleFiles){
        FilePicker.allowMultipleFiles = allowMultipleFiles;
        return this;
    }

    /**
     * Sets the listener called when files are picked.
     * @param listener the listener.
     * @return current FilePicker instance.
     */

    public FilePicker setListener(OnFilesPickedListener listener){
        FilePicker.listener = listener;
        return this;
    }

    /**
     * Shows file picker.
     */

    public void pick(){
        activity.startActivity(new Intent().setClass(activity, FilePickerActivity.class));
    }

    /**
     * Interface called when files were picked
     */

    public interface OnFilesPickedListener {
        /**
         * Returns paths to selected files.
         * @param files - Paths to selected files.
         *              null if an error occurred
         *              during picking files.
         */
        void onResult(@Nullable ArrayList<Uri> files);
    }

    public static class FilePickerActivity extends Activity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Intent intent = new Intent();
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, fileTypes);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowMultipleFiles);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, title), PICKFILE_REQUEST_CODE);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            ArrayList<Uri> uris = new ArrayList<>();
            if(requestCode == PICKFILE_REQUEST_CODE) {
                if(null != data) { // checking empty selection
                    if(null != data.getClipData()) { // checking multiple selection or not
                        for(int i = 0; i < data.getClipData().getItemCount(); i++) {
                            uris.add(data.getClipData().getItemAt(i).getUri());
                        }
                    } else {
                        uris.add(data.getData());
                    }
                }
            }
            listener.onResult(uris);
            finish();
        }

    }

}
