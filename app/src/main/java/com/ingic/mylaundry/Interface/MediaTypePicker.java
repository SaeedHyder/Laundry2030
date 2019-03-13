package com.ingic.mylaundry.Interface;

import com.ingic.mylaundry.models.AdressData;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by developer.ingic on 11/23/2017.
 */

public interface MediaTypePicker {
    void onPhotoClicked(ArrayList<File> file);
    void onDocClicked(ArrayList<String> files);
}
