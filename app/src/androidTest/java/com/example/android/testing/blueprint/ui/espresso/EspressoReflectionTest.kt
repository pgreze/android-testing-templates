/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.blueprint.ui.espresso

import android.app.Activity
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EspressoReflectionTest {

    @Suppress("MemberVisibilityCanPrivate") // ActivityTestRule needs to be public
    @get:Rule
    var activityRule = object : ActivityTestRule<Activity>(Activity::class.java) {
        override fun getActivityIntent(): Intent {
            return object : Intent(ACTION_VIEW) {
                init {
                    super.setClassName(
                        InstrumentationRegistry.getInstrumentation().targetContext,
                        "com.example.android.testing.blueprint.HelloTestingBlueprintActivity"
                    )
                }

                override fun setClassName(packageName: String, className: String): Intent {
                    return this
                }
            }
        }
    }
    val activity by lazy { activityRule.activity }

    @Test fun verifyItsWorking() {
        onView(withText("Android Testing!")).perform(click())
        onView(withId(activity.resources.getIdentifier("text_view_rocks", "id", activity.packageName)))
            .check(ViewAssertions.matches(withText("Rocks")))
    }
}
