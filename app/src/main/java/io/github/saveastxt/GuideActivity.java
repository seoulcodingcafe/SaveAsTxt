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

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GuideActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		Button buttonShareThisGuideText = findViewById(R.id.buttonShareThisGuideText);
		buttonShareThisGuideText.setOnClickListener(v -> {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_SEND);
			intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.how_to_use));
			intent.setType("text/plain");
			Intent chooseIntent = Intent.createChooser(intent, null);
			startActivity(chooseIntent);
		});

		Button buttonLicense = findViewById(R.id.buttonLicense);
		buttonLicense.setOnClickListener(v -> {
			Intent intent = new Intent(this, LicenseActivity.class);
			startActivity(intent);
		});
	}
}