package com.geekbrains.myfirsttests

import android.content.Context
import android.os.Build
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.myfirsttests.view.details.DetailsActivity
import com.geekbrains.myfirsttests.view.search.MainActivity
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {

    private var scenario = ActivityScenario.launch(MainActivity::class.java)
    private lateinit var context: Context

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @After
    fun close() {
        scenario.close()
    }

    @Test
    fun activity_AssertEditText() {
        scenario.onActivity {
            val editText = it.findViewById<EditText>(R.id.searchEditText)
            editText.setText("text", TextView.BufferType.EDITABLE)
            assertNotNull(editText.text)
            assertEquals("text", editText.text.toString())
        }
    }

    @Test
    fun activity_EditTextAction() {
        scenario.onActivity {
            val editText = it.findViewById<EditText>(R.id.searchEditText)
            editText.onEditorAction(EditorInfo.IME_ACTION_SEARCH)
            assertEquals(ShadowToast.getTextOfLatestToast(), "Enter search word")
        }
    }
}