package com.training.mdv348o_ce02;



import android.os.Bundle;
//import android.support.v17.leanback.app.VerticalGridSupportFragment;
//import android.support.v17.leanback.widget.ArrayObjectAdapter;
//import android.support.v17.leanback.widget.FocusHighlight;
////import android.support.v17.leanback.widget.GridLayoutManager;
//import android.support.v17.leanback.widget.OnItemViewClickedListener;
//import android.support.v17.leanback.widget.Presenter;
//import android.support.v17.leanback.widget.VerticalGridPresenter;


import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridPresenter;

import java.util.List;

public class MainFragment extends VerticalGridSupportFragment {

    private static final int NUM_COLUMNS = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MovieList movieList1 = createMovieList1(); // Custom data for category 1
        MovieList movieList2 = createMovieList2(); // Custom data for category 2
        MovieList movieList3 = createMovieList3(); // Custom data for category 3

        ArrayObjectAdapter adapter = new ArrayObjectAdapter(new CardPresenter());

        adapter.addAll(0, movieList1.getMovies());
        adapter.addAll(movieList1.getMovies().size(), movieList2.getMovies());
        adapter.addAll(movieList1.getMovies().size() + movieList2.getMovies().size(), movieList3.getMovies());

        setTitle("Android TV App");
        setAdapter(adapter);
        setOnItemViewClickedListener(new ItemViewClickedListener());
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);
    }

    private MovieList createMovieList1() {
        // Custom data for category 1
        MovieList movieList = new MovieList();
        movieList.addMovie(new Movie("A Space Odyssey", "An imposing black structure provides a connection between the past and the future in this enigmatic adaptation of a short story by revered sci-fi author Arthur C. Clarke", R.drawable.a_space_od_br, R.drawable.a_space_od_card));
        movieList.addMovie(new Movie("The Matrix", "Neo (Keanu Reeves) believes that Morpheus (Laurence Fishburne), an elusive figure considered to be the most dangerous man alive, can answer his question", R.drawable.the_matrix_br, R.drawable.the_matrix_card));
        movieList.addMovie(new Movie("Blade Runner-1982", "Deckard (Harrison Ford) is forced by the police Boss (M. Emmet Walsh) to continue his old job as Replicant Hunter. ", R.drawable.blade_runner_br, R.drawable.blade_runner_card));
        movieList.addMovie(new Movie("Inception ", "Deckard (Harrison Ford) is forced by the police Boss (M. Emmet Walsh) to continue his old job as Replicant Hunter. ", R.drawable.inception_br, R.drawable.inception_card));
        movieList.addMovie(new Movie("Pulp Fiction", "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", R.drawable.pulp_fiction_br, R.drawable.pulp_fiction_card));
        return movieList;
    }

    private MovieList createMovieList2() {
        // Custom data for category 2
        // Add your custom movies here
        MovieList movieList = new MovieList();
        movieList.addMovie(new Movie("Forrest Gump", "The history of the United States from the 1950s to the '70s unfolds from the perspective of an Alabama man with an IQ of 75", R.drawable.forest_gump_br, R.drawable.forest_gump_card));
        movieList.addMovie(new Movie("Life of Pi", "After deciding to sell their zoo in India and move to Canada, Santosh and Gita Patel board a freighter with their sons and a few remaining animals.", R.drawable.life_of_pi_br, R.drawable.life_of_pi_card));
        movieList.addMovie(new Movie("Gravity (2013)", "Dr. Ryan Stone (Sandra Bullock) is a medical engineer on her first shuttle mission.", R.drawable.gravity_br, R.drawable.gravity_card));
        movieList.addMovie(new Movie("Hero", "In this visually arresting martial arts epic set in ancient China", R.drawable.hero_br, R.drawable.hero_card));
        movieList.addMovie(new Movie("The Hangover", "In fact, when the three groomsmen wake up the next morning, they can't remember a thing; nor can they find Doug.", R.drawable.thehangover_br, R.drawable.the_hangover_card));
        return movieList;
    }

    private MovieList createMovieList3() {
        // Custom data for category 3
        // Add your custom movies here
        MovieList movieList = new MovieList();
        movieList.addMovie(new Movie("Roma", "Cleo is one of two domestic workers who help Antonio and Sofía take care of their four children in 1970s Mexico City. ", R.drawable.roma_br, R.drawable.roma_card));
        movieList.addMovie(new Movie("The Grand Budapest Hotel", "Comedy/Drama: In the 1930s, the Grand Budapest Hotel is a popular European ski resort, presided over by concierge Gustave H. ", R.drawable.gran_budapest_hotel_br, R.drawable.grand_budapest_hotel_card));
        movieList.addMovie(new Movie("Your Name", "Fantasy/Romance:Two teenagers share a profound, magical connection upon discovering they are swapping bodies", R.drawable.your_name_br, R.drawable.your_name_card));
        movieList.addMovie(new Movie("SuperBad", "Two inseparable best friends navigate the last weeks of high school and are invited to a gigantic house party", R.drawable.superbad_br, R.drawable.superbad_card));
        movieList.addMovie(new Movie("Step Brothers", "Brennan Huff (Will Ferrell) and Dale Doback (John C. Reilly) have one thing in common", R.drawable.step_brotehr_br, R.drawable.step_brother_card));
        return movieList;
    }

    private class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {

        }
    }
}
