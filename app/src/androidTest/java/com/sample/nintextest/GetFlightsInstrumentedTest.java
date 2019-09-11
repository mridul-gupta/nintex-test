package com.sample.nintextest;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.sample.nintextest.ui.MainActivity;
import com.sample.nintextest.ui.SearchResultRecyclerViewAdapter;
import com.sample.nintextest.ui.SearchViewModel;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Locale;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GetFlightsInstrumentedTest {

    /* search fragment views */
    /* search enter invalid from */
    /* search enter invalid to */
    /* search enter invalid departure */
    /* search enter invalid return */
    /* search enter all valid no network */
    /* search enter all valid good case */

    /* result fragment views */
    /* result recycler view count, text */
    /* result recycler view match with flight list */
    /* result recycler view click */
    /* result fragment back key */
    private IdlingResource mIdlingResource;
    private RecyclerView recyclerView;
    private SearchResultRecyclerViewAdapter adapter;
    private SearchViewModel mViewModel;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mainActivity;

    @Before
    public void setUp() {
        mainActivity = rule.getActivity();
        mIdlingResource = mainActivity.getIdlingResource();

        IdlingRegistry.getInstance().register(mIdlingResource);

        initViewModels();
    }

    @After
    public void cleanUp() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    @Test
    public void checkSearchViews() {
        onView(withId(R.id.text_view_from)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(TextView.class)));
        onView(withId(R.id.edit_text_from)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(EditText.class)));
        onView(withId(R.id.seperator1)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(View.class)));
        onView(withId(R.id.text_view_to)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(TextView.class)));
        onView(withId(R.id.edit_text_to)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(EditText.class)));
        onView(withId(R.id.seperator2)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(View.class)));
        onView(withId(R.id.tv_departure)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(TextView.class)));
        onView(withId(R.id.tv_dayDeparture)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(TextView.class)));
        onView(withId(R.id.tv_monthYearDeparture)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(TextView.class)));
        onView(withId(R.id.tv_dayNameDeparture)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(TextView.class)));
        onView(withId(R.id.tv_return)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(TextView.class)));
        onView(withId(R.id.tv_day_return)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(TextView.class)));
        onView(withId(R.id.tv_monthYearReturn)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(TextView.class)));
        onView(withId(R.id.tv_dayNameReturn)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(TextView.class)));
        onView(withId(R.id.seperator3)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(View.class)));
        onView(withId(R.id.progress_circular)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(ProgressBar.class)));
        onView(withId(R.id.bt_search)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(Button.class)));
    }

    private void checkResultViews() {
        onView(withId(R.id.rv_flights)).check(matches(notNullValue())).check(matches(CoreMatchers.instanceOf(RecyclerView.class)));
    }

    @Test
    public void getFlights() {
        onView(withId(R.id.bt_search)).perform(click());

        try {
            sleep(1000); /* wait for results page to load */
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recyclerView = mainActivity.findViewById(R.id.rv_flights);
        adapter = (SearchResultRecyclerViewAdapter) recyclerView.getAdapter();
        assert adapter != null;
        int itemCount = adapter.getItemCount();


        /* check result views */
        checkResultViews();

        if (itemCount > 0) {
            /* check first item view */
            checkItemView(1);


            /* check last item view */
            onView(withId(R.id.rv_flights))
                    .inRoot(withDecorView(Matchers.is(mainActivity.getWindow().getDecorView())))
                    .perform(RecyclerViewActions.scrollToPosition(itemCount - 1));
            checkItemView(itemCount - 1);
        }
    }
    private void initViewModels() {
        final ViewModelFactory factory = ViewModelFactory.getInstance(mainActivity.getApplication());
        mViewModel = ViewModelProviders.of(mainActivity, factory).get(SearchViewModel.class);
    }

    private void checkItemView(int position) {
        onView(withId(R.id.rv_flights)).check(matches(withViewAtPosition(position, hasDescendant(ViewMatchers.withId(R.id.iv_airline_logo)))));
        onView(withId(R.id.rv_flights)).check(matches(withViewAtPosition(position, hasDescendant(ViewMatchers.withId(R.id.tv_airline_name)))))
                .check(matches(withViewAtPosition(position, hasDescendant(withText(mViewModel.mFlightList.get(position).getAirlineName())))));

        onView(withId(R.id.rv_flights)).check(matches(withViewAtPosition(position, hasDescendant(ViewMatchers.withId(R.id.tv_out_label)))));

        String[] outStr = mViewModel.mFlightList.get(position).getOutboundFlightsDuration().split(":");
        onView(withId(R.id.rv_flights)).check(matches(withViewAtPosition(position, hasDescendant(ViewMatchers.withId(R.id.tv_outbound_duration)))))
                .check(matches(withViewAtPosition(position, hasDescendant(withText(String.format(mainActivity.getString(R.string.time_format), outStr[0], outStr[1]))))));

        onView(withId(R.id.rv_flights)).check(matches(withViewAtPosition(position, hasDescendant(ViewMatchers.withId(R.id.tv_in_label)))));

        String[] inStr = mViewModel.mFlightList.get(position).getInboundFlightsDuration().split(":");
        onView(withId(R.id.rv_flights)).check(matches(withViewAtPosition(position, hasDescendant(ViewMatchers.withId(R.id.tv_inbound_duration)))))
                .check(matches(withViewAtPosition(position, hasDescendant(withText(String.format(mainActivity.getString(R.string.time_format), inStr[0], inStr[1]))))));

        onView(withId(R.id.rv_flights)).check(matches(withViewAtPosition(position, hasDescendant(ViewMatchers.withId(R.id.tv_total_amount)))))
                .check(matches(withViewAtPosition(position, hasDescendant(withText(String.format(Locale.getDefault(), "$%.0f", mViewModel.mFlightList.get(position).getTotalAmount()))))));
    }

    private Matcher<View> withViewAtPosition(final int position, final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                final RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}
