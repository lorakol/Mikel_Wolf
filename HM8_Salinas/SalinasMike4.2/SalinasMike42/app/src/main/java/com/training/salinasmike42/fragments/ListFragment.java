package com.training.salinasmike42.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.training.salinasmike42.MainActivity;
import com.training.salinasmike42.R;
import com.training.salinasmike42.User;

import java.util.ArrayList;
import java.util.Set;

public class ListFragment extends Fragment {

    private WebView webViewList;
    private String strHeader = "<html><body><h1 style='text-align:center'>Items</h1>";
    private String strNoList = "<p style='text-align:center'>Please add an item to get started</p>";
    public static ArrayList<User> dbRecords;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        dbRecords = new ArrayList<>();
        webViewList = view.findViewById(R.id.webViewList);
        webViewList.getSettings().setJavaScriptEnabled(true);
        webViewList.setWebViewClient(new CustomWebViewClient());
        webViewList.loadDataWithBaseURL(null, generateListItems(), "text/html", "UTF-8", null);
        setHasOptionsMenu(true);
        return view;
    }

    private String generateListItems() {
        StringBuilder sb = new StringBuilder();
        sb.append(strHeader);
        dbRecords = MainActivity.dbHelper.getAllUser();
        if(dbRecords.size() == 0){
            sb.append(strNoList);
        }
        else{
            sb.append("<ul>");
            for(User user : dbRecords){
                String row = "<li><a href=\"my-app-schema://details-screen?id="+user.getId()+"&first=" + user.getFirst_name();
                row += "&last="+user.getLast_name()+"&age="+user.getAge()+"\">"+ user.getFullname() + "</a></li>";
                sb.append(row);
            }
            sb.append("</ul>");
        }
        sb.append("</body></html>");

        return sb.toString();
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            Uri uri = request.getUrl();
            goToDetail(uri);
            return false;
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("my-app-schema://details-screen")) {
                // Extract the link parameters from the URL

                // Transition to the details screen and pass the extracted data
                Uri uri = Uri.parse(url);
                goToDetail(uri);
                return true;
            }

            return super.shouldOverrideUrlLoading(view, url);
        }

        private void goToDetail(Uri uri){
            int id = Integer.parseInt(uri.getQueryParameter("id")) ;
            String first_name = uri.getQueryParameter("first");
            String last_name = uri.getQueryParameter("last");
            int age = Integer.parseInt(uri.getQueryParameter("age"));
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setUser(new User(id, first_name, last_name, age));
            fragmentTransaction.replace(R.id.fragment_container, detailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            //add item
            goToForm();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToForm(){
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        FormFragment formFragment = new FormFragment();
        fragmentTransaction.replace(R.id.fragment_container, formFragment);
        fragmentTransaction.commit();
    }
}



