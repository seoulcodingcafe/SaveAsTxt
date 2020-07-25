//    The GNU General Public License does not permit incorporating this program
//    into proprietary programs.
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <https://www.gnu.org/licenses/>.

package io.github.saveastxt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import io.github.saveastxt.tools.ReadFile;

public class LicenseActivity extends AppCompatActivity {

	CollapsingToolbarLayout mToolbarLayout;
	FloatingActionButton mFab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_license);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		mFab = findViewById(R.id.fab);
		mFab.setOnClickListener(view -> finish());
		TextView license = findViewById(R.id.license);

		new Thread(() -> {
			try {
				String licString = new ReadFile().readString(getAssets().open("gpl-3.0.txt"));
				new Handler(Looper.getMainLooper()).post(() -> {
					license.setText(licString);
					license.invalidate();
				});
			} catch (IOException i) {
				i.printStackTrace();
			}
		}).start();
	}
}