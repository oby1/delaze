package net.qwuke.unblyopia;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class MenuActivity extends Activity {
    private Toolbar toolbar;
    private boolean musicToggle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.title_name));
        CharSequence styledText = Html.fromHtml(text);
        toolbar.setTitle(styledText);
        setActionBar(toolbar);
        Intent svc=new Intent(this, MusicService.class);
        startService(svc);
        final Button button = (Button) findViewById(R.id.button_tetris);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.GAME"));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.settings:
                settingsMenuItem();
                break;
            case R.id.about:
                aboutMenuItem();
                break;
            case R.id.music:
                musicMenuItem();
                break;
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.music);
        item.setIcon(getMenuItemIconResId());
        return true;
    }

    private int getMenuItemIconResId() {
        if (musicToggle) {
            musicToggle = false;
            return R.drawable.ic_volume_off_white_48dp;
        } else {
            musicToggle = true;
            return R.drawable.ic_volume_up_white_48dp;
        }
    }



    private void settingsMenuItem(){

    }
    private void aboutMenuItem(){

    }

    private void musicMenuItem(){
        invalidateOptionsMenu();
    }
}
