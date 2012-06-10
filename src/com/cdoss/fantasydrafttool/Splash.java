package com.cdoss.fantasydrafttool;

import java.io.IOException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Splash extends Activity {

	// private static String DatabaseName = "FFDT.db";
	// private static String DatabasePath =
	// "/data/data/com.cdoss.fantasydrafttool/databases";
	private DatabaseHelper dbHelper = null;
	// private SQLiteDatabase myDatabase = null;
	private Cursor aCursor = null;
	private DraftAdapter anAdapter = null;
	public String filter = null;
	public String draftGroup = null;
	public String rankDelimiter = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);

			// this is our ListView element, obtained by id from our XML layout
			final ListView myListView = (ListView) findViewById(R.id.ListView01);

			// create our database Helper
			dbHelper = new DatabaseHelper(this);
			// we call the create right after initializing the helper, just in
			// case they have never run the app before
			dbHelper.createDataBase();
			// open the database!! Our helper now has a SQLiteDatabase database
			// object
			dbHelper.openDataBase();
			// get our cursor. A cursor is a pointer to a dataset, in this case
			// a set of results from a database query
			draftGroup = "draftgroup>=15";
			rankDelimiter = "Ranks<=250";
			aCursor = dbHelper.getCursor(draftGroup, draftGroup, rankDelimiter);
			// tell android to start managing the cursor,
			// we do this just incase our activity is interrupted or ends, we
			// want the activity
			// to close and deactivate the cursor, as needed
			startManagingCursor(aCursor);
			// create our adapter
			anAdapter = new DraftAdapter(aCursor);
			// set the adapter!!!
			myListView.setAdapter(anAdapter);

			// Defining buttons with their corresponding ids
			Button bTop250 = (Button) findViewById(R.id.bTop250);
			Button bQB = (Button) findViewById(R.id.bQB);
			Button bRB = (Button) findViewById(R.id.bRB);
			Button bWR = (Button) findViewById(R.id.bWR);
			Button bTE = (Button) findViewById(R.id.bTE);
			Button bK = (Button) findViewById(R.id.bK);
			Button bDST = (Button) findViewById(R.id.bDST);
			Button bSleepers = (Button) findViewById(R.id.bSleepers);
			Button bBreakout = (Button) findViewById(R.id.bBoutPlayers);
			Button bMyTeam = (Button) findViewById(R.id.bMyTeam); 

			final TextView title = (TextView) findViewById(R.id.tvTitle);
			// Displays the Top250 by default
			defaultDisplay();

			// Listens for button click on the list view
			myListView
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub

							// Get the item position from the cursor
							Cursor cursor = (Cursor) myListView
									.getItemAtPosition(arg2);
							String rank = cursor.getString(cursor
									.getColumnIndex("Ranks"));
							
							// Calls draftPlayer method passing a string
							// containing player rank
							draftPlayer(rank);
							
							TextView title = (TextView) findViewById(R.id.tvTitle);
							title.setText("Top 250");
							Button top250 = (Button) findViewById(R.id.bTop250);
							deselectButton();
							selectedButton(top250);
						}
					});
			
			View emptyView = findViewById(android.R.id.empty);
			if(emptyView != null){
				myListView.setEmptyView(emptyView);
				}

			// Listens for button click and displays the Top250
			bTop250.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					defaultDisplay();

					cursorProcessing(draftGroup, draftGroup, rankDelimiter);
				}
			});

			// Listens for button click and displays Quarterbacks in the list
			bQB.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					title.setText("QuarterBacks");
					Button localButton = (Button) findViewById(R.id.bQB);
					deselectButton();
					selectedButton(localButton);
					
					String rankDelimiter = "Ranks>0";

					String filter = "position='QB'";

					cursor2Processing(filter, draftGroup, rankDelimiter);
				}
			});

			// Listens for button click and displays Runningbacks in the list
			bRB.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					title.setText("RunningBacks");
					Button localButton = (Button) findViewById(R.id.bRB);
					deselectButton();
					selectedButton(localButton);
					
					String rankDelimiter = "Ranks>0";

					String filter = "position='RB'";

					cursor2Processing(filter, draftGroup, rankDelimiter);
				}
			});

			// Listens for button click and displays Widereceivers in the list
			bWR.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					title.setText("WideReceivers");
					Button localButton = (Button) findViewById(R.id.bWR);
					deselectButton();
					selectedButton(localButton);
					
					String rankDelimiter = "Ranks>0";

					String filter = "position='WR'";

					cursor2Processing(filter, draftGroup, rankDelimiter);
				}
			});

			// Listens for button click and displays Tightends in the list
			bTE.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					title.setText("TightEnds");
					Button localButton = (Button) findViewById(R.id.bTE);
					deselectButton();
					selectedButton(localButton);
					
					String rankDelimiter = "Ranks>0";

					String filter = "position='TE'";

					cursor2Processing(filter, draftGroup, rankDelimiter);
				}
			});

			// Listens for button click and displays Kickers in the list
			bK.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					title.setText("Kickers");
					Button localButton = (Button) findViewById(R.id.bK);
					deselectButton();
					selectedButton(localButton);
					
					String rankDelimiter = "Ranks>0";

					String filter = "position='K'";

					cursor2Processing(filter, draftGroup, rankDelimiter);
				}
			});

			// Listens for button click and displays Defense in the list
			bDST.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					title.setText("Defense");
					Button localButton = (Button) findViewById(R.id.bDST);
					deselectButton();
					selectedButton(localButton);
					
					String rankDelimiter = "Ranks>0";

					String filter = "position='DEF'";

					cursor2Processing(filter, draftGroup, rankDelimiter);
				}
			});

			// Listens for button click and displays user defined sleepers list
			bSleepers.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Add to player to sleepers list
					title.setText("Sleepers");
					Button localButton = (Button) findViewById(R.id.bSleepers);
					deselectButton();
					selectedButton(localButton);
					
					String aDraftGroup = "draftgroup=15";
					cursorProcessing(aDraftGroup, aDraftGroup, rankDelimiter);
				}
			});

			// Listens for button click and displays user defined breakout
			// players list
			bBreakout.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Need to add breakout players as a new record on different
					// team
					// displayData("SELECT * FROM table1 WHERE Position='RB' AND Team >= 17 ORDER BY Rank;");
					title.setText("BreakOut Players");
					Button localButton = (Button) findViewById(R.id.bBoutPlayers);
					deselectButton();
					selectedButton(localButton);
					
					String aDraftGroup = "draftgroup=16";
					cursorProcessing(aDraftGroup, aDraftGroup, rankDelimiter);
				}
			});
			
			//Listens for button click and displays the users team
			bMyTeam.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {

					//TextView title = (TextView) findViewById(R.id.tvTitle);
					title.setText("My Team");
					Button localButton = (Button) findViewById(R.id.bMyTeam);
					deselectButton();
					selectedButton(localButton);

					String draftGroup = "draftgroup=0";
					cursorProcessing(draftGroup, draftGroup, rankDelimiter);
					
				}
			});
			
		} catch (Exception e) {

			// this is the line of code that sends a real error message to the
			// log
			Log.e("ERROR", "ERROR IN CODE: " + e.toString());

			// this is the line that prints out the location in
			// the code where the error occurred.
			e.printStackTrace();
		}
	}

	class DraftAdapter extends CursorAdapter {
		DraftAdapter(Cursor c) {
			super(Splash.this, c);
		}

		// this is a CusorAdapter
		// instead of Using a getView and if(row==null)
		// we use bindView and newView calls
		// we can get away with this because CursorAdapters have
		// a default implementation of getView that calls bindView and newView
		// as needed. This makes our code a bit cleaner, and is the better way
		// to
		// do this.
		@Override
		public void bindView(View row, Context cntxt, Cursor c) {
			DraftHolder holder = (DraftHolder) row.getTag();
			holder.populateFrom(c, dbHelper);
		}

		@Override
		public View newView(Context cntxt, Cursor c, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.list_item, parent, false);
			DraftHolder holder = new DraftHolder(row);
			row.setTag(holder);
			return (row);
		}
	}

	static class DraftHolder {
		private TextView name = null;
		private TextView oldStats = null;
		private TextView newStats = null;

		DraftHolder(View row) {
			name = (TextView) row.findViewById(R.id.playerText);
			oldStats = (TextView) row.findViewById(R.id.oldStatsText);
			newStats = (TextView) row.findViewById(R.id.projectedStatsText);
		}

		void populateFrom(Cursor c, DatabaseHelper d) {
			name.setText(d.getPlayer(c));
			oldStats.setText(d.getOldStats(c));
			newStats.setText(d.getNewStats(c));
		}
	}

	private void cursorProcessing(String posFilter, String draftGroup, String rankDelimiter) {

		ListView lv = (ListView) findViewById(R.id.ListView01);
		View emptyView = findViewById(android.R.id.empty);

		aCursor = dbHelper.getCursor(posFilter, draftGroup, rankDelimiter);
		startManagingCursor(aCursor);
		anAdapter = new DraftAdapter(aCursor);
		lv.setAdapter(anAdapter);
		if(emptyView != null){
		lv.setEmptyView(emptyView);
		}
	}
	
	private void cursor2Processing(String posFilter, String draftGroup, String rankDelimiter) {

		ListView lv = (ListView) findViewById(R.id.ListView01);
		View emptyView = findViewById(android.R.id.empty);

		aCursor = dbHelper.getCursor2(posFilter, draftGroup, rankDelimiter);
		startManagingCursor(aCursor);
		anAdapter = new DraftAdapter(aCursor);
		lv.setAdapter(anAdapter);
		if(emptyView != null){
		lv.setEmptyView(emptyView);
		}
	}

	// Highlights button clicked to know what list is being displayed
	private void deselectButton() {
		
		((Button) findViewById(R.id.bTop250))
				.setBackgroundResource(R.drawable.bkg1);
		((Button) findViewById(R.id.bQB))
				.setBackgroundResource(R.drawable.bkg1);
		((Button) findViewById(R.id.bRB))
				.setBackgroundResource(R.drawable.bkg1);
		((Button) findViewById(R.id.bWR))
				.setBackgroundResource(R.drawable.bkg1);
		((Button) findViewById(R.id.bTE))
				.setBackgroundResource(R.drawable.bkg1);
		((Button) findViewById(R.id.bK))
				.setBackgroundResource(R.drawable.bkg1);
		((Button) findViewById(R.id.bDST))
				.setBackgroundResource(R.drawable.bkg1);
		((Button) findViewById(R.id.bSleepers))
				.setBackgroundResource(R.drawable.bkg1);
		((Button) findViewById(R.id.bBoutPlayers))
				.setBackgroundResource(R.drawable.bkg1);
		((Button) findViewById(R.id.bMyTeam))
			.setBackgroundResource(R.drawable.bkg1);
		((Button) findViewById(R.id.bMyTeam))
			.setBackgroundResource(R.drawable.bkg1);
	}
	
	private void selectedButton(Button aButton) {
		((Button) aButton).setBackgroundResource(R.drawable.bkg2);
	}

	// Displays the Top250 by default
	private void defaultDisplay() {

		Button defaultButton = (Button) findViewById(R.id.bTop250);
		deselectButton();
		selectedButton(defaultButton);
		final TextView title = (TextView) findViewById(R.id.tvTitle);
		title.setText("Top 250");
	}

	// Inflating a menu resource
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	// Handles menu item selection
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.myteam:
			displayMyTeam();
			return true;
		case R.id.otherteams:
			displayOtherTeams();
			return true;
		case R.id.reset:
			reset();
			return true;
		case R.id.help:
			help();
			return true;
		case R.id.about:
			about();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// Replaces current database with original database
	private void reset() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to reset the lists?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								// we call the create right after initializing
								// the helper, just in
								// case they have never run the app before

								try {
									dbHelper.resetDatabase();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								String draftGroup = "draftgroup>=15";
								cursorProcessing(draftGroup, draftGroup, rankDelimiter);
							}

						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();

	}

	// Displays help topics
	private void help() {
//		final CharSequence[] items = { "Red", "Green", "Blue" };
//
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setTitle("Help Topics");
//		builder.setItems(items, new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int item) {
//				Toast.makeText(getApplicationContext(), items[item],
//						Toast.LENGTH_SHORT).show();
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(
						"Fantasy Football Draft Tool v1\n\n"
								+ "This app is very simple to use. After the app loads you will be taken to the default page. " 
								+ "The default page is the top 250 fantasy players for this season.\n" 
								+ "From here you have several options. You could:\n\n" 
								+ "1. Check who you have in your team by clicking the 'My Team' button on the top right of the page.\n" +
										"2. Filter the list by position to be able to look through the players listed only for the " +
										"position you are looking for.\n" +
										"3. Select a player from the list to either draft on your team, draft on another team, or make them " +
										"a sleeper or breakout player.\n\n" +
										"Sleepers and Breakout players\n\n" +
										"By defaul these lists are empty. They are set up to allow you to add players you may have heard about" +
										"so that during your draft you can quickly look them up and not forget them.\n" +
										"If a player has been added to either list by mistake simple select the player from the list" +
										"and select 'undraft player'.\n\n" +
										"Menu\n\n" +
										"By selecting the meny button on your phone several options appear.\n" +
										"1. 'My Team' which allows you to view your players that you have already drafted.\n" +
										"2. 'Other Teams' allows you to select from a list of other teams to see what players " +
										"they have already drafted.\n" +
										"3. 'Reset Lists' ff at any time you would like to reset the app simple hit the menu button on your phone and" +
										"when the option appears select reset list. You must confirm before it actually resets.\n" +
										"4. 'Help' brings up this help list.\n" +
										"5. 'About' tells you about this app.\n\n" +
										"Players\n\n" +
										"Each player is listed by what rank they are for the current season.\n" +
										"When you select the top 250 the players are ranked from 1-250 with one being " +
										"the best choice.\n" +
										"When you select a position the players are ranked from 1 to how ever many players there" +
										"are for that position. Again 1 is the best choice.\n" +
										"After the rank is the players name listed by last name and first initial.\n" +
										"Next is the team the player plays for. The team name is abbreviated.\n" +
										"The next thing is what position the player plays, again this is abbreviated.\n" +
										"Finally comes the players bye week. This is the week the player does not play.\n\n" +
										"Player Stats\n\n" +
										"Each player has both there previous seasons stats and their projected stats.\n" +
										"Each position has different stats displayed. For example, a quarterback will throw" +
										"touchdown passes whereas a wide receiver catches touchdown passes.\n\n" +
										"The breakdown of stats is as follows:\n\n" +
										"QB's: pass completions/passing yards/passing touchdowns\n" +
										"RB's: rushing attempts/rushing yards/rushing touchdowns\n" +
										"WR's: receptions/receiving yards/ receiving touchdowns\n" +
										"TE's: receptions/receiving yards/ receiving touchdowns\n" +
										"K's: field goals made/extra points made\n" +
										"DST's: sacks/interceptions/fumble recoveries/return touchdowns\n\n" +
										"If there are any problems, suggestions to make this app better or you need to contact " +
										"us for any reason we can be reach at:\n\n" +
										"fantasyfootballdrafttool@gmail.com\n\n" +
										"Thanks for downloading our app.\n\n"
								+ "by Chandy Doss and Carlos Goulart\n")
						.setCancelable(false)
						.setNeutralButton("Close",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog, int id) {
										dialog.cancel();
									}
								});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// Displays general information about app
	private void about() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Fantasy Football Draft Tool v1\n\n"
						+ "A fantasy football draft tool used to help players during their fantasy draft. " 
						+ "No longer do you need stacks of paper, magazines and multiple colored pens and markers." 
						+ "This app will be all you need to help you draft the next fantasy football championship team." 
						+ "You'll be hoisting your leagues trophy in no time.\n\n"
						+ "by Chandy Doss and Carlos Goulart\n")
				.setCancelable(false)
				.setNeutralButton("Close",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// Opens the players draft team list menu and drafts the player into
	// corresponding team
	private void draftPlayer(final String rank) {
		final CharSequence[] teamList = new CharSequence[18];
		teamList[0] = "My Team";
		teamList[1] = "Team 1";
		teamList[2] = "Team 2";
		teamList[3] = "Team 3";
		teamList[4] = "Team 4";
		teamList[5] = "Team 5";
		teamList[6] = "Team 6";
		teamList[7] = "Team 7";
		teamList[8] = "Team 8";
		teamList[9] = "Team 9";
		teamList[10] = "Team 10";
		teamList[11] = "Team 11";
		teamList[12] = "Team 12";
		teamList[13] = "Team 13";
		teamList[14] = "Team 14";
		teamList[15] = "Sleepers";
		teamList[16] = "Breakout Players";
		teamList[17] = "Undraft";

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Add Player To A List");
		builder.setItems(teamList, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				// Toast.makeText(getApplicationContext(), teamList[item],
				// Toast.LENGTH_SHORT).show();

				dbHelper.updatePlayerDraftList(rank, item);

				cursorProcessing(draftGroup, draftGroup, rankDelimiter);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// Display your draft team picks
	private void displayMyTeam() {

		TextView title = (TextView) findViewById(R.id.tvTitle);
		title.setText("My Team");

		String draftGroup = "draftgroup=0";
		
		deselectButton();
		cursorProcessing(draftGroup, draftGroup, rankDelimiter);
	}

	// Display the other teams draft picks
	private void displayOtherTeams() {

		final CharSequence[] teamList = new CharSequence[14];
		teamList[0] = "Team 1";
		teamList[1] = "Team 2";
		teamList[2] = "Team 3";
		teamList[3] = "Team 4";
		teamList[4] = "Team 5";
		teamList[5] = "Team 6";
		teamList[6] = "Team 7";
		teamList[7] = "Team 8";
		teamList[8] = "Team 9";
		teamList[9] = "Team 10";
		teamList[10] = "Team 11";
		teamList[11] = "Team 12";
		teamList[12] = "Team 13";
		teamList[13] = "Team 14";

		final TextView title = (TextView) findViewById(R.id.tvTitle);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select Team To Display");
		builder.setItems(teamList, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				// Toast.makeText(getApplicationContext(), teamList[item],
				// Toast.LENGTH_SHORT).show();

				String selectedTeam = (String) teamList[item];
				title.setText(selectedTeam);

				String draftGroup = "draftgroup=";
				int newItem = item + 1;
				String draftTeam = draftGroup + newItem;

				cursorProcessing(draftTeam, draftTeam, rankDelimiter);
				deselectButton();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
}