// This file is part of panoptimage.
//
// panoptimage is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// panoptimage is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with panoptimage.  If not, see <http://www.gnu.org/licenses/>

package org.fereor.panoptimage.activity.about;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.activity.PanoptesActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class AboutActivity extends PanoptesActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_about, menu);
		return true;
	}

	public void doBack(View view) {
		finish();
	}

	@Override
	protected void displayTooltips() {
		showTooltip(R.id.tooltip_back);  
	}

	@Override
	protected void displayTutorials() {
	}

}
