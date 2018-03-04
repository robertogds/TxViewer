package com.robertogds.txviewer.features.transactions.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.robertogds.txviewer.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
/**
 * Espresso UI test
 *
 * Should use a testing API or it will fail as soon as the final balance change
 * */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TxsActivityEspressoTest {

    @Rule
    public ActivityTestRule<TxsActivity> mActivityTestRule = new ActivityTestRule<>(TxsActivity.class);

    @Test
    public void txsActivityEspressoTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView),
                        childAtPosition(
                                withId(R.id.activity_main),
                                2)));
        recyclerView.perform(actionOnItemAtPosition(4, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.final_balance), withText("0.01210512 BTC"),
                        childAtPosition(
                                allOf(withId(R.id.trade_row),
                                        childAtPosition(
                                                withId(R.id.collapsing_toolbar),
                                                0)),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("0.01210512 BTC")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
