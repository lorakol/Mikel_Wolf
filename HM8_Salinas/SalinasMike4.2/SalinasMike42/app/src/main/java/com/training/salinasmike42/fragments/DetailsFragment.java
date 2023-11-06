package com.training.salinasmike42.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.training.salinasmike42.DatabaseHelper;
import com.training.salinasmike42.MainActivity;
import com.training.salinasmike42.R;
import com.training.salinasmike42.User;

import android.webkit.JavascriptInterface;


public class DetailsFragment extends Fragment {

    private WebView webViewDetails;
    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        databaseHelper = new DatabaseHelper(requireContext());

        webViewDetails = view.findViewById(R.id.webViewDetails);
        webViewDetails.getSettings().setJavaScriptEnabled(true);
        webViewDetails.addJavascriptInterface(new JavaScriptInterface(), "Android");
        webViewDetails.setWebViewClient(new CustomWebViewClient());
        webViewDetails.loadDataWithBaseURL(null, getDetailsHTMLContent(), "text/html", "UTF-8", null);

        return view;
    }

    public void setUser(User user){
        this.currentUser = user;
    }

    private String getDetailsHTMLContent() {
        return "<html><body>" +
                "<h1>Details Screen</h1>" +
                "<h4>First name</h4>" +
                "<p>"+currentUser.getFirst_name()+ "</p>" +
                "<h4>Last name</h4>" +
                "<p>"+currentUser.getLast_name()+ "</p>" +
                "<h4>Age</h4>" +
                "<p>"+currentUser.getAge()+ "</p>" +
                "<button onclick=\"deleteData()\">Delete</button>" +
                "<script>" +
                "function deleteData() {" +
                "  Android.deleteData();" +
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

    private class JavaScriptInterface {

        @JavascriptInterface
        public void deleteData() {
            MainActivity.dbHelper.deleteItem(currentUser.getId());
            // Data deleted, transition back to the list screen
            getActivity().runOnUiThread(() -> {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                ListFragment listFragment = new ListFragment();
                fragmentTransaction.replace(R.id.fragment_container, listFragment);
                fragmentTransaction.commit();
            });
        }
    }
}

