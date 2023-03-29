package com.example.ce05;

import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ce05.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    ListView listview;
    Spinner spinner;
    TextView txtIssue, txtName, txtDate, txtDesc;
    ImageView image;
    String[] arrIssues = {
            "30","36","20",
            "40","44","45",
            "50","56","47","65"

    };
    String[] arrTitles ={
            "Sabiha Franco","Piers Thorpe", "Jak Lam",
            "Marcus Bloom","Humza Stein","Sameera Peck",
            "Asmir Phan","Nikodem Kane","Mandeep Oconnor",
            "Keiren Broughton",
    };
    String[] arrDates ={
            "10/10/1995","11/11/1997","1/15/1998",
            "12/2/2005","12/26/2007","12/10/2008",
            "9/2/2009","12/2/2015","12/5/2016",
            "9/14/2018"
    };
    Integer[] arrImages = {
            R.drawable.image_1,R.drawable.image_2,R.drawable.image_3,
            R.drawable.image_4,R.drawable.image_5,R.drawable.image_6,
            R.drawable.image_7,R.drawable.image_8,R.drawable.image_9,
            R.drawable.image_10
    };
    String[] arrDesc ={
            "Let me not to the marriage of true minds",
            "Wikis are enabled by wiki software, otherwise known as wiki engines. A wiki engine, being a form of a content management system, differs from other web-based systems such as blog software, in that the content is created without any defined owner or leader, and wikis have little inherent structure",
            "Some permit control over different functions (levels of access); for example, editing rights may permit changing, adding, or removing material. Others may permit access without enforcing access control. Other rules may be imposed to organize content",
            "They that have power to hurt and will do none",
            "Weary with toil I haste me to my bed",
            "Shall I compare thee to a summer's day?",
            "When forty winters shall besiege thy brow",
            "What a piece of work is a man",
            "I had rather be a toad",
            "This sonnet (a poem of 14 lines) is by Wilfred Owen—perhaps the most famous of the First World War English poets. It concerns \"a piece of our heavy artillery\"—in other words a large gun or cannon."
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listview_obj);
        spinner = (Spinner) findViewById(R.id.spinner_obj);
        txtIssue = (TextView) findViewById(R.id.txt_issue);
        txtIssue = (TextView) findViewById(R.id.txt_issue);
        txtName = (TextView) findViewById(R.id.txt_name);
        txtDate = (TextView) findViewById(R.id.txt_first);
        txtDesc = (TextView) findViewById(R.id.txt_description);
        image = (ImageView) findViewById(R.id.image_src);

        int norient = this.getResources().getConfiguration().orientation;
        showView(norient == Configuration.ORIENTATION_PORTRAIT);

        CollectionAdapter adapter = new CollectionAdapter(this, arrTitles, arrDates);
        ArrayAdapter<String> sadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrTitles);
        listview.setAdapter(adapter);
        spinner.setAdapter(sadapter);
        spinner.setOnItemSelectedListener(this);
        listview.setOnItemClickListener(this);
        showItem(0);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        showView(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    private void showView(boolean blist){
        if(blist) {
            spinner.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
        }
        else{
            spinner.setVisibility(View.VISIBLE);
            listview.setVisibility(View.GONE);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        showItem(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        showItem(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showItem(int index) {
        image.setImageResource(arrImages[index]);
        txtIssue.setText("Issues: \t" + arrIssues[index]);
        txtName.setText("Name: \t" + arrTitles[index]);
        txtDate.setText("First Appearance: \t" + arrDates[index]);
        txtDesc.setText("Description\n" + arrDesc[index]);
    }
}