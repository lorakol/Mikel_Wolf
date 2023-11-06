package com.training.salinasmike42.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.training.salinasmike42.DatabaseHelper;
import com.training.salinasmike42.MainActivity;
import com.training.salinasmike42.R;


public class FormFragment extends Fragment {

    private WebView webViewForm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form, container, false);


        webViewForm = view.findViewById(R.id.webViewForm);
        webViewForm.getSettings().setJavaScriptEnabled(true);
        webViewForm.addJavascriptInterface(new WebAppInterface(), "Android");
        webViewForm.setWebViewClient(new CustomWebViewClient());
        webViewForm.loadDataWithBaseURL(null, getFormHTMLContent(), "text/html", "UTF-8", null);

        return view;
    }

    private String getFormHTMLContent() {
        return "<html><body>" +
                "<h1>Form Screen</h1>" +
                "<form onsubmit=\"saveData()\">" +
                "<label for=\"firstName\">First Name:</label>" +
                "<input type=\"text\" id=\"firstName\" name=\"firstName\"><br><br>" +
                "<label for=\"lastName\">Last Name:</label>" +
                "<input type=\"text\" id=\"lastName\" name=\"lastName\"><br><br>" +
                "<label for=\"age\">Age:</label>" +
                "<input type=\"number\" id=\"age\" name=\"age\"><br><br>" +
                "<input type=\"submit\" value=\"Save\">" +
                "</form>" +
                "<script>" +
                "function saveData() {" +
                "  var firstName = document.getElementById('firstName').value;" +
                "  var lastName = document.getElementById('lastName').value;" +
                "  var age = document.getElementById('age').value;" +
                "  Android.saveFormData(firstName, lastName, age);" +
                "}" +
                "</script>" +
                "</body></html>";
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // Handle custom links if needed
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
    private class WebAppInterface {

        @JavascriptInterface
        public void saveFormData(String firstName, String lastName, String age) {
            long result = MainActivity.dbHelper.insertItem(firstName, lastName, Integer.parseInt(age));
            if (result != -1) {
                getActivity().runOnUiThread(() -> {
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    ListFragment listFragment = new ListFragment();
                    fragmentTransaction.replace(R.id.fragment_container, listFragment);
                    fragmentTransaction.commit();
                });
            } else {
                // Failed to save data, handle error
            }
        }
    }
}



