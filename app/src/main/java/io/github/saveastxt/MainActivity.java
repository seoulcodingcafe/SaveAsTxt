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

import java.io.IOException;
import java.io.OutputStream;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	String mText = "";
	ProgressBar mProgress;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				new Thread(() -> {
					try {
						OutputStream os = getContentResolver().openOutputStream(uri);
						os.write(mText.getBytes());
						os.flush();
						os.close();
						mProgress.post(this::finish);
					} catch (IOException | NullPointerException i) {
						mProgress.post(this::finish);
					}
				}).start();
			} else finish();
		} else finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mProgress = findViewById(R.id.progressBar);

		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		if (Intent.ACTION_SEND.equals(action) && type != null) {
			if ("text/plain".equals(type)) {
				mText = intent.getStringExtra(Intent.EXTRA_TEXT);
				startSaveAsProcess();
			} else
				whenNoIntent();
		} else
			whenNoIntent();
	}

	private void startSaveAsProcess() {
		Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
		intent.setType("text/plain");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, 1);
	}

	private void whenNoIntent() {
		Intent intent = new Intent(this, GuideActivity.class);
		startActivity(intent);
		finish();
	}
}